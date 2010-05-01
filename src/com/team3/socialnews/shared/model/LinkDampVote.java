package com.team3.socialnews.shared.model;

import java.util.Date;

import javax.persistence.Id;

import com.googlecode.objectify.annotation.Cached;

@Cached
public class LinkDampVote {

	@Id
	private Long id;
	
	private Long dampedLinkId;	// the link vote that caused the damp vote
	
	private Long sourceLinkVoteId;	// the link vote that caused the damp vote 
	
	private Integer dampVoteEnergy;
	
	private Date dampVoteDate;
	
	private Boolean wasUnvoted;
	
	LinkDampVote(){}
	
	public LinkDampVote(LinkVote vote, Link dampVoteCandidate, int dampVoteEnergy) {
		this.sourceLinkVoteId = vote.getId(); 	// TODO: make sure i really have the id here
		this.dampedLinkId = dampVoteCandidate.getId();
		this.dampVoteEnergy = dampVoteEnergy;
		this.dampVoteDate = new Date();
		this.wasUnvoted = false;
	}

	public Long getId() {
		return id;
	}
	
	public Long getSourceLinkVoteId() {
		return sourceLinkVoteId;
	}
	
	public Long getDampedLinkId() {
		return dampedLinkId;
	}
	
	public Date getDampVoteDate() {
		return dampVoteDate;
	}
	
	public int getDampVoteEnergy() {
		return dampVoteEnergy;
	}
	
	public boolean getWasUnvoted() {
		return this.wasUnvoted;
	}

	public void setWasUnvoted(boolean wasUnvoted) {
		this.wasUnvoted = wasUnvoted;		
	}
}
