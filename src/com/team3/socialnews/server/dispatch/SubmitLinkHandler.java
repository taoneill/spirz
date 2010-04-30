package com.team3.socialnews.server.dispatch;

import net.apptao.highway.server.dispatch.HwyHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.ActionException;

import com.team3.socialnews.server.LoginManager;
import com.team3.socialnews.server.RequiresLogin;
import com.team3.socialnews.server.guice.GuiceFactory;
import com.team3.socialnews.server.persistence.LinkRepository;
import com.team3.socialnews.shared.dispatch.SubmitLink;
import com.team3.socialnews.shared.dispatch.SubmitLinkResult;
import com.team3.socialnews.shared.model.Link;
import com.team3.socialnews.shared.model.LocalUser;

public class SubmitLinkHandler implements HwyHandler<SubmitLink, SubmitLinkResult> {

	@Override
	@RequiresLogin
	public SubmitLinkResult execute(SubmitLink action, ExecutionContext context) throws ActionException {
		LoginManager loginManager = GuiceFactory.getInjector().getInstance(LoginManager.class);
		LinkRepository repository =  GuiceFactory.getInjector().getInstance(LinkRepository.class);
		
		LocalUser user = loginManager.getLocalUser();
		
		Link l = repository.submit(
				action.getTitle(), 
				action.getUrl(), 
				user.getGaeId(),
				user.getNickname());
		return new SubmitLinkResult(l);
	}

	@Override
	public Class<SubmitLink> getActionType() {
		return SubmitLink.class;
	}

	@Override
	@RequiresLogin
	public void rollback(SubmitLink action, SubmitLinkResult result, ExecutionContext context) throws ActionException {		
	}
}
