package com.team3.socialnews.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface ClockTickEventHandler extends EventHandler  {
	void onTick(ClockTickEvent event);
}
