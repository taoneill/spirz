package com.team3.socialnews.shared.dispatch;

import net.apptao.highway.shared.dispatch.HwyResult;

import com.team3.socialnews.shared.model.Comment;

public class CreateCommentResult implements HwyResult {
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
