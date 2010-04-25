package com.team3.socialnews.server.guice;

import net.customware.gwt.dispatch.server.guice.GuiceStandardDispatchServlet;

import com.google.inject.Singleton;
import com.google.inject.matcher.Matchers;
import com.google.inject.servlet.ServletModule;
import com.team3.socialnews.server.LoginManager;
import com.team3.socialnews.server.LoginManagerImpl;
import com.team3.socialnews.server.RequiresLogin;
import com.team3.socialnews.server.dispatch.RequiresLoginInterceptor;
import com.team3.socialnews.server.persistence.CommentRepository;
import com.team3.socialnews.server.persistence.CommentRepositoryImpl;
import com.team3.socialnews.server.persistence.CommentVoteRepository;
import com.team3.socialnews.server.persistence.CommentVoteRepositoryImpl;
import com.team3.socialnews.server.persistence.LinkDampVoteRepository;
import com.team3.socialnews.server.persistence.LinkDampVoteRepositoryImpl;
import com.team3.socialnews.server.persistence.LinkRepository;
import com.team3.socialnews.server.persistence.LinkRepositoryImpl;
import com.team3.socialnews.server.persistence.LinkVoteRepository;
import com.team3.socialnews.server.persistence.LinkVoteRepositoryImpl;
import com.team3.socialnews.server.persistence.LocalUserRepository;
import com.team3.socialnews.server.persistence.LocalUserRepositoryImpl;
import com.team3.socialnews.server.persistence.SpirzCache;
import com.team3.socialnews.server.vote.CurrentLinkEnergyModifier;
import com.team3.socialnews.server.vote.LinkAgeModifier;
import com.team3.socialnews.server.vote.LinkPredator;
import com.team3.socialnews.server.vote.VoteEnergyCalculator;

public class DispatchServletModule extends ServletModule  {
	
    protected void configureServlets() {
    	serve("/socialnews/dispatch").with(GuiceStandardDispatchServlet.class);
  
    
    }
    
}	