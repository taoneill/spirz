package com.team3.socialnews.server.persistence;

import java.util.List;

import com.team3.socialnews.shared.model.Link;
import com.team3.socialnews.shared.model.LinkVote;

public interface LinkVoteRepository {
	void storeVote(LinkVote vote);
	LinkVote get(Long linkVoteId);
	List<Boolean> getVoteMaskForLinks(List<Link> links, String userId);
	LinkVote getActiveVoteByLinkAndUser(Long linkId, String userId);
	void unvote(LinkVote previousVote);
	
}
