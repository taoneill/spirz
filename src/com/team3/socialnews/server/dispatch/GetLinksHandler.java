package com.team3.socialnews.server.dispatch;

import java.util.List;

import net.apptao.highway.server.dispatch.HwyHandler;
import net.apptao.highway.server.dispatch.Unsecured;
import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.ActionException;

import com.team3.socialnews.server.LoginManager;
import com.team3.socialnews.server.guice.GuiceFactory;
import com.team3.socialnews.server.persistence.LinkRepository;
import com.team3.socialnews.server.persistence.LinkVoteRepository;
import com.team3.socialnews.shared.dispatch.GetLinks;
import com.team3.socialnews.shared.dispatch.GetLinksResult;
import com.team3.socialnews.shared.model.Link;
import com.team3.socialnews.shared.model.LocalUser;

@Unsecured
public class GetLinksHandler implements HwyHandler<GetLinks, GetLinksResult> {

	@Override
	public GetLinksResult execute(GetLinks action, ExecutionContext context)
			throws ActionException {
		LinkRepository linkRepository =  GuiceFactory.getInjector().getInstance(LinkRepository.class);
		List<Link> links = linkRepository.get(action.getOrder(), action.getStart(), action.getFinish());
		
		LoginManager manager =  GuiceFactory.getInjector().getInstance(LoginManager.class);
		
		if (manager.isGaeLoggedIn()) {
			LocalUser user = manager.getLocalUser();
			if(user != null) {
				String userId = user.getGaeId();
				LinkVoteRepository linkVotes = GuiceFactory.getInjector().getInstance(LinkVoteRepository.class);
				List<Boolean> voteMask = linkVotes.getVoteMaskForLinks(links, userId);
				return new GetLinksResult(links, voteMask);
			}
		}
		return new GetLinksResult(links);
	}

	@Override
	public Class<GetLinks> getActionType() {
		return GetLinks.class;
	}

	@Override
	public void rollback(GetLinks action, GetLinksResult result,
			ExecutionContext context) throws ActionException {		
	}

}
