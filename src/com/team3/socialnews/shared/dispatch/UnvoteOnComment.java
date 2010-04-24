package com.team3.socialnews.shared.dispatch;

import net.customware.gwt.dispatch.shared.Action;

public class UnvoteOnComment implements Action<UnvoteOnCommentResult> {
	/** For serialization only */
	UnvoteOnComment() {}
	
	private Long commentId;
	
	public UnvoteOnComment(Long commentId) {
		this.commentId = commentId;
	}
	
	public Long getCommentId() {
		return commentId;
	}
}
