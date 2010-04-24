package com.team3.socialnews.server.vote;

import com.team3.socialnews.shared.model.LinkVote;

public class LinkAgeModifier implements VoteEnergyModifier {

	@Override
	public Integer getModifierEffect(LinkVote vote) {
		Long age = vote.getVoteDate().getTime() - vote.getLinkCreateDate().getTime();
		if (age < 60000){
			// a minute old
			return 6;
		}			
		else if(age < 3600000){
			// an hour
			return 3;
		}
		else {
			return 0;
		}
	}

}
