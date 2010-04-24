package com.team3.socialnews.server.guice;

import net.customware.gwt.dispatch.server.guice.ActionHandlerModule;

import com.team3.socialnews.server.dispatch.GetLoginInfoHandler;
import com.team3.socialnews.server.dispatch.RegisterHandler;

public class LoginModule extends ActionHandlerModule {

	@Override
	protected void configureHandlers() {
		this.bindHandler(GetLoginInfoHandler.class);
		this.bindHandler(RegisterHandler.class);
	}

}
