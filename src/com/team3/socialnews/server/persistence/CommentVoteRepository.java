package com.team3.socialnews.server.persistence;

import java.util.HashMap;
import java.util.List;

import com.team3.socialnews.shared.model.Comment;
import com.team3.socialnews.shared.model.CommentVote;

public interface CommentVoteRepository {
	void storeVote(CommentVote vote);
	CommentVote get(Long commentVoteId);
	CommentVote getActiveVoteByCommentAndUser(Long commentId, String userId);
	void unvote(CommentVote previousVote);
	HashMap<Long, Boolean> getVoteMaskForComments(
			List<Comment> comments,
			String userId);
	
}
