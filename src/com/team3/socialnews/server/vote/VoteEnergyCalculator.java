package com.team3.socialnews.server.vote;

import java.util.ArrayList;

import com.google.inject.Inject;
import com.team3.socialnews.server.admin.SpirzConfigRepository;
import com.team3.socialnews.shared.model.LinkVote;

public class VoteEnergyCalculator {
	private ArrayList<VoteEnergyModifier> modifiers = new ArrayList<VoteEnergyModifier>();
	private SpirzConfigRepository config;

	@Inject
	public VoteEnergyCalculator(SpirzConfigRepository config, LinkAgeModifier linkAge) {
		modifiers.add(linkAge);
		this.config = config;
	}
	
	/**
	 * How much energy should a vote give to a link?
	 */
	public int calculateEnergy(LinkVote vote) {
		Integer voteEnergy = 1; // base contribution
		for(VoteEnergyModifier modifier : modifiers) {
			voteEnergy += modifier.getModifierEffect(vote);
		}
		return voteEnergy;
	}
	
	/**
	 * How hard should the energy of a damp vote candidate fall
	 * compared to the original vote? 
	 * Returns a negative value.
	 */
	public int getDampEnergy(LinkVote sourceVote) {
		 return - sourceVote.getVoteEnergyContribution() * config.get().getDampKnob()/100;
	}
}
