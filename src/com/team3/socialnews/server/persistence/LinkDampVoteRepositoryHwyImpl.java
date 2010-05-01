package com.team3.socialnews.server.persistence;

import net.apptao.highway.server.Highway;

import com.google.inject.Inject;
import com.googlecode.objectify.Objectify;
import com.team3.socialnews.shared.model.LinkDampVote;

public class LinkDampVoteRepositoryHwyImpl extends AbstractRepository<LinkDampVote> implements
		LinkDampVoteRepository {
	
	private Highway hwy;

	@Inject
	public LinkDampVoteRepositoryHwyImpl(Highway hwy) {
		this.hwy = hwy;
	}

	@Override
	public void storeDampVote(LinkDampVote vote) {
		hwy.dao().put(vote);
	}
	
	@Override
	public LinkDampVote getBySourceLinkVoteId(Long sourceLinkVoteId) {
		return hwy.dao().query(LinkDampVote.class).filter("sourceLinkVoteId", sourceLinkVoteId).get();
	}

	@Override
	public void undampen(LinkDampVote dampVote) {
		Objectify ofy = hwy.dao(true);
		try {
			// make the dampvote unvoted
			LinkDampVote oldDampVote = ofy.find(LinkDampVote.class, dampVote.getId());
			if(oldDampVote != null) {
				oldDampVote.setWasUnvoted(true);
				ofy.put(oldDampVote);
			}
			
			ofy.getTxn().commit();
		} 
		finally {
			if (ofy.getTxn().isActive())
				ofy.getTxn().rollback();
		}
	}
	
	@Override
	public LinkDampVote get(Long linkDampVoteId) {
		return hwy.dao().find(LinkDampVote.class, linkDampVoteId);
	}
}
