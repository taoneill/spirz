package com.team3.socialnews.shared.model;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class LinkVote {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;
	
	@Persistent
	private String voterId;
	
	@Persistent
	private Long linkId;
	
	@Persistent
	private Date voteDate;
	
	@Persistent
	private Date linkCreateDate;
	
	@Persistent
	private Integer linkEnergy;
	
	@Persistent
	private Integer voteEnergyContribution;

	@Persistent
	private Boolean wasUnvoted;
	
	/**
	 * @param userId The id of the user who votes on the link.
	 * @param link The link 
	 */
	public LinkVote(String userId, Link link) {
		this.voteDate = new Date();
		this.voterId = userId;
		this.linkId = link.getId();
		this.linkCreateDate = link.getCreateDate();
		this.linkEnergy = link.getEnergy();
		this.voteEnergyContribution = 0;
		this.wasUnvoted = false;
	}
	
	public Long getId() {
		return id;
	}
	
	public String getVoterId() {
		return voterId;
	}
	
	public Long getLinkId() {
		return linkId;
	}
	
	public Date getVoteDate() {
		return voteDate;
	}
	
	public Date getLinkCreateDate() {
		return linkCreateDate;
	}
	
	public Integer getLinkEnergy() {
		return linkEnergy;
	}
	
	public Integer getVoteEnergyContribution() {
		return voteEnergyContribution;
	}
	
	public void setVoteEnergyContribution(Integer voteEnergyContribution) {
		this.voteEnergyContribution = voteEnergyContribution;
	}
	
	public boolean getWasUnvoted() {
		return this.wasUnvoted;
	}

	public void setWasUnvoted(boolean wasUnvoted) {
		this.wasUnvoted = wasUnvoted;		
	}
}
