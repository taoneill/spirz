package com.team3.socialnews.server.guice;

import net.customware.gwt.dispatch.server.guice.ActionHandlerModule;

import com.google.inject.Singleton;
import com.team3.socialnews.client.admin.GetConfig;
import com.team3.socialnews.client.admin.SaveLoadTestReport;
import com.team3.socialnews.client.admin.SetConfig;
import com.team3.socialnews.server.admin.GetConfigHandler;
import com.team3.socialnews.server.admin.LoadTestReportRepository;
import com.team3.socialnews.server.admin.LoadTestReportRepositoryImpl;
import com.team3.socialnews.server.admin.SaveLoadTestReportHandler;
import com.team3.socialnews.server.admin.SetConfigHandler;
import com.team3.socialnews.server.admin.SpirzConfigRepository;
import com.team3.socialnews.server.admin.SpirzConfigRepositoryImpl;

public class AdminModule extends ActionHandlerModule  {
	@Override
	protected void configureHandlers() {
		this.bindHandler(SaveLoadTestReport.class, SaveLoadTestReportHandler.class);
		bind(LoadTestReportRepository.class).to(LoadTestReportRepositoryImpl.class).in(Singleton.class);
		this.bindHandler(GetConfig.class, GetConfigHandler.class);
		this.bindHandler(SetConfig.class, SetConfigHandler.class);
		bind(SpirzConfigRepository.class).to(SpirzConfigRepositoryImpl.class).in(Singleton.class);
	}
}
