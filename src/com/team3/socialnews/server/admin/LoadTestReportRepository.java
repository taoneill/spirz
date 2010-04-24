package com.team3.socialnews.server.admin;

import com.team3.socialnews.shared.admin.LoadTestReport;

public interface LoadTestReportRepository {

	void put(LoadTestReport report);
}