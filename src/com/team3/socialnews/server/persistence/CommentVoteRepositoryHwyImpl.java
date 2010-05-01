package com.team3.socialnews.server.persistence;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import net.apptao.highway.server.Highway;

import com.google.inject.Inject;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.team3.socialnews.shared.model.Comment;
import com.team3.socialnews.shared.model.CommentVote;

public class CommentVoteRepositoryHwyImpl extends AbstractRepository<CommentVote> implements
		CommentVoteRepository {

	private Highway hwy;

	@Inject
	public CommentVoteRepositoryHwyImpl(Highway hwy) {
		this.hwy = hwy;
	}

	@Override
	public void storeVote(CommentVote vote){
		hwy.dao().put(vote);
	}

	@Override
	public HashMap<Long, Boolean> getVoteMaskForComments(List<Comment> comments, String userId) {
		HashMap<Long, Boolean> voteMask = new HashMap<Long, Boolean>();
		Objectify ofy = hwy.dao();
		for(Comment comment : comments){
			Key<CommentVote> anyUnvotedVote = ofy.query(CommentVote.class)
												.filter("voterId", userId)
												.filter("commentId", comment.getId())
												.filter("wasUnvoted", false).getKey();
			if(anyUnvotedVote == null){
				voteMask.put(comment.getId(), false);
			}
			else {
				voteMask.put(comment.getId(), true);
			}
		}
		return voteMask;
	}

	@Override
	/**
	 * Returns only active (not unvoted) votes	
	 */
	public CommentVote getActiveVoteByCommentAndUser(Long commentId, String userId) {
		return hwy.dao().query(CommentVote.class)
						.filter("voterId", userId)
						.filter("commentId", commentId)
						.filter("wasUnvoted", false).get();
	}

	@Override
	public void unvote(CommentVote previousVote) {
		Objectify ofy = hwy.dao(true);
		try {
			// make the previousVote unvoted
			CommentVote linkVote = ofy.find(CommentVote.class, previousVote.getId());
			if(linkVote != null){
				linkVote.setWasUnvoted(true);
				ofy.put(linkVote);
			}
			ofy.getTxn().commit();
		} 
		finally {
			if(ofy.getTxn().isActive())
				ofy.getTxn().rollback();
		}
	}
	
	@Override
	public CommentVote get(Long commentVoteId) {
		return hwy.dao().find(CommentVote.class, commentVoteId);
	}

	

}
