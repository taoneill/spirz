package com.team3.socialnews.client.admin;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.team3.socialnews.client.view.AbstractView;
import com.team3.socialnews.client.view.RoundedPanel;
import com.team3.socialnews.shared.admin.LoadTestReport;

public class AdminPanel extends AbstractView implements AdminPresenter.Display {

	private static AdminPanelUiBinder uiBinder = GWT
			.create(AdminPanelUiBinder.class);

	interface AdminPanelUiBinder extends UiBinder<Widget, AdminPanel> {
	}

	@UiField
	RoundedPanel roundedPanel;
	@UiField
	TextBox dampingKnob;
	@UiField
	TextBox survivalParameter;
	@UiField
	TextBox territory;
	@UiField
	Button saveConfigButton;
	@UiField
	Label oldestHitProbability;
	@UiField
	Label newestHitProbability;
	@UiField
	Label territoryOverflow;	
	@UiField
	Label hotLinksCount;
	
	@UiField
	Button loadTestButton;
	@UiField
	TextBox reqsPerSecText;
	@UiField
	TextBox durationText;
	@UiField
	Label reportTitle;
	@UiField
	Label duration;
	@UiField
	Label requestsSent;
	@UiField
	Label succeeded;
	@UiField
	Label failed;
	@UiField
	Label reqsSentPerSec;
	@UiField
	Label responseTime;
	@UiField
	VerticalPanel loadTestReport;
	@UiField
	Anchor saveReport;
	@UiField
	CheckBox waitForResponse;
	
	HandlerManager eventBus;

	public AdminPanel() {
		initWidget(uiBinder.createAndBindUi(this));
		this.loadTestButton.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				setStarted(true);

				duration.setText(" seconds elapsed");
				reqsSentPerSec.setText(" reqs sent per second");
				requestsSent.setText(" requests sent ");
				succeeded.setText(" requests succeeded ");
				failed.setText(" requests failed");
				
				responseTime.setText(" s/req");
			}
		});
		this.saveReport.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				saveReport.setText("Saving...");
			}
		});
		loadTestButton.setText("Start load test");
		saveReport.setVisible(false);
	}
	
	public void setStarted(boolean started){
		if(started){
			loadTestButton.setEnabled(false);
			this.loadTestReport.setVisible(true);
			saveReport.setVisible(true);
		}
		else {
			loadTestButton.setEnabled(true);
			
		}
	}
	
	public void setEventBus(HandlerManager eventBus) {
		this.eventBus = eventBus;
	}

	@Override
	public HasClickHandlers getLoadTestButton() {
		return loadTestButton;
	}
	
	@Override
	public HasClickHandlers getSaveReportButton() {
		return saveReport;
	}

	@Override
	public HasValue<String> getReqsPerSecTarget() {
		return this.reqsPerSecText;
	}

	@Override
	public HasValue<String> getDurationTarget() {
		return this.durationText;
	}

	@Override
	public Widget asWidget() {
		return this;
	}

	@Override
	public void setReport(LoadTestReport report) {
		this.duration.setText(report.getDuration() + " seconds elapsed");
		this.requestsSent.setText(report.getNumberOfRequests() + " requests sent ");
		this.reqsSentPerSec.setText(report.getReqsPerSecStr() + " reqs sent per second");
		
		this.succeeded.setText(report.getSucceededRequests() + " requests succeeded ");
		this.failed.setText(report.getFailedRequests() + " requests failed");
		
		this.responseTime.setText(report.getSecPerReq() + " s/req");
	}

	@Override
	public void hideSaveReportButton() {
		this.saveReport.setVisible(false);
	}

	@Override
	public void resetSaveReportButton() {
		saveReport.setText("Save report");
	}

	@Override
	public boolean getWaitForResponse() {
		return this.waitForResponse.getValue();
	}

	@Override
	public void setReqsPerSecTarget(int i) {
		this.reqsPerSecText.setValue(i+"");
	}

	@Override
	public void setVoteParameters(int dampKnob, double survivalParameter, int predatorTerritory, int minumumEnergy) {
		this.dampingKnob.setValue(dampKnob+"");
		this.survivalParameter.setValue(survivalParameter+"");
		this.territory.setValue(predatorTerritory+"");
	}
	
	@Override
	public HasClickHandlers getSaveConfigButton() {
		return saveConfigButton;
	}

	@Override
	public int getDampKnob() {
		return Integer.parseInt(dampingKnob.getValue());
	}

	@Override
	public int getPredatorTerritory() {
		return Integer.parseInt(territory.getValue());		
	}

	@Override
	public double getSurvivalParameter() {
		return Double.parseDouble(survivalParameter.getValue());
	}

	@Override
	public void setConfigSaveSuccess(double top25OldestHitProb,
			double top25NewestHitProb, double territoryOverflow) {
		saveConfigButton.setText("Saved");
		this.oldestHitProbability.setText("The 25% oldest: at least " + Math.floor(top25OldestHitProb*100) +"% of the time. ");
		this.newestHitProbability.setText("The 25% newest oldest: at least " + Math.floor(top25NewestHitProb*100) +"% of the time.");
		this.territoryOverflow.setText(Math.floor(territoryOverflow*100) +"% of the damping votes are allocated at random.");
	}

	@Override
	public void setSiteStatus(int hotLinkCount, double oldestHitProbability,
			double newestHitProbability, double territoryOverflow) {
		hotLinksCount.setText(hotLinkCount + " hot links.");
		this.oldestHitProbability.setText("The 25% oldest: at least " + Math.floor(oldestHitProbability*100) +"% of the time. ");
		this.newestHitProbability.setText("The 25% newest oldest: at least " + Math.floor(newestHitProbability*100) +"% of the time.");
		this.territoryOverflow.setText(Math.floor(territoryOverflow*100) +"% of the damping votes are allocated at random.");
	}
}
