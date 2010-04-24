package com.team3.socialnews.server.guice;

import net.apptao.highway.server.guice.HighwayModule;

import com.team3.socialnews.server.dispatch.CreateCommentHandler;
import com.team3.socialnews.server.dispatch.GetLinkCommentsHandler;
import com.team3.socialnews.server.dispatch.GetLinksHandler;
import com.team3.socialnews.server.dispatch.SubmitLinkHandler;
import com.team3.socialnews.server.dispatch.UnvoteOnCommentHandler;
import com.team3.socialnews.server.dispatch.UnvoteOnLinkHandler;
import com.team3.socialnews.server.dispatch.VoteOnCommentHandler;
import com.team3.socialnews.server.dispatch.VoteOnLinkHandler;
import com.team3.socialnews.shared.model.Link;

public class LinksModule extends HighwayModule  {
	@Override
	protected void configureModule() {
		// Objectify registration (replaces 
		this.register(Link.class);
		
		// Old school gwt-dispatch action handlers can still be bound
		this.bindHandler(SubmitLinkHandler.class);
		this.bindHandler(GetLinksHandler.class);
		this.bindHandler(GetLinkCommentsHandler.class);
		this.bindHandler(CreateCommentHandler.class);
		this.bindHandler(VoteOnLinkHandler.class);
		this.bindHandler(UnvoteOnLinkHandler.class);
		this.bindHandler(VoteOnCommentHandler.class);
		this.bindHandler(UnvoteOnCommentHandler.class);
	}
}
