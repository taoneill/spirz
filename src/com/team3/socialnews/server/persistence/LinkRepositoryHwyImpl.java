package com.team3.socialnews.server.persistence;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.apptao.highway.server.Highway;

import com.google.appengine.api.datastore.QueryResultIterable;
import com.google.inject.Inject;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.Query;
import com.team3.socialnews.client.Order;
import com.team3.socialnews.server.vote.LinkPredator;
import com.team3.socialnews.shared.model.Link;
import com.team3.socialnews.shared.model.LinkVote;

public class LinkRepositoryHwyImpl implements LinkRepository {

	private Highway hwy;
	private LinkPredator linkPredator;

	@Inject
	public LinkRepositoryHwyImpl(Highway hwy, LinkPredator linkPredator){
		this.hwy = hwy;
		this.linkPredator = linkPredator;
	}

	@Override
	public List<Link> get(Order order, long start, long finish) {
		Query<Link> getLinks = hwy.dao().query(Link.class);
		getLinks.offset((int)start).limit((int)(finish - start));
		
		if(order == Order.CHRONOLOGICAL){
			getLinks.order("createDate");
		}
		else if (order == Order.HOT) {
			getLinks.filter("energy >", 0).order("-energy");
			// assume minimum energy for hot page is 1
		}
		else {
			// Order.NEW
			getLinks.order("-createDate");
		}
	
		QueryResultIterable<Link> result = getLinks.fetch();
		List<Link> links = new ArrayList<Link>();
		for(Link link : result) links.add(link);
		return links;
	}

	@Override
	public Link get(Long linkId) {
		return hwy.dao().find(Link.class, linkId);
	}

	@Override
	public Link submit(String title, String url, String userId,
			String submitterNickname) {
		Link link = new Link(title, url, userId, submitterNickname);
		hwy.dao().put(link);
		return link;
	}
	
	@Override
	public void voteOnLink(Long linkId, int energyContribution) {
		Objectify ofy = hwy.dao(true);
		try
		{
			Link link = ofy.find(Link.class, linkId);
			if(link != null){
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
			    ofy.put(link);
			}	// do nothing for now if link doesn't exist
			// TODO log votes on missing links
			ofy.getTxn().commit();
		} 
		finally
		{
		    if (ofy.getTxn().isActive()) ofy.getTxn().rollback();
		}
	}

	@Override
	public void unvote(Long linkId, int previousVoteEnergyContribution) {
		Objectify ofy = hwy.dao(true);
		try
		{
			Link link = ofy.find(Link.class, linkId);
			if(link != null){
				link.setEnergy(link.getEnergy() - previousVoteEnergyContribution);
				link.setVoteTotal(link.getVoteTotal() - 1);
				if(link.getEnergy() > 0) {	// assume minimum energy for hot page is 1
					link.setHot(true);
				}
				else {
					link.setHot(false);
				}
				ofy.put(link);
			}	// do nothing for now if link doesn't exist
			// TODO log votes on missing links
			ofy.getTxn().commit();
		} 
		finally
		{
		    if (ofy.getTxn().isActive()) ofy.getTxn().rollback();
		}

	}

	@Override
	public Link getDampVoteCandidate(LinkVote sourceLinkVote) {
		Objectify ofy = hwy.dao();
		Date sourceLinkCreateDate = sourceLinkVote.getLinkCreateDate();
		// - a keys only query (refer to http://code.google.com/appengine/docs/java/datastore/queriesandindexes.html#Queries_on_Keys)
		// - getter all links older than self, with a maximum range according to the linkPredator
		Iterable<Key<Link>> olderLinkKeys = ofy.query(Link.class)
								.filter("createDate <", sourceLinkCreateDate)
								.filter("hot", true)
								.order("createDate")
								.limit(linkPredator.getTerritory())
								.fetchKeys();
		Link candidate = null;
		int candidateIndex = 0;
		List<Key<Link>> keys = new ArrayList<Key<Link>>();
		for(Key<Link> key : olderLinkKeys) keys.add(key);
		int noOfFetchedOlderLinks = keys.size();
		if(noOfFetchedOlderLinks > 0){
			candidateIndex = linkPredator.getPreyIndex(noOfFetchedOlderLinks);
			candidate = ofy.find(keys.get(candidateIndex));
		}
		return candidate;	// should only return null if oldest
	}

	
	@Override
	public void dampenLink(Long linkId, int energyContribution) {
		Objectify ofy = hwy.dao(true);
		try
		{
			Link link = ofy.find(Link.class, linkId);
			if(link != null){
				link.setEnergy(link.getEnergy() + energyContribution);
				if(link.getEnergy() > 0) {	// assume minimum energy for hot page is 1
					link.setHot(true);
				}
				else {
					link.setHot(false);
				}
				ofy.put(link);
			}	// do nothing for now if link doesn't exist
			// TODO log votes on missing links
			ofy.getTxn().commit();
		} 
		finally
		{
		    if (ofy.getTxn().isActive()) ofy.getTxn().rollback();
		}		
	}

	@Override
	public void undampen(Long dampedLinkId, int dampVoteEnergy) {
		Objectify ofy = hwy.dao(true);
		try
		{
			Link link = ofy.find(Link.class, dampedLinkId);
			if(link != null){
				link.setEnergy(link.getEnergy() - dampVoteEnergy);
				if(link.getEnergy() > 0) {	// assume minimum energy for hot page is 1
					link.setHot(true);
				}
				else {
					link.setHot(false);
				}
				ofy.put(link);
			}	// do nothing for now if link doesn't exist
			// TODO log votes on missing links
			ofy.getTxn().commit();
		} 
		finally
		{
		    if (ofy.getTxn().isActive()) ofy.getTxn().rollback();
		}
	}

	@Override
	public void newComment(Long linkId) {
		Objectify ofy = hwy.dao(true);
		try
		{
			Link link = ofy.find(Link.class, linkId);
			if(link != null){
				Integer oldTotal = link.getCommentTotal();
				if(oldTotal == null){
					link.setCommentTotal(1);
				} else {
					link.setCommentTotal(oldTotal + 1);
				}
				ofy.put(link);
			}	// do nothing for now if link doesn't exist
			// TODO log votes on missing links
			ofy.getTxn().commit();
		} 
		finally
		{
		    if (ofy.getTxn().isActive()) ofy.getTxn().rollback();
		}
	}
}
