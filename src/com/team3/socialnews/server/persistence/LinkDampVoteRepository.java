package com.team3.socialnews.server.persistence;


import com.team3.socialnews.shared.model.LinkDampVote;

public interface LinkDampVoteRepository {
	public LinkDampVote get(Long linkDampVoteId);
	void storeDampVote(LinkDampVote vote);
	LinkDampVote getBySourceLinkVoteId(Long sourceLinkVoteId);
	void undampen(LinkDampVote dampVote);
}
