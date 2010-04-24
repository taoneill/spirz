package com.team3.socialnews.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class VoteButton extends Composite {

	private static VoteButtonUiBinder uiBinder = GWT
			.create(VoteButtonUiBinder.class);

	interface VoteButtonUiBinder extends UiBinder<Widget, VoteButton> {
	}
	
	interface VoteButtonClickHandler {
		void onClick(Boolean isVoted);
	}

	@UiField Anchor voteAnchor;
	@UiField Anchor unvoteAnchor;
	boolean voted;
	private int voteCount;
	
	public VoteButton() {
		initWidget(uiBinder.createAndBindUi(this));
		this.voted = false;
		voteAnchor.setVisible(true);
		unvoteAnchor.setVisible(false);
		setVoteCount(0);
		voteAnchor.addClickHandler(new ClickHandler()
		{
			// makes the icon click feedback immediate
			@Override
			public void onClick(ClickEvent event) {
				setVotedByUser(true); // unvote Anchor is brought to front
				setVoteCount(voteCount+1);
			}
		});		
		unvoteAnchor.addClickHandler(new ClickHandler()
		{
			// makes the icon click feedback immediate
			@Override
			public void onClick(ClickEvent event) {
				setVotedByUser(false); // vote Anchor is brought to front
				setVoteCount(voteCount-1);
			}
		});	
	}
	
	public void setVoteCount(int voteCount) {
		this.voteCount = voteCount;
		String countString = voteCount + "";
		voteAnchor.setText(countString);
		unvoteAnchor.setText(countString);
	}

	public void setVotedByUser(Boolean votedByUser) {
		setVotedByUser(votedByUser, false);
	}

	public void setVotedByUser(Boolean votedByUser, Boolean voteJustFailed){
		if(votedByUser != null && votedByUser){
			voteAnchor.setVisible(false);
			unvoteAnchor.setVisible(true);
			if(voteJustFailed) setVoteCount(voteCount+1);	// unvote failed
		}
		else {
			unvoteAnchor.setVisible(false);
			voteAnchor.setVisible(true);
			if(voteJustFailed) setVoteCount(voteCount-1);	// vote just failed 
		}
	}

	public void addVoteClickHandler(final ClickHandler clickHandler) {
		this.voteAnchor.addClickHandler(clickHandler);			
	}
	
	public void addUnvoteClickHandler(final ClickHandler clickHandler) {
		this.unvoteAnchor.addClickHandler(clickHandler);			
	}
	
	
}
