package com.team3.socialnews.server.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class GuiceFactory {
	private static final Injector injector = Guice.createInjector(
			new DispatchServletModule(), 
			new LinksModule(), 
			new LoginModule(),
			new AdminModule());
	
	public static Injector getInjector() {
		return injector;
	}
}
