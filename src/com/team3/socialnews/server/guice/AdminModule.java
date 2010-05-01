package com.team3.socialnews.server.guice;

import net.apptao.highway.server.guice.HighwayModule;

import com.google.inject.Singleton;
import com.team3.socialnews.client.admin.GetConfig;
import com.team3.socialnews.client.admin.SaveLoadTestReport;
import com.team3.socialnews.client.admin.SetConfig;
import com.team3.socialnews.server.admin.GetConfigHandler;
import com.team3.socialnews.server.admin.LoadTestReportRepository;
import com.team3.socialnews.server.admin.LoadTestReportRepositoryHwyImpl;
import com.team3.socialnews.server.admin.SaveLoadTestReportHandler;
import com.team3.socialnews.server.admin.SetConfigHandler;
import com.team3.socialnews.server.admin.SpirzConfig;
import com.team3.socialnews.server.admin.SpirzConfigRepository;
import com.team3.socialnews.server.admin.SpirzConfigRepositoryHwyImpl;
import com.team3.socialnews.shared.admin.LoadTestReport;

public class AdminModule extends HighwayModule  {
	@Override
	protected void configureModule() {
		register(SpirzConfig.class);
		this.bindCommand(GetConfig.class, GetConfigHandler.class);
		this.bindCommand(SetConfig.class, SetConfigHandler.class);
		bind(SpirzConfigRepository.class).to(SpirzConfigRepositoryHwyImpl.class).in(Singleton.class);
		
		register(LoadTestReport.class);
		this.bindCommand(SaveLoadTestReport.class, SaveLoadTestReportHandler.class);
		bind(LoadTestReportRepository.class).to(LoadTestReportRepositoryHwyImpl.class).in(Singleton.class);
	}
}
