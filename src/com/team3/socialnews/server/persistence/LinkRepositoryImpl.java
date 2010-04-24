package com.team3.socialnews.server.persistence;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import net.sf.jsr107cache.Cache;

import com.google.inject.Inject;
import com.team3.socialnews.client.Order;
import com.team3.socialnews.server.vote.LinkPredator;
import com.team3.socialnews.shared.model.Link;
import com.team3.socialnews.shared.model.LinkVote;

public class LinkRepositoryImpl extends AbstractRepository<Link> implements LinkRepository {
	
	private SpirzCache cache;
	private LinkPredator linkPredator;
	
	@Inject
	public LinkRepositoryImpl(SpirzCache cache, LinkPredator linkPredator) {
		this.cache = cache;
		this.linkPredator = linkPredator;
	}
	
	public Cache getCache(){
		return cache.getInstance();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Link get(Long linkId) {
		// try cache first
		if(getCache() != null && getCache().containsKey(linkId)){
			try{
				Link link = (Link) getCache().get(linkId);
				return link;
			}
			catch(Throwable e){
				System.err.print("Failed at cache get " + linkId + ". Thrown: " + e.toString());
			}
		}
		
		PersistenceManager pm = getPersistenceManager();
		try {
			Query q = pm.newQuery(Link.class, "id == "+linkId);
			Collection<Link> results = (Collection<Link>)q.execute();
			Link link = results.iterator().next();
			if(link != null){	
				// cache it for next time
				// assumes any changes made to the link will be reflected in the cached copy
				if(getCache() != null) getCache().put(link.getId(), link);
			}
			return link;
		}
		catch(Throwable t){
			System.err.print("No links. Thrown: " + t.toString());
		}
		finally {
			pm.close();
		}
		return null;
	}

	public Link submit(String title, String url, String userId, String submitterNickname) {
		PersistenceManager pm = getPersistenceManager();
		try {
			Link link = new Link(url, title, userId, submitterNickname);
			pm.makePersistent(link);
			return link;
		}
		finally {
			pm.close();
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Link> get(Order order, long start, long finish) {
		PersistenceManager pm = getPersistenceManager();
		try {
			Query getLinkIds = null;
			List<Long> linkIds = new ArrayList<Long>();
			if(order == Order.CHRONOLOGICAL){
				getLinkIds = pm.newQuery("select id from " + Link.class.getName() + " " +
										"order by createDate asc");
			}
			else if (order == Order.HOT) {
				getLinkIds = pm.newQuery("select id from " + Link.class.getName() + " " +
										"where energy > 0 " +	// assume minimum energy for hot page is 1
										"order by energy desc");
			}
			else {
				// Order.NEW
				getLinkIds = pm.newQuery("select id from " + Link.class.getName() + " " +
										"order by createDate desc");
			}
			getLinkIds.setRange(start, finish);
			linkIds = (List<Long>)getLinkIds.execute();
		
			List<Link> links = new ArrayList<Link>();
			for(Long linkId : linkIds)
				links.add(this.get(linkId));
			return links;
		}
		finally {
			pm.close();
		}
		
	}

	@Override
	/**
	 * Updates the link by adding the energy contribution
	 * Increments the number of votes if energy contribution is positive
	 * 
	 */
	public void voteOnLink(Long linkId, int energyContribution) {
		PersistenceManager pm = getPersistenceManager();
		try {
			pm.currentTransaction().begin();
			// update the link energy and vote values
			Link link = get(linkId);
			link.setEnergy(link.getEnergy() + energyContribution);
			if(energyContribution > 0) {
				// not a damp vote
				link.setVoteTotal(link.getVoteTotal() + 1);
			}
			if(link.getEnergy() > 0) {	// assume minimum energy for hot page is 1
				link.setHot(true);
			}
			else {
				link.setHot(false);
			}				
			pm.makePersistent(link);
			pm.currentTransaction().commit();
			if(getCache() != null) getCache().put(link.getId(), link);
		} 
		finally {
			pm.close();
		}
	}
	
	@Override
	public void unvote(Long linkId, int previousVoteEnergyContribution) {
		PersistenceManager pm = getPersistenceManager();
		try {
			pm.currentTransaction().begin();
			// update the link energy and vote values
			Link link = get(linkId);
			link.setEnergy(link.getEnergy() - previousVoteEnergyContribution);
			link.setVoteTotal(link.getVoteTotal() - 1);
			if(link.getEnergy() > 0) {	// assume minimum energy for hot page is 1
				link.setHot(true);
			}
			else {
				link.setHot(false);
			}
			pm.makePersistent(link);
			pm.currentTransaction().commit();
			if(getCache() != null) getCache().put(link.getId(), link);
		} 
		finally {
			pm.close();
		}
	}
	
	@Override
	/**
	 * Updates the link by adding the energy contribution 
	 * (i.e. the value of energyContribution should be negative, 
	 * otherwise energy is being added without incrementing the 
	 * number of votes)
	 */
	public void dampenLink(Long linkId, int energyContribution) {
		PersistenceManager pm = getPersistenceManager();
		try {
			pm.currentTransaction().begin();
			// update the link energy (assumes negative value)
			Link link = get(linkId);
			link.setEnergy(link.getEnergy() + energyContribution);
			if(link.getEnergy() > 0) {	// assume minimum energy for hot page is 1
				link.setHot(true);
			}
			else {
				link.setHot(false);
			}
			pm.makePersistent(link);
			pm.currentTransaction().commit();
			if(getCache() != null) getCache().put(link.getId(), link);
		} 
		finally {
			pm.close();
		}
	}
	

	@Override
	/**
	 * Restores the energy back to the previously damped link
	 * (assumes a negative value for dampVoteEnergy)
	 */
	public void undampen(Long dampedLinkId, int dampVoteEnergy) {
		PersistenceManager pm = getPersistenceManager();
		try {
			pm.currentTransaction().begin();
			// restore the link energy
			Link link = get(dampedLinkId);
			link.setEnergy(link.getEnergy() - dampVoteEnergy);
			if(link.getEnergy() > 0) {	// assume minimum energy for hot page is 1
				link.setHot(true);
			}
			else {
				link.setHot(false);
			}
			pm.makePersistent(link);
			pm.currentTransaction().commit();
			if(getCache() != null) getCache().put(link.getId(), link);
		} 
		finally {
			pm.close();
		}
	}
	

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * Gets a link at random that has higher energy and that is older 
	 * than a linkvote's source link.
	 */
	public Link getDampVoteCandidate(LinkVote sourceLinkVote) {
		PersistenceManager pm = getPersistenceManager();
		try {
			Date sourceLinkCreateDate = sourceLinkVote.getLinkCreateDate();
			// - a keys only query (refer to http://code.google.com/appengine/docs/java/datastore/queriesandindexes.html#Queries_on_Keys)
			// - getter all links older than self, with a maximum range according to the linkPredator
			Query getOlderAliveLinks = pm.newQuery("select id from " + Link.class.getName() + " " +
								"where createDate < sourceLinkCreateDate " +
								"&& hot == true " +							// need this flag on the link to help us narrow it down
																			// hot = true iff above energy minimum
								"parameters Date sourceLinkCreateDate " +
								"order by createDate asc");
			getOlderAliveLinks.declareImports("import java.util.Date;");
			getOlderAliveLinks.setRange(0, linkPredator.getTerritory());
			List<Long> olderLinkIds = (List<Long>) getOlderAliveLinks.execute(sourceLinkCreateDate);
				
			Link candidate = null;
			int candidateIndex = 0;
			if(olderLinkIds.size() > 0){
				candidateIndex = linkPredator.getPreyIndex(olderLinkIds.size());
				candidate = get(olderLinkIds.get(candidateIndex));
			}			
			
			return candidate;	// should only return null if oldest
		}
		finally {
			pm.close();
		}
	}

	@Override
	public void newComment(Long linkId) {
		PersistenceManager pm = getPersistenceManager();
		try {
			pm.currentTransaction().begin();
			Link link = get(linkId);
			Integer oldTotal = link.getCommentTotal();
			if(oldTotal == null){
				link.setCommentTotal(1);
			} else {
				link.setCommentTotal(oldTotal + 1);
			}
			pm.makePersistent(link);
			pm.currentTransaction().commit();
			if(getCache() != null) getCache().put(link.getId(), link);
		}
		catch(Throwable e){
			System.err.print("Couldn't save comment total increase for " + linkId);
		}
		finally {
			pm.close();
		}
	}

}