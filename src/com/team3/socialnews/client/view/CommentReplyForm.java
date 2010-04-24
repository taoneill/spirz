package com.team3.socialnews.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;

public class CommentReplyForm extends Composite {

	private static CommentReplyFormUiBinder uiBinder = GWT
			.create(CommentReplyFormUiBinder.class);

	interface CommentReplyFormUiBinder extends
			UiBinder<Widget, CommentReplyForm> {
	}

	@UiField
	Button button;
	@UiField
	TextArea body;
	@UiField
	Label error;

	public CommentReplyForm() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public HasClickHandlers getButton() {
		return button;
	}

	public HasValue<String> getBody() {
		return body;
	}

	public void startProcessing() {
		setEnabled(false);
		error.setVisible(false);
	}

	public void stopProcessing() {
		setEnabled(true);
	}

	public void setEnabled(boolean isEnabled) {
		button.setEnabled(isEnabled);
		body.setEnabled(isEnabled);
	}

	public void showError(String string) {
		error.setVisible(true);
		error.setText(string);
	}
}
