package com.team3.socialnews.server.dispatch;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.ActionException;

import com.team3.socialnews.server.LoginManager;
import com.team3.socialnews.server.guice.GuiceFactory;
import com.team3.socialnews.shared.dispatch.GetLoginInfo;
import com.team3.socialnews.shared.dispatch.GetLoginInfoResult;

public class GetLoginInfoHandler implements ActionHandler<GetLoginInfo, GetLoginInfoResult> {

	@Override
	public GetLoginInfoResult execute(GetLoginInfo action, ExecutionContext context)
			throws ActionException {
		LoginManager loginManager = GuiceFactory.getInjector().getInstance(LoginManager.class);
		return new GetLoginInfoResult(loginManager.getLoginInfo(action.getRequestURI()));
	}

	@Override
	public Class<GetLoginInfo> getActionType() {
		return GetLoginInfo.class;
	}

	@Override
	public void rollback(GetLoginInfo arg0, GetLoginInfoResult arg1, ExecutionContext arg2)
			throws ActionException {}

}
