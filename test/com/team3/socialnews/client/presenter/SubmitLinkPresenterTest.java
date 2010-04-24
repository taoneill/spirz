package com.team3.socialnews.client.presenter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.presenter.client.EventBus;

import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasValue;
import com.team3.socialnews.shared.dispatch.SubmitLink;

public class SubmitLinkPresenterTest {
	
	// Some handlers that need to be set
	private ClickHandler submitButtonClickHandler;
	private SubmitLink submitLinkAction;
	
	private SubmitLinkPresenter.Display mockDisplay;
	private EventBus mockEventBus;
	private DispatchAsync mockDispatch;
	
	@Before
	public void setUp() {
		mockDisplay = mock(SubmitLinkPresenter.Display.class);
		mockEventBus = mock(EventBus.class);
		mockDispatch = mock(DispatchAsync.class);
		setUpMockDisplay();
		setUpMockSubmitButton();
		setUpMockDispatch();
	}
	
	@SuppressWarnings("unchecked")
	private void setUpMockDisplay() {
		final String EXPECTED_TITLE = "Test Title";
		final String EXPECTED_URL = "http://www.example.com";
		// Mock the title and URL
		HasValue<String> mockTitle = mock(HasValue.class);
		HasValue<String> mockUrl = mock(HasValue.class);
		when(mockTitle.getValue()).thenReturn(EXPECTED_TITLE);
		when(mockUrl.getValue()).thenReturn(EXPECTED_URL);
		// Set mock values for the display
		when(mockDisplay.getLinkTitle()).thenReturn(mockTitle);
		when(mockDisplay.getLinkURL()).thenReturn(mockUrl);
	}
	
	@SuppressWarnings("unchecked")
	private void setUpMockSubmitButton() {
		// Mock the submit button
		HasClickHandlers mockSubmitButton = mock(HasClickHandlers.class);
		when(mockDisplay.getSubmitButton()).thenReturn(mockSubmitButton);
		
		// Mock the call to mockSubmitButton.addClickHandler
		when(mockSubmitButton.addClickHandler(any(ClickHandler.class))).thenAnswer(new Answer() {
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				submitButtonClickHandler = (ClickHandler) invocation.getArguments()[0];
				return null;
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	private void setUpMockDispatch() {
		// Mock the call to mockDispatch.execute
		doAnswer(new Answer() {
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				submitLinkAction = (SubmitLink) invocation.getArguments()[0];
				return null;
			}
		}).when(mockDispatch).execute(any(Action.class), any(AsyncCallback.class));
	}
	
	private void bindPresenter() {
		// Create our tested class.
		SubmitLinkPresenter slp = new SubmitLinkPresenter(
				mockDisplay, mockEventBus, mockDispatch);
		slp.bind();
	}
	
	@Test
	public void testBind() {
		bindPresenter();
		assertNotNull("Presenter is not listening to events from button.", 
				submitButtonClickHandler);
	}
	
	/**
	 * Test to see if Presenter is responding by simulating an onClick event
	 */
	@Test
	public void testSubmitButtonOnClickEvent() {
		bindPresenter();
		submitButtonClickHandler.onClick(null);
		assertNotNull("Presenter did not respond to event " +
				"by dispatching a SubmitLinkAction", 
				submitLinkAction);
		assertEquals(mockDisplay.getLinkTitle().getValue(), 
				submitLinkAction.getTitle());
		assertEquals(mockDisplay.getLinkURL().getValue(), 
				submitLinkAction.getUrl());
	}
}