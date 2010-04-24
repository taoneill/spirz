package com.team3.socialnews.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class ClockTickEvent extends GwtEvent<ClockTickEventHandler> {
	
	public static Type<ClockTickEventHandler> TYPE = new Type<ClockTickEventHandler>();

	@Override
	protected void dispatch(ClockTickEventHandler handler) {
		handler.onTick(this);		
	}

	@Override
	public Type<ClockTickEventHandler> getAssociatedType() {
		return TYPE;
	}
	
}
