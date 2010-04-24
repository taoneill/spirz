package com.team3.socialnews.shared.dispatch;

import net.customware.gwt.dispatch.shared.Action;

public class UnvoteOnLink implements Action<UnvoteOnLinkResult> {
	/** For serialization only */
	UnvoteOnLink() {}
	
	private Long linkId;
	
	public UnvoteOnLink(Long linkId) {
		this.linkId = linkId;
	}
	
	public Long getLinkId() {
		return linkId;
	}
}
