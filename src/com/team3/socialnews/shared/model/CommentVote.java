package com.team3.socialnews.shared.model;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class CommentVote {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;
	
	@Persistent
	private String voterId;
	
	@Persistent
	private Long commentId;
	
	@Persistent
	private Date voteDate;
	
	@Persistent
	private Boolean wasUnvoted;
			
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
