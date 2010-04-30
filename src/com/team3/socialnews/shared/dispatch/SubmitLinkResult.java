package com.team3.socialnews.shared.dispatch;

import net.apptao.highway.shared.dispatch.HwyResult;

import com.team3.socialnews.shared.model.Link;

public class SubmitLinkResult implements HwyResult {

	Link link;
	
	/* for serialization */
	SubmitLinkResult() {}
	
	public SubmitLinkResult(Link link) {
		this.link = link;
	}

	public Link getLink() {
		return link;
	}
}
