package com.team3.socialnews.client.admin;

import net.customware.gwt.dispatch.shared.Action;

public class SetConfig implements Action<SetConfigResult> {
	private int dampKnob;
	private double survivalParameter;
	private int predatorTerritory;
	private int minumumEnergy;
	
	public SetConfig() { 
	}
	
	public SetConfig(int dampKnob, double survivalParameter, int predatorTerritory, int minumumEnergy) {
		this.dampKnob = dampKnob;
		this.survivalParameter = survivalParameter;
		this.predatorTerritory = predatorTerritory;
		this.minumumEnergy = minumumEnergy;
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
}
