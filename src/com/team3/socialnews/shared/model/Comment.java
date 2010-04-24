package com.team3.socialnews.shared.model;

import java.util.Date;

public interface Comment {

	public abstract Long getId();
	public abstract String getAuthorNickname();

	public abstract String getBody();

	public abstract Long getLinkId();

	public abstract Long getParentId();

	public abstract void setVoteTotal(Integer voteTotal);

	public abstract Integer getVoteTotal();

	public abstract void setCommentDate(Date commentDate);

	public abstract Date getCommentDate();

}