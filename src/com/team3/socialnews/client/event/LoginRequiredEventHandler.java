package com.team3.socialnews.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface LoginRequiredEventHandler extends EventHandler {

	void onLoginRequired(LoginRequiredEvent loginRequiredEvent);
}
