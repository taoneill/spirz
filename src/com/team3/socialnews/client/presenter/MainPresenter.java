package com.team3.socialnews.client.presenter;

import java.util.ArrayList;

import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.Presenter;
import net.customware.gwt.presenter.client.place.PlaceChangedEvent;
import net.customware.gwt.presenter.client.place.PlaceChangedHandler;
import net.customware.gwt.presenter.client.place.PlaceRequest;
import net.customware.gwt.presenter.client.place.PlaceRequestEvent;
import net.customware.gwt.presenter.client.widget.WidgetDisplay;
import net.customware.gwt.presenter.client.widget.WidgetPresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.team3.socialnews.client.LoginManager;
import com.team3.socialnews.client.Order;
import com.team3.socialnews.client.admin.AdminPresenter;
import com.team3.socialnews.client.event.LoadErrorEvent;
import com.team3.socialnews.client.event.LoadErrorEventHandler;
import com.team3.socialnews.client.event.LoadSuccessEvent;
import com.team3.socialnews.client.event.LoadSuccessEventHandler;
import com.team3.socialnews.client.event.RegistrationRequiredEvent;
import com.team3.socialnews.client.event.RegistrationRequiredEventHandler;
import com.team3.socialnews.client.resource.SpirzBundle;
import com.team3.socialnews.shared.model.LoginInfo;

public class MainPresenter extends AbstractPresenter<MainPresenter.Display> {
	
	public interface Display extends AbstractPresenter.Display {
		HasWidgets getBannerSection();
		HasWidgets getMainSection();
		void addAccountMenuItem(String itemName, ClickHandler handler);
		void clearAccountMenuItems();
		Widget asWidget();
		void showModal(Widget widget);
		void hideModal();
		void setNickname(String nickname);
		void setIsLoggedIn(boolean b);
		HasClickHandlers getLogo();
		void goToSiteUrl(String string);
		
		HasClickHandlers getHotButton();
		HasClickHandlers getNewButton();
		void setOrder(Order order);
		void setLoadingMessage(String message); 
		void hideMessage();
		void setLoadingErrorMessage(String string);
	}

	private WidgetPresenter<? extends WidgetDisplay> currentPresenter;
	
	/**
	 * Handles the event when the login button is pressed.
	 */
	private ClickHandler loginClickHandler;
	private ClickHandler optionsClickHandler;
	private ClickHandler adminClickHandler;
	
	private LoginInfo loginInfo;

	private ArrayList<Provider<? extends Presenter>> presenters = 
		new ArrayList<Provider<? extends Presenter>>();
	
	private Provider<RegistrationPresenter> registrationPresenterProvider;
	private Provider<LinksPresenter> glinksPresenter;
	private Provider<AdminPresenter> adminPresenterProvider;
	private SpirzBundle bundle;
	
	/**
	 * The presenters referenced in this object
	 * need to be injected with a Provider. This is
	 * because those presenters also depend on the MainPresenter
	 * to display themselves. If presenters are injected directly,
	 * Gin goes in an infinite loop trying to build the MainPresenter.
	 */
	
	@Inject
	public MainPresenter(
			Display display, 
			EventBus eventBus,
			LoginManager loginManager,
			Provider<LinkCommentsPresenter> linkCommentsPresenter,
			Provider<LinksPresenter> linksPresenter,
			Provider<RegistrationPresenter> regPresProvider,
			Provider<AdminPresenter> adminPresenterProvider) {
		super(display, eventBus, loginManager);
		bundle = GWT.create(SpirzBundle.class);
		bundle.css().ensureInjected();
		
		// Storing this one right away in case we need to reuse it.
		loginClickHandler = new LoginClickHandler();
		optionsClickHandler = new OptionsClickHandler();
				
		presenters.add(linkCommentsPresenter);
		presenters.add(linksPresenter);
		
		registrationPresenterProvider = regPresProvider;
		glinksPresenter = linksPresenter;
		this.adminPresenterProvider = adminPresenterProvider;
		

	}

	@Override
	public void onBind() {
		super.onBind();
		for(Provider<? extends Presenter> p : presenters) 
			p.get().bind();
		eventBus.addHandler(RegistrationRequiredEvent.TYPE, new RegistrationRequiredEventHandler() {
			@Override
			public void onRegistationRequired(
					RegistrationRequiredEvent registrationRequiredEvent) {
				RegistrationPresenter rp = registrationPresenterProvider.get();
				rp.bind();
				rp.revealDisplay();
			}
		});
		
		display.getLogo().addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
//				PlaceRequest pr = new PlaceRequest(LinksPresenter.PLACE)
//				.with("order", Order.HOT.toString());
//				eventBus.fireEvent(new PlaceRequestEvent(pr));
				display.goToSiteUrl("http://www.spirz.com");
			}
		});
		
		display.getHotButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				PlaceRequest pr = new PlaceRequest(LinksPresenter.PLACE)
				.with("order", Order.HOT.toString());
				eventBus.fireEvent(new PlaceRequestEvent(pr));
			}
		});
		
		display.getNewButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				PlaceRequest pr = new PlaceRequest(LinksPresenter.PLACE)
				.with("order", Order.NEW.toString());
				eventBus.fireEvent(new PlaceRequestEvent(pr));
				
			}
		});
		
		eventBus.addHandler(PlaceChangedEvent.getType(),  new PlaceChangedHandler() {
			@Override
			public void onPlaceChange(PlaceChangedEvent event) {
				String newPlaceName = event.getRequest().getPlace().toString();
				String linksPlaceName = glinksPresenter.get().getPlace().toString();
				if(newPlaceName.contentEquals(linksPlaceName)){
					// if we're in the links section, we need to highlight the places button
					String orderParam = event.getRequest().getParameter("order", Order.HOT.toString());
					if(orderParam.contentEquals(Order.HOT.toString())){
						display.setOrder(Order.HOT);
						display.setLoadingMessage("Getting hottest links...");	
					} else if(orderParam.contentEquals(Order.NEW.toString())){
						display.setOrder(Order.NEW);
						display.setLoadingMessage("Getting new submissions...");
					}
				} else {
					display.setOrder(null);
				}
			}
		});
		
		eventBus.addHandler(LoadErrorEvent.TYPE, new LoadErrorEventHandler() {
			@Override
			public void onError(LoadErrorEvent event) {
				display.setLoadingErrorMessage(event.getError());
			}
		});
		
		eventBus.addHandler(LoadSuccessEvent.TYPE, new LoadSuccessEventHandler() {
			@Override
			public void onSuccess(LoadSuccessEvent event) {
				display.hideMessage();
			}
		});
	}
	
	@Override
	protected void onLoginInfoFetched(LoginInfo loginInfo) {
		this.loginInfo = loginInfo;
		handleLogin(loginInfo);
	}
	
	@Override
	protected void onLoginInfoFetchedFailed() {
		Window.alert("Failed to fetch login information, please press the refresh button.");
	}
	
	/**
	 * Does the necessary modifications to the display
	 * depending if the user is logged in or not.
	 */
	private void handleLogin(LoginInfo loginInfo)  {
		if (loginInfo.isLoggedIn()) {
			showLoggedIn(loginInfo);
			
		} else {
			showLoggedOut();
		}
	}
	
	public void setPresenter(AbstractPresenter<? extends WidgetDisplay> presenter) {
		removeCurrentPresenter();
		currentPresenter = presenter;
		display.getMainSection().add(currentPresenter.getDisplay().asWidget());
	}
	
	private void removeCurrentPresenter() {
		if (currentPresenter != null) {
			currentPresenter = null;
			display.getMainSection().clear();
		}
	}
	
	private void showOptions(LoginInfo loginInfo)
	{
		//display.addAccountMenuItem("Settings", optionsClickHandler);
		if(loginInfo.getIsAdmin()) {
			adminClickHandler = new AdminClickHandler();
			display.addAccountMenuItem("Admin", adminClickHandler);
			
		}
	}
	
	private void showLoggedIn(LoginInfo loginInfo) {
		display.clearAccountMenuItems();
		showOptions(loginInfo);
		display.addAccountMenuItem("Logout", loginClickHandler);
		display.setNickname(loginInfo.getNickname());
		display.setIsLoggedIn(true);
	}
	
	private void showLoggedOut() {
		display.clearAccountMenuItems();
		display.addAccountMenuItem("Login", loginClickHandler);
		display.setIsLoggedIn(false);
	}
	
	private class LoginClickHandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			String url = loginInfo.isLoggedIn() ? 
					loginInfo.getLogoutUrl() : 
					loginInfo.getLoginUrl();
			Window.Location.replace(url);
		}
	}

	//CP
	private class OptionsClickHandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) 
		{
			RegistrationPresenter rp = registrationPresenterProvider.get();
			rp.bind();
			setRightPanel(rp);
		}
	}
	
	private void setRightPanel(RegistrationPresenter regPres)
	{	
		LinksPresenter.Display linkDisplay = (LinksPresenter.Display)glinksPresenter.get().getDisplay();
		
		linkDisplay.getRightSideContainer().clear();
		linkDisplay.getRightSideContainer().add(regPres.getDisplay().asWidget());
		
		setPresenter(glinksPresenter.get());
	}

	private class AdminClickHandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) 
		{
			AdminPresenter adminPanel = adminPresenterProvider.get();
			adminPanel.bind();
			setRightPanel(adminPanel);
		}
	}

	private void setRightPanel(AdminPresenter adminPresenter) {
		LinksPresenter.Display linkDisplay = (LinksPresenter.Display)glinksPresenter.get().getDisplay();
		linkDisplay.getRightSideContainer().clear();
		linkDisplay.getRightSideContainer().add(adminPresenter.getDisplay().asWidget());
		//setPresenter(glinksPresenter.get());
	}
	
	@Override
	protected void onUnbind() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refreshDisplay() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void revealDisplay() {
		// TODO Auto-generated method stub
		
	}
}
