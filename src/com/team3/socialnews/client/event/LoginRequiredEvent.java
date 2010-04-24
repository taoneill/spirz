package com.team3.socialnews.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class LoginRequiredEvent extends GwtEvent<LoginRequiredEventHandler> {

	public static Type<LoginRequiredEventHandler> TYPE = new Type<LoginRequiredEventHandler>();
	
	@Override
	protected void dispatch(LoginRequiredEventHandler handler) {
		handler.onLoginRequired(this);
	}

	@Override
	public GwtEvent.Type<LoginRequiredEventHandler> getAssociatedType() {
		return TYPE;
	}

}
