package com.team3.socialnews.server.persistence;

import java.util.List;

import com.team3.socialnews.shared.model.Comment;

public interface CommentRepository {

	Comment create(Long linkId, String body, String authorNickname, Long parentId);
	List<Comment> getLinkComments(Long linkId);
	Comment get(Long parentId);
	void vote(Long commentId);
	void unvote(Long commentId);
}