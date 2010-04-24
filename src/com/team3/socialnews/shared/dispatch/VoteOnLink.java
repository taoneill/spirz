package com.team3.socialnews.shared.dispatch;

import net.customware.gwt.dispatch.shared.Action;
/**
 * Action that makes the currently logged-in user
 * vote on link.
 * @author tristan
 *
 */
public class VoteOnLink implements Action<VoteOnLinkResult> {
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
