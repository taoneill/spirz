package com.team3.socialnews.shared.model;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class LinkDampVote {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;
	
	@Persistent
	private Long dampedLinkId;	// the link vote that caused the damp vote
	
	@Persistent
	private Long sourceLinkVoteId;	// the link vote that caused the damp vote 
	
	@Persistent
	private Integer dampVoteEnergy;
	
	@Persistent
	private Date dampVoteDate;
	
	@Persistent
	private Boolean wasUnvoted;
	
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
