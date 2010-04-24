package com.team3.socialnews.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.team3.socialnews.shared.model.Link;

public class ShowLinkCommentsRequestedEvent extends
		GwtEvent<ShowLinkCommentsRequestedEventHandler> {

	public static Type<ShowLinkCommentsRequestedEventHandler> TYPE = 
		new Type<ShowLinkCommentsRequestedEventHandler>();
	
	Link link;
	
	public ShowLinkCommentsRequestedEvent(Link link) {
		this.link = link;
	}

	@Override
	protected void dispatch(ShowLinkCommentsRequestedEventHandler handler) {
		handler.onShowLinkRequest(this);
		
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ShowLinkCommentsRequestedEventHandler> getAssociatedType() {
		return TYPE;
	}
	
	public Link getLink() {
		return link;
	}

}
