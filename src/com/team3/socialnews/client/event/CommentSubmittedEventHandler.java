package com.team3.socialnews.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface CommentSubmittedEventHandler extends EventHandler {
	void onCommentSubmit(CommentSubmittedEvent event);
}
