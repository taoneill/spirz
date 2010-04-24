package com.team3.socialnews.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface RegistrationCompletedEventHandler extends EventHandler {

	void onRegistrationCompleted(
			RegistrationCompletedEvent registrationCompletedEvent);

}
