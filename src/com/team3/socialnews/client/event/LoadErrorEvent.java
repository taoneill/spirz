package com.team3.socialnews.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class LoadErrorEvent extends GwtEvent<LoadErrorEventHandler> {
	
	public static Type<LoadErrorEventHandler> TYPE = new Type<LoadErrorEventHandler>();
	private String error;

	public LoadErrorEvent(String error){
		this.setError(error);
	}
	
	@Override
	protected void dispatch(LoadErrorEventHandler handler) {
		handler.onError(this);		
	}

	@Override
	public Type<LoadErrorEventHandler> getAssociatedType() {
		return TYPE;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getError() {
		return error;
	}
	
}
