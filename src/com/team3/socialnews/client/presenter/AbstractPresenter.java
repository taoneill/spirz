package com.team3.socialnews.client.presenter;

import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.place.Place;
import net.customware.gwt.presenter.client.place.PlaceRequest;
import net.customware.gwt.presenter.client.widget.WidgetDisplay;
import net.customware.gwt.presenter.client.widget.WidgetPresenter;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.team3.socialnews.client.LoginManager;
import com.team3.socialnews.shared.model.LoginInfo;
/**
 * Superclass for all presenters in this application.
 * @param <D> The display of this presenter.
 */
public abstract class AbstractPresenter<D extends AbstractPresenter.Display> extends
		WidgetPresenter<D> {

	public interface Display extends WidgetDisplay {}

	private LoginManager loginManager;
	
	/**
	 * User this contructor if LoginInfo is not needed.
	 */
	public AbstractPresenter(final D display, final EventBus eventBus) {
		super(display, eventBus);
	}
	
	/**
	 * Use this constructor if LoginInfo is needed.
	 * @param loginManager An injected instance of LoginManager.
	 */
	public AbstractPresenter(final D display, final EventBus eventBus, LoginManager loginManager) {
		this(display, eventBus);
		this.loginManager = loginManager;
	}
	
	@Override
	protected void onBind() {
		if (loginManager != null) {
			loginManager.getLoginInfo(new AsyncCallback<LoginInfo>() {
				@Override
				public void onFailure(Throwable caught) {
					onLoginInfoFetchedFailed();
				}
	
				@Override
				public void onSuccess(LoginInfo result) {
					onLoginInfoFetched(result);
				}
			});
		}
	}
	
	/**
	 * Override this method to be notified when
	 * the LoginInfo fails to be received.
	 */
	protected void onLoginInfoFetchedFailed() {}

	/**
	 * Subclasses may override this to do something
	 * when the @Link{LoginManager} has received the @link{LoginInfo}
	 * object from the server.
	 * @param loginInfo
	 */
	protected void onLoginInfoFetched(LoginInfo loginInfo) {};
	
	@Override
	protected void onPlaceRequest(PlaceRequest placeRequest) {}

	@Override
	public Place getPlace() {
		return null;
	}
}