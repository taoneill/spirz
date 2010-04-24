package com.team3.socialnews.client.view;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.team3.socialnews.client.DateFormatter;

public class LinkTile extends Composite {

	private static LinkTileUiBinder uiBinder = GWT
			.create(LinkTileUiBinder.class);

	interface LinkTileUiBinder extends UiBinder<Widget, LinkTile> {
	}

	@UiField
	Anchor titleAnchor;
	@UiField
	VerticalPanel linkTitleWrapper;
	@UiField
	FlowPanel linkDetails;
	@UiField
	Anchor commentsAnchor;
	@UiField
	RoundedPanel root;
	@UiField
	FlowPanel buttons;
	@UiField
	Anchor voteAnchor;
	@UiField
	Anchor unvoteAnchor;
	@UiField
	Label energy;
	

	private int voteCount;
	private Date submittedDate;
	private String submitterNickname;
	
	public LinkTile(
			Long linkId,
			int energy,
			String title, 
			String url, 
			Date date, 
			int numberOfComments, 
			int voteCount,
			String submitterNickname,
			Boolean votedByUser) {
		initWidget(uiBinder.createAndBindUi(this));
		titleAnchor.setText(title);
		titleAnchor.setHref(url);
		linkTitleWrapper.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		linkTitleWrapper.setCellVerticalAlignment(titleAnchor, HasVerticalAlignment.ALIGN_MIDDLE);
		
		setVotedByUser(votedByUser);
		voteAnchor.addClickHandler(new ClickHandler()
		{
			// makes the icon click feedback immediate
			@Override
			public void onClick(ClickEvent event) {
				setVotedByUser(true); // unvote Anchor is brought to front
				incrementVoteTotal();
			}
		});		
		unvoteAnchor.addClickHandler(new ClickHandler()
		{
			// makes the icon click feedback immediate
			@Override
			public void onClick(ClickEvent event) {
				setVotedByUser(false); // vote Anchor is brought to front
				decrementVoteTotal();
			}
		});	
		
		commentsAnchor.setText(numberOfComments + " comments");
		commentsAnchor.setHref("#LinkComments;linkId="+linkId);
		setEnergy(energy);
		setDetails(voteCount, date, submitterNickname);
	}

	public void setVotedByUser(Boolean votedByUser) {
		setVotedByUser(votedByUser, false);
	}

	public void setVotedByUser(Boolean votedByUser, Boolean voteJustFailed){
		if(votedByUser != null && votedByUser){
			voteAnchor.setVisible(false);
			unvoteAnchor.setVisible(true);
			if(voteJustFailed) incrementVoteTotal();	// unvote failed
		}
		else {
			unvoteAnchor.setVisible(false);
			voteAnchor.setVisible(true);
			if(voteJustFailed) decrementVoteTotal();	// vote just failed 
		}
	}
	
	public void incrementVoteTotal(){
		this.voteCount++;
		refreshDetails();
	}
	
	public void decrementVoteTotal(){
		this.voteCount--;
		refreshDetails();
	}
	
	public void setEnergy(int energy) {
		this.energy.setText(energy+"");
	}
	
	private void setDetails(int voteCount, Date date, String submitterNickname){
		this.voteCount = voteCount;
		this.submittedDate = date;
		this.submitterNickname = submitterNickname;
		refreshDetails();
	}
	
	public void refreshDetails(){
		linkDetails.clear();
		linkDetails.add(new Label(getTimeAgoFromDate(this.submittedDate) + " by " + this.submitterNickname));
		voteAnchor.setText(voteCount + "");
		unvoteAnchor.setText(voteCount + "");
	}
	
	public void hideAsyncWorkMessage()
	{
	}
	
	private String getTimeAgoFromDate(Date date){
		return DateFormatter.getTimeAgoFromDate(date);
	}
	
	public void setWidth(double pixel){
		super.setWidth(pixel + "px");
		root.setMidColumnWidth(pixel - 32); // because left and right are both 16 pixels
	}
	
	public void setHighlightLevel(int level){
		root.setHighlightLevel(level);
	}
}
