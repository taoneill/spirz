package com.team3.socialnews.server.persistence;

import com.team3.socialnews.shared.model.LocalUser;

public interface LocalUserRepository {
	/**
	 * Fetches a local user.
	 * @param gaeId The id of the google AppEngine user.
	 */
	public LocalUser getByGAE(String gaeId);
	
	/**
	 * Creates new local user.
	 * @param gaeId The id of the google AppEngine user.
	 * @param nickname The nickname of the user.
	 * @return The created LocalUser instance.
	 */
	public LocalUser create(String gaeId, String nickname);
	
	/**
	 * Checks if a nickname is available.
	 * @param nickname
	 * @return true if unique false if not.
	 */
	public Boolean isNicknameAvailable(String nickname);
}
