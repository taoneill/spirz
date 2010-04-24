package com.team3.socialnews.client.presenter;

import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.place.PlaceRequest;
import net.customware.gwt.presenter.client.place.PlaceRequestEvent;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasValue;
import com.google.inject.Inject;
import com.team3.socialnews.client.LoginManager;
import com.team3.socialnews.client.Order;
import com.team3.socialnews.client.event.RegistrationCompletedEvent;
import com.team3.socialnews.client.event.RegistrationRequiredEvent;
import com.team3.socialnews.client.event.RegistrationRequiredEventHandler;
import com.team3.socialnews.shared.dispatch.NicknameNotAvailable;
import com.team3.socialnews.shared.dispatch.Register;
import com.team3.socialnews.shared.dispatch.RegisterResult;

public class RegistrationPresenter extends AbstractPresenter<RegistrationPresenter.Display> {

	public interface Display extends AbstractPresenter.Display {
		HasValue<String> getNickname();
		HasClickHandlers getSubmitButton();
		void showError(String error);
	}
	
	private DispatchAsync dispatchAsync;
	private MainPresenter container;
	
	@Inject
	public RegistrationPresenter(
			Display display, 
			EventBus eventBus, 
			LoginManager loginManager,
			DispatchAsync dispatchAsync,
			MainPresenter container) {
		super(display, eventBus, loginManager);
		this.container = container;
		this.dispatchAsync = dispatchAsync;
	}

	@Override
	public void onBind() {
		super.onBind();
		display.getSubmitButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				submit();
			}
		});
		
		eventBus.addHandler(RegistrationRequiredEvent.TYPE, new RegistrationRequiredEventHandler() {
			@Override
			public void onRegistationRequired(
					RegistrationRequiredEvent registrationRequiredEvent) {
				revealDisplay();
			}
		});
	}
	
	private void submit() {
		final String nickname = display.getNickname().getValue();
		display.startProcessing();
		dispatchAsync.execute(new Register(nickname), new AsyncCallback<RegisterResult>() {
			@Override
			public void onFailure(Throwable caught) {
				String errorMsg;
				if (caught instanceof NicknameNotAvailable) {
					errorMsg = "Nickname is already taken.";
				} else {
					errorMsg = "An unexpected error occured!";
				}
				display.showError(errorMsg);
				display.stopProcessing();
			}

			@Override
			public void onSuccess(RegisterResult result) {
				eventBus.fireEvent(new RegistrationCompletedEvent(nickname));
				display.stopProcessing();
				eventBus.fireEvent(new PlaceRequestEvent(
					new PlaceRequest(LinksPresenter.PLACE).with("order", Order.NEW.toString())));
			}
		});
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
		container.setPresenter(this);
	}
}
