package com.team3.socialnews.server.guice;

import net.apptao.highway.server.guice.HighwayServletModule;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class GuiceFactory {
	private static final Injector injector = Guice.createInjector(
			new HighwayServletModule(),
			new CoreModule(), 
			new LinksModule(),
			new CommentsModule(),
			new LoginModule(),
			new AdminModule());
	
	public static Injector getInjector() {
		return injector;
	}
}
