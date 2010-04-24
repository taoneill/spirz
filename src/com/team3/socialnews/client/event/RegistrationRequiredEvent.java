package com.team3.socialnews.client.event;

import com.google.gwt.event.shared.GwtEvent;


public class RegistrationRequiredEvent extends GwtEvent<RegistrationRequiredEventHandler> {

	public static Type<RegistrationRequiredEventHandler> TYPE = 
		new Type<RegistrationRequiredEventHandler>();
	
	@Override
	protected void dispatch(RegistrationRequiredEventHandler handler) {
		handler.onRegistationRequired(this);
	}

	@Override
	public Type<RegistrationRequiredEventHandler> getAssociatedType() {
		return TYPE;
	}

}
