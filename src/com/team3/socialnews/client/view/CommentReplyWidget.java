package com.team3.socialnews.client.view;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.ParagraphElement;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.team3.socialnews.client.DateFormatter;

public class CommentReplyWidget extends Composite {

	private static CommentReplyWidgetUiBinder uiBinder = GWT
			.create(CommentReplyWidgetUiBinder.class);

	interface CommentReplyWidgetUiBinder extends
			UiBinder<Widget, CommentReplyWidget> {
	}

	@UiField
	ParagraphElement bodyContent;
	@UiField
	Anchor authorNickname;
	@UiField
	Anchor reply;
	@UiField
	Label date;
	@UiField
	VoteButton voteButton;
	
	private Long commentId;
	
	public CommentReplyWidget() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void setAuthorNickname(String nickname) {
		authorNickname.setText(nickname);
	}
	
	public void setBody(String body) {
		bodyContent.setInnerText(body);
	}
	
	public HasClickHandlers getReplyButton() {
		return reply;
	}
	
	public void setDate(Date date) {
		this.date.setText(DateFormatter.getTimeAgoFromDate(date));
	}

	public Long getCommentId() {
		return this.commentId;
	}

	public void setCommentId(Long id) {
		this.commentId = id;
	}

}
