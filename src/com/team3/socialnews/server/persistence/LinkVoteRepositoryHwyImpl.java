package com.team3.socialnews.server.persistence;

import java.util.ArrayList;
import java.util.List;

import net.apptao.highway.server.Highway;

import com.google.inject.Inject;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.team3.socialnews.shared.model.Link;
import com.team3.socialnews.shared.model.LinkVote;

public class LinkVoteRepositoryHwyImpl implements LinkVoteRepository {
	private Highway hwy;

	@Inject
	public LinkVoteRepositoryHwyImpl(Highway hwy){
		this.hwy = hwy;
	}

	@Override
	public LinkVote get(Long linkVoteId) {
		return hwy.dao().find(LinkVote.class, linkVoteId);
	}

	@Override
	public LinkVote getActiveVoteByLinkAndUser(Long linkId, String userId) {
		return hwy.dao().query(LinkVote.class)
						.filter("voterId", userId)
						.filter("linkId", linkId)
						.filter("wasUnvoted", false).get();
	}

	@Override
	public List<Boolean> getVoteMaskForLinks(List<Link> links, String userId) {
		Objectify ofy = hwy.dao();
		List<Boolean> voteMask = new ArrayList<Boolean>();
		for(Link link : links){
			Key<LinkVote> keyForLinkVote = ofy.query(LinkVote.class)
											.filter("voterId", userId)
											.filter("wasUnvoted", false)
											.filter("linkId", link.getId()).getKey();
			if(keyForLinkVote != null){
				voteMask.add(true);
			}
			else {
				voteMask.add(false);
			}
		}
		return voteMask;
	}

	@Override
	public void storeVote(LinkVote vote) {
		hwy.dao().put(vote);
	}

	@Override
	public void unvote(LinkVote previousVote) {
		Objectify ofy = hwy.dao(true);
		try {
			// make the previousVote unvoted
			LinkVote linkVote = ofy.find(LinkVote.class, previousVote.getId());
			if(linkVote != null) {
				linkVote.setWasUnvoted(true);
				ofy.put(linkVote);
			}			
			ofy.getTxn().commit();
		} 
		finally {
			if (ofy.getTxn().isActive()) 
		    	ofy.getTxn().rollback();
		}
	}

}
