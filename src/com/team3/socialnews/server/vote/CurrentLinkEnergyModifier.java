package com.team3.socialnews.server.vote;

import com.team3.socialnews.shared.model.LinkVote;

public class CurrentLinkEnergyModifier implements VoteEnergyModifier {

	@Override
	public Integer getModifierEffect(LinkVote vote) {
		Integer energy = vote.getLinkEnergy();
		if(energy < 10) {
			return 3;
		} else if (energy < 100) {
			return 1;
		}
		else {
			return 0;
		}
	}

}
