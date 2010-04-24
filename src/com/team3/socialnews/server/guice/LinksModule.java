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
		this.bindHandler(CreateComment.class, CreateCommentHandler.class);
		this.bindHandler(VoteOnLink.class, VoteOnLinkHandler.class);
		this.bindHandler(UnvoteOnLink.class, UnvoteOnLinkHandler.class);
		this.bindHandler(VoteOnComment.class, VoteOnCommentHandler.class);
		this.bindHandler(UnvoteOnComment.class, UnvoteOnCommentHandler.class);
	}
}
