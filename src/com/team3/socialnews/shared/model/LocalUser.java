package com.team3.socialnews.shared.model;

import javax.persistence.Id;

import com.googlecode.objectify.annotation.Cached;

/**
 * Represents a user that is registered with the website.
 */
@Cached
public class LocalUser {
	
	@Id
	String gaeId;
	
	String nickname;

	private Boolean isAdmin = false;	// by default, use datastore ui to create admins by flipping this
	
	public LocalUser() {}
	
	/**
	 * @param gaeId The ID of the Google AppEngine User.
	 * @param nickname The nickname of the user. Must be unique.
	 */
	public LocalUser(String gaeId, String nickname) {
		this.gaeId = gaeId;
		this.nickname = nickname;
	}

	public String getNickname() {
		return nickname;
	}
	
	public String getGaeId() {
		return gaeId;
	}

	public boolean getIsAdmin() {
		// TODO Auto-generated method stub
		return isAdmin;
	}
}
