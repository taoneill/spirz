package com.team3.socialnews.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.team3.socialnews.shared.model.Comment;

public class CommentSubmittedEvent extends
		GwtEvent<CommentSubmittedEventHandler> {

	private Comment comment;
	public CommentSubmittedEvent(Comment c) {
		this.comment = c;
	}
	
	public static Type<CommentSubmittedEventHandler> TYPE = 
		new Type<CommentSubmittedEventHandler>();
	
	@Override
	protected void dispatch(CommentSubmittedEventHandler handler) {
		handler.onCommentSubmit(this);
	}

	@Override
	public Type<CommentSubmittedEventHandler> getAssociatedType() {
		return TYPE;
	}
	
	public Comment getComment() {
		return comment;
	}

}
