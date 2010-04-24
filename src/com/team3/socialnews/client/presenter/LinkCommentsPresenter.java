package com.team3.socialnews.client.presenter;

import java.util.HashMap;

import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.place.Place;
import net.customware.gwt.presenter.client.place.PlaceChangedEvent;
import net.customware.gwt.presenter.client.place.PlaceRequest;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.team3.socialnews.client.LoginManager;
import com.team3.socialnews.client.event.CommentSubmittedEvent;
import com.team3.socialnews.client.event.CommentSubmittedEventHandler;
import com.team3.socialnews.client.model.CommentClient;
import com.team3.socialnews.client.presenter.LinkCommentsPresenter.Display.CommentUnvoteClickHandler;
import com.team3.socialnews.client.presenter.LinkCommentsPresenter.Display.CommentVoteClickHandler;
import com.team3.socialnews.client.presenter.LinkCommentsPresenter.Display.ReplyHandler;
import com.team3.socialnews.client.presenter.LinksPresenter.Display.LinkUnvoteClickHandler;
import com.team3.socialnews.client.presenter.LinksPresenter.Display.LinkVoteClickHandler;
import com.team3.socialnews.shared.dispatch.AlreadyVotedException;
import com.team3.socialnews.shared.dispatch.CreateComment;
import com.team3.socialnews.shared.dispatch.CreateCommentResult;
import com.team3.socialnews.shared.dispatch.GetLinkComments;
import com.team3.socialnews.shared.dispatch.GetLinkCommentsResult;
import com.team3.socialnews.shared.dispatch.NotLoggedInException;
import com.team3.socialnews.shared.dispatch.UnvoteOnComment;
import com.team3.socialnews.shared.dispatch.UnvoteOnCommentResult;
import com.team3.socialnews.shared.dispatch.UnvoteOnLink;
import com.team3.socialnews.shared.dispatch.UnvoteOnLinkResult;
import com.team3.socialnews.shared.dispatch.VoteOnComment;
import com.team3.socialnews.shared.dispatch.VoteOnCommentResult;
import com.team3.socialnews.shared.dispatch.VoteOnLink;
import com.team3.socialnews.shared.dispatch.VoteOnLinkResult;
import com.team3.socialnews.shared.model.Comment;
import com.team3.socialnews.shared.model.Link;
import com.team3.socialnews.shared.model.LoginInfo;

public class LinkCommentsPresenter extends AbstractPresenter<LinkCommentsPresenter.Display> {

	private DispatchAsync dispatch;

	public static final Place PLACE = new Place("LinkComments");
	
	/**
	 * The current link that is presented.
	 * Initially null.
	 */
	private Link link;
	
	/**
	 * A reference to the main presenter so that
	 * we can request to be displayed.
	 */
	private MainPresenter mainPresenter;
	
	/**
	 * Provider to lazily load the submitComment.
	 * It may never be loaded if the user does not log in.
	 */
	
	private Provider<SubmitCommentPresenter> submitCommentPresenterProvider;
	
	/**
	 * Handles the click of a reply button in the display.
	 */
	private ReplyHandler replyHandler = new ReplyHandler() {
		@Override
		public void onReplySubmitted(Long commentId, String replyBody) {
			reply(commentId, replyBody);
		}
	};
	
	
	@Inject
	public LinkCommentsPresenter(
			Display display,
			Provider<SubmitCommentPresenter> submitCommentPresenter,
			EventBus eventBus,
			LoginManager loginManager,
			DispatchAsync dispatch,
			MainPresenter presenter) {
		super(display, eventBus, loginManager);
		this.mainPresenter = presenter;
		this.dispatch = dispatch;
		this.submitCommentPresenterProvider = submitCommentPresenter;
	}
	
	/**
	 * Sends a request to the server to
	 * create a reply to a comment
	 * @param commentId The id of the comment that is replied to.
	 * @param replyBody The textual body of the reply.
	 */
	protected void reply(Long commentId, String replyBody) {
		CreateComment action = new CreateComment(link.getId(), replyBody, commentId);
		display.startProcessing();
		// Send the request
		dispatch.execute(action, new AsyncCallback<CreateCommentResult>() {
			@Override
			public void onFailure(Throwable caught) {
				display.stopProcessing();
				display.showReplyError("An error occured");
			}

			@Override
			public void onSuccess(CreateCommentResult result) {
				// Display the updated comment
				showComment(result.getComment());
				display.stopProcessing();
			}
		});
	}

	protected void showComment(Comment comment) {
		// For lack of a better solution right now,
		// we refresh the entire display.
		refreshDisplay();
	}

	public interface Display extends AbstractPresenter.Display {
		
		/**
		 * Sets the link to be displayed.
		 * @param link The link to be displayed.
		 * @param votedOnByUser Set to true to show that
		 * the user has previously voted for this link.
		 */
		void setLink(Link link, Boolean votedOnByUser);
		
		/**
		 * Shows an error message that has happended
		 * when sending a reply to the server.
		 * @param string
		 */
		void showReplyError(String string);
		
		/**
		 * Sets the comments to be displayed.
		 */
		void setComments(Comment[] comments);
		
		/**
		 * Sets the comments to be displayed with a mask
		 * mapping the ids of the comments and a boolean value.
		 * A boolean value of true means that the user has
		 * previously voted for that link.
		 */
		void setComments(Comment[] comments, HashMap<Long, Boolean> voteMask);
		
		/**
		 * Sets a widget to show as a submit form.
		 * When this widget is set, it is displayed.
		 * Do not pass the submit form widget if you
		 * do not want the form to be display, for example
		 * if the user is not logged in.
		 * @param widget
		 */
		void setSubmitForm(Widget widget);
		
		/**
		 * A handler that is called when a reply button
		 * is clicked.
		 */
		void setReplyHandler(ReplyHandler replyHandler);
		
		/**
		 * For handling when the reply button is clicked.
		 */
		interface ReplyHandler {
			/**
			 * Called when a reply button is clicked.
			 * @param commentId The id of the parent comment.
			 * @param replyBody The textual body of the reply.
			 */
			void onReplySubmitted(Long commentId, String replyBody);
		}
		
		/**
		 * Called when a link vote is voted on. In this case,
		 * this should always be the currently displayed link.
		 * @param linkVoteClickHandler
		 */
		void setLinkVoteClickHandler(LinkVoteClickHandler linkVoteClickHandler);	// from LinkPresenter
		/**
		 * Called when a link is unvoted. In this case
		 * this should always be the currently displayed link.
		 * @param linkUnvoteClickHandler
		 */
		void setLinkUnvoteClickHandler(LinkUnvoteClickHandler linkUnvoteClickHandler);
		void setVotedByUser( boolean voted, boolean justTriedToVote);
		void setVoteFailureMessage(String failureMessage);
		void setVoteSuccessResult(Integer newLinkEnergy);
		void clearTileAndComments();
		
		void setCommentVoteClickHandler(CommentVoteClickHandler commentVoteClickHandler);
		void setCommentUnvoteClickHandler(CommentUnvoteClickHandler commentUnvoteClickHandler);
		interface CommentVoteClickHandler {
			void onClick(Comment comment);
		}
		interface CommentUnvoteClickHandler {
			void onClick(Comment comment);
		}
		void setCommentVotedByUser(Long commentId, boolean votedOnByUser, boolean voteJustFailed);
		void setCommentVoteFailureMessage(Long commentId, String message);
		void showLoggedIn(boolean showLoggedIn);
	}
	
	@Override
	public Place getPlace() {
		return PLACE;
	}

	/**
	 * Fetches the link to be presented.
	 * @param linkId The id of the link to be presented.
	 */
	private void fetchLink(final Long linkId) {
		// Prevent the user from using any part of the display.
		display.startProcessing();
		
		if(link != null && link.getId() != linkId){
			// we're not just refreshing the comments: we're actually loading another 
			// link's comment thread.
			display.clearTileAndComments();
			
			// Show the submit link box unfocused
			getSubmitCommentPresenter().getDisplay().setUnfocused();
		}
		
		// Pass the id of the link to the submit comment
		// presenter so that it may know on what link
		// is a comment posted for.
		getSubmitCommentPresenter().setLinkId(linkId);
		
		// Send the request to the server.
		dispatch.execute(new GetLinkComments(linkId), new AsyncCallback<GetLinkCommentsResult>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO: Do not loop indefinitely
				GWT.log("Error fetching comments", caught);
				Window.alert("An error occured while fetching comments");
			}

			@Override
			public void onSuccess(GetLinkCommentsResult result) {
				// Set and display the link. It may have been
				// updated.
				link = result.getLink();
				display.setLink(link, result.getVotedOnByUser());
				// Set the vote mask if available.
				try {
					HashMap<Long, Boolean> voteMask = result.getCommentsVoteMask();
					if(voteMask != null) {
						display.setComments(result.getComments(), voteMask);
	            	} else {
	            		display.setComments(result.getComments());
	            	}
				} catch (Throwable e) {
					Window.alert(e.getMessage());
				}
				
				// Allow the display to be used.
				display.stopProcessing();
			}
		});
	}

	@Override
	protected void onBind() {
		super.onBind();
		submitCommentPresenterProvider.get().bind();
		registerHandler(eventBus.addHandler(CommentSubmittedEvent.TYPE, 
				new CommentSubmittedEventHandler() {
					@Override
					public void onCommentSubmit(CommentSubmittedEvent event) {
						refreshDisplay();
					}
		}));
		
		display.setReplyHandler(replyHandler);
		
		display.setLinkVoteClickHandler(new LinkVoteClickHandler() {
			@Override
			public void onClick(final Link link) {
				dispatch.execute(new VoteOnLink(link.getId()), new AsyncCallback<VoteOnLinkResult>() {
					public void onFailure( Throwable e ) {
						GWT.log("Error voting for link.", e);
						// Here follows the lazy way to tell the use to log in
						// TODO: handled not-logged-in state in the client .. maybe with a cookie?
						// No, I'm thinking that the user should not be allowed to click on the
						// button anyways.
						display.setVotedByUser(false, true); // go back to unvoted state
						if(e instanceof NotLoggedInException) {
							display.setVoteFailureMessage("Please log in to vote.");
						}
						else if(e instanceof AlreadyVotedException) {
							display.setVoteFailureMessage(e.getMessage());
						} else {
							display.setVoteFailureMessage("Oops! Try again please.");
						}
		            }

		            public void onSuccess(VoteOnLinkResult result) {
		            	display.setVoteSuccessResult(result.getNewLinkEnergy());
		            }
				});
			}
		});
		
		display.setLinkUnvoteClickHandler(new LinkUnvoteClickHandler() {
			@Override
			public void onClick(final Link link) {
				dispatch.execute(new UnvoteOnLink(link.getId()), new AsyncCallback<UnvoteOnLinkResult>() {
					public void onFailure( Throwable e ) {
						GWT.log("Error unvoting for link.", e);
						// Here follows the lazy way to tell the use to log in
						// TODO: handled not-logged-in state in the client .. maybe with a cookie?
						display.setVotedByUser(true, true); // go back to voted state
						if(e instanceof NotLoggedInException) {
							display.setVoteFailureMessage("Please log in to unvote.");
						}
						else if(e instanceof AlreadyVotedException) {
							display.setVoteFailureMessage(e.getMessage());
						} else {
							display.setVoteFailureMessage("Oops! Try again please.");
						}
		            }

		            public void onSuccess(UnvoteOnLinkResult result) {
		            	display.setVoteSuccessResult(result.getNewLinkEnergy());
		            }
				});
			}
		});
		
		display.setCommentVoteClickHandler(new CommentVoteClickHandler(){
			@Override
			public void onClick(Comment comment) {
				final Long commentId = comment.getId();
				dispatch.execute(new VoteOnComment(commentId), new AsyncCallback<VoteOnCommentResult>() {
					public void onFailure( Throwable e ) {
						GWT.log("Error voting for comment.", e);
						// Here follows the lazy way to tell the use to log in
						// TODO: handled not-logged-in state in the client .. maybe with a cookie?
						display.setCommentVotedByUser(commentId, false, true); // go back to unvoted state
						if(e instanceof NotLoggedInException) {
							display.setCommentVoteFailureMessage(commentId, "Please log in to vote.");
						}
						else if(e instanceof AlreadyVotedException) {
							display.setCommentVoteFailureMessage(commentId, e.getMessage());
						} else {
							display.setCommentVoteFailureMessage(commentId, "Oops! Try again please.");
						}
		            }

		            public void onSuccess(VoteOnCommentResult result) {
		            }
				});
			}
		});
		
		display.setCommentUnvoteClickHandler(new CommentUnvoteClickHandler() {
			@Override
			public void onClick(final Comment comment) {
				final Long commentId = comment.getId();
				dispatch.execute(new UnvoteOnComment(commentId), new AsyncCallback<UnvoteOnCommentResult>() {
					public void onFailure( Throwable e ) {
						GWT.log("Error voting unvoting for comment.", e);
						// Here follows the lazy way to tell the use to log in
						// TODO: handled not-logged-in state in the client .. maybe with a cookie?
						display.setCommentVotedByUser(commentId, true, true); // go back to voted state
						if(e instanceof NotLoggedInException) {
							display.setCommentVoteFailureMessage(commentId, "Please log in to unvote.");
						}
						else if(e instanceof AlreadyVotedException) {
							display.setCommentVoteFailureMessage(commentId, e.getMessage());
						} else {
							display.setCommentVoteFailureMessage(commentId, "Oops! Try again please.");
						}
		            }

		            public void onSuccess(UnvoteOnCommentResult result) {
		            }
				});
			}
		});
	}
	
	private SubmitCommentPresenter getSubmitCommentPresenter() {
		return submitCommentPresenterProvider.get();
	}
	
	@Override
	protected void onLoginInfoFetched(LoginInfo loginInfo) {
		if (loginInfo.isLoggedIn() && loginInfo.isRegistered()) {
			display.setSubmitForm(getSubmitCommentPresenter().getDisplay().asWidget());
			display.showLoggedIn(true);
		} else {
			display.showLoggedIn(false);
		}
	}

	@Override
	protected void onPlaceRequest(PlaceRequest request) {
		Long linkId = Long.parseLong(request.getParameter("linkId", null));
		fetchLink(linkId);
		eventBus.fireEvent(new PlaceChangedEvent(request));
	}

	@Override
	protected void onUnbind() {
	}

	@Override
	public void refreshDisplay() {
		if (link != null) {
			fetchLink(link.getId());
		}
	}

	@Override
	public void revealDisplay() {
		mainPresenter.setPresenter(this);
	}
}
