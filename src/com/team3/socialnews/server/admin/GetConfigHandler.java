package com.team3.socialnews.server.admin;

import net.apptao.highway.server.dispatch.HwyHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.ActionException;

import com.team3.socialnews.client.admin.GetConfig;
import com.team3.socialnews.client.admin.GetConfigResult;
import com.team3.socialnews.server.LoginManager;
import com.team3.socialnews.server.RequiresLogin;
import com.team3.socialnews.server.guice.GuiceFactory;
import com.team3.socialnews.server.vote.LinkPredator;
import com.team3.socialnews.shared.model.LocalUser;

public class GetConfigHandler implements HwyHandler<GetConfig, GetConfigResult> {

	@Override
	@RequiresLogin
	public GetConfigResult execute(GetConfig action, ExecutionContext context)
			throws ActionException {
		LoginManager manager =  GuiceFactory.getInjector().getInstance(LoginManager.class);
		LocalUser user = manager.getLocalUser();
		
		if (manager.isGaeLoggedIn() && user.getIsAdmin()) {
			SpirzConfigRepository configRepo = GuiceFactory.getInjector().getInstance(SpirzConfigRepository.class);
			SpirzConfig config = configRepo.get();
			LinkPredator predator = GuiceFactory.getInjector().getInstance(LinkPredator.class);
			return new GetConfigResult(config.getDampKnob(), config.getSurvivalParameter(), 
					config.getPredatorTerritory(), config.getMinumumEnergy(), configRepo.getHotLinkCount(),
					predator.getOldestHitProbability(0.25), predator.getNewestHitProbability(0.25), predator.getTerritoryOverflowProbability());
		}
		return new GetConfigResult();
	}

	@Override
	public Class<GetConfig> getActionType() {
		return GetConfig.class;
	}

	@Override
	public void rollback(GetConfig arg0, GetConfigResult arg1,
			ExecutionContext arg2) throws ActionException {
		// TODO Auto-generated method stub	
	}

}
