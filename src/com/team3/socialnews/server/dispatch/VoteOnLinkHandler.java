package com.team3.socialnews.server.dispatch;

import net.apptao.highway.server.dispatch.HwyHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.ActionException;

import com.google.inject.Injector;
import com.team3.socialnews.server.LoginManager;
import com.team3.socialnews.server.RequiresLogin;
import com.team3.socialnews.server.guice.GuiceFactory;
import com.team3.socialnews.server.persistence.LinkDampVoteRepository;
import com.team3.socialnews.server.persistence.LinkRepository;
import com.team3.socialnews.server.persistence.LinkVoteRepository;
import com.team3.socialnews.server.vote.VoteEnergyCalculator;
import com.team3.socialnews.shared.dispatch.AlreadyVotedException;
import com.team3.socialnews.shared.dispatch.VoteOnLink;
import com.team3.socialnews.shared.dispatch.VoteOnLinkResult;
import com.team3.socialnews.shared.model.Link;
import com.team3.socialnews.shared.model.LinkDampVote;
import com.team3.socialnews.shared.model.LinkVote;
import com.team3.socialnews.shared.model.LocalUser;

public class VoteOnLinkHandler implements HwyHandler<VoteOnLink, VoteOnLinkResult> {

	@Override
	@RequiresLogin
	public VoteOnLinkResult execute(VoteOnLink action, ExecutionContext context)
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
		if(previousVote == null || user.getIsAdmin()) { // User has never voted (admins can vote many times)
			// 1) Calculate the energy effect of the vote 
			LinkVote vote = new LinkVote(user.getGaeId(), link);
			VoteEnergyCalculator calc = GuiceFactory.getInjector().getInstance(VoteEnergyCalculator.class);
			Integer energyContribution = calc.calculateEnergy(vote);
			vote.setVoteEnergyContribution(energyContribution);	
			// 2) Update the link with energy and vote total
			links.voteOnLink(link.getId(), energyContribution);
			// 3) Store vote with its context as means to keep history
			linkVotes.storeVote(vote);
			
			// 4) The cool part: auto-dampen a randomly chosen link that
			// has more energy than AND that is older than the link that 
			// was just voted on.
			int dampVoteEnergy = calc.getDampEnergy(vote);
			Link dampVoteCandidate = links.getDampVoteCandidate(vote);
			if(dampVoteCandidate != null){
				links.dampenLink(dampVoteCandidate.getId(), dampVoteEnergy);
				LinkDampVote autoDampVote = new LinkDampVote(vote, dampVoteCandidate, dampVoteEnergy);
				linkDampVotes.storeDampVote(autoDampVote);
			}
			// TODO: should log when no damping is done
			
			// 5) return the new energy of the link
			return new VoteOnLinkResult(link.getEnergy() + energyContribution);	
		}
		
		throw new AlreadyVotedException("Already voted");
	}

	@Override
	public Class<VoteOnLink> getActionType() {
		return VoteOnLink.class;
	}

	@Override
	public void rollback(VoteOnLink arg0, VoteOnLinkResult arg1,
			ExecutionContext arg2) throws ActionException {	
	}

}
