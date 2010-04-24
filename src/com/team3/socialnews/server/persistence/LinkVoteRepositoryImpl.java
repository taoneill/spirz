package com.team3.socialnews.server.persistence;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.team3.socialnews.shared.model.Link;
import com.team3.socialnews.shared.model.LinkVote;

public class LinkVoteRepositoryImpl extends AbstractRepository<LinkVote> implements
		LinkVoteRepository {

	@Override
	public void storeVote(LinkVote vote) {
		PersistenceManager pm = getPersistenceManager();
		try {
			pm.makePersistent(vote);
		}
		finally {
			pm.close();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Boolean> getVoteMaskForLinks(List<Link> links, String userId) {
		PersistenceManager pm = getPersistenceManager();
		try {
			List<Boolean> voteMask = new ArrayList<Boolean>();
			Query q = pm.newQuery(LinkVote.class);	// TODO: possible optimization: select only the id (the key)
			q.setFilter("voterId == thisUserId && linkId == thisLinkId && wasUnvoted == false"); //important to filter out votes that were unvoted
			q.declareParameters("String thisUserId, Long thisLinkId");
			for(Link link : links){
				Collection<LinkVote> collection = (Collection<LinkVote>)q.execute(userId, link.getId());
				if(collection.isEmpty()){
					voteMask.add(false);
				}
				else {
					voteMask.add(true);
				}
			}
			return voteMask;
		}
		finally {
			pm.close();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * Returns only active (not unvoted) votes	
	 */
	public LinkVote getActiveVoteByLinkAndUser(Long linkId, String userId) {
		PersistenceManager pm = getPersistenceManager();
		try {
			Query q = pm.newQuery(LinkVote.class);
			q.setFilter("voterId == thisUserId && linkId == thisLinkId && wasUnvoted == false");
			q.declareParameters("String thisUserId, Long thisLinkId");
			Collection<LinkVote> collection = (Collection<LinkVote>)q.execute(userId, linkId);
			if(!collection.isEmpty()) return collection.iterator().next();
			return null;
		}
		finally {
			pm.close();
		}
	}

	@Override
	public void unvote(LinkVote previousVote) {
		PersistenceManager pm = getPersistenceManager();
		try {
			pm.currentTransaction().begin();
			
			// make the previousVote unvoted
			LinkVote linkVote = get(previousVote.getId());
			linkVote.setWasUnvoted(true);
			pm.makePersistent(linkVote);
			
			pm.currentTransaction().commit();
		} 
		finally {
			pm.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public LinkVote get(Long linkVoteId) {
		PersistenceManager pm = getPersistenceManager();
		try {
			Query q = pm.newQuery(LinkVote.class, "id == "+linkVoteId);
			return ((Collection<LinkVote>)q.execute()).iterator().next();
		}
		finally {
			pm.close();
		}
	}

}
