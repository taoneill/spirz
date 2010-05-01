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
import com.team3.socialnews.shared.dispatch.VoteOnComment;
import com.team3.socialnews.shared.dispatch.VoteOnCommentResult;
import com.team3.socialnews.shared.model.Comment;
import com.team3.socialnews.shared.model.CommentVote;
import com.team3.socialnews.shared.model.LocalUser;

public class VoteOnCommentHandler implements HwyHandler<VoteOnComment, VoteOnCommentResult> {

	@Override
	@RequiresLogin
	public VoteOnCommentResult execute(VoteOnComment action, ExecutionContext context)
			throws ActionException {
		
		Injector injector = GuiceFactory.getInjector();
		CommentRepository comments =  injector.getInstance(CommentRepository.class);
		CommentVoteRepository commentVotes =  injector.getInstance(CommentVoteRepository.class);
		LoginManager loginManager = injector.getInstance(LoginManager.class);
		LocalUser user = loginManager.getLocalUser();
		
		// Get the latest info about the comment
		Comment comment = comments.get(action.getCommentId());
		CommentVote previousVote = commentVotes.getActiveVoteByCommentAndUser(comment.getId(), user.getGaeId());
		if(previousVote == null || user.getIsAdmin()) { // User has never voted (admins can vote many times)
			CommentVote vote = new CommentVote(user.getGaeId(), comment);
			// Update comment  vote total
			comments.vote(comment.getId());
			// Store vote with its context as means to keep history
			commentVotes.storeVote(vote);
			return new VoteOnCommentResult();	
		}
		
		throw new AlreadyVotedException("Already voted");
	}

	@Override
	public Class<VoteOnComment> getActionType() {
		return VoteOnComment.class;
	}

	@Override
	public void rollback(VoteOnComment arg0, VoteOnCommentResult arg1,
			ExecutionContext arg2) throws ActionException {	
	}

}
