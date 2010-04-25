package com.team3.socialnews.server.guice;

import com.google.inject.Singleton;
import com.team3.socialnews.server.dispatch.CreateCommentHandler;
import com.team3.socialnews.server.dispatch.UnvoteOnCommentHandler;
import com.team3.socialnews.server.dispatch.VoteOnCommentHandler;
import com.team3.socialnews.server.persistence.CommentRepository;
import com.team3.socialnews.server.persistence.CommentRepositoryImpl;
import com.team3.socialnews.server.persistence.CommentVoteRepository;
import com.team3.socialnews.server.persistence.CommentVoteRepositoryImpl;
import com.team3.socialnews.shared.dispatch.CreateComment;
import com.team3.socialnews.shared.dispatch.UnvoteOnComment;
import com.team3.socialnews.shared.dispatch.VoteOnComment;

import net.apptao.highway.server.guice.HighwayModule;

public class CommentsModule extends HighwayModule {

	@Override
	protected void configureModule() {
		this.bindHandler(CreateComment.class, CreateCommentHandler.class);
		this.bindHandler(VoteOnComment.class, VoteOnCommentHandler.class);
		this.bindHandler(UnvoteOnComment.class, UnvoteOnCommentHandler.class);
		  
    	bind(CommentRepository.class).to(CommentRepositoryImpl.class).in(Singleton.class);
    	bind(CommentVoteRepository.class).to(CommentVoteRepositoryImpl.class).in(Singleton.class);
	}

}
