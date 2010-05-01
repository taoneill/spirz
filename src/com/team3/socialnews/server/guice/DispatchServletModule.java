package com.team3.socialnews.server.guice;

import net.customware.gwt.dispatch.server.guice.GuiceStandardDispatchServlet;

import com.google.inject.servlet.ServletModule;

public class DispatchServletModule extends ServletModule  {
	
    protected void configureServlets() {
    	serve("/socialnews/dispatch").with(GuiceStandardDispatchServlet.class);
    }
    
}	