package com.team3.socialnews.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface LoadErrorEventHandler extends EventHandler  {
	void onError(LoadErrorEvent event);
}
