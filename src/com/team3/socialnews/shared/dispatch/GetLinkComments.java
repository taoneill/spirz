package com.team3.socialnews.shared.dispatch;

import net.customware.gwt.dispatch.shared.Action;

/**
 * Action to fetch comments for a link.
 *
 */
public class GetLinkComments implements Action<GetLinkCommentsResult> {
	
	/** For serialization */
	GetLinkComments() {}
	
	private Long linkId;
	/**
	 * @param linkId The id of the link for which for fetch the comments.
	 */
	public GetLinkComments(Long linkId) {
		this.linkId = linkId;
	}
	
	public Long getLinkId() {
		return linkId;
	}
}
