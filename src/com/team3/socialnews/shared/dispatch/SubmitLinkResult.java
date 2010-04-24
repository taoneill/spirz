package com.team3.socialnews.shared.dispatch;

import net.customware.gwt.dispatch.shared.Result;

import com.team3.socialnews.shared.model.Link;

public class SubmitLinkResult implements Result {

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
