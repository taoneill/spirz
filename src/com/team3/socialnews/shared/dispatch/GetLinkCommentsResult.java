package com.team3.socialnews.shared.dispatch;

import java.util.HashMap;

import net.apptao.highway.shared.dispatch.HwyResult;

import com.team3.socialnews.client.model.CommentClient;
import com.team3.socialnews.shared.model.Link;

public class GetLinkCommentsResult implements HwyResult {
	
	/** For serialiation */
	GetLinkCommentsResult() {}
	
	private Link link;
	private CommentClient[] comments;
	private Boolean votedOnByUser;
	private HashMap<Long, Boolean> commentsVoteMask;
	public GetLinkCommentsResult(Link link, CommentClient[] comments) {
		this.link = link;
		this.comments = comments;
	}
	
	public GetLinkCommentsResult(Link link, CommentClient[] comments, Boolean votedOnByUser, HashMap<Long, Boolean> commentsVoteMask) {
		this.link = link;
		this.comments = comments;
		this.setVotedOnByUser(votedOnByUser);
		this.setCommentsVoteMask(commentsVoteMask);
	}

	/**
	 * @return An updated version of the link.
	 */
	public Link getLink() {
		return link;
	}
	
	/**
	 * @return Comments for a link
	 */
	public CommentClient[] getComments() {
		return comments;
	}

	public void setVotedOnByUser(Boolean votedOnByUser) {
		this.votedOnByUser = votedOnByUser;
	}

	public Boolean getVotedOnByUser() {
		return votedOnByUser;
	}

	public void setCommentsVoteMask(HashMap<Long, Boolean> commentsVoteMask) {
		this.commentsVoteMask = commentsVoteMask;
	}

	public HashMap<Long, Boolean> getCommentsVoteMask() {
		return commentsVoteMask;
	}
}
