package com.team3.socialnews.shared.model;

import java.io.Serializable;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.Unique;
@PersistenceCapable(identityType = IdentityType.APPLICATION)
/**
 * Represents a user that is registered with the website.
 */
public class LocalUser implements Serializable {
	
	/** for serialization */
	LocalUser() {} 
	
	@PrimaryKey
	@Persistent
	String gaeId;
	
	@Unique
	@Persistent
	String nickname;

	@Persistent
	private Boolean isAdmin = false;	// by default, use datastore ui to create admins by flipping this
	
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
