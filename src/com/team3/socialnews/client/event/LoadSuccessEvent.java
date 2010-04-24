package com.team3.socialnews.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class LoadSuccessEvent extends GwtEvent<LoadSuccessEventHandler> {
	
	public static Type<LoadSuccessEventHandler> TYPE = new Type<LoadSuccessEventHandler>();

	
	@Override
	protected void dispatch(LoadSuccessEventHandler handler) {
		handler.onSuccess(this);		
	}

	@Override
	public Type<LoadSuccessEventHandler> getAssociatedType() {
		return TYPE;
	}

	
}
