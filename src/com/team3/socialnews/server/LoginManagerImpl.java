package com.team3.socialnews.server;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.team3.socialnews.server.persistence.LocalUserRepository;
import com.team3.socialnews.shared.model.LocalUser;
import com.team3.socialnews.shared.model.LoginInfo;

@Singleton
public class LoginManagerImpl implements LoginManager {
	
	private LocalUserRepository localUserRepo;
	
	@Inject
	public LoginManagerImpl(LocalUserRepository localUserRepo) {
		this.localUserRepo = localUserRepo;
	}
	
	public User getGaeUser() {
		UserService userService = UserServiceFactory.getUserService();
		return userService.getCurrentUser();
	}
	
	
	public boolean isGaeLoggedIn() {
		return getGaeUser() != null;
	}

	@Override
	public LoginInfo getLoginInfo(String requestURI) {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		
		LoginInfo loginInfo = new LoginInfo();
		loginInfo.setLoginUrl(userService.createLoginURL(requestURI));
		loginInfo.setLogoutUrl(userService.createLogoutURL(requestURI));
		
		if (user != null) {
			// User is logged in with GAE
			loginInfo.setLoggedIn(true);
			LocalUser localUser = localUserRepo.getByGAE(user.getUserId());
			loginInfo.setIsRegistered(localUser != null);
			
			// User is registered
			if (localUser != null) {
				loginInfo.setNickname(localUser.getNickname());
				loginInfo.setIsAdmin(localUser.getIsAdmin());
			}
		} else {
			// User is not logged in with GAE
			loginInfo.setLoggedIn(false);
		}
		return loginInfo;
	}

	@Override
	public LocalUser getLocalUser() {
		return localUserRepo.getByGAE(getGaeUser().getUserId());
	}
}
