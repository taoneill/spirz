package com.team3.socialnews.shared.admin;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;

import com.googlecode.objectify.annotation.Unindexed;

public class LoadTestReport implements Serializable  {
	
	private static final long serialVersionUID = -100372154547365802L;

	@Id
	private Long id;
	
	@Unindexed
	private int numberOfRequests;
	
	@Unindexed
	private double reqsPerSec;

	@Unindexed
	private Date start;

	@Unindexed
	private Date end;

	@Unindexed
	private String nickname;

	@Unindexed
	private int failedRequests;

	@Unindexed
	private int succeededRequests;
	
	@Unindexed
	private boolean lastRequestSent;

	LoadTestReport() {}
	
	public LoadTestReport(int numberOfRequests, double reqsPerSec, Date start, Date end, String nickname, int succeededRequests, int failedRequests, boolean lastRequestSent) {
		this.numberOfRequests = numberOfRequests;
		this.reqsPerSec = reqsPerSec; 
		this.start = start;
		this.end = end;
		this.nickname = nickname;
		this.succeededRequests = succeededRequests;
		this.failedRequests = failedRequests;
		this.lastRequestSent = lastRequestSent;
	}
	
	public Long getId(){
		return id;
	}

	public String getReqsPerSecStr() {
		String s = String.valueOf(this.reqsPerSec);
		if(s.length()>5)
			return s.substring(0, 4);
		else
			return s;
	}
		
	public String getSecPerReq() {
		if(getDuration() > 0){
			String s = String.valueOf(((double)getDuration())/(succeededRequests + failedRequests));
			if(s.length()>5)
				return s.substring(0, 4);
			else
				return s;
		}
		return "0";
	}
	
	public int getDuration() {
		return (int)(end.getTime() - start.getTime())/1000 ;
	}

	public boolean isFinished() {
		int reqsCompleted = succeededRequests + failedRequests;
		return (reqsCompleted == numberOfRequests) && lastRequestSent; 
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
