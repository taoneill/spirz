package com.team3.socialnews.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.team3.socialnews.shared.admin.LoadTestReport;

public class LoadTestResponseEvent extends GwtEvent<LoadTestResponseEventHandler> {
	
	public static Type<LoadTestResponseEventHandler> TYPE = new Type<LoadTestResponseEventHandler>();
	
	private LoadTestReport report;
	
	public LoadTestResponseEvent(LoadTestReport report){
		this.report = report;
	}


	@Override
	protected void dispatch(LoadTestResponseEventHandler handler) {
		handler.onLoadTestResponse(this);		
	}
	
	public LoadTestReport getReport(){
		return this.report;
	}

	@Override
	public Type<LoadTestResponseEventHandler> getAssociatedType() {
		return TYPE;
	}
	
}
