package com.team3.socialnews.client.presenter;


import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import net.customware.gwt.presenter.client.DefaultEventBus;
import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.place.PlaceRequestEvent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.Description;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.team3.socialnews.client.LoginManager;
import com.team3.socialnews.client.Pager;
import com.team3.socialnews.client.dispatch.AsyncProgressMonitor;
import com.team3.socialnews.client.dispatch.MonitoredDispatchAsync;
import com.team3.socialnews.client.event.LinkSubmittedEvent;
import com.team3.socialnews.shared.dispatch.GetLinks;
import com.team3.socialnews.shared.model.Link;

public class LinksPresenterTest {

	private LinksPresenter.Display mockDisplay;
	private EventBus mockEventBus;
	private MonitoredDispatchAsync mockDispatch;
	private Pager mockPager; 
	private HasClickHandlers mockNextButton;
	private HasClickHandlers mockPrevButton;
	private LoginManager mockLoginManager;
	private MainPresenter mockMainPresenter;
	private SubmitLinkPresenter mockSubmitLinkPresenter;
	
	@Before
	public void setup(){
		mockDisplay = mock(LinksPresenter.Display.class);
		mockEventBus = mock(EventBus.class);
		mockDispatch = mock(MonitoredDispatchAsync.class);
		mockPager = mock(Pager.class);
		mockNextButton = mock(HasClickHandlers.class);
		mockPrevButton = mock(HasClickHandlers.class);
		mockLoginManager = mock(LoginManager.class);
		mockMainPresenter = mock(MainPresenter.class);
		mockSubmitLinkPresenter = mock(SubmitLinkPresenter.class);
	}
	
	public void setupMockButtons() {
		when(mockDisplay.getNextButton()).thenReturn(mockNextButton);
		when(mockNextButton.addClickHandler(any(ClickHandler.class))).thenReturn(null);
		when(mockDisplay.getPreviousButton()).thenReturn(mockPrevButton);
		when(mockPrevButton.addClickHandler(any(ClickHandler.class))).thenReturn(null);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testThatRevealDisplayGetsTheLinks(){
		// Arrange
		when(mockEventBus.addHandler(any(Type.class), any(EventHandler.class))).thenReturn(null);
		setupMockButtons();
		
		// Act
		LinksPresenter presenter = new LinksPresenter(
				mockDisplay, 
				mockEventBus, 
				mockDispatch,
				mockPager, 
				mockLoginManager, mockMainPresenter, null);
		presenter.revealDisplay();
		
		/// Assert
		verify(mockDispatch).execute(any(GetLinks.class), any(AsyncCallback.class), any(AsyncProgressMonitor.class));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testThatLinksAreRefreshedUponLinkSubmittedEvent() {
		// Arrange
		EventBus eventBus = new DefaultEventBus();
		setupMockButtons();
		
		// Act
		LinksPresenter presenter = new LinksPresenter(
				mockDisplay, 
				eventBus, 
				mockDispatch, 
				mockPager, 
				mockLoginManager, 
				mockMainPresenter, 
				mockSubmitLinkPresenter);
		presenter.bind();
		eventBus.fireEvent(new LinkSubmittedEvent(new Link("test", "test", "test", "test"))); // simulate link submitted event
		
		// Assert
		verify(mockDispatch).execute(any(GetLinks.class), any(AsyncCallback.class), any(AsyncProgressMonitor.class));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testThatBindHooksUpTheNextPageButton(){
		// Arrange
		when(mockEventBus.addHandler(any(Type.class), any(EventHandler.class))).thenReturn(null);
		HasClickHandlers nextButton = new MockButton(); 
		when(mockDisplay.getNextButton()).thenReturn(nextButton);
		when(mockDisplay.getPreviousButton()).thenReturn(mockPrevButton);
		when(mockPrevButton.addClickHandler(any(ClickHandler.class))).thenReturn(null);
		
		// Act
		LinksPresenter presenter = new LinksPresenter(
				mockDisplay, 
				mockEventBus, 
				mockDispatch,
				mockPager, 
				mockLoginManager, 
				mockMainPresenter,
				mockSubmitLinkPresenter);
		presenter.bind();
		nextButton.fireEvent(new MockClickEvent());
		
		// Assert
		verify(mockPager).getPage();
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testThatNextPageButtonDoesAPlaceRequest(){
		// Arrange
		when(mockEventBus.addHandler(any(Type.class), any(EventHandler.class))).thenReturn(null);
		HasClickHandlers nextButton = new MockButton(); 
		when(mockDisplay.getNextButton()).thenReturn(nextButton);
		when(mockDisplay.getPreviousButton()).thenReturn(mockPrevButton);
		when(mockPrevButton.addClickHandler(any(ClickHandler.class))).thenReturn(null);
		
		// Act
		LinksPresenter presenter = new LinksPresenter(
				mockDisplay, 
				mockEventBus, 
				mockDispatch,
				mockPager, 
				mockLoginManager, 
				mockMainPresenter,
				mockSubmitLinkPresenter);
		presenter.bind();
		nextButton.fireEvent(new MockClickEvent());
		
		// Assert
		verify(mockEventBus).fireEvent(any(PlaceRequestEvent.class));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testThatBindHooksUpThePreviousPageButton(){
		// Arrange
		when(mockEventBus.addHandler(any(Type.class), any(EventHandler.class))).thenReturn(null);
		when(mockDisplay.getNextButton()).thenReturn(mockNextButton);
		when(mockNextButton.addClickHandler(any(ClickHandler.class))).thenReturn(null);
		HasClickHandlers prevButton = new MockButton(); 
		when(mockDisplay.getPreviousButton()).thenReturn(prevButton);
		
		// Act
		LinksPresenter presenter = 
			new LinksPresenter(
					mockDisplay, 
					mockEventBus, 
					mockDispatch,
					mockPager, 
					mockLoginManager, 
					mockMainPresenter, 
					mockSubmitLinkPresenter);
		presenter.bind();
		prevButton.fireEvent(new MockClickEvent());
		
		/// Assert
		verify(mockPager).getPage();
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testThatPreviousPageButtonDoesAPlaceRequest(){
		// Arrange
		when(mockEventBus.addHandler(any(Type.class), any(EventHandler.class))).thenReturn(null);
		when(mockDisplay.getNextButton()).thenReturn(mockNextButton);
		when(mockNextButton.addClickHandler(any(ClickHandler.class))).thenReturn(null);
		HasClickHandlers prevButton = new MockButton(); 
		when(mockDisplay.getPreviousButton()).thenReturn(prevButton);
		
		// Act
		LinksPresenter presenter = 
			new LinksPresenter(
					mockDisplay, 
					mockEventBus, 
					mockDispatch,
					mockPager, 
					mockLoginManager, 
					mockMainPresenter, 
					mockSubmitLinkPresenter);
		presenter.bind();
		prevButton.fireEvent(new MockClickEvent());
		
		/// Assert
		verify(mockEventBus).fireEvent(any(PlaceRequestEvent.class));
	}
	
	public class MockClickEvent extends ClickEvent {
		
	}
	
	public class MockButton implements HasClickHandlers {

		ArrayList<ClickHandler> handlers = new ArrayList<ClickHandler>();
		
		@Override
		public HandlerRegistration addClickHandler(ClickHandler handler) {
			handlers.add(handler);
			return null;
		}

		@Override
		public void fireEvent(GwtEvent<?> event) {
			for(ClickHandler handler : handlers) {
				handler.onClick(new MockClickEvent());
			}
			
		}
	}
}
