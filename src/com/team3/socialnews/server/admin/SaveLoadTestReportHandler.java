package com.team3.socialnews.server.admin;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.ActionException;

import com.team3.socialnews.client.admin.SaveLoadTestReport;
import com.team3.socialnews.client.admin.SaveLoadTestReportResult;
import com.team3.socialnews.server.LoginManager;
import com.team3.socialnews.server.RequiresLogin;
import com.team3.socialnews.server.guice.GuiceFactory;
import com.team3.socialnews.shared.admin.LoadTestReport;
import com.team3.socialnews.shared.model.LocalUser;


public class SaveLoadTestReportHandler implements ActionHandler<SaveLoadTestReport, SaveLoadTestReportResult> {

	@Override
	@RequiresLogin
	public SaveLoadTestReportResult execute(SaveLoadTestReport action, ExecutionContext context)
			throws ActionException {
		LoginManager manager =  GuiceFactory.getInjector().getInstance(LoginManager.class);
		LocalUser user = manager.getLocalUser();
		
		if (manager.isGaeLoggedIn() && user.getIsAdmin()) {
			LoadTestReportRepository linkRepo = GuiceFactory.getInjector().getInstance(LoadTestReportRepository.class);
			LoadTestReport report = new LoadTestReport(
					action.getNumberOfRequests(), action.getReqsPerSec(), action.getStart(), action.getEnd(), action.getNickname(),
					action.getSucceededRequests(), action.getFailedRequests(), action.isLastRequestSent());
			linkRepo.put(report);
		}
		return new SaveLoadTestReportResult();
	}

	@Override
	public Class<SaveLoadTestReport> getActionType() {
		return SaveLoadTestReport.class;
	}

	@Override
	public void rollback(SaveLoadTestReport arg0, SaveLoadTestReportResult arg1,
			ExecutionContext arg2) throws ActionException {
		// TODO Auto-generated method stub	
	}

}
