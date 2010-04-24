package com.team3.socialnews.client.presenter;

import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.presenter.client.EventBus;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasValue;
import com.google.inject.Inject;
import com.team3.socialnews.client.event.LinkSubmittedEvent;
import com.team3.socialnews.client.event.LoadSuccessEvent;
import com.team3.socialnews.client.event.LoadSuccessEventHandler;
import com.team3.socialnews.shared.dispatch.SubmitLink;
import com.team3.socialnews.shared.dispatch.SubmitLinkResult;

public class SubmitLinkPresenter extends AbstractPresenter<SubmitLinkPresenter.Display>  {
	
	private final DispatchAsync dispatchAsync;
	
	public interface Display extends AbstractPresenter.Display {
		HasClickHandlers getSubmitButton();
		HasValue<String> getLinkTitle();
		HasValue<String> getLinkURL();
		void waitForResponse();
		void stopWaitForResponse();
		void clear();
		void showSuccess();
		void showSubmitting();
		void showError();
		void setErrorLabelText(String string);
		void setSubmitButtonText(String string);
	}
	
	@Inject
    public SubmitLinkPresenter(
    		final Display display, 
    		final EventBus eventBus,
    		DispatchAsync dispatchAsync)
    {
		super(display, eventBus, null);
		this.dispatchAsync = dispatchAsync;
    }
	
	

	@Override
	public void onBind(){
		super.onBind();
		HasClickHandlers hch = display.getSubmitButton();
		hch.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				String title = display.getLinkTitle().getValue();
				String url = display.getLinkURL().getValue();
				display.showSubmitting();
				dispatchAsync.execute(new SubmitLink(title, url), new AsyncCallback<SubmitLinkResult>() {
					
					@Override
					public void onFailure(Throwable error) {
						display.showError();
					}

					@Override
					public void onSuccess(SubmitLinkResult result) {
						eventBus.fireEvent(new LinkSubmittedEvent(result.getLink()));
						display.showSuccess();
					}
				});
			}
		});

		eventBus.addHandler(LoadSuccessEvent.TYPE, new LoadSuccessEventHandler() {
			@Override
			public void onSuccess(LoadSuccessEvent event) {
				// The the hot or new links load, reset the labels
				display.setSubmitButtonText("Submit");
				display.setErrorLabelText("");
			}
		});


	}
	@Override
	public void revealDisplay() {
	}

	@Override
	protected void onUnbind() {
		
	}

	@Override
	public void refreshDisplay() {
		// TODO Auto-generated method stub	
	}
}
