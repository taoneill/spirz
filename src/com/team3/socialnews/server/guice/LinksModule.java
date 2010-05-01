package com.team3.socialnews.server.guice;

import net.apptao.highway.server.guice.HighwayModule;

import com.google.inject.Singleton;
import com.team3.socialnews.server.dispatch.GetLinkCommentsHandler;
import com.team3.socialnews.server.dispatch.GetLinksHandler;
import com.team3.socialnews.server.dispatch.SubmitLinkHandler;
import com.team3.socialnews.server.dispatch.UnvoteOnLinkHandler;
import com.team3.socialnews.server.dispatch.VoteOnLinkHandler;
import com.team3.socialnews.server.persistence.LinkDampVoteRepository;
import com.team3.socialnews.server.persistence.LinkDampVoteRepositoryHwyImpl;
import com.team3.socialnews.server.persistence.LinkRepository;
import com.team3.socialnews.server.persistence.LinkRepositoryHwyImpl;
import com.team3.socialnews.server.persistence.LinkVoteRepository;
import com.team3.socialnews.server.persistence.LinkVoteRepositoryHwyImpl;
import com.team3.socialnews.server.vote.CurrentLinkEnergyModifier;
import com.team3.socialnews.server.vote.LinkAgeModifier;
import com.team3.socialnews.server.vote.LinkPredator;
import com.team3.socialnews.server.vote.VoteEnergyCalculator;
import com.team3.socialnews.shared.dispatch.GetLinkComments;
import com.team3.socialnews.shared.dispatch.GetLinks;
import com.team3.socialnews.shared.dispatch.SubmitLink;
import com.team3.socialnews.shared.dispatch.UnvoteOnLink;
import com.team3.socialnews.shared.dispatch.VoteOnLink;
import com.team3.socialnews.shared.model.Link;
import com.team3.socialnews.shared.model.LinkDampVote;
import com.team3.socialnews.shared.model.LinkVote;

public class LinksModule extends HighwayModule  {
	@Override
	protected void configureModule() {
		// Objectify registration
		this.register(Link.class);
		this.register(LinkVote.class);
		this.register(LinkDampVote.class);
		
		// Bind commands or gwt-dispatch Actions to handlers
		this.bindCommand(SubmitLink.class, SubmitLinkHandler.class);
		this.bindCommand(GetLinks.class, GetLinksHandler.class);
		this.bindCommand(VoteOnLink.class, VoteOnLinkHandler.class);
		this.bindCommand(UnvoteOnLink.class, UnvoteOnLinkHandler.class);
		
		// Repositories
		bind(LinkRepository.class).to(LinkRepositoryHwyImpl.class).in(Singleton.class);
    	bind(LinkVoteRepository.class).to(LinkVoteRepositoryHwyImpl.class).in(Singleton.class);
    	bind(LinkDampVoteRepository.class).to(LinkDampVoteRepositoryHwyImpl.class).in(Singleton.class);
    	
    	bind(LinkPredator.class).in(Singleton.class);
    	bind(VoteEnergyCalculator.class);
    	bind(LinkAgeModifier.class);
    	bind(CurrentLinkEnergyModifier.class);
	}
}
