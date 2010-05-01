package com.team3.socialnews.shared.dispatch;

import net.apptao.highway.shared.dispatch.HwyCommand;

/**
 * Action to fetch comments for a link.
 *
 */
public class GetLinkComments implements HwyCommand<GetLinkCommentsResult> {
	
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
