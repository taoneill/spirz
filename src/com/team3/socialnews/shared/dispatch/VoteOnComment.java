package com.team3.socialnews.shared.dispatch;

import net.apptao.highway.shared.dispatch.HwyCommand;

public class VoteOnComment implements HwyCommand<VoteOnCommentResult> {
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
