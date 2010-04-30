package com.team3.socialnews.shared.dispatch;

import net.apptao.highway.shared.dispatch.HwyCommand;
import net.customware.gwt.dispatch.shared.Action;
/**
 * Action for submitting a new link.
 */
public class SubmitLink implements HwyCommand<SubmitLinkResult> {
	private String title;
	private String url;

	/** for serialization only */
	SubmitLink() {
	}

	/**
	 * @param title A title for the link.
	 * @param url The url of the link.
	 */
	public SubmitLink(String title, String url) {
		this.title = title;
		this.url = url;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public String getUrl() {
		return this.url;
	}
}
