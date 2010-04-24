package com.team3.socialnews.client.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.team3.socialnews.client.presenter.LinkCommentsPresenter;
import com.team3.socialnews.client.presenter.LinksPresenter.Display.LinkUnvoteClickHandler;
import com.team3.socialnews.client.presenter.LinksPresenter.Display.LinkVoteClickHandler;
import com.team3.socialnews.shared.model.Comment;
import com.team3.socialnews.shared.model.CommentComparator;
import com.team3.socialnews.shared.model.Link;

public class LinkCommentsPanel extends AbstractView implements LinkCommentsPresenter.Display {

	private static LinkCommentsPanelUiBinder uiBinder = GWT
			.create(LinkCommentsPanelUiBinder.class);

	interface LinkCommentsPanelUiBinder extends
			UiBinder<Widget, LinkCommentsPanel> {
	}

	@UiField
	FlowPanel processingPanel;
	@UiField
	FlowPanel linkTilePanel;
	@UiField
	AdjustedColumns commentsPanel;
	@UiField
	AdjustedColumns topPanel;
	@UiField
	VerticalPanel submitForm;
	@UiField
	RoundedPanel submitFormWrapper;
	
	@UiField
	Label noCommentsMessage;
	@UiField
	FlowPanel noCommentsPanel;
	
	ReplyHandler replyHandler;
	CommentReplyForm replyForm;
	LinkTile tile;
	
	Comment commentOnReply;
	CommentWidget replyFormHolder;
	ArrayList<CommentWidget> rootCommentWidgets = 
		new ArrayList<CommentWidget>();
	protected LinkVoteClickHandler linkVoteClickHandler;
	protected LinkUnvoteClickHandler linkUnvoteClickHandler;
	private HashMap<Long, Boolean> commentsVoteMask;
	private CommentUnvoteClickHandler commentUnvoteClickHandler;
	private CommentVoteClickHandler commentVoteClickHandler;
	
	public LinkCommentsPanel() {
		initWidget(uiBinder.createAndBindUi(this));
		replyForm = new CommentReplyForm();
		replyForm.body.addBlurHandler(new BlurHandler(){
			@Override
			public void onBlur(BlurEvent event) {
				if(replyForm.body.getValue().length() == 0){
					// if empty on unfocus, close the form
					replyFormHolder.hideReplyForm();
				}				
			}
		});
		replyForm.button.addClickHandler(new SubmitReplyClickHandler());
		Window.addResizeHandler(new ResizeHandler() {

			@Override
			public void onResize(ResizeEvent event) {
				commentsPanel.resizeColumns();
				topPanel.resizeColumns();
			}
		});
	}
	
	/**
	 * When the submit button in the reply form is clicked
	 * the instance of this class calls notifies the
	 * reply handler.
	 */
	private class SubmitReplyClickHandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			replyHandler.onReplySubmitted(commentOnReply.getId(), replyForm.getBody().getValue());
		}
	}
	
	/**
	 * On instance per comment widget. When the reply
	 * button is pressed, the reply comment form
	 * is removed from the previous widget and
	 * added to the comment widget on which the reply
	 * button was clicked.
	 */
	private class ReplyButtonClickHandler implements ClickHandler {
		final Comment comment;
		final CommentWidget widget;
		public ReplyButtonClickHandler(Comment comment, CommentWidget widget) {
			this.comment = comment;
			this.widget = widget;
		}
		@Override
		public void onClick(ClickEvent event) {
			if (replyFormHolder != null) {
				replyFormHolder.hideReplyForm();
			}
			replyFormHolder = widget;
			commentOnReply = comment;
			replyForm.body.setValue(""); // clear the contents every time we reopen
			
			Timer setReplyFormFocusAfterDelay = new Timer() {
				@Override
				public void run() {
					replyForm.body.setFocus(true);
				}
			};
			setReplyFormFocusAfterDelay.schedule(150);	// got to do this with a delay, otherwise the form won't get the focus
			
			replyFormHolder.showReplyForm(replyForm);
		}
	}
	
	@Override
	public Widget asWidget() {
		return this;
	}
	
	public void setComments(Comment[] comments, HashMap<Long, Boolean> voteMask) {
		this.commentsVoteMask = voteMask;
		setComments(comments);
		// Weird place to resize the top topPanel
		// Putting this in the constructor will
		// always result in a top panel with 1 column.
		// This is like that because the size of the widget
		// is zero when it is not rendered yet.
		topPanel.resizeColumns();
	}
	
	private void flattenComments(List<Comment> comments, List<Comment> roots, Map<Long, ArrayList<Comment>> commentChildren) {
		// Loop through all comment and find root comment
		// and replies. Root comments are stored in the
		// roots list. All comments children's list are
		// mapped in the commentChildren map.
		for(Comment comment : comments) {
			
			// Add ourselves to the parent children
			// list if there is a parent. If there
			// is no parent, the comment is a root
			// and we add it to the roots list.
			Long parentId = comment.getParentId();
			if (parentId == null) {
				// This is a root comment
				// add it to the roots list
				roots.add(comment);
			} else {
				// This parent has a parent.
				// Set this comment's children list
				// to its parent's list.
				
				// Get the parent's list of children.
				ArrayList<Comment> parentChildren = commentChildren.get(parentId);
				// If the parent has not been encountered yet,
				// then its list will be null. We should create it now
				// so that we can add the children of this comment.
				if (parentChildren == null) {
					parentChildren = new ArrayList<Comment>();
					commentChildren.put(parentId, parentChildren);
				}
				// Add this comment as a child.
				parentChildren.add(comment);
				
				// Perhaps children of this comment have been
				// encountered already, then some children might
				// have been collected. We add those children to
				// this comment's parent.
				Long id = comment.getId();
				ArrayList<Comment> children = commentChildren.get(id);
				ArrayList<Comment> parentsChildren = commentChildren.get(parentId);
				
				if (children != null) {
					// No children have been encountered
					// up to this point
					parentsChildren.addAll(children);
				} else {
					children = parentsChildren;
				}
				commentChildren.put(id, children);
			}
		}
	}
	
	private void showNoCommentsMessage(boolean showNoCommentsMessage) {
		if (showNoCommentsMessage) {
			noCommentsPanel.setVisible(true);
			noCommentsMessage.setText("Start the discussion by posting a comment.");
		} else {
			noCommentsPanel.setVisible(false);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void setComments(Comment[] comments) {
		// Remove CommentWidgets
		for(CommentWidget cw : rootCommentWidgets)
			commentsPanel.remove(cw);
		if (comments.length == 0) {
			showNoCommentsMessage(true);
		} else {
			showNoCommentsMessage(false);
			// Root level comments with no parents
			ArrayList<Comment> roots = new ArrayList<Comment>();
			// Maps comments to their children.
			HashMap<Long, ArrayList<Comment>> commentChildren = new HashMap<Long, ArrayList<Comment>>();
			flattenComments(Arrays.asList(comments), roots, commentChildren);
			
			// Sort the roots by voteTotal
			Collections.sort(roots, new CommentComparator());
			
			// Create the widgets
			boolean isFirst = true;
			ArrayList<Widget> commentWidgets = new ArrayList<Widget>();
			for(final Comment root : roots) {
				CommentWidget cw = new CommentWidget(root);
				if (isFirst) {
					cw.setHighlightLevel(1);
					isFirst = false;
				}
				ReplyButtonClickHandler rootReplyHandler = new ReplyButtonClickHandler(root, cw);
				cw.getReplyButton().addClickHandler(rootReplyHandler);
				cw.voteButton.addVoteClickHandler(new ClickHandler(){
					@Override
					public void onClick(ClickEvent even) {
						commentVoteClickHandler.onClick(root);
					}
				});
				cw.voteButton.addUnvoteClickHandler(new ClickHandler(){
					@Override
					public void onClick(ClickEvent even) {
						commentUnvoteClickHandler.onClick(root);
					}
				});
				if(commentsVoteMask != null){
					cw.voteButton.setVotedByUser(commentsVoteMask.get(root.getId()));
				}
			
				ArrayList<Comment> children = commentChildren.get(root.getId());
				if (children != null) {
					for(final Comment child : children) {
						CommentReplyWidget reply = new CommentReplyWidget();
						reply.setAuthorNickname(child.getAuthorNickname());
						reply.setBody(child.getBody());
						reply.setDate(child.getCommentDate());
						reply.setCommentId(child.getId());
						reply.voteButton.setVoteCount(child.getVoteTotal());
						ReplyButtonClickHandler childReplyHandler = new ReplyButtonClickHandler(child, cw); 
						reply.getReplyButton().addClickHandler(childReplyHandler);
						reply.voteButton.addVoteClickHandler(new ClickHandler(){
							@Override
							public void onClick(ClickEvent even) {
								commentVoteClickHandler.onClick(child);
							}
						});
						reply.voteButton.addUnvoteClickHandler(new ClickHandler(){
							@Override
							public void onClick(ClickEvent even) {
								commentUnvoteClickHandler.onClick(child);
							}
						});
						if(commentsVoteMask != null){
							reply.voteButton.setVotedByUser(commentsVoteMask.get(child.getId()));
						}
						cw.getReplies().add(reply);
					}
				}
				else{
					// no replies
					cw.hideReplies();
				}
				
				commentWidgets.add(cw);
				rootCommentWidgets.add(cw);
			}
			commentsPanel.addAll(commentWidgets);
		}
	}

	@Override
	public void setLink(final Link link, Boolean votedOnByUser) {
		tile = new LinkTile(
						link.getId(),
						link.getEnergy(), 
						link.getTitle(), 
						link.getUrl(), 
						link.getCreateDate(), 
						link.getCommentTotal() == null? 0 : link.getCommentTotal(),
						link.getVoteTotal(),
						link.getSubmitterNickname(),
						votedOnByUser
						);
		linkTilePanel.clear();
		linkTilePanel.add(tile);
		tile.voteAnchor.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				linkVoteClickHandler.onClick(link);
			}
		});
		tile.unvoteAnchor.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				linkUnvoteClickHandler.onClick(link);
			}
		});
	}
	
	@Override
	public void startProcessing() {
		noCommentsPanel.setVisible(false);
		processingPanel.setVisible(true);
		// don't hide the tile panel: it will be hidden by the presenter if
		// we are really switching links
		replyForm.setEnabled(false);
		
		// Keep the submit form visible only if there are some comments
		// already shown in the view.
		submitFormWrapper.setVisible(commentsPanel.iterator().hasNext());
	}
	
	@Override
	public void stopProcessing() {
		processingPanel.setVisible(false);
		linkTilePanel.setVisible(true);
		replyForm.setEnabled(true);
		submitFormWrapper.setVisible(true);
		topPanel.resizeColumns();
	}

	@Override
	public void setSubmitForm(Widget widget) {
		submitForm.clear();
		submitForm.add(widget);
	}

	@Override
	public void setReplyHandler(ReplyHandler replyHandler) {
		this.replyHandler = replyHandler;
	}

	@Override
	public void showReplyError(String string) {
		replyForm.showError(string);
	}

	@Override
	public void setLinkVoteClickHandler(
			LinkVoteClickHandler linkVoteClickHandler) {
		this.linkVoteClickHandler= linkVoteClickHandler;
	}

	@Override
	public void setLinkUnvoteClickHandler(
			LinkUnvoteClickHandler linkUnvoteClickHandler) {
		this.linkUnvoteClickHandler  = linkUnvoteClickHandler;
	}

	@Override
	public void setVoteFailureMessage(String failureMessage) {
		tile.linkDetails.clear();
		Label label = new Label(failureMessage);
		label.setStyleName("failureMessage");
		tile.linkDetails.add(label);
		Timer timer = new Timer(){
			@Override
			public void run(){
				tile.refreshDetails();
			}
		};
		timer.schedule(4000); // wait a few seconds before reverting back to the details
		
	}

	@Override
	public void setVoteSuccessResult(Integer newLinkEnergy) {
		tile.setEnergy(newLinkEnergy);
		
	}

	@Override
	public void setVotedByUser(boolean votedByUser, boolean justTriedToVote) {
		tile.setVotedByUser(votedByUser, justTriedToVote);
		
	}

	@Override
	public void clearTileAndComments() {
		linkTilePanel.clear();
		for(CommentWidget cw : rootCommentWidgets)
			commentsPanel.remove(cw);
	}


	@Override
	public void setCommentUnvoteClickHandler(
			CommentUnvoteClickHandler commentUnvoteClickHandler) {
		this.commentUnvoteClickHandler = commentUnvoteClickHandler;
		
	}


	@Override
	public void setCommentVoteClickHandler(
			CommentVoteClickHandler commentVoteClickHandler) {
		this.commentVoteClickHandler = commentVoteClickHandler;
		
	}


	@Override
	public void setCommentVotedByUser(Long commentId, boolean votedOnByUser,
			boolean voteJustFailed) {
		CommentWidget commentWidget = getCommentForId(commentId);
		if(commentWidget != null){
			commentWidget.voteButton.setVotedByUser(votedOnByUser, voteJustFailed);
		} else {
			CommentReplyWidget replyWidget = getReplyForId(commentId);
			if(replyWidget!= null){
				replyWidget.voteButton.setVotedByUser(votedOnByUser, voteJustFailed);
			}
		}
	}


	@Override
	public void setCommentVoteFailureMessage(Long commentId, String message) {
		// don't do anything for now.
	}
	
	private CommentWidget getCommentForId(Long commentId){
		for(CommentWidget cw : rootCommentWidgets){
			if(cw.getCommentId() == commentId){
				return cw;
			}
		}
		return null;
	}
	
	private CommentReplyWidget getReplyForId(Long commentId){
		for(CommentWidget cw : rootCommentWidgets){
			for(Widget widget : cw.getReplies()){
				CommentReplyWidget reply = (CommentReplyWidget) widget;
				if(reply.getCommentId() == commentId){
					return reply;
				}
			}
		}
		return null;
	}

	@Override
	public void showLoggedIn(boolean showLoggedIn) {
		if (showLoggedIn == false) {
			FlowPanel flowPanel = new FlowPanel();
			flowPanel.add(new Label("Please login to post a comment!"));
			flowPanel.addStyleName("commentsPleaseLoginMessage");
			this.setSubmitForm(flowPanel);
		}
	}
}
