package com.team3.socialnews.shared.dispatch;

import net.apptao.highway.shared.dispatch.HwyCommand;

public class UnvoteOnLink implements HwyCommand<UnvoteOnLinkResult> {
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
