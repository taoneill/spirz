package com.team3.socialnews.shared.dispatch;

import net.apptao.highway.shared.dispatch.HwyCommand;

public class GetLoginInfo implements HwyCommand<GetLoginInfoResult> {
	private String requestURI;
	
	/**
	 * For serialization only.
	 */
	GetLoginInfo() {}
	
	/**
	 * @param requestURI The URI from which the LoginInfo is being
	 * requested. This is necessary for building a login URI that
	 * returns to the requested URI.
	 */
	public GetLoginInfo(String requestURI) {
		this.requestURI = requestURI;
	}
	
	public String getRequestURI() {
		return requestURI;
	}
}
