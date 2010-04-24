package com.team3.socialnews.server.admin;

import java.util.Collection;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.team3.socialnews.server.persistence.AbstractRepository;
import com.team3.socialnews.shared.model.Link;

public class SpirzConfigRepositoryImpl extends AbstractRepository<SpirzConfig> implements SpirzConfigRepository {

	private SpirzConfig instance = null;
	
	@SuppressWarnings("unchecked")
	@Override
	public void put(int dampKnob, int minumumEnergy, int predatorTerritory, double survivalParameter) {
		PersistenceManager pm = getPersistenceManager();
		try {
			Query q = pm.newQuery(SpirzConfig.class);
			Collection<SpirzConfig> savedConfigs = ((Collection<SpirzConfig>)q.execute());
			SpirzConfig config = null;
			if(savedConfigs.size() > 0){
				// updating the stored record and updating self
				config = savedConfigs.iterator().next();
			}
			else{
				// first ever put, create the record
				config = new SpirzConfig();	// use the utility ctor that doesnt try to fetch itself from repo
			}
			config.setDampKnob(dampKnob);
			config.setMinumumEnergy(minumumEnergy);
			config.setPredatorTerritory(predatorTerritory);
			config.setSurvivalParameter(survivalParameter);
			pm.makePersistent(config);
			
			// make sure the instance is up to date
			instance.setDampKnob(dampKnob);
			instance.setMinumumEnergy(minumumEnergy);
			instance.setPredatorTerritory(predatorTerritory);
			instance.setSurvivalParameter(survivalParameter);
		}
		finally {
			pm.close();
		}		
	}

	@SuppressWarnings("unchecked")
	@Override
	public SpirzConfig get() {
		if(instance == null) {
			PersistenceManager pm = getPersistenceManager();
			try {
				Query q = pm.newQuery(SpirzConfig.class);
				Collection<SpirzConfig> savedConfigs = ((Collection<SpirzConfig>)q.execute());
				if(savedConfigs.size() > 0){
					instance = savedConfigs.iterator().next();
				}
				else {
					// first ever get, create instance and put default values 
					instance = new SpirzConfig();
					instance.setDampKnob(50);
					instance.setMinumumEnergy(1);
					instance.setPredatorTerritory(77);
					instance.setSurvivalParameter(22);
					put(50, 1, 77, 22);
					// technically this will be the only config record ever created
				}
			}
			finally {
				pm.close();
			}
		}
		return instance;		
	}
	
	@SuppressWarnings("unchecked")
	public int getHotLinkCount() {
		PersistenceManager pm = getPersistenceManager();
		try {
			Query hotLinkIds = pm.newQuery("select id from " + Link.class.getName() + " " +
					"where hot == true ");
			return ((Collection<Long>)hotLinkIds.execute()).size();
		}
		finally{
			pm.close();
		}
	}
}
