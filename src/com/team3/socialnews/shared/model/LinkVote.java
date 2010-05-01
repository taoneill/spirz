package com.team3.socialnews.shared.model;

import java.util.Date;

import javax.persistence.Id;

import com.googlecode.objectify.annotation.Cached;

@Cached
public class LinkVote {

	@Id
	private Long id;
	
	private String voterId;
	
	private Long linkId;
	
	private Date voteDate;
	
	private Date linkCreateDate;
	
	private Integer linkEnergy;
	
	private Integer voteEnergyContribution;

	private Boolean wasUnvoted;
	
	LinkVote() {}
	
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
