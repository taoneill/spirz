package com.team3.socialnews.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class RegistrationCompletedEvent extends GwtEvent<RegistrationCompletedEventHandler> {

	public static Type<RegistrationCompletedEventHandler> TYPE = new Type<RegistrationCompletedEventHandler>();
	
	String nickname;
	
	public RegistrationCompletedEvent(String nickname) {
		this.nickname = nickname;
	}
	
	@Override
	protected void dispatch(RegistrationCompletedEventHandler handler) {
		handler.onRegistrationCompleted(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<RegistrationCompletedEventHandler> getAssociatedType() {
		return TYPE;
	}

	public String getNickname() {
		return nickname;
	}

}
