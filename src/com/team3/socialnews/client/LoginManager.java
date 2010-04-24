package com.team3.socialnews.client;

import java.util.ArrayList;

import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.presenter.client.EventBus;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.team3.socialnews.client.event.RegistrationCompletedEvent;
import com.team3.socialnews.client.event.RegistrationCompletedEventHandler;
import com.team3.socialnews.client.event.RegistrationRequiredEvent;
import com.team3.socialnews.shared.dispatch.GetLoginInfo;
import com.team3.socialnews.shared.dispatch.GetLoginInfoResult;
import com.team3.socialnews.shared.model.LoginInfo;
/**
 * Responsible for fetching the login information
 * of the user.
 */
@Singleton
public class LoginManager {
	private DispatchAsync dispatch;
	private EventBus eventBus;
	
	/**
	 * Handler registration for the ReigstrationSuccessFullEvent.
	 * This is initially null.
	 */
	private HandlerRegistration registrationHandlerRegistration;
	
	/**
	 * This is where we store the login information.
	 */
	private LoginInfo loginInfo;
	/**
	 * True when LoginManager is in the process
	 * of fetching login information from the server.
	 */
	private boolean isFetchingLoginInfo = false;
	
	/**
	 * Stores callbacks for objects that are interested
	 * in receiving the loginInfo. This list is always empty
	 * when the login info has been received.
	 */
	private ArrayList<AsyncCallback<LoginInfo>> callbacks = new ArrayList<AsyncCallback<LoginInfo>>();
	
	@Inject
	public LoginManager(DispatchAsync dispatch, EventBus eventBus) {
		this.dispatch = dispatch;
		this.eventBus = eventBus;
	}
	
	/**
	 * Fetches the login info. This call is asynchronous.
	 * The callback may or may not be called immediately.
	 * @param callback
	 */
	public void getLoginInfo(
			final AsyncCallback<LoginInfo> callback) {
		getLoginInfo(callback, 0);
	}
	
	/**
	 * 
	 * @param callback
	 * @param retryCount The number of times that fetching
	 * the login info has been retried.
	 */
	private void getLoginInfo(final AsyncCallback<LoginInfo> callback, final int retryCount) {
		if (loginInfo != null) {
			// If the loginInfo is already available, no
			// need to fetch it on the server. Return it
			// immediately.
			callback.onSuccess(loginInfo);
		
		} else {
			
			// Add the callback to the list
			// of callback waiting for the
			// login information.
			callbacks.add(callback);
			
			// If we are not fetching the loginInfo already
			// then we make a request.
			if (isFetchingLoginInfo == false) {
				
				// Set the flag so that when this method
				// is called and the login information is
				// still not received, we do not make the
				// request twice.
				isFetchingLoginInfo = true;
				
				// Send the request to the server.
				dispatch.execute(new GetLoginInfo(GWT.getHostPageBaseURL()), 
						new AsyncCallback<GetLoginInfoResult>() {

							@Override
							public void onFailure(Throwable caught) {
								// The request failed. Notify the callbacks.
								for(AsyncCallback<LoginInfo> callback : callbacks)
									callback.onFailure(caught);
							}

							@Override
							public void onSuccess(GetLoginInfoResult result) {
								final LoginInfo loginInfo = result.getLoginInfo();
								// If the user is logged in but not registered
								if (loginInfo.isLoggedIn() && !loginInfo.isRegistered()) {
									// We subscribe to the event and keep a reference
									// to the handler so that we may unregister when
									// the user has successfully registered.
									registrationHandlerRegistration = 
										eventBus.addHandler(RegistrationCompletedEvent.TYPE, 
											new RegistrationCompletedEventHandler() {
												@Override
												public void onRegistrationCompleted(
														RegistrationCompletedEvent registrationCompletedEvent) {		
													// User had registered, we want our presenters
													// to know about this.
													loginInfo.setIsRegistered(true);
													// Remove the RegistrationCompletedEvent handler
													// from the event bus, just in case.
													if (registrationHandlerRegistration != null) {
														registrationHandlerRegistration.removeHandler();
														registrationHandlerRegistration = null;
													}
													// Set the login info and send it
													// through the callbacks.
													setLoginInfo(loginInfo);
												}
									});
									// Send the an event to let interested parties
									// know that registration is required.
									setLoginInfo(loginInfo);
									eventBus.fireEvent(new RegistrationRequiredEvent());
									
								} else {
									// User is either not logged in
									// or logged in and registered.
									setLoginInfo(loginInfo);
								}
							}
						}
				);
			}
		}
	}
	
	/**
	 * Sets the loginInfo field of this object
	 * and notifies the callbacks that the loginInfo
	 * has been received.
	 * @param loginInfo
	 */
	private void setLoginInfo(LoginInfo loginInfo) {
		this.loginInfo = loginInfo;
		// We're not fetching the loginInfo anymore.
		this.isFetchingLoginInfo = false;
		// Notify the call backs
		for(AsyncCallback<LoginInfo> callback : callbacks)
			callback.onSuccess(loginInfo);
		// Now that the callbacks are notified,
		// we may empty the list.
		callbacks.clear();
	}
}
