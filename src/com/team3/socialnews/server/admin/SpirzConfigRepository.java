package com.team3.socialnews.server.admin;


public interface SpirzConfigRepository {

	void put(int dampKnob, int minumumEnergy, int predatorTerritory, double survivalParameter);
	SpirzConfig get();
	int getHotLinkCount();
}