package com.team3.socialnews.server.admin;

import net.apptao.highway.server.dispatch.HwyHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.ActionException;

import com.team3.socialnews.client.admin.SetConfig;
import com.team3.socialnews.client.admin.SetConfigResult;
import com.team3.socialnews.server.LoginManager;
import com.team3.socialnews.server.RequiresLogin;
import com.team3.socialnews.server.guice.GuiceFactory;
import com.team3.socialnews.server.vote.LinkPredator;
import com.team3.socialnews.shared.model.LocalUser;


public class SetConfigHandler implements HwyHandler<SetConfig, SetConfigResult> {

	@Override
	@RequiresLogin
	public SetConfigResult execute(SetConfig action, ExecutionContext context)
			throws ActionException {
		LoginManager manager =  GuiceFactory.getInjector().getInstance(LoginManager.class);
		LocalUser user = manager.getLocalUser();
		
		if (manager.isGaeLoggedIn() && user.getIsAdmin()) {
			SpirzConfigRepository config = GuiceFactory.getInjector().getInstance(SpirzConfigRepository.class);
			LinkPredator predator = GuiceFactory.getInjector().getInstance(LinkPredator.class);
			config.put(action.getDampKnob(), action.getMinumumEnergy(), 
					action.getPredatorTerritory(), action.getSurvivalParameter());
			return new SetConfigResult(predator.getOldestHitProbability(0.25), predator.getNewestHitProbability(0.25), predator.getTerritoryOverflowProbability());
		}
		return new SetConfigResult();
	}

	@Override
	public Class<SetConfig> getActionType() {
		return SetConfig.class;
	}

	@Override
	public void rollback(SetConfig arg0, SetConfigResult arg1,
			ExecutionContext arg2) throws ActionException {
		// TODO Auto-generated method stub	
	}

}
