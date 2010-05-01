package com.team3.socialnews.shared.model;

import java.util.Date;

import javax.persistence.Id;

import com.googlecode.objectify.annotation.Cached;

@Cached
public class CommentVote {

	@Id
	private Long id;

	private String voterId;
	
	private Long commentId;
	
	private Date voteDate;
	
	private Boolean wasUnvoted;
			
	CommentVote() {}
	
	public CommentVote(String userId, Comment comment) {
		this.voteDate = new Date();
		this.voterId = userId;
		this.wasUnvoted = false;
		this.commentId = comment.getId();
	}
	
	public Long getId() {
		return id;
	}
	
	public String getVoterId() {
		return voterId;
	}
	
	public Date getVoteDate() {
		return voteDate;
	}
	
	public boolean getWasUnvoted() {
		return this.wasUnvoted;
	}

	public void setWasUnvoted(boolean wasUnvoted) {
		this.wasUnvoted = wasUnvoted;		
	}

	public Long getCommentId() {
		return commentId;
	}
}
