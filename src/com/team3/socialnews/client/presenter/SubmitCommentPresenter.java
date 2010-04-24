package com.team3.socialnews.client.presenter;

import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.presenter.client.EventBus;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasValue;
import com.google.inject.Inject;
import com.team3.socialnews.client.LoginManager;
import com.team3.socialnews.client.event.CommentSubmittedEvent;
import com.team3.socialnews.shared.dispatch.CreateComment;
import com.team3.socialnews.shared.dispatch.CreateCommentResult;

public class SubmitCommentPresenter extends AbstractPresenter<SubmitCommentPresenter.Display> {

	private DispatchAsync dispatch;
	Long linkId;
	
	@Inject
	public SubmitCommentPresenter(Display display, EventBus eventBus,
			LoginManager loginManager, DispatchAsync dispatch) {
		super(display, eventBus, loginManager);
		this.dispatch = dispatch;
	}
	
	public interface Display extends AbstractPresenter.Display {
		HasClickHandlers getSubmitButton();
		HasValue<String> getSubmitBody();
		void setUnfocused();
	}
	
	public void setLinkId(Long linkId) {
		this.linkId = linkId;
	}
	
	@Override
	protected void onBind() {
		super.onBind();
		registerHandler(display.getSubmitButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				submitComment();
			}
		}));
	}
	
	private void submitComment() {
		String body = display.getSubmitBody().getValue();
		display.startProcessing();
		dispatch.execute(new CreateComment(linkId, body, null), 
				new AsyncCallback<CreateCommentResult>() {
					@Override
					public void onFailure(Throwable caught) {
						display.stopProcessing();
					}

					@Override
					public void onSuccess(CreateCommentResult result) {
						display.stopProcessing();
						display.setUnfocused();
						eventBus.fireEvent(new CommentSubmittedEvent(result.getComment()));
					}
		});
	}

	@Override
	protected void onUnbind() {
		// TODO Auto-generated method stub

	}

	@Override
	public void refreshDisplay() {
		// TODO Auto-generated method stub

	}

	@Override
	public void revealDisplay() {
		// TODO Auto-generated method stub

	}

}
