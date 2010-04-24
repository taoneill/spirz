package com.team3.socialnews.shared.dispatch;

import net.customware.gwt.dispatch.shared.Action;

/**
 * Creates a new comment.
 */
public class CreateComment implements Action<CreateCommentResult> {
	/** For serialization */
	CreateComment() {}
	
	private Long linkId;
	private String body;
	private Long parentCommentId;
	/**
	 * @param linkId The id of the link for which this comment is on.
	 * @param body The textual body of the comment.
	 * @param parentCommentId The id of the comment comment if this is a reply or
	 * null if this comment has no parent.
	 */
	public CreateComment(Long linkId, String body, Long parentCommentId) {
		this.linkId = linkId;
		this.body = body;
		this.parentCommentId = parentCommentId;
	}
	
	/**
	 * Returns the link id of the link for which this comment is one.
	 * @return
	 */
	public Long getLinkId() {
		return linkId;
	}
	
	/**
	 * Returns the id of the comment comment if this is a reply or null if this comment has no parent
	 * @return 
	 */
	public Long getParentId() {
		return parentCommentId;
	}
	
	/**
	 * Returns The textual body of the comment.
	 * @return
	 */
	public String getBody() {
		return body;
	}
}
