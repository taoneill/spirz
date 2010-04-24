package com.team3.socialnews.server.guice;

import net.customware.gwt.dispatch.server.guice.ActionHandlerModule;

import com.team3.socialnews.server.dispatch.GetLoginInfoHandler;
import com.team3.socialnews.server.dispatch.RegisterHandler;
import com.team3.socialnews.shared.dispatch.GetLoginInfo;
import com.team3.socialnews.shared.dispatch.Register;

public class LoginModule extends ActionHandlerModule {

	@Override
	protected void configureHandlers() {
		this.bindHandler(GetLoginInfo.class, GetLoginInfoHandler.class);
		this.bindHandler(Register.class, RegisterHandler.class);
	}

}
