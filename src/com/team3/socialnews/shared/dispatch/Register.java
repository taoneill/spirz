package com.team3.socialnews.shared.dispatch;

import net.customware.gwt.dispatch.shared.Action;

/**
 * Action for registering the currently logged in
 * user.
 * @author tristan
 *
 */
public class Register implements Action<RegisterResult> {
	String nickname;
	
	/** for serialization */
	Register() {}
	
	/**
	 * @param nickname The nickname tha the user has chosen.
	 */
	public Register(String nickname) {
		this.nickname = nickname;
	}
	public String getNickname() {
		return nickname;
	}
}
