package com.team3.socialnews.shared.dispatch;

import net.apptao.highway.shared.dispatch.HwyCommand;

public class UnvoteOnComment implements HwyCommand<UnvoteOnCommentResult> {
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
