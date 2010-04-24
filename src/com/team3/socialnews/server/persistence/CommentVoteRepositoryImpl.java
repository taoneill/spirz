package com.team3.socialnews.server.persistence;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.team3.socialnews.client.model.CommentClient;
import com.team3.socialnews.shared.model.Comment;
import com.team3.socialnews.shared.model.CommentVote;

public class CommentVoteRepositoryImpl extends AbstractRepository<CommentVote> implements
		CommentVoteRepository {

	@Override
	public void storeVote(CommentVote vote){
		PersistenceManager pm = getPersistenceManager();
		try {
			pm.makePersistent(vote);
		}
		finally {
			pm.close();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public HashMap<Long, Boolean> getVoteMaskForComments(
			List<Comment> comments, String userId) {
		PersistenceManager pm = getPersistenceManager();
		try {
			HashMap<Long, Boolean> voteMask = new HashMap<Long, Boolean>();
			Query q = pm.newQuery(CommentVote.class);	// TODO: possible optimization: select only the id (the key)
			q.setFilter("voterId == thisUserId && commentId == thisCommentId && wasUnvoted == false"); //important to filter out votes that were unvoted
			q.declareParameters("String thisUserId, Long thisCommentId");
			for(Comment comment : comments){
				Collection<CommentVote> collection = (Collection<CommentVote>)q.execute(userId, comment.getId());
				if(collection.isEmpty()){
					voteMask.put(comment.getId(), false);
				}
				else {
					voteMask.put(comment.getId(), true);
				}
			}
			return voteMask;
		}
		finally {
			pm.close();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * Returns only active (not unvoted) votes	
	 */
	public CommentVote getActiveVoteByCommentAndUser(Long commentId, String userId) {
		PersistenceManager pm = getPersistenceManager();
		try {
			Query q = pm.newQuery(CommentVote.class);
			q.setFilter("voterId == thisUserId && commentId == thisCommentId && wasUnvoted == false");
			q.declareParameters("String thisUserId, Long thisCommentId");
			Collection<CommentVote> collection = (Collection<CommentVote>)q.execute(userId, commentId);
			if(!collection.isEmpty()) return collection.iterator().next();
			return null;
		}
		finally {
			pm.close();
		}
	}

	@Override
	public void unvote(CommentVote previousVote) {
		PersistenceManager pm = getPersistenceManager();
		try {
			pm.currentTransaction().begin();
			
			// make the previousVote unvoted
			CommentVote linkVote = get(previousVote.getId());
			linkVote.setWasUnvoted(true);
			pm.makePersistent(linkVote);
			
			pm.currentTransaction().commit();
		} 
		finally {
			pm.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public CommentVote get(Long commentVoteId) {
		PersistenceManager pm = getPersistenceManager();
		try {
			Query q = pm.newQuery(CommentVote.class, "id == "+commentVoteId);
			return ((Collection<CommentVote>)q.execute()).iterator().next();
		}
		finally {
			pm.close();
		}
	}

	

}
