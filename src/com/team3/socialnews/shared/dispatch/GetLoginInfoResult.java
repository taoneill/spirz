package com.team3.socialnews.shared.dispatch;

import net.customware.gwt.dispatch.shared.Result;

import com.team3.socialnews.shared.model.LoginInfo;

public class GetLoginInfoResult implements Result {
	LoginInfo info;
	
	/* for serialization */
	GetLoginInfoResult() {}
	
	public GetLoginInfoResult(LoginInfo info) {
		this.info = info;
	}
	
	public LoginInfo getLoginInfo() {
		return info;
	}
}
