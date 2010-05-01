package com.team3.socialnews.server.guice;

import net.apptao.highway.server.guice.HighwayModule;

import com.google.inject.matcher.Matchers;
import com.team3.socialnews.server.RequiresLogin;
import com.team3.socialnews.server.dispatch.RequiresLoginInterceptor;

public class CoreModule extends HighwayModule {

	@Override
	protected void configureModule() {
    	//bind(SpirzCache.class).in(Singleton.class);
    	
    	// Bind the @RequiresLogin interceptor
    	bindInterceptor(Matchers.any(), 
				Matchers.annotatedWith(RequiresLogin.class), 
				new RequiresLoginInterceptor());
	}

}
