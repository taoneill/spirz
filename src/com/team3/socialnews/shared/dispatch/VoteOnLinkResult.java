package com.team3.socialnews.shared.dispatch;

import net.customware.gwt.dispatch.shared.Result;

public class VoteOnLinkResult implements Result {

	private Integer newLinkEnergy;
	
	VoteOnLinkResult() { 
	}
	
	public VoteOnLinkResult(Integer newLinkEnergy) {
		this.newLinkEnergy = newLinkEnergy;
	}
	
	public Integer getNewLinkEnergy() {
		return newLinkEnergy;
	}

	public void setNewLinkEnergy(Integer newLinkEnergy) {
		this.newLinkEnergy = newLinkEnergy;
	}
	
}
