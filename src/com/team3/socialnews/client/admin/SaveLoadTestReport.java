package com.team3.socialnews.client.admin;

import java.util.Date;

import net.apptao.highway.shared.dispatch.HwyCommand;

public class SaveLoadTestReport implements HwyCommand<SaveLoadTestReportResult> {

	private static final long serialVersionUID = 17643038564372040L;

	/** For serialization */
	SaveLoadTestReport() {}
	
	//private LoadTestReport report; 

	private int numberOfRequests;
	
	private double reqsPerSec;

	private Date start;

	private Date end;

	private String nickname;

	private int failedRequests;

	private int succeededRequests;
	
	private boolean lastRequestSent;

	public SaveLoadTestReport(int numberOfRequests, double reqsPerSec, Date start, Date end, String nickname, int succeededRequests, int failedRequests, boolean lastRequestSent) {
		this.setNumberOfRequests(numberOfRequests);
		this.setReqsPerSec(reqsPerSec); 
		this.setStart(start);
		this.setEnd(end);
		this.setNickname(nickname);
		this.setSucceededRequests(succeededRequests);
		this.setFailedRequests(failedRequests);
		this.setLastRequestSent(lastRequestSent);
	}

	public void setNumberOfRequests(int numberOfRequests) {
		this.numberOfRequests = numberOfRequests;
	}

	public int getNumberOfRequests() {
		return numberOfRequests;
	}

	public void setReqsPerSec(double reqsPerSec) {
		this.reqsPerSec = reqsPerSec;
	}

	public double getReqsPerSec() {
		return reqsPerSec;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getStart() {
		return start;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public Date getEnd() {
		return end;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getNickname() {
		return nickname;
	}

	public void setFailedRequests(int failedRequests) {
		this.failedRequests = failedRequests;
	}

	public int getFailedRequests() {
		return failedRequests;
	}

	public void setSucceededRequests(int succeededRequests) {
		this.succeededRequests = succeededRequests;
	}

	public int getSucceededRequests() {
		return succeededRequests;
	}

	public void setLastRequestSent(boolean lastRequestSent) {
		this.lastRequestSent = lastRequestSent;
	}

	public boolean isLastRequestSent() {
		return lastRequestSent;
	}
	

}
