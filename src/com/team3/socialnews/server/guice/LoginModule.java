package com.team3.socialnews.server.guice;

import net.apptao.highway.server.guice.HighwayModule;
import net.customware.gwt.dispatch.server.guice.ActionHandlerModule;

import com.google.inject.Singleton;
import com.team3.socialnews.server.LoginManager;
import com.team3.socialnews.server.LoginManagerImpl;
import com.team3.socialnews.server.dispatch.GetLoginInfoHandler;
import com.team3.socialnews.server.dispatch.RegisterHandler;
import com.team3.socialnews.server.persistence.LocalUserRepository;
import com.team3.socialnews.server.persistence.LocalUserRepositoryImpl;
import com.team3.socialnews.shared.dispatch.GetLoginInfo;
import com.team3.socialnews.shared.dispatch.Register;

public class LoginModule extends HighwayModule {

	@Override
	protected void configureModule() {
		bind(LoginManager.class).to(LoginManagerImpl.class).in(Singleton.class);
		
		this.bindHandler(GetLoginInfo.class, GetLoginInfoHandler.class);
		this.bindHandler(Register.class, RegisterHandler.class);

    	bind(LocalUserRepository.class).to(LocalUserRepositoryImpl.class).in(Singleton.class);
	}

}
