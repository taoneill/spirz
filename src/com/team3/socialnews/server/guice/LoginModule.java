package com.team3.socialnews.server.guice;

import net.apptao.highway.server.guice.HighwayModule;

import com.google.inject.Singleton;
import com.team3.socialnews.server.LoginManager;
import com.team3.socialnews.server.LoginManagerImpl;
import com.team3.socialnews.server.dispatch.GetLoginInfoHandler;
import com.team3.socialnews.server.dispatch.RegisterHandler;
import com.team3.socialnews.server.persistence.LocalUserRepository;
import com.team3.socialnews.server.persistence.LocalUserRepositoryHwyImpl;
import com.team3.socialnews.shared.dispatch.GetLoginInfo;
import com.team3.socialnews.shared.dispatch.Register;
import com.team3.socialnews.shared.model.LocalUser;

public class LoginModule extends HighwayModule {

	@Override
	protected void configureModule() {
		register(LocalUser.class);
		
		bind(LoginManager.class).to(LoginManagerImpl.class).in(Singleton.class);
		
		this.bindHandler(GetLoginInfo.class, GetLoginInfoHandler.class);
		this.bindHandler(Register.class, RegisterHandler.class);

    	bind(LocalUserRepository.class).to(LocalUserRepositoryHwyImpl.class).in(Singleton.class);
	}

}
