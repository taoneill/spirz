package com.team3.socialnews.client.admin;


import net.apptao.highway.shared.dispatch.HwyResult;

public class GetConfigResult implements HwyResult {

	private int dampKnob;
	private double survivalParameter;
	private int predatorTerritory;
	private int minumumEnergy;
	private int hotLinkCount;
	private double oldestHitProbability;
	private double newestHitProbability;
	private double territoryOverflowProbability;
	
	public GetConfigResult() { 
	}
	
	public GetConfigResult(int dampKnob, double survivalParameter, int predatorTerritory, 
			int minumumEnergy, int hotLinkCount, double oldestHitProbability, double newestHitProbability,
			double territoryOverflow) {
		this.dampKnob = dampKnob;
		this.survivalParameter = survivalParameter;
		this.predatorTerritory = predatorTerritory;
		this.minumumEnergy = minumumEnergy;
		this.setHotLinkCount(hotLinkCount);
		setOldestHitProbability(oldestHitProbability);
		setNewestHitProbability(newestHitProbability);
		setTerritoryOverflowProbability(territoryOverflow);
	}

	public void setDampKnob(int dampKnob) {
		this.dampKnob = dampKnob;
	}

	public int getDampKnob() {
		return dampKnob;
	}

	public void setSurvivalParameter(double survivalParameter) {
		this.survivalParameter = survivalParameter;
	}

	public double getSurvivalParameter() {
		return survivalParameter;
	}

	public void setPredatorTerritory(int predatorTerritory) {
		this.predatorTerritory = predatorTerritory;
	}

	public int getPredatorTerritory() {
		return predatorTerritory;
	}

	public void setMinumumEnergy(int minumumEnergy) {
		this.minumumEnergy = minumumEnergy;
	}

	public int getMinumumEnergy() {
		return minumumEnergy;
	}

	public void setHotLinkCount(int hotLinkCount) {
		this.hotLinkCount = hotLinkCount;
	}

	public int getHotLinkCount() {
		return hotLinkCount;
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

	public void setTerritoryOverflowProbability(double territoryOverflowProbability) {
		this.territoryOverflowProbability = territoryOverflowProbability;
	}

	public double getTerritoryOverflowProbability() {
		return territoryOverflowProbability;
	}
}
