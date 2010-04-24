package com.team3.socialnews.server.persistence;

import java.util.List;

import com.team3.socialnews.client.Order;
import com.team3.socialnews.shared.model.Link;
import com.team3.socialnews.shared.model.LinkVote;

public interface LinkRepository{
	
	public List<Link> get(Order order, long start, long finish);
	public Link submit(String title, String url, String userId, String submitterNickname);
	public Link get(Long linkId);
	public void voteOnLink(Long linkId, int energyContribution);
	public void unvote(Long linkId, int energyContribution);
	public Link getDampVoteCandidate(LinkVote sourceLinkVote);
	public void dampenLink(Long linkId, int energyContribution);
	public void undampen(Long dampedLinkId, int dampVoteEnergy);
	public void newComment(Long linkId);
}