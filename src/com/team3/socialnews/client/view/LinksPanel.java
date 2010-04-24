package com.team3.socialnews.client.view;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.team3.socialnews.client.presenter.LinksPresenter;
import com.team3.socialnews.shared.model.Link;

public class LinksPanel extends AbstractView implements LinksPresenter.Display {

	private static LinksPanelUiBinder uiBinder = GWT
			.create(LinksPanelUiBinder.class);

	interface LinksPanelUiBinder extends UiBinder<Widget, LinksPanel> {
	}

	@UiField
	FlowPanel rightSection;
	@UiField
	FlowPanel linksPanel;
	
	@UiField
	FlowPanel orderedLinks;
	@UiField
	HTMLPanel pagerPanel;
	@UiField
	Anchor nextAnchor;
	@UiField
	Anchor previousAnchor;
	
	private List<Link> newLinks;
	private List<Boolean> voteMask;
//	private Order order;
	LinkVoteClickHandler linkVoteClickHandler;
	//CommentClickHandler commentClickHandler;
	LinkUnvoteClickHandler linkUnvoteClickHandler;

 LinksPanel() {
		initWidget(uiBinder.createAndBindUi(this));
		
		int windowWidth = Window.getClientWidth();
		
	
		
		Window.addResizeHandler(new ResizeHandler() {
			@Override
			public void onResize(ResizeEvent event) {
				resizeTiles();
			}
		});
		rightSection.setVisible(false);
	}

	private void resizeTiles() {
		int tileSize = 453;
		int windowWidth = Window.getClientWidth();
		this.setWidth(windowWidth+"px");
		if (newLinks != null && newLinks.size() > 0) {
	 		 
			int howManyFit = windowWidth/tileSize; 
			if (howManyFit == 0) {
				howManyFit = 1;
			}
			double pixel;
			int displayedWidgets = orderedLinks.getWidgetCount();
			if (rightSection.getWidgetCount() > 0) {
				displayedWidgets++;
			}
			if (displayedWidgets < howManyFit) {
				pixel = windowWidth/displayedWidgets - 10 ;
			} else {
				pixel = (windowWidth - 10*howManyFit)/howManyFit; // two 5px margins
			}
			
			for(int i = 0; i < orderedLinks.getWidgetCount(); i++) {
				LinkTile lt = (LinkTile)orderedLinks.getWidget(i);
				lt.setWidth(pixel);
			}
			if(rightSection.getWidgetCount() > 0 ){
				if(windowWidth > tileSize){
					rightSection.setWidth(pixel+10+"px");
				}
				else {
					rightSection.setWidth(windowWidth+"px");
				}
			}
		} else if(rightSection.getWidgetCount() > 0 ){
			if(windowWidth > tileSize){
				rightSection.setWidth(tileSize+10+"px");
			}
			else {
				rightSection.setWidth(windowWidth+"px");
			}
		}

 	}
 	
	@Override
	public Widget asWidget() {
		return this;
	}

	public void setLinks(List<Link> links) {
		this.newLinks = links;
	}

	@Override
	public void refresh() {
		orderedLinks.clear();
		if (newLinks != null && newLinks.size() > 0) {
			int i = 0;
			for(final Link link : this.newLinks) {
				boolean votedOnByUser = voteMask == null ? false : voteMask.get(i);
				LinkTile linkWidget = new LinkTile(		// Notice here we are not using the old LinkWidget
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
				
//				linkWidget.commentsAnchor.addClickHandler(new ClickHandler() {
//					@Override
//					public void onClick(ClickEvent event) {
//						commentClickHandler.onClick(link);
//					}
//				});
				linkWidget.voteAnchor.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						linkVoteClickHandler.onClick(link);
					}
				});
				linkWidget.unvoteAnchor.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						linkUnvoteClickHandler.onClick(link);
					}
				});
				orderedLinks.add(linkWidget);
				i++;
			}
		} else {
			Label noLinks = new Label("No links here, sorry!");
			noLinks.setStyleName("noLinksLabel");
			orderedLinks.add(noLinks);
		}
		rightSection.setVisible(true);
		resizeTiles();
	}

	@Override
	public HasClickHandlers getNextButton() {
		return nextAnchor;
	}

	@Override
	public HasClickHandlers getPreviousButton() {
		return previousAnchor;
	}

	@Override
	public void setPagerVisibility(boolean showPreviousButton, boolean showNextButton) {
		pagerPanel.setVisible(showPreviousButton || showNextButton);
		previousAnchor.setVisible(showPreviousButton);
		nextAnchor.setVisible(showNextButton);
	}

	@Override
	public void setLinkVoteClickHandler(LinkVoteClickHandler handler) {
		this.linkVoteClickHandler = handler;
	}	

	@Override
	public void setLinkUnvoteClickHandler(LinkUnvoteClickHandler handler) {
		this.linkUnvoteClickHandler = handler;
	}
	
//	@Override
//	public void setCommentClickHandler(CommentClickHandler handler) {
//		this.commentClickHandler = handler;
//	}

	@Override
	public void setVotedByUser(Long linkId, boolean voted, boolean justTriedToVote) {
		LinkTile linkWidget = getLinkWidget(linkId);
		linkWidget.setVotedByUser(voted, justTriedToVote);
	}
	
	@Override
	public void setVoteSuccessResult(Long linkId, int newLinkEnergy) {
		LinkTile linkWidget = getLinkWidget(linkId);
		//linkWidget.setVotedByUser(true); // already set upon the click 
		linkWidget.setEnergy(newLinkEnergy);
	}
	
	@Override
	public void setUnvoteSuccessResult(Long linkId, int newLinkEnergy) {
		LinkTile linkWidget = getLinkWidget(linkId);
		//linkWidget.setVotedByUser(false); // already set upon the click
		linkWidget.setEnergy(newLinkEnergy);
	}
	
	private LinkTile getLinkWidget(Long linkId) {
		int i = 0;
		for(Link link : newLinks) {
			if(linkId == link.getId()) {
				return (LinkTile)orderedLinks.getWidget(i);
			}
			i++;
		}
		return null;
	}

	@Override
	public void setVoteMask(List<Boolean> voteMask) {
		this.voteMask = voteMask;
	}

	@Override
	public HasWidgets getRightSideContainer() {
		return rightSection;
	}

	@Override
	public void setVoteFailureMessage(Long linkId, String failureMessage) {
		final LinkTile widget = getLinkWidget(linkId);
		if(widget != null) {
			widget.linkDetails.clear();
			Label label = new Label(failureMessage);
			label.setStyleName("failureMessage");
			widget.linkDetails.add(label);
			Timer timer = new Timer(){
				@Override
				public void run(){
					widget.refreshDetails();
				}
			};
			timer.schedule(4000); // wait a few seconds before reverting back to the details
		}
	}

	@Override
	public void highlightFirstTile() {
		LinkTile tile = (LinkTile)orderedLinks.getWidget(0);
		tile.setHighlightLevel(1);
	}
	
	

}
