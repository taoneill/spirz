package com.team3.socialnews.client.gin;

import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.place.PlaceManager;

import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.team3.socialnews.client.presenter.MainPresenter;

@GinModules(SocialNewsGinModule.class)
public interface SocialNewsGinjector extends Ginjector {
	MainPresenter getMainPresenter();
	PlaceManager getPlaceManager();
	EventBus getEventBus();
}
