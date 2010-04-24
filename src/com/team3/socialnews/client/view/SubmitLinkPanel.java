package com.team3.socialnews.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.team3.socialnews.client.event.LoadSuccessEvent;
import com.team3.socialnews.client.event.LoadSuccessEventHandler;
import com.team3.socialnews.client.presenter.SubmitLinkPresenter;
import com.team3.socialnews.client.validation.ValidationHandler;
import com.team3.socialnews.client.validation.ValidationProcessor;
import com.team3.socialnews.client.validation.ValidatorFactory;

public class SubmitLinkPanel extends AbstractView implements SubmitLinkPresenter.Display {

	private static SubmitLinkPanelUiBinder uiBinder = GWT
			.create(SubmitLinkPanelUiBinder.class);

	interface SubmitLinkPanelUiBinder extends UiBinder<Widget, SubmitLinkPanel> {
	}

	@UiField
	Button submitButton;
	@UiField
	TextArea titleField;
	@UiField
	TextBox urlField;
	@UiField
	Label errorLabel;
	@UiField
	Label successLabel;
	@UiField
	RoundedPanel roundedPanel;
	@UiField
	Label charactersLeft;
	@UiField
	Anchor linkTest;
	
	HandlerManager eventBus;
	final ValidationProcessor vp;
	
	
	public SubmitLinkPanel() {
		initWidget(uiBinder.createAndBindUi(this));
		titleField.setVisibleLines(3);
		vp = 
			new ValidationProcessor(new ValidationHandler() {

				@Override
				public void onFail() {
					submitButton.setEnabled(false);
					successLabel.setText("");
				}

				@Override
				public void onPass() {
					if(submitButton.getText().contentEquals("Submit")) {
						submitButton.setEnabled(true);
						successLabel.setText("");
					}
				}
				
			});
		vp.add(titleField, ValidatorFactory.getNonEmptyValidator());
		vp.add(titleField, ValidatorFactory.getCharacterLimitValidator(140));
		vp.add(urlField, ValidatorFactory.getURLValidator());
		vp.run();
		
		new Timer() {
			@Override
			public void run() {
				vp.run();
				int currentLength = titleField.getText().length();
				charactersLeft.setText((140 - currentLength) + "");
			}
		}.scheduleRepeating(100);
		
		linkTest.setTarget("_blank");	// opens in new window/tab
		urlField.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				String url = urlField.getValue();
				linkTest.setHref(url);
			}
		});
	}
	
	public void setEventBus(HandlerManager eventBus) {
		this.eventBus = eventBus;
	}

	@Override
	public HasClickHandlers getSubmitButton() {
		return submitButton;
	}

	@Override
	public HasValue<String> getLinkURL() {
		return urlField;
	}

	@Override
	public HasValue<String> getLinkTitle() {
		return titleField;
	}

	@Override
	public Widget asWidget() {
		return this;
	}

	@Override
	public void clear() {
		titleField.setValue("");
		urlField.setValue("");
	}
	
	@Override
	public void stopWaitForResponse() {
		setEnabled(true);
	}

	@Override
	public void waitForResponse() {
		setEnabled(false);
	}
	
	public void setEnabled(boolean b) {
		submitButton.setEnabled(b);
		titleField.setEnabled(b);
		urlField.setEnabled(b);
	}
	
	public void showSubmitting() { 	
		errorLabel.setText("");
		submitButton.setText("Submitting...");
		setEnabled(false);
	}

	@Override
	public void showSuccess() {
		clear();
		setEnabled(true);
		submitButton.setText("Submitted");
		errorLabel.setText("Redirecting to new links...");
		vp.run();
	}

	@Override
	public void showError() {
		errorLabel.setText("Couldn't submit. Please try again.");
		setEnabled(true);
		vp.run();
	}

	@Override
	public void setErrorLabelText(String string) {
		errorLabel.setText(string);
	}

	@Override
	public void setSubmitButtonText(String string) {
		submitButton.setText(string);		
	}
}
