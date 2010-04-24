package com.team3.socialnews.shared.dispatch;

import net.customware.gwt.dispatch.shared.Action;

public class VoteOnComment implements Action<VoteOnCommentResult> {
	/** For serialization */
	VoteOnComment() {}
	
	private Long commentId;
	
	public VoteOnComment(Long commentId) {
		this.commentId = commentId;
	}
	
	public Long getCommentId() {
		return commentId;
	}
}
