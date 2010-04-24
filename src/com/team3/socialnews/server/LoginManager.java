package com.team3.socialnews.server;

import com.google.appengine.api.users.User;
import com.team3.socialnews.shared.model.LocalUser;
import com.team3.socialnews.shared.model.LoginInfo;

/**
 * Server side object responsible for managing login and logout
 * of users in the system.
 */
public interface LoginManager {
	/**
	 * @return Object detailing a user in the system or null if
	 * the user is not logged in.
	 */
	User getGaeUser();
	
	/**
	 * @return true if the user is logged in.
	 */
	boolean isGaeLoggedIn();
	
	/**
	 * Returns an object with details about the login status
	 * of the user.
	 * @param requestURI The URI where the request is coming from.
	 * @return A LoginInfo object detailing the results of the login
	 * operation. If the user is not already logged in, then the LoginInfo
	 * contains a URI indicating where the user can be redirect in order
	 * to login.
	 */
	LoginInfo getLoginInfo(String requestURI);

	LocalUser getLocalUser();
}
