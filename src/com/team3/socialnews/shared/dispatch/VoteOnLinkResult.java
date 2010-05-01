package com.team3.socialnews.shared.dispatch;

import net.apptao.highway.shared.dispatch.HwyResult;

public class VoteOnLinkResult implements HwyResult {

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
