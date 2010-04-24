package com.team3.socialnews.server.dispatch;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.ActionException;

import com.google.inject.Injector;
import com.team3.socialnews.server.LoginManager;
import com.team3.socialnews.server.RequiresLogin;
import com.team3.socialnews.server.guice.GuiceFactory;
import com.team3.socialnews.server.persistence.LinkDampVoteRepository;
import com.team3.socialnews.server.persistence.LinkRepository;
import com.team3.socialnews.server.persistence.LinkVoteRepository;
import com.team3.socialnews.shared.dispatch.AlreadyVotedException;
import com.team3.socialnews.shared.dispatch.UnvoteOnLink;
import com.team3.socialnews.shared.dispatch.UnvoteOnLinkResult;
import com.team3.socialnews.shared.model.Link;
import com.team3.socialnews.shared.model.LinkDampVote;
import com.team3.socialnews.shared.model.LinkVote;
import com.team3.socialnews.shared.model.LocalUser;

public class UnvoteOnLinkHandler implements ActionHandler<UnvoteOnLink, UnvoteOnLinkResult> {

	@Override
	@RequiresLogin
	public UnvoteOnLinkResult execute(UnvoteOnLink action, ExecutionContext context)
			throws ActionException {
		
		Injector injector = GuiceFactory.getInjector();
		LinkRepository links =  injector.getInstance(LinkRepository.class);
		LinkVoteRepository linkVotes =  injector.getInstance(LinkVoteRepository.class);
		LinkDampVoteRepository linkDampVotes = injector.getInstance(LinkDampVoteRepository.class);
		LoginManager loginManager = injector.getInstance(LoginManager.class);
		LocalUser user = loginManager.getLocalUser();
		
		// Get the latest info about the link
		Link link = links.get(action.getLinkId());
		
		LinkVote previousVote = linkVotes.getActiveVoteByLinkAndUser(link.getId(), user.getGaeId());
		if(previousVote != null) { // User has a already voted
			// 1) Nullify the effect of the previous vote
			links.unvote(link.getId(), previousVote.getVoteEnergyContribution());
			// 2) Update the old vote so it doesn't affect the vote mask anymore, but don't delete it.
			linkVotes.unvote(previousVote);
			
			// 3) Find the related damp-vote
			LinkDampVote dampVote = linkDampVotes.getBySourceLinkVoteId(previousVote.getId());
			if(dampVote != null) {
				//4) Nullify the effect of the old damp vote 
				links.undampen(dampVote.getDampedLinkId(), dampVote.getDampVoteEnergy());
				//5) Mark the damp vote as unvoted
				linkDampVotes.undampen(dampVote);
			}
			
			return new UnvoteOnLinkResult(link.getEnergy() - previousVote.getVoteEnergyContribution());
		}
		throw new AlreadyVotedException("Can't unvote, never voted.");
	}

	@Override
	public Class<UnvoteOnLink> getActionType() {
		return UnvoteOnLink.class;
	}

	@Override
	public void rollback(UnvoteOnLink arg0, UnvoteOnLinkResult arg1,
			ExecutionContext arg2) throws ActionException {	
	}

}
