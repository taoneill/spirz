package com.team3.socialnews.client.admin;

import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.presenter.client.EventBus;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasValue;
import com.google.inject.Inject;
import com.team3.socialnews.client.LoginManager;
import com.team3.socialnews.client.event.LoadTestRequestSentEvent;
import com.team3.socialnews.client.event.LoadTestRequestSentEventHandler;
import com.team3.socialnews.client.event.LoadTestResponseEvent;
import com.team3.socialnews.client.event.LoadTestResponseEventHandler;
import com.team3.socialnews.client.presenter.AbstractPresenter;
import com.team3.socialnews.shared.admin.LoadTestReport;
import com.team3.socialnews.shared.admin.LoadTester;
import com.team3.socialnews.shared.model.LoginInfo;

public class AdminPresenter extends AbstractPresenter<AdminPresenter.Display> {
	private final DispatchAsync dispatchAsync;
	private LoadTester loadTest = null;
	protected String submitter; 
	private LoadTestReport report = null;
	
	public interface Display extends AbstractPresenter.Display {
		HasClickHandlers getLoadTestButton();
		HasValue<String> getReqsPerSecTarget();
		HasValue<String> getDurationTarget();
		void setReport(LoadTestReport report);
		void setStarted(boolean started);
		HasClickHandlers getSaveReportButton();
		void hideSaveReportButton();
		void resetSaveReportButton();
		boolean getWaitForResponse();
		void setReqsPerSecTarget(int i);
		HasClickHandlers getSaveConfigButton();
		void setVoteParameters(int dampKnob, double survivalParameter, int predatorTerritory, int minumumEnergy);
		int getDampKnob();
		double getSurvivalParameter();
		int getPredatorTerritory();
		void setConfigSaveSuccess(double top25OldestHitProb, double  top25NewestHitProb, double territoryOverflowProb);
		void setSiteStatus(int hotLinkCount, double oldestHitProbability,
				double newestHitProbability, double territoryOverflowProbability);
	}
	
	@Inject
    public AdminPresenter(
    		final Display display, 
    		final EventBus eventBus,
    		DispatchAsync dispatchAsync,
    		LoginManager login,
    		LoadTester loadTester)
    {
		super(display, eventBus, login);
		this.dispatchAsync = dispatchAsync;
		this.loadTest = loadTester;
    }
	
	@Override
	protected void onLoginInfoFetched(LoginInfo result) {
		submitter = result.getNickname();
	}

	@Override
	public void onBind(){
		super.onBind();
		getVoteParameters();
		HasClickHandlers loadTestButton = display.getLoadTestButton();
		loadTestButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				startLoadTest();
			}
		});
		
		HasClickHandlers saveReportButton = display.getSaveReportButton();
		saveReportButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				saveReport();
			}
		});

		eventBus.addHandler(LoadTestRequestSentEvent.TYPE, new LoadTestRequestSentEventHandler(){
			@Override
			public void onLoadTestRequestSent(LoadTestRequestSentEvent event) {
				handleLoadTestEvent(event.getReport());
			}
		});
		
		eventBus.addHandler(LoadTestResponseEvent.TYPE, new LoadTestResponseEventHandler(){
			@Override
			public void onLoadTestResponse(LoadTestResponseEvent event) {
				handleLoadTestEvent(event.getReport());
			}
		});
		
		HasClickHandlers saveConfigButton = display.getSaveConfigButton();
		saveConfigButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				saveConfig();
			}
		});

	}

	protected void handleLoadTestEvent(LoadTestReport report) {
		display.setReport(report);
		this.report = report;
		if(report.isFinished()) display.setStarted(false);	// go back to "Start load test"		
	}


	private void startLoadTest() {
		try{
		this.loadTest.start(Integer.parseInt(display.getReqsPerSecTarget().getValue()), 
				Integer.parseInt(display.getDurationTarget().getValue()),
				display.getWaitForResponse(),
				submitter);
		}
		catch(Throwable e){
			display.setReqsPerSecTarget(50);
		}
	}

	
	protected void saveReport() {
		SaveLoadTestReport action = new SaveLoadTestReport(
				report.getNumberOfRequests(), report.getReqsPerSec(), report.getStart(), report.getEnd(), report.getNickname(),
				report.getSucceededRequests(), report.getFailedRequests(), report.isLastRequestSent()); 
		dispatchAsync.execute(action, new AsyncCallback<SaveLoadTestReportResult>(){
			@Override
			public void onFailure(Throwable caught) {
				display.resetSaveReportButton();
			}
			@Override
			public void onSuccess(SaveLoadTestReportResult result) {
				display.hideSaveReportButton();
			}
		});
	}
	

	protected void saveConfig() {
		dispatchAsync.execute(new SetConfig(display.getDampKnob(), 
				display.getSurvivalParameter(), display.getPredatorTerritory(), 1), new AsyncCallback<SetConfigResult>() {
			public void onFailure( Throwable e ) {
				
            }

            public void onSuccess(SetConfigResult result) {
                display.setConfigSaveSuccess(result.getOldestHitProbability(), result.getNewestHitProbability(), result.getTerritoryOverflow());
            }
		});
	}

	
	private void getVoteParameters(){
		dispatchAsync.execute(new GetConfig(), new AsyncCallback<GetConfigResult>() {
			public void onFailure( Throwable e ) {
				
            }

            public void onSuccess(GetConfigResult result) {
                display.setVoteParameters(result.getDampKnob(),result.getSurvivalParameter(), 
                		result.getPredatorTerritory(), result.getMinumumEnergy());
                display.setSiteStatus(result.getHotLinkCount(), result.getOldestHitProbability(), 
                		result.getNewestHitProbability(), result.getTerritoryOverflowProbability());
            }
		});
	}

	@Override
	public void revealDisplay() {
	}

	@Override
	protected void onUnbind() {
		
	}

	@Override
	public void refreshDisplay() {
		// TODO Auto-generated method stub	
	}
}
