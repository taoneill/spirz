package com.team3.socialnews.server.admin;

import java.util.ArrayList;
import java.util.List;

import net.apptao.highway.server.Highway;

import com.google.inject.Inject;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.team3.socialnews.server.persistence.AbstractRepository;
import com.team3.socialnews.shared.model.Link;

public class SpirzConfigRepositoryHwyImpl extends AbstractRepository<SpirzConfig> implements SpirzConfigRepository {

	private SpirzConfig instance = null;
	private Highway hwy;
	
	@Inject
	public SpirzConfigRepositoryHwyImpl(Highway hwy){
		this.hwy = hwy;
	}
	
	@Override
	public void put(int dampKnob, int minumumEnergy, int predatorTerritory, double survivalParameter) {
		Objectify ofy = hwy.dao();
		SpirzConfig config = ofy.query(SpirzConfig.class).get();
		if(config == null){
			// first ever put, create the record
			config = new SpirzConfig();	// use the utility ctor that doesnt try to fetch itself from repo
		}
		config.setDampKnob(dampKnob);
		config.setMinumumEnergy(minumumEnergy);
		config.setPredatorTerritory(predatorTerritory);
		config.setSurvivalParameter(survivalParameter);
		ofy.put(config);
		
		// make sure the instance is up to date
		instance.setDampKnob(dampKnob);
		instance.setMinumumEnergy(minumumEnergy);
		instance.setPredatorTerritory(predatorTerritory);
		instance.setSurvivalParameter(survivalParameter);
	}

	@Override
	public SpirzConfig get() {
		if(instance == null) {
			Objectify ofy = hwy.dao();
			instance = ofy.query(SpirzConfig.class).get();
			if(instance == null) {
				// first ever get, create instance and put default values 
				instance = new SpirzConfig();
				instance.setDampKnob(50);
				instance.setMinumumEnergy(1);
				instance.setPredatorTerritory(77);
				instance.setSurvivalParameter(22);
				ofy.put(instance);
				// technically this will be the only config record ever created
			}
		}
		return instance;		
	}
	
	public int getHotLinkCount() {
		Iterable<Key<Link>> hotLinkIds = hwy.dao().query(Link.class).filter("hot", true).fetchKeys();
		List<Key<Link>> keysList = new ArrayList<Key<Link>>(); 
		for(Key<Link> key : hotLinkIds) keysList.add(key);
		return keysList.size();
	}
}
