package com.team3.socialnews.shared.dispatch;

import java.util.List;

import net.apptao.highway.shared.dispatch.HwyResult;
import net.customware.gwt.dispatch.shared.Result;

import com.team3.socialnews.shared.model.Link;

public class GetLinksResult implements HwyResult {

	private List<Link> links;
	private List<Boolean> voteMask;

	/* for serialization */
	GetLinksResult() {
	}
	
	public GetLinksResult(List<Link> links) {
		this.links = links;
	}
	
	public GetLinksResult(List<Link> links, List<Boolean> voteMask) {
		this.links = links;
		this.voteMask = voteMask;
	}
	
	public List<Link> getLinks() {
		return this.links;
	}
	
	public List<Boolean> getVoteMask() {
		return this.voteMask;
	}
}
