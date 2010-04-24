package com.team3.socialnews.client;


import net.customware.gwt.presenter.client.place.PlaceManager;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.RootPanel;
import com.team3.socialnews.client.gin.SocialNewsGinjector;
import com.team3.socialnews.client.presenter.LinksPresenter;
import com.team3.socialnews.client.presenter.MainPresenter;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class SocialNews implements EntryPoint {
	
	// get our injector and its config
	private final SocialNewsGinjector injector = GWT.create(SocialNewsGinjector.class);
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {		
	    // bootstrap the injector by getting the main app panel 
		MainPresenter presenter = injector.getMainPresenter();
	    presenter.bind();
	    RootPanel.get().add(presenter.getDisplay().asWidget());
	    
	    // Ensure that a history token is set in order for a presenter 
		// to handle its onPlaceEvent and show the first panel.
		if ("".equals(History.getToken())) {
			History.newItem(LinksPresenter.PLACE.getId(), false);
		}
		
		// Trigger any passed-in history tokens.
	    PlaceManager places = injector.getPlaceManager();
	    places.fireCurrentPlace();
	}
}
