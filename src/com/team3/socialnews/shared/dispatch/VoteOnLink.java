package com.team3.socialnews.shared.dispatch;

import net.apptao.highway.shared.dispatch.HwyCommand;
/**
 * Action that makes the currently logged-in user
 * vote on link.
 * @author tristan
 *
 */
public class VoteOnLink implements HwyCommand<VoteOnLinkResult> {
	/** For serialization only */
	VoteOnLink() {}
	
	private Long linkId;
	
	/**
	 * @param linkId Id of the link to vote on.
	 */
	public VoteOnLink(Long linkId) {
		this.linkId = linkId;
	}
	
	public Long getLinkId() {
		return linkId;
	}
}
