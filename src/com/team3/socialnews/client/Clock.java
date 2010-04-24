package com.team3.socialnews.client;

import net.customware.gwt.presenter.client.EventBus;

import com.google.gwt.user.client.Timer;
import com.google.inject.Inject;
import com.team3.socialnews.client.event.ClockTickEvent;

public class Clock {

	private Timer timer;
	private EventBus eventBus;
	private static final int REFRESH_INTERVAL = 1000*15; // 15 seconds
	
	@Inject
	public Clock(EventBus eventBus){
		this.eventBus = eventBus;
	}
	
	public void startTick(){
		if(timer == null) {
			timer = new Timer(){
				@Override
				public void run(){
					eventBus.fireEvent(new ClockTickEvent());
				}
			};
			timer.scheduleRepeating(REFRESH_INTERVAL);			
		}
	}
}
