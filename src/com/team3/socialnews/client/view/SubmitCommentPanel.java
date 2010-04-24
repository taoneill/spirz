package com.team3.socialnews.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;
import com.team3.socialnews.client.presenter.SubmitCommentPresenter;

public class SubmitCommentPanel extends Composite implements SubmitCommentPresenter.Display {

	@UiField
	TextArea submitBody;
	@UiField
	Button submitButton;
	
	private static SubmitCommentPanelUiBinder uiBinder = GWT
			.create(SubmitCommentPanelUiBinder.class);

	interface SubmitCommentPanelUiBinder extends
			UiBinder<Widget, SubmitCommentPanel> {
	}
	
	public SubmitCommentPanel() {
		initWidget(uiBinder.createAndBindUi(this));
		setUnfocused();
		submitBody.addFocusHandler(new FocusHandler(){
			@Override
			public void onFocus(FocusEvent event) {
				submitBody.setHeight("70px");
				submitButton.setVisible(true);
				if(submitBody.getValue().contentEquals("Post a comment")){
					submitBody.setValue("");
				}
				submitBody.setStyleName("submitCommentBody");
			}
				
				
		});	
		submitBody.addBlurHandler(new BlurHandler(){
			@Override
			public void onBlur(BlurEvent event) {
				if(submitBody.getValue().length() == 0) {
					setUnfocused();
				}
			}		
		});	
		
	}

	@Override
	public void setUnfocused() {
		Timer setCommentUnFocused = new Timer() {
		   public void run() {
				submitBody.setHeight("22px");
				submitButton.setVisible(false);
				submitBody.setValue("Post a comment");
				submitBody.setStyleName("submitCommentBodyUnfocused");
		   }
		};
		setCommentUnFocused.schedule(100); // you have to do this after a delay otherwise all the comments below the form 
		// shift up as the form becomes thinner before any clicks have registered, interrupting the user if he is clicking on a comment component
		
	}

	@Override
	public HasValue<String> getSubmitBody() {
		return submitBody;
	}

	@Override
	public HasClickHandlers getSubmitButton() {
		return submitButton;
	}

	@Override
	public Widget asWidget() {
		return this;
	}

	@Override
	public void startProcessing() {
		setEnabled(false);
	}
	
	private void setEnabled(boolean isEnabled) {
		submitBody.setEnabled(isEnabled);
		submitButton.setEnabled(isEnabled);
	}

	@Override
	public void stopProcessing() {
		setEnabled(true);
	}
}
