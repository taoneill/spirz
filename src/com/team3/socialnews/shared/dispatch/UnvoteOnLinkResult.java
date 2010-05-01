package com.team3.socialnews.shared.dispatch;

import net.apptao.highway.shared.dispatch.HwyResult;

public class UnvoteOnLinkResult implements HwyResult {

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
