package com.team3.socialnews.client.view;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.ParagraphElement;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.team3.socialnews.client.DateFormatter;
import com.team3.socialnews.shared.model.Comment;

public class CommentWidget extends Composite {

	private static CommentWidgetUiBinder uiBinder = GWT.create(CommentWidgetUiBinder.class);

	interface CommentWidgetUiBinder extends UiBinder<Widget, CommentWidget> {
	}

	@UiField
	ParagraphElement bodyContent;
	@UiField
	Anchor authorNickname;
	@UiField
	Anchor reply;
	@UiField
	FlowPanel replyFormPanel;
	@UiField
	VerticalPanel replies;
	@UiField
	Label date;
	@UiField
	VoteButton voteButton;
	@UiField
	RoundedPanel roundedPanel;
	
	private Long commentId;
	
	public CommentWidget(Comment comment) {
		initWidget(uiBinder.createAndBindUi(this));
		this.setCommentId(comment.getId());
		this.setBody(comment.getBody());
		this.setAuthorNickname(comment.getAuthorNickname());
		this.setDate(comment.getCommentDate());
		this.voteButton.setVoteCount(comment.getVoteTotal());
	}
	
	private void setDate(Date date) {
		this.date.setText(DateFormatter.getTimeAgoFromDate(date));
	}

	public HasWidgets getReplies() {
		return replies;
	}
	
	public void setBody(String body) {
		bodyContent.setInnerText(body);
	}
	
	public void setAuthorNickname(String authorNickname) {
		this.authorNickname.setText(authorNickname);
	}
	
	public HasClickHandlers getReplyButton() {
		return reply;
	}
	
	public void showReplyForm(final Widget w) {
		Timer hideFormWithADelay = new Timer(){
			@Override
			public void run() {
				replyFormPanel.clear();
				replyFormPanel.add(w);
				replyFormPanel.setVisible(true);
				reply.setVisible(false);
				showReplyButtonInChildren(false);
			}
		};
		hideFormWithADelay.schedule(110);
	}
	

	public void hideReplyForm() {
		Timer hideFormWithADelay = new Timer(){
			@Override
			public void run() {
				replyFormPanel.clear();
				replyFormPanel.setVisible(false);
				reply.setVisible(true);
				showReplyButtonInChildren(true);
			}
		};
		hideFormWithADelay.schedule(100);	// do this with a delay so that the fact that UI that shifts up when the form is hidden
											// doesn't prevent the user from clicking anything
											// Delay here _must_ be less than the one in showReplyForm 
		
	}

	private void showReplyButtonInChildren(boolean show) {
		for(int i = 0; i < replies.getWidgetCount(); i++){
			CommentReplyWidget replyWidget = (CommentReplyWidget)replies.getWidget(i);
			replyWidget.reply.setVisible(show);
		}	
	}

	public void hideReplies() {
		replies.setVisible(false);		
	}

	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}

	public Long getCommentId() {
		return commentId;
	}

	public void setHighlightLevel(int i) {
		roundedPanel.setHighlightLevel(i);
	}
}
