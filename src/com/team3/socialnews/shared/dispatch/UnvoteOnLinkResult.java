package com.team3.socialnews.shared.dispatch;

import net.customware.gwt.dispatch.shared.Result;

public class UnvoteOnLinkResult implements Result {

	private Integer newLinkEnergy;
	
	UnvoteOnLinkResult() { 
	}
	
	public UnvoteOnLinkResult(Integer newLinkEnergy) {
		this.newLinkEnergy = newLinkEnergy;
	}

	public Integer getNewLinkEnergy() {
		return newLinkEnergy;
	}
	
	public void setNewLinkEnergy(Integer newLinkEnergy) {
		this.newLinkEnergy = newLinkEnergy;
	}
}
