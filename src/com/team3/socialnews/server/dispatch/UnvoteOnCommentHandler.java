package com.team3.socialnews.server.dispatch;

import net.apptao.highway.server.dispatch.HwyHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.ActionException;

import com.google.inject.Injector;
import com.team3.socialnews.server.LoginManager;
import com.team3.socialnews.server.RequiresLogin;
import com.team3.socialnews.server.guice.GuiceFactory;
import com.team3.socialnews.server.persistence.CommentRepository;
import com.team3.socialnews.server.persistence.CommentVoteRepository;
import com.team3.socialnews.shared.dispatch.AlreadyVotedException;
import com.team3.socialnews.shared.dispatch.UnvoteOnComment;
import com.team3.socialnews.shared.dispatch.UnvoteOnCommentResult;
import com.team3.socialnews.shared.model.Comment;
import com.team3.socialnews.shared.model.CommentVote;
import com.team3.socialnews.shared.model.LocalUser;

public class UnvoteOnCommentHandler implements HwyHandler<UnvoteOnComment, UnvoteOnCommentResult> {

	@Override
	@RequiresLogin
	public UnvoteOnCommentResult execute(UnvoteOnComment action, ExecutionContext context)
			throws ActionException {
		
		Injector injector = GuiceFactory.getInjector();
		CommentRepository comments =  injector.getInstance(CommentRepository.class);
		CommentVoteRepository commentVotes =  injector.getInstance(CommentVoteRepository.class);
		LoginManager loginManager = injector.getInstance(LoginManager.class);
		LocalUser user = loginManager.getLocalUser();
		
		// Get the latest info about the comment
		Comment comment = comments.get(action.getCommentId());
		CommentVote previousVote = commentVotes.getActiveVoteByCommentAndUser(comment.getId(), user.getGaeId());
		if(previousVote != null) { // User has voted 
			// Update comment  vote total
			comments.unvote(comment.getId());
			// Store vote with its context as means to keep history
			commentVotes.unvote(previousVote);
			return new UnvoteOnCommentResult();	
		}
		
		throw new AlreadyVotedException("Can't unvote, never voted");
	}

	@Override
	public Class<UnvoteOnComment> getActionType() {
		return UnvoteOnComment.class;
	}

	@Override
	public void rollback(UnvoteOnComment arg0, UnvoteOnCommentResult arg1,
			ExecutionContext arg2) throws ActionException {	
	}

}

