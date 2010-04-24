package com.team3.socialnews.shared.dispatch;

import net.customware.gwt.dispatch.shared.Result;

import com.team3.socialnews.shared.model.Comment;

public class CreateCommentResult implements Result {
	/** For serialization */
	CreateCommentResult() {}
	
	Comment comment;
	public CreateCommentResult(Comment comment) {
		this.comment = comment;
	}
	
	public Comment getComment() {
		return comment;
	}
}
