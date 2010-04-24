package com.team3.socialnews.server.guice;

import net.customware.gwt.dispatch.server.guice.ActionHandlerModule;

import com.team3.socialnews.server.dispatch.CreateCommentHandler;
import com.team3.socialnews.server.dispatch.GetLinkCommentsHandler;
import com.team3.socialnews.server.dispatch.GetLinksHandler;
import com.team3.socialnews.server.dispatch.SubmitLinkHandler;
import com.team3.socialnews.server.dispatch.UnvoteOnCommentHandler;
import com.team3.socialnews.server.dispatch.UnvoteOnLinkHandler;
import com.team3.socialnews.server.dispatch.VoteOnCommentHandler;
import com.team3.socialnews.server.dispatch.VoteOnLinkHandler;

public class LinksModule extends ActionHandlerModule  {
	@Override
	protected void configureHandlers() {
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
