package com.team3.socialnews.client.presenter;

import java.util.List;

import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.place.Place;
import net.customware.gwt.presenter.client.place.PlaceChangedEvent;
import net.customware.gwt.presenter.client.place.PlaceRequest;
import net.customware.gwt.presenter.client.place.PlaceRequestEvent;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.inject.Inject;
import com.team3.socialnews.client.LoginManager;
import com.team3.socialnews.client.Order;
import com.team3.socialnews.client.Pager;
import com.team3.socialnews.client.dispatch.AsyncProgressMonitor;
import com.team3.socialnews.client.dispatch.MonitoredDispatchAsync;
import com.team3.socialnews.client.event.ClockTickEvent;
import com.team3.socialnews.client.event.ClockTickEventHandler;
import com.team3.socialnews.client.event.LinkSubmittedEvent;
import com.team3.socialnews.client.event.LinkSubmittedEventHandler;
import com.team3.socialnews.client.event.LoadErrorEvent;
import com.team3.socialnews.client.event.LoadSuccessEvent;
import com.team3.socialnews.client.event.RegistrationCompletedEvent;
import com.team3.socialnews.client.event.RegistrationCompletedEventHandler;
import com.team3.socialnews.client.presenter.LinksPresenter.Display.LinkUnvoteClickHandler;
import com.team3.socialnews.client.presenter.LinksPresenter.Display.LinkVoteClickHandler;
import com.team3.socialnews.shared.dispatch.AlreadyVotedException;
import com.team3.socialnews.shared.dispatch.GetLinks;
import com.team3.socialnews.shared.dispatch.GetLinksResult;
import com.team3.socialnews.shared.dispatch.NotLoggedInException;
import com.team3.socialnews.shared.dispatch.UnvoteOnLink;
import com.team3.socialnews.shared.dispatch.UnvoteOnLinkResult;
import com.team3.socialnews.shared.dispatch.VoteOnLink;
import com.team3.socialnews.shared.dispatch.VoteOnLinkResult;
import com.team3.socialnews.shared.model.Link;
import com.team3.socialnews.shared.model.LoginInfo;

public class LinksPresenter extends AbstractPresenter<LinksPresenter.Display> {
	
	public interface Display extends AbstractPresenter.Display {
		void setLinks(List<Link> links);
		void setVoteMask(List<Boolean> voteMask);
		void refresh();

		HasClickHandlers getNextButton();
		HasClickHandlers getPreviousButton();
		HasWidgets getRightSideContainer();
		void setPagerVisibility(boolean showPreviousButton, boolean showNextButton);
		void setVoteSuccessResult(Long linkId, int newLinkEnergy);
		void setUnvoteSuccessResult(Long linkId, int newLinkEnergy);
		void setVoteFailureMessage(Long linkId, String failureMessage);
		void setLinkVoteClickHandler(LinkVoteClickHandler handler);
		void setLinkUnvoteClickHandler(LinkUnvoteClickHandler handler);
		void setVotedByUser(Long linkId, boolean voted, boolean justTriedToVote);
		interface LinkVoteClickHandler {
			void onClick(Link link);
		}
		interface LinkUnvoteClickHandler {
			void onClick(Link link);
		}
		
		//void setCommentClickHandler(CommentClickHandler handler);
		interface CommentClickHandler {
			void onClick(Link link);
		}
		void highlightFirstTile();
	}

	private final MonitoredDispatchAsync dispatchAsync;
	private MainPresenter mainPresenter;
	
	private Pager pager;
	private Order order;
	private SubmitLinkPresenter submitLinkPresenter;
//	private int retries = 0;
	
	@Inject
	public LinksPresenter(
			final Display display, 
			final EventBus eventBus, 
			MonitoredDispatchAsync dispatchAsync, 
			Pager pager,
			LoginManager loginManager,
			MainPresenter mainPresenter,
			SubmitLinkPresenter presenter) {
		super(display, eventBus, loginManager);
		this.dispatchAsync = dispatchAsync;
		this.mainPresenter = mainPresenter;
		this.pager = pager;
		this.order = Order.HOT;
		this.submitLinkPresenter = presenter;
	}
	
	public static final Place PLACE = new Place("Links");
	
	@Override
	public void onBind() {
		super.onBind();
		eventBus.addHandler(LinkSubmittedEvent.TYPE, new LinkSubmittedEventHandler() {

			@Override
			public void onLinkSubmitted(LinkSubmittedEvent event) {
				if(order != Order.NEW){
					// make sure the user sees his submitted link
					PlaceRequest pr = new PlaceRequest(LinksPresenter.PLACE)
					.with("order", Order.NEW.toString());
					eventBus.fireEvent(new PlaceRequestEvent(pr));
				}
				else {
					refreshDisplay();
				}
			}
		});
		
		eventBus.addHandler(RegistrationCompletedEvent.TYPE, new RegistrationCompletedEventHandler() {
			@Override
			public void onRegistrationCompleted(RegistrationCompletedEvent event) {
				
			}
		});
		
		eventBus.addHandler(ClockTickEvent.TYPE, new ClockTickEventHandler() {
			@Override
			public void onTick(ClockTickEvent event) {
				//refreshDisplay(); // uncomment to re-enable auto-refresh
			}
		});

		
		display.getNextButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				goForward();
			}
		});
		
		display.getPreviousButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				goBackward();
			}
		});
		
		display.setLinkVoteClickHandler(new LinkVoteClickHandler() {
			@Override
			public void onClick(final Link link) {
				dispatchAsync.execute(new VoteOnLink(link.getId()), new AsyncCallback<VoteOnLinkResult>() {
					public void onFailure( Throwable e ) {
						GWT.log("Error voting.", e);
						// Here follows the lazy way to tell the use to log in
						// TODO: handled not-logged-in state in the client .. maybe with a cookie?
						display.setVotedByUser(link.getId(), false, true); // go back to unvoted state
						if(e instanceof NotLoggedInException) {
							display.setVoteFailureMessage(link.getId(), "Please log in to vote.");
						}
						else if(e instanceof AlreadyVotedException) {
							display.setVoteFailureMessage(link.getId(), e.getMessage());
						} else {
							display.setVoteFailureMessage(link.getId(), "Oops! Try again please.");
						}
		            }

		            public void onSuccess(VoteOnLinkResult result) {
		            	display.setVoteSuccessResult(link.getId(), result.getNewLinkEnergy());
		            }
				});
			}
		});
		
		display.setLinkUnvoteClickHandler(new LinkUnvoteClickHandler() {
			@Override
			public void onClick(final Link link) {
				dispatchAsync.execute(new UnvoteOnLink(link.getId()), new AsyncCallback<UnvoteOnLinkResult>() {
					public void onFailure( Throwable e ) {
						GWT.log("Error voting.", e);
						// Here follows the lazy way to tell the use to log in
						// TODO: handled not-logged-in state in the client .. maybe with a cookie?
						display.setVotedByUser(link.getId(), true, true); // go back to voted state
						if(e instanceof NotLoggedInException) {
							display.setVoteFailureMessage(link.getId(), "Please log in to unvote.");
						}
						else if(e instanceof AlreadyVotedException) {
							display.setVoteFailureMessage(link.getId(), e.getMessage());
						} else {
							display.setVoteFailureMessage(link.getId(), "Oops! Try again please.");
						}
		            }

		            public void onSuccess(UnvoteOnLinkResult result) {
		            	display.setUnvoteSuccessResult(link.getId(), result.getNewLinkEnergy());
		            }
				});
			}
		});
		
//		display.setCommentClickHandler(new CommentClickHandler() {
//			@Override
//			public void onClick(Link link) {
//				PlaceRequest pr = new PlaceRequest(LinkCommentsPresenter.PLACE)
//					.with("linkId", link.getId().toString());
//				eventBus.fireEvent(new PlaceRequestEvent(pr));
//			}
//		});
	}
	
	@Override
	protected void onLoginInfoFetched(LoginInfo loginInfo) {
		if (loginInfo.isLoggedIn()) {
			submitLinkPresenter.bind();
			display.getRightSideContainer().add(submitLinkPresenter.getDisplay().asWidget());
		}
	}

	private void goForward() {
		PlaceRequest pr = new PlaceRequest(LinksPresenter.PLACE)
		.with("order", this.order.toString()).with("page", (pager.getPage()+1)+"");
		eventBus.fireEvent(new PlaceRequestEvent(pr));
	}
	
	private void goBackward() {
		PlaceRequest pr = new PlaceRequest(LinksPresenter.PLACE)
		.with("order", this.order.toString()).with("page", (pager.getPage()-1)+"");
		eventBus.fireEvent(new PlaceRequestEvent(pr));
	}
	
	@Override
	public Place getPlace() {
		return PLACE;
	}

	@Override
	protected void onPlaceRequest(PlaceRequest request) {
		Integer page = Integer.parseInt(request.getParameter("page", "0"));
		pager.setPage(page);
		this.order = Enum.valueOf(Order.class, request.getParameter("order", Order.HOT.toString()));	// default to hot links
		eventBus.fireEvent(new PlaceChangedEvent(request));
	}
	


	@Override
	protected void onUnbind() {
		submitLinkPresenter.unbind();
	}

	@Override
	public void refreshDisplay() {
		GetLinks action = new GetLinks(order, pager.getPageStart(), pager.getPageEnd());
		dispatchAsync.execute(action, 
				new AsyncCallback<GetLinksResult>() {

					@Override
					public void onFailure(Throwable caught) {
						eventBus.fireEvent(new LoadErrorEvent(
								"Seems like Spirz is broken. So sorry. Please try again."));
					}

					@Override
					public void onSuccess(GetLinksResult result) {
						 List<Link> links = result.getLinks();
			                List<Boolean> voteMask = result.getVoteMask();
			                display.setPagerVisibility(pager.canGoPreviousPage(), pager.canGoNextPage(links.size()));
			            	display.setLinks(links);
			            	if(voteMask != null) {
			            		display.setVoteMask(voteMask);
			            	}
			                display.refresh();
			                if(!pager.canGoPreviousPage() && links.size() > 0 && order == Order.HOT){
			                	display.highlightFirstTile();
			                }
			                eventBus.fireEvent(new LoadSuccessEvent());
					}
			
				}, new AsyncProgressMonitor() {

					@Override
					public boolean onFailure(Throwable caught,
							int remainingAttempts) {
						eventBus.fireEvent(new LoadErrorEvent(
								"Spirz is asleep. We're waking it up; just a moment please..."));
						return true;
					}
					
				});
	}

	@Override
	public void revealDisplay() {
		mainPresenter.setPresenter(this);
		refreshDisplay();
	}
}
