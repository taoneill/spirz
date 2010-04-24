package com.team3.socialnews.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.team3.socialnews.client.presenter.RegistrationPresenter;
import com.team3.socialnews.client.validation.ValidationHandler;
import com.team3.socialnews.client.validation.ValidationProcessor;
import com.team3.socialnews.client.validation.ValidatorFactory;
public class RegistrationPanel extends Composite implements RegistrationPresenter.Display {

	private static RegistrationPanelUiBinder uiBinder = GWT
			.create(RegistrationPanelUiBinder.class);

	interface RegistrationPanelUiBinder extends
			UiBinder<Widget, RegistrationPanel> {
	}

	@UiField
	Button submitButton;
	@UiField
	TextBox nicknameField;
	@UiField
	Label errorLabel;
	
	ValidationProcessor vp;
	final Integer CHAR_LIMIT = 30; 

	public RegistrationPanel() {
		initWidget(uiBinder.createAndBindUi(this));
		
		vp = new ValidationProcessor(new ValidationHandler() {
				@Override
				public void onFail() {
					submitButton.setEnabled(false);
					if(nicknameField.getValue().length() > CHAR_LIMIT)
						errorLabel.setText("No more than " + CHAR_LIMIT + " characters please");
				}

				@Override
				public void onPass() {
					submitButton.setEnabled(true);
					if(errorLabel.getText().contains("No more than")){
						errorLabel.setText("");
					}
				}
				
			});
		vp.add(nicknameField, ValidatorFactory.getNonEmptyValidator());
		vp.add(nicknameField, ValidatorFactory.getCharacterLimitValidator(CHAR_LIMIT));
		vp.run();
		
		new Timer() {
			@Override
			public void run() {
				vp.run();
			}
		}.scheduleRepeating(100);
	}

	@Override
	public HasValue<String> getNickname() {
		return nicknameField;
	}

	@Override
	public HasClickHandlers getSubmitButton() {
		return submitButton;
	}

	@Override
	public void showError(String error) {
		errorLabel.setText(error);
	}

	@Override
	public Widget asWidget() {
		return this;
	}

	@Override
	public void startProcessing() {
		submitButton.setEnabled(false);
		nicknameField.setEnabled(false);
		// TODO Handle processing better
		
	}

	@Override
	public void stopProcessing() {
		submitButton.setEnabled(true);
		nicknameField.setEnabled(true);
		// TODO Handle processing better
		
	}
}
