package com.team3.socialnews.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface LinkSubmittedEventHandler extends EventHandler {
	void onLinkSubmitted(LinkSubmittedEvent event);
}
