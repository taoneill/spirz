package com.team3.socialnews.server.guice;

import net.apptao.highway.server.guice.HighwayModule;

import com.google.inject.Singleton;
import com.team3.socialnews.server.dispatch.CreateCommentHandler;
import com.team3.socialnews.server.dispatch.GetLinkCommentsHandler;
import com.team3.socialnews.server.dispatch.UnvoteOnCommentHandler;
import com.team3.socialnews.server.dispatch.VoteOnCommentHandler;
import com.team3.socialnews.server.model.CommentJDO;
import com.team3.socialnews.server.persistence.CommentRepository;
import com.team3.socialnews.server.persistence.CommentRepositoryHwyImpl;
import com.team3.socialnews.server.persistence.CommentVoteRepository;
import com.team3.socialnews.server.persistence.CommentVoteRepositoryHwyImpl;
import com.team3.socialnews.shared.dispatch.CreateComment;
import com.team3.socialnews.shared.dispatch.GetLinkComments;
import com.team3.socialnews.shared.dispatch.UnvoteOnComment;
import com.team3.socialnews.shared.dispatch.VoteOnComment;
import com.team3.socialnews.shared.model.CommentVote;

public class CommentsModule extends HighwayModule {

	@Override
	protected void configureModule() {
		register(CommentJDO.class);
		register(CommentVote.class);
		
		this.bindCommand(GetLinkComments.class, GetLinkCommentsHandler.class);
		this.bindCommand(CreateComment.class, CreateCommentHandler.class);
		this.bindCommand(VoteOnComment.class, VoteOnCommentHandler.class);
		this.bindCommand(UnvoteOnComment.class, UnvoteOnCommentHandler.class);
		  
    	bind(CommentRepository.class).to(CommentRepositoryHwyImpl.class).in(Singleton.class);
    	bind(CommentVoteRepository.class).to(CommentVoteRepositoryHwyImpl.class).in(Singleton.class);
	}

}
