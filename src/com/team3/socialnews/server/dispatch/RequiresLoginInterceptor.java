package com.team3.socialnews.server.dispatch;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import com.team3.socialnews.server.LoginManager;
import com.team3.socialnews.server.RequiresLogin;
import com.team3.socialnews.server.guice.GuiceFactory;
import com.team3.socialnews.shared.dispatch.NotLoggedInException;

/**
 * Checks if a user is logged with AppEngine before proceeding with a method invocation.
 */
public class RequiresLoginInterceptor implements MethodInterceptor {

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		RequiresLogin requiresLogin = 
			invocation.getMethod().getAnnotation(RequiresLogin.class);
		LoginManager loginManager = GuiceFactory.getInjector().getInstance(LoginManager.class);
		if (!loginManager.isGaeLoggedIn())
			throw new NotLoggedInException();
		if (requiresLogin.withRegistration() && loginManager.getLocalUser() == null)
			throw new NotLoggedInException();
		return invocation.proceed();
	}
}