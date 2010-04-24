package com.team3.socialnews.shared.model;

import java.io.Serializable;
import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Link implements Serializable {
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;
	
	@Persistent
	private String url;
	
	@Persistent
	private String title;
	
	@Persistent
	private Date createDate;

	@Persistent
	private String nickname;
	
	@Persistent
	private Integer energy = 0;
	
	@Persistent
	private Integer voteTotal = 0;
	
	@Persistent
	private Integer commentTotal = 0;

	@Persistent
	private String submitterId;
	
	@Persistent
	private Boolean hot = false;
	
	public Link() {
		this.setCreateDate(new Date());
	}
	
	/**
	 * @param url The url of the link.
	 * @param title The title of the link.
	 * @param submitterId The ID of the user who submitted the link.
	 * @param submitterNickname The nickname of the user who submitted the link.
	 */
	public Link(String url, String title, String submitterId, String submitterNickname) {
		this();
		this.url = url;
		this.title = title;
		this.nickname = submitterNickname;
		this.submitterId = submitterId;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public String getSubmitterNickname() {
		return nickname;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getCreateDate() {
		return createDate;
	}
	
	public Long getId() {
		return id;
	}
	
	public Integer getEnergy() {
		if(energy == null){
			setEnergy(0);
		}
		return energy;
	}
	
	public void setEnergy(Integer energy) {
		this.energy = energy;
	}
	
	public Integer getVoteTotal() {
		if(voteTotal == null){
			setVoteTotal(0);
		}
		return voteTotal;
	}
	
	public void setVoteTotal(Integer voteTotal) {
		this.voteTotal = voteTotal;
	}
	
	public String getSubmitterId() {
		return this.submitterId;
	}

	public void setHot(boolean hot) {
		this.hot = hot;
	}

	public boolean isHot() {
		return hot;
	}

	public void setCommentTotal(Integer commentTotal) {
		this.commentTotal = commentTotal;
	}

	public Integer getCommentTotal() {
		return commentTotal;
	}
}
