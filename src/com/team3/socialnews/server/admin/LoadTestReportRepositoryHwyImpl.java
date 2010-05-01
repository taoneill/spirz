package com.team3.socialnews.server.admin;

import net.apptao.highway.server.Highway;

import com.google.inject.Inject;
import com.team3.socialnews.server.persistence.AbstractRepository;
import com.team3.socialnews.shared.admin.LoadTestReport;

public class LoadTestReportRepositoryHwyImpl extends AbstractRepository<LoadTestReport> implements LoadTestReportRepository {

	private Highway hwy;
	
	@Inject
	protected LoadTestReportRepositoryHwyImpl(Highway hwy){
		this.hwy = hwy;
	}
	@Override
	public void put(LoadTestReport report) {
		hwy.dao().put(report);
	}	
}
