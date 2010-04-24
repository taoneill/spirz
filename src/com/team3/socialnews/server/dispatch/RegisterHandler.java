package com.team3.socialnews.server.dispatch;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.ActionException;

import com.google.appengine.api.users.User;
import com.team3.socialnews.server.LoginManager;
import com.team3.socialnews.server.RequiresLogin;
import com.team3.socialnews.server.guice.GuiceFactory;
import com.team3.socialnews.server.persistence.LocalUserRepository;
import com.team3.socialnews.shared.dispatch.NicknameNotAvailable;
import com.team3.socialnews.shared.dispatch.Register;
import com.team3.socialnews.shared.dispatch.RegisterResult;

public class RegisterHandler implements ActionHandler<Register, RegisterResult> {

	@Override
	@RequiresLogin(withRegistration=false)
	public RegisterResult execute(Register action, ExecutionContext context)
			throws ActionException {
		LoginManager loginManager = GuiceFactory.getInjector().getInstance(LoginManager.class);
		String nickname = action.getNickname();
		LocalUserRepository userRepo = GuiceFactory.getInjector().getInstance(LocalUserRepository.class);
		if (!userRepo.isNicknameAvailable(nickname))
			throw new NicknameNotAvailable();
		
		User user = loginManager.getGaeUser();
		userRepo.create(user.getUserId(), nickname);
		return new RegisterResult();
	}

	@Override
	public Class<Register> getActionType() {
		return Register.class;
	}

	@Override
	public void rollback(Register arg0, RegisterResult arg1,
			ExecutionContext arg2) throws ActionException { 
		// call roll this back 
	}
}
