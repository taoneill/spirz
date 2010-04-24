package com.team3.socialnews.server.admin;

import javax.jdo.PersistenceManager;

import com.team3.socialnews.server.persistence.AbstractRepository;
import com.team3.socialnews.shared.admin.LoadTestReport;

public class LoadTestReportRepositoryImpl extends AbstractRepository<LoadTestReport> implements LoadTestReportRepository {

	@Override
	public void put(LoadTestReport report) {
		PersistenceManager pm = getPersistenceManager();
		try {
			pm.makePersistent(report);
		}
		finally {
			pm.close();
		}
	}	
}
