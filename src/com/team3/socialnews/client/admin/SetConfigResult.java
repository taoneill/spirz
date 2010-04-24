package com.team3.socialnews.client.admin;

import net.customware.gwt.dispatch.shared.Result;

public class SetConfigResult implements Result {
	private double oldestHitProbability;
	private double newestHitProbability;
	private double territoryOverflow;

	public SetConfigResult() { 
	}

	public SetConfigResult(double oldestHitProbability, double newestHitProbability, double territoryOverflow) {
		this.setTerritoryOverflow(territoryOverflow);
		this.setOldestHitProbability(oldestHitProbability);
		this.setNewestHitProbability(newestHitProbability);
	}

	public void setOldestHitProbability(double oldestHitProbability) {
		this.oldestHitProbability = oldestHitProbability;
	}

	public double getOldestHitProbability() {
		return oldestHitProbability;
	}

	public void setNewestHitProbability(double newestHitProbability) {
		this.newestHitProbability = newestHitProbability;
	}

	public double getNewestHitProbability() {
		return newestHitProbability;
	}

	public void setTerritoryOverflow(double territoryOverflow) {
		this.territoryOverflow = territoryOverflow;
	}

	public double getTerritoryOverflow() {
		return territoryOverflow;
	}
}
