package com.team3.socialnews.shared.dispatch;

import net.customware.gwt.dispatch.shared.Action;

import com.team3.socialnews.client.Order;



public class GetLinks implements Action<GetLinksResult> {

	private Order order;
	private long start, finish;
	
	/** For serialization */
	GetLinks() {}
	
	/**
	 * @param order The order in which the links are to be ordered.
	 * @param start The starting index of the links.
	 * @param finish The end index of the links.
	 */
	public GetLinks(Order order, long start, long finish) {
		this.order = order;
		this.start = start;
		this.finish = finish;
	}
	
	public Order getOrder(){
		return order;
	}

	public long getFinish() {
		return finish;
	}

	public long getStart() {
		return start;
	}
	
}
