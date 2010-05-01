package com.team3.socialnews.server.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;

import com.google.appengine.api.datastore.Text;
import com.googlecode.objectify.annotation.Cached;
import com.team3.socialnews.shared.model.Comment;

@Cached
public class CommentJDO implements Serializable, Comment {
	
	private static final long serialVersionUID = 4735573962874408301L;

	@Id
	private Long id;
	
	private String authorNickname;
	
	private Text body;

	private Long linkId;
	
	private Date commentDate;

	private Integer voteTotal = 0;
	
	/**
	 * Root comments have a parentId = 0
	 */
	private Long parentId;
	
	/** For serialization only */
	CommentJDO() {}
	
	/** Creates a new comment.
	 * The id for the comment will be null
	 * until the comment is serialized.
	 * @param authorNickname The nickname of the author.
	 * @param body The contents of the comment.
	 * */
	public CommentJDO(String authorNickname, String body, Long linkId, Long parentId) {
		this.authorNickname = authorNickname;
		this.body = new Text(body);
		this.linkId = linkId;
		this.parentId = parentId;
		this.setCommentDate(new Date());
	}
	
	public String getAuthorNickname() {
		return authorNickname;
	}
	
	public String getBody() {
		return body.getValue();
	}
	
	public Long getId() {
		return id;
	}
	
	public Long getLinkId() {
		return linkId;
	}
	
	public Long getParentId() {
		return parentId;
	}

	public void setVoteTotal(Integer voteTotal) {
		this.voteTotal = voteTotal;
	}

	public Integer getVoteTotal() {
		if(voteTotal == null){
			voteTotal = 0;
		}
		return voteTotal;
	}

	public void setCommentDate(Date commentDate) {
		this.commentDate = commentDate;
	}

	public Date getCommentDate() {
		if(commentDate == null){
			commentDate = new Date();
		}
		return commentDate;
	}
}
