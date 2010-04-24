package com.team3.socialnews.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.team3.socialnews.shared.model.Link;

public class LinkSubmittedEvent extends GwtEvent<LinkSubmittedEventHandler> {

	public static Type<LinkSubmittedEventHandler> TYPE = new Type<LinkSubmittedEventHandler>();
	
	private Link link;
	
	public LinkSubmittedEvent(Link link) {
		this.setLink(link);
	}
	
	@Override
	protected void dispatch(LinkSubmittedEventHandler handler) {
		handler.onLinkSubmitted(this);
	}

	@Override
	public GwtEvent.Type<LinkSubmittedEventHandler> getAssociatedType() {
		return TYPE;
	}

	public void setLink(Link link) {
		this.link = link;
	}

	public Link getLink() {
		return link;
	}

}
