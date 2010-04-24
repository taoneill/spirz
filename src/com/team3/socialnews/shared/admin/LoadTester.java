package com.team3.socialnews.shared.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.presenter.client.EventBus;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.team3.socialnews.client.Order;
import com.team3.socialnews.client.event.LoadTestRequestSentEvent;
import com.team3.socialnews.client.event.LoadTestRequestSentEventHandler;
import com.team3.socialnews.client.event.LoadTestResponseEvent;
import com.team3.socialnews.client.event.LoadTestResponseEventHandler;
import com.team3.socialnews.shared.dispatch.GetLinks;
import com.team3.socialnews.shared.dispatch.GetLinksResult;
import com.team3.socialnews.shared.model.Link;

public class LoadTester {
	
	private DispatchAsync dispatchAsync;
	private int reqsPerSecTarget;
//	private int durationTarget; // in seconds
	private boolean started = false;
	private Date startDate;
//	private Date stopDate;
	
	private double actualReqsPerSec; 
	private long actualDuration;
	private int numberOfRequests;
	
	private List<Link> linksToSubmit = new ArrayList<Link>();
	private Date targetEndDate;
	private EventBus eventBus;
	private String nickname;
	protected int failedRequests = 0;
	protected int succeededRequests = 0;
	private boolean waitForResponse;
	private boolean lastRequestSent = false;
	
	@Inject
	public LoadTester(DispatchAsync dispatchAsync, EventBus bus){	
		this.dispatchAsync = dispatchAsync;
		this.eventBus = bus;
		
		// blantantly stolen from reddit front page march 16
		linksToSubmit.add(new Link("LG Rigs Fridge to be Environmentally Friendly. But Only When it detects it's in a Laboratory Testing Environment","http://www.smh.com.au/environment/green-fridge-labelled-a-fraud-20100316-qclx.html","", nickname));
		linksToSubmit.add(new Link("NY Times CEO paid $4.9 million (while they cut 8% of their workforce)","http://www.google.com/hostednews/ap/article/ALeqM5gzYTwvd8G8Jdfji1HfDY42azReBQD9EFTND81","", nickname));
		linksToSubmit.add(new Link("Developing the sensitivity that lays golden eggs","http://mettarefuge.wordpress.com/2010/03/16/developing-the-sensitivity-that-lays-golden-eggs/","", nickname));
		linksToSubmit.add(new Link("McCain's new bill: \"...a citizen of the United States...may be detained without criminal charges and without trial\"","http://www.opencongress.org/articles/view/1715-McCain-and-Lieberman-s-Nightmarish-Detention-Bill","", nickname));
		linksToSubmit.add(new Link("Syfy Considers Making Another BSG Spin-Off Series","http://io9.com/5494246/syfy-considers-making-another-bsg-spin+off-series-plus-two-superhero-shows","", nickname));
		linksToSubmit.add(new Link("World's smallest sloth on the verge of extinction","http://news.mongabay.com/2010/0316-hance_fs_pygmythree.html","", nickname));
		linksToSubmit.add(new Link("What's Wrong with the U.S. Education System? - Parental Neglect! If the parent is not participating, the kids stand little chance.","http://globaleconomicanalysis.blogspot.com/2010/03/stupid-in-america-whats-wrong-with-us.html","", nickname));
		linksToSubmit.add(new Link("NASA finds a shrimp, 183 metres (600 feet) under the Antarctic ice. ","http://ca.news.yahoo.com/s/capress/100315/science/science_us_sci_antarctica_sea_life_1","", nickname));
		linksToSubmit.add(new Link("Google appears to drop censorship in China","http://www.msnbc.msn.com/id/35886780/ns/business-world_business/","", nickname));
		linksToSubmit.add(new Link("Michigan's only known wild wolverine found dead","http://www.wtol.com/global/story.asp?s=12143672","", nickname));
		linksToSubmit.add(new Link("Benchmark of Python web servers (WSGI)","http://nichol.as/benchmark-of-python-web-servers","", nickname));
		linksToSubmit.add(new Link("More artists are joining the fight against mountaintop removal coal mining.","http://tenthmil.com/campaigns/arts/still_moving_mountains_the_journey_home","", nickname));
	}
	
	public void start(int reqsPerSecTarget, int durationTarget, boolean waitForResponse, String nickname) throws Throwable {
		if(reqsPerSecTarget > 50){
			throw new Throwable();
		}
		this.reqsPerSecTarget = reqsPerSecTarget;
//		this.durationTarget = durationTarget;
		this.waitForResponse = waitForResponse;
		started = true;
		lastRequestSent = false;
		startDate = new Date();
		this.targetEndDate = (Date) startDate.clone();
		targetEndDate.setTime(targetEndDate.getTime() + durationTarget*1000);
		numberOfRequests = 0;
		
		if(!this.waitForResponse){
			eventBus.addHandler(LoadTestRequestSentEvent.TYPE, new LoadTestRequestSentEventHandler(){
				@Override
				public void onLoadTestRequestSent(LoadTestRequestSentEvent event) {
					//wait a few millis at least will ya? still, lets do this again soon!
					Timer timer = new Timer(){
						@Override
						public void run(){
							go();
						}
					};
					timer.schedule(15);
				}
			});
		}
		else {
			eventBus.addHandler(LoadTestResponseEvent.TYPE, new LoadTestResponseEventHandler(){
				@Override
				public void onLoadTestResponse(LoadTestResponseEvent event) {
					go();
				}
			});
		}
		go();
	}
	
	public void go() {
		if(started && new Date().before(targetEndDate)){	// stop requesting at hard time limit
			long now = new Date().getTime();
			long startTime = startDate.getTime();
			long timeSinceStart = now - startTime;
			actualDuration = timeSinceStart/1000;
			if(actualDuration > 0) actualReqsPerSec = ((double)numberOfRequests/actualDuration); 
			if(reqsPerSecTarget > 0 && actualReqsPerSec > reqsPerSecTarget){	// slow down if above target reqs/sec
				Timer timer = new Timer(){
					@Override
					public void run(){
						go();	// hopefully first condition in if above if enough to
								// prevent infinite loops of waiting
					}
				};
				timer.schedule(1000); // wait a few seconds before reverting back to the details
			}
			else{
				// Perform the next action
				nextAction();
			}
		}
		else {
			this.lastRequestSent = true;
		}
	}

	private void nextAction() {
		numberOfRequests++;
		dispatchAsync.execute(new GetLinks(Order.HOT, 0, 40), new AsyncCallback<GetLinksResult>(){
			@Override
			public void onFailure(Throwable caught) {
				failedRequests++;
				fireEventAndSendReport();
			}
			@Override
			public void onSuccess(GetLinksResult result) {
				succeededRequests++;
				fireEventAndSendReport();	
			}
		});
		LoadTestReport report = new LoadTestReport(numberOfRequests, actualReqsPerSec, startDate, new Date(), nickname, succeededRequests, failedRequests, lastRequestSent);
		eventBus.fireEvent(new LoadTestRequestSentEvent(report));
	}

	protected void fireEventAndSendReport() {
		LoadTestReport report = new LoadTestReport(numberOfRequests, actualReqsPerSec, startDate, new Date(), nickname, succeededRequests, failedRequests, lastRequestSent);
		if(report.isFinished()){
			started = false;
//			stopDate = new Date();			
		}
		eventBus.fireEvent(new LoadTestResponseEvent(report));
	}

	public boolean isStarted() {
		return started;
	}

	public void setActualReqsPerSec(double actualReqsPerSec) {
		this.actualReqsPerSec = actualReqsPerSec;
	}

	public double getActualReqsPerSec() {
		return actualReqsPerSec;
	}

	public void setActualDuration(long actualDuration) {
		this.actualDuration = actualDuration;
	}

	public long getActualDuration() {
		return actualDuration;
	}
}
