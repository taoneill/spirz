package com.team3.socialnews.shared.model;

import java.io.Serializable;

/**
 * Contains the login information of a user.
 */
public class LoginInfo implements Serializable {

	private boolean loggedIn = false;
	private String loginUrl;
	private String logoutUrl;
	private String nickname;
	private boolean isRegistered;
	private boolean isAdmin;

	public void setIsRegistered(boolean isRegistered) {
		this.isRegistered = isRegistered;
	}

	/**
	 * True is the user has an account with the website.
	 */
	public boolean isRegistered() {
		return isRegistered;
	}

	/**
	 * True if user is logged in through AppEngine.
	 * Does not mean that the user is registered.
	 */
	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	/**
	 * Returns the URL to which to send the user
	 * so that he can log in.
	 * @return
	 */
	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	/**
	 * Returns the URL to which to send the user
	 * so that he can log out.
	 * @return
	 */
	public String getLogoutUrl() {
		return logoutUrl;
	}

	public void setLogoutUrl(String logoutUrl) {
		this.logoutUrl = logoutUrl;
	}

	/**
	 * Returns the nickname of the currently
	 * logged in user. This is null if the user
	 * is not logged in.
	 * @return
	 */
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	/**
	 * Returns true if the user is an administrator.
	 * Returns false if the user is not an administrator,
	 * is not logged in or has no account.
	 */
	public boolean getIsAdmin() {
		return this.isAdmin;
	}

	public void setIsAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
}