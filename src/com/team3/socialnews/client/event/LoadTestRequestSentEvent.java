package com.team3.socialnews.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.team3.socialnews.shared.admin.LoadTestReport;

public class LoadTestRequestSentEvent extends GwtEvent<LoadTestRequestSentEventHandler> {
	
	public static Type<LoadTestRequestSentEventHandler> TYPE = new Type<LoadTestRequestSentEventHandler>();
	private LoadTestReport report;
	
	public LoadTestRequestSentEvent(LoadTestReport report){
		this.setReport(report);
	}


	@Override
	protected void dispatch(LoadTestRequestSentEventHandler handler) {
		handler.onLoadTestRequestSent(this);		
	}

	@Override
	public Type<LoadTestRequestSentEventHandler> getAssociatedType() {
		return TYPE;
	}


	public void setReport(LoadTestReport report) {
		this.report = report;
	}


	public LoadTestReport getReport() {
		return report;
	}
	
}
