package com.team3.socialnews.server.guice;

import net.apptao.highway.server.guice.HighwayModule;

import com.google.inject.Singleton;
import com.team3.socialnews.server.dispatch.CreateCommentHandler;
import com.team3.socialnews.server.dispatch.GetLinkCommentsHandler;
import com.team3.socialnews.server.dispatch.GetLinksHandler;
import com.team3.socialnews.server.dispatch.SubmitLinkHandler;
import com.team3.socialnews.server.dispatch.UnvoteOnCommentHandler;
import com.team3.socialnews.server.dispatch.UnvoteOnLinkHandler;
import com.team3.socialnews.server.dispatch.VoteOnCommentHandler;
import com.team3.socialnews.server.dispatch.VoteOnLinkHandler;
import com.team3.socialnews.server.persistence.CommentRepository;
import com.team3.socialnews.server.persistence.CommentRepositoryImpl;
import com.team3.socialnews.server.persistence.CommentVoteRepository;
import com.team3.socialnews.server.persistence.CommentVoteRepositoryImpl;
import com.team3.socialnews.server.persistence.LinkDampVoteRepository;
import com.team3.socialnews.server.persistence.LinkDampVoteRepositoryImpl;
import com.team3.socialnews.server.persistence.LinkDao;
import com.team3.socialnews.server.persistence.LinkRepository;
import com.team3.socialnews.server.persistence.LinkRepositoryImpl;
import com.team3.socialnews.server.persistence.LinkVoteRepository;
import com.team3.socialnews.server.persistence.LinkVoteRepositoryImpl;
import com.team3.socialnews.server.persistence.SpirzCache;
import com.team3.socialnews.server.vote.CurrentLinkEnergyModifier;
import com.team3.socialnews.server.vote.LinkAgeModifier;
import com.team3.socialnews.server.vote.LinkPredator;
import com.team3.socialnews.server.vote.VoteEnergyCalculator;
import com.team3.socialnews.shared.dispatch.CreateComment;
import com.team3.socialnews.shared.dispatch.GetLinkComments;
import com.team3.socialnews.shared.dispatch.GetLinks;
import com.team3.socialnews.shared.dispatch.SubmitLink;
import com.team3.socialnews.shared.dispatch.UnvoteOnComment;
import com.team3.socialnews.shared.dispatch.UnvoteOnLink;
import com.team3.socialnews.shared.dispatch.VoteOnComment;
import com.team3.socialnews.shared.dispatch.VoteOnLink;
import com.team3.socialnews.shared.model.Link;

public class LinksModule extends HighwayModule  {
	@Override
	protected void configureModule() {
		// Objectify registration (replaces 
		this.register(Link.class);
		
		// Old school gwt-dispatch action handlers can still be bound
		this.bindHandler(SubmitLink.class, SubmitLinkHandler.class);
		this.bindHandler(GetLinks.class, GetLinksHandler.class);
		this.bindHandler(GetLinkComments.class, GetLinkCommentsHandler.class);
		
		this.bindHandler(VoteOnLink.class, VoteOnLinkHandler.class);
		this.bindHandler(UnvoteOnLink.class, UnvoteOnLinkHandler.class);
		
		// Repositories
		bind(LinkRepository.class).to(LinkDao.class).in(Singleton.class);
    	bind(LinkVoteRepository.class).to(LinkVoteRepositoryImpl.class).in(Singleton.class);
    	bind(LinkDampVoteRepository.class).to(LinkDampVoteRepositoryImpl.class).in(Singleton.class);
    	
    	bind(LinkPredator.class).in(Singleton.class);
    	bind(VoteEnergyCalculator.class);
    	bind(LinkAgeModifier.class);
    	bind(CurrentLinkEnergyModifier.class);
	}
}
