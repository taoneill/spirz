package com.team3.socialnews.server.persistence;

import java.util.List;

import net.apptao.highway.server.Highway;

import com.google.inject.Inject;
import com.team3.socialnews.client.Order;
import com.team3.socialnews.shared.model.Link;
import com.team3.socialnews.shared.model.LinkVote;

public class LinkDao implements LinkRepository {

	private Highway hwy;

	@Inject
	public LinkDao(Highway hwy){
		this.hwy = hwy;
	}

	@Override
	public List<Link> get(Order order, long start, long finish) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Link get(Long linkId) {
		return hwy.dao().find(Link.class, linkId);
	}

	@Override
	public Link submit(String title, String url, String userId,
			String submitterNickname) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void voteOnLink(Long linkId, int energyContribution) {
		// TODO Auto-generated method stub

	}

	@Override
	public void unvote(Long linkId, int energyContribution) {
		// TODO Auto-generated method stub

	}

	@Override
	public Link getDampVoteCandidate(LinkVote sourceLinkVote) {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public void dampenLink(Long linkId, int energyContribution) {
				
	}

	@Override
	public void undampen(Long dampedLinkId, int dampVoteEnergy) {
		// TODO Auto-generated method stub

	}

	@Override
	public void newComment(Long linkId) {
		// TODO Auto-generated method stub

	}
}
