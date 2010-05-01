package com.team3.socialnews.server.persistence;

import java.util.ArrayList;
import java.util.List;

import net.apptao.highway.server.Highway;

import com.google.inject.Inject;
import com.googlecode.objectify.Objectify;
import com.team3.socialnews.client.model.CommentClient;
import com.team3.socialnews.server.model.CommentJDO;
import com.team3.socialnews.shared.model.Comment;

public class CommentRepositoryHwyImpl extends AbstractRepository<CommentClient> implements CommentRepository {
	
	private Highway hwy;

	@Inject
	public CommentRepositoryHwyImpl(Highway hwy) {
		this.hwy = hwy;
	}
	
	/**
	 * Converts a comment for the DB to a comment
	 * that can be sent over RPC.
	 * @param comment
	 * @return
	 */
	private CommentClient toClient(CommentJDO comment) {
		CommentClient commentClient = new CommentClient(
					comment.getId(),
					comment.getAuthorNickname(), 
					comment.getBody(), 
					comment.getLinkId(), 
					comment.getParentId());
		commentClient.setVoteTotal(comment.getVoteTotal());
		commentClient.setCommentDate(comment.getCommentDate());
		return commentClient;
	}
	
	@Override
	public Comment create(Long linkId, String body, String authorNickname, Long parentId) {
		CommentJDO comment = new CommentJDO(authorNickname, body, linkId, parentId);
		hwy.dao().put(comment);
		return toClient(comment);
	}
	
	@Override
	public List<Comment> getLinkComments(Long linkId) {
		List<Comment> commentsClient = new ArrayList<Comment>();
		Iterable<CommentJDO> commentsJDO = hwy.dao().query(CommentJDO.class)
													.filter("linkId", linkId).fetch();
		for (CommentJDO commentJDO : commentsJDO)
			commentsClient.add(toClient(commentJDO));
		return commentsClient;
	}

	@Override
	public Comment get(Long id) {
			CommentJDO cjdo = hwy.dao().find(CommentJDO.class, id);
			if(cjdo != null)
				return toClient(cjdo);
			return null;
	}

	@Override
	public void vote(Long commentId) {
		Objectify ofy = hwy.dao(true);
		try {
			CommentJDO comment = ofy.find(CommentJDO.class, commentId);
			comment.setVoteTotal(comment.getVoteTotal() + 1);
			ofy.put(comment);
		} finally {
			if(ofy.getTxn().isActive())
				ofy.getTxn().rollback();
		}	
	}
	
	@Override
	public void unvote(Long commentId) {
		Objectify ofy = hwy.dao(true);
		try {
			CommentJDO comment = ofy.find(CommentJDO.class, commentId);
			comment.setVoteTotal(comment.getVoteTotal() - 1);
			ofy.put(comment);
		} finally {
			if(ofy.getTxn().isActive())
				ofy.getTxn().rollback();
		}
	}
}
