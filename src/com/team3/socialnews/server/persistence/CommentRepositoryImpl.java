package com.team3.socialnews.server.persistence;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.team3.socialnews.client.model.CommentClient;
import com.team3.socialnews.server.model.CommentJDO;
import com.team3.socialnews.shared.model.Comment;

public class CommentRepositoryImpl extends AbstractRepository<CommentClient> implements CommentRepository {
	
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
		PersistenceManager pm = getPersistenceManager();
		try {
			CommentJDO comment = new CommentJDO(authorNickname, body, linkId, parentId);
			pm.makePersistent(comment);
			return toClient(comment);
		} finally {
			pm.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Comment> getLinkComments(Long linkId) {
		PersistenceManager pm = getPersistenceManager();
		try {
			Query q = pm.newQuery(CommentJDO.class, "linkId == "+linkId);	
			List<Comment> commentsClient = new ArrayList<Comment>();
			Collection<CommentJDO> commentsJDO = (Collection<CommentJDO>) q.execute();
			for (CommentJDO commentJDO : commentsJDO)
				commentsClient.add(toClient(commentJDO));
			return commentsClient;
		} finally {
			pm.close();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Comment get(Long id) {
		PersistenceManager pm = getPersistenceManager();
		try {
			Query q = pm.newQuery(CommentJDO.class, "id == "+id);	
			return toClient(((Collection<CommentJDO>) q.execute()).iterator().next());
		} finally {
			pm.close();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void vote(Long commentId) {
		PersistenceManager pm = getPersistenceManager();
		try {
			Query q = pm.newQuery(CommentJDO.class, "id == "+commentId);	
			CommentJDO comment = ((Collection<CommentJDO>) q.execute()).iterator().next();
			comment.setVoteTotal(comment.getVoteTotal() + 1);
			pm.makePersistent(comment);
		} finally {
			pm.close();
		}	
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void unvote(Long commentId) {
		PersistenceManager pm = getPersistenceManager();
		try {
			Query q = pm.newQuery(CommentJDO.class, "id == "+commentId);	
			CommentJDO comment = ((Collection<CommentJDO>) q.execute()).iterator().next();
			comment.setVoteTotal(comment.getVoteTotal() - 1);
			pm.makePersistent(comment);
		} finally {
			pm.close();
		}	
	}
}
