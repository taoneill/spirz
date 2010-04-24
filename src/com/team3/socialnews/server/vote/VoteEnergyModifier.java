package com.team3.socialnews.server.vote;

import com.team3.socialnews.shared.model.LinkVote;

public interface VoteEnergyModifier {
	public Integer getModifierEffect(LinkVote vote);
}
