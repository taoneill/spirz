package com.team3.socialnews.client.gin;

import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.dispatch.client.standard.StandardDispatchAsync;
import net.customware.gwt.presenter.client.DefaultEventBus;
import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.gin.AbstractPresenterModule;
import net.customware.gwt.presenter.client.place.PlaceManager;

import com.google.inject.Singleton;
import com.team3.socialnews.client.Clock;
import com.team3.socialnews.client.LoginManager;
import com.team3.socialnews.client.Pager;
import com.team3.socialnews.client.admin.AdminPanel;
import com.team3.socialnews.client.admin.AdminPresenter;
import com.team3.socialnews.client.dispatch.CustomDispatchAsync;
import com.team3.socialnews.client.dispatch.MonitoredDispatchAsync;
import com.team3.socialnews.client.presenter.LinkCommentsPresenter;
import com.team3.socialnews.client.presenter.LinksPresenter;
import com.team3.socialnews.client.presenter.MainPresenter;
import com.team3.socialnews.client.presenter.RegistrationPresenter;
import com.team3.socialnews.client.presenter.SubmitCommentPresenter;
import com.team3.socialnews.client.presenter.SubmitLinkPresenter;
import com.team3.socialnews.client.view.LinkCommentsPanel;
import com.team3.socialnews.client.view.LinksPanel;
import com.team3.socialnews.client.view.MainView;
import com.team3.socialnews.client.view.RegistrationPanel;
import com.team3.socialnews.client.view.SubmitCommentPanel;
import com.team3.socialnews.client.view.SubmitLinkPanel;
import com.team3.socialnews.shared.admin.LoadTester;

public class SocialNewsGinModule extends AbstractPresenterModule {
	
	@Override
	protected void configure() {
		// Bind objects commonly used objects by presenters.
		bind(EventBus.class).to(DefaultEventBus.class).in(Singleton.class);
		bind(PlaceManager.class).in(Singleton.class);
		bind(DispatchAsync.class).to(CustomDispatchAsync.class);
		bind(MonitoredDispatchAsync.class).to(CustomDispatchAsync.class);
		bind(Clock.class).in(Singleton.class);
		bind(Pager.class);
		bind(LoadTester.class).in(Singleton.class);
		bind(LoginManager.class).in(Singleton.class);
		
		// Bind the presenters to their displays
		bind(MainPresenter.class).in(Singleton.class);
		bindDisplay(MainPresenter.Display.class, MainView.class);
		
		bind(LinksPresenter.class).in(Singleton.class);
		bindDisplay(LinksPresenter.Display.class, LinksPanel.class);
		
		bind(SubmitLinkPresenter.class).in(Singleton.class);
		bindDisplay(SubmitLinkPresenter.Display.class, SubmitLinkPanel.class);
		
		bind(AdminPresenter.class).in(Singleton.class);
		bindDisplay(AdminPresenter.Display.class, AdminPanel.class);
		
		bind(SubmitCommentPresenter.class).in(Singleton.class);
		bindDisplay(SubmitCommentPresenter.Display.class, SubmitCommentPanel.class);
		
		bind(RegistrationPresenter.class).in(Singleton.class);
		bindDisplay(RegistrationPresenter.Display.class, RegistrationPanel.class);
		
		bind(LinkCommentsPresenter.class).in(Singleton.class);
		bindDisplay(LinkCommentsPresenter.Display.class, LinkCommentsPanel.class);
	}
}
