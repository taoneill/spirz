package com.team3.socialnews.client.model;

import java.io.Serializable;
import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.team3.socialnews.shared.model.Comment;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class CommentClient implements Serializable, Comment {

	private Long id;
	private String authorNickname;
	private String body;
	private Long linkId;
	private Date commentDate;
	private Integer voteTotal = 0;
	
	/**
	 * Root comments have a parentId = 0
	 */
	private Long parentId;
	
	/** For serialization only */
	CommentClient() {}
	
	/** Creates a new comment.
	 * The id for the comment will be null
	 * until the comment is serialized.
	 * @param authorNickname The nickname of the author.
	 * @param body The contents of the comment.
	 * */
	public CommentClient(Long id, String authorNickname, String body, Long linkId, Long parentId) {
		this.id = id;
		this.authorNickname = authorNickname;
		this.body = body;
		this.linkId = linkId;
		this.parentId = parentId;
		this.setCommentDate(new Date());
	}
	
	/* (non-Javadoc)
	 * @see com.team3.socialnews.client.model.Comment#getAuthorNickname()
	 */
	public String getAuthorNickname() {
		return authorNickname;
	}
	
	/* (non-Javadoc)
	 * @see com.team3.socialnews.client.model.Comment#getBody()
	 */
	public String getBody() {
		return body;
	}
	
	public Long getId() {
		return id;
	}
	
	/* (non-Javadoc)
	 * @see com.team3.socialnews.client.model.Comment#getLinkId()
	 */
	public Long getLinkId() {
		return linkId;
	}
	
	/* (non-Javadoc)
	 * @see com.team3.socialnews.client.model.Comment#getParentId()
	 */
	public Long getParentId() {
		return parentId;
	}

	/* (non-Javadoc)
	 * @see com.team3.socialnews.client.model.Comment#setVoteTotal(java.lang.Integer)
	 */
	public void setVoteTotal(Integer voteTotal) {
		this.voteTotal = voteTotal;
	}

	/* (non-Javadoc)
	 * @see com.team3.socialnews.client.model.Comment#getVoteTotal()
	 */
	public Integer getVoteTotal() {
		if(voteTotal == null){
			voteTotal = 0;
		}
		return voteTotal;
	}

	/* (non-Javadoc)
	 * @see com.team3.socialnews.client.model.Comment#setCommentDate(java.util.Date)
	 */
	public void setCommentDate(Date commentDate) {
		this.commentDate = commentDate;
	}

	/* (non-Javadoc)
	 * @see com.team3.socialnews.client.model.Comment#getCommentDate()
	 */
	public Date getCommentDate() {
		if(commentDate == null){
			commentDate = new Date();
		}
		return commentDate;
	}
}
