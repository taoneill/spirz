package com.team3.socialnews.client.view;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.team3.socialnews.client.DateFormatter;

public class LinkWidget extends Composite {

	private static LinkWidgetUiBinder uiBinder = GWT
			.create(LinkWidgetUiBinder.class);

	interface LinkWidgetUiBinder extends UiBinder<Widget, LinkWidget> {
	}

	@UiField
	Label numberLabel;
	@UiField
	Anchor titleAnchor;
	@UiField
	FlowPanel detailsPanel;
	@UiField
	Anchor commentsAnchor;
	@UiField
	HorizontalPanel root;
	@UiField
	Anchor arrowAnchor;	
	
	private int voteCount;
	private Date submittedDate;
	private String submitterNickname;
	
	public LinkWidget(
			int energy,
			String title, 
			String url, 
			Date date, 
			int numberOfComments, 
			int voteCount,
			String submitterNickname,
			boolean votedByUser) {
		initWidget(uiBinder.createAndBindUi(this));
		root.setVerticalAlignment(HorizontalPanel.ALIGN_MIDDLE);
		
		setEnergy(energy);
		titleAnchor.setText(title);
		titleAnchor.setHref(url);
		commentsAnchor.setText(numberOfComments + " comments");
		setDetails(voteCount, date, submitterNickname);
		setVotedByUser(votedByUser);
	}
	
	public void setEnergy(int energy) {
		numberLabel.setText(Integer.toString(energy));		
	}

	public void setVotedByUser(boolean votedByUser){
		if(votedByUser){
			arrowAnchor.setStyleName("arrowAnchorVoted");
		}
		else {
			arrowAnchor.setStyleName("arrowAnchor");
		}
	}
	
	public void incrementVoteTotal(){
		this.voteCount++;
		refreshDetails();
	}
	
	private void setDetails(int voteCount, Date date, String submitterNickname){
		this.voteCount = voteCount;
		this.submittedDate = date;
		this.submitterNickname = submitterNickname;
		refreshDetails();
	}
	
	private void refreshDetails(){
		detailsPanel.clear();
		detailsPanel.add(new Label(Integer.toString(this.voteCount) + " votes submitted " 
				+ DateFormatter.getTimeAgoFromDate(this.submittedDate) + " by " + this.submitterNickname));	
	}
}
