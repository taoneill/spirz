package com.team3.socialnews.server.dispatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.ActionException;

import com.team3.socialnews.client.model.CommentClient;
import com.team3.socialnews.server.LoginManager;
import com.team3.socialnews.server.guice.GuiceFactory;
import com.team3.socialnews.server.persistence.CommentRepository;
import com.team3.socialnews.server.persistence.CommentVoteRepository;
import com.team3.socialnews.server.persistence.LinkRepository;
import com.team3.socialnews.server.persistence.LinkVoteRepository;
import com.team3.socialnews.shared.dispatch.GetLinkComments;
import com.team3.socialnews.shared.dispatch.GetLinkCommentsResult;
import com.team3.socialnews.shared.model.Comment;
import com.team3.socialnews.shared.model.Link;
import com.team3.socialnews.shared.model.LocalUser;

public class GetLinkCommentsHandler implements ActionHandler<GetLinkComments, GetLinkCommentsResult> {

	@Override
	public GetLinkCommentsResult execute(GetLinkComments action,
			ExecutionContext context) throws ActionException {
		CommentRepository repo = GuiceFactory.getInjector().getInstance(CommentRepository.class);
		LinkRepository linkRepo = GuiceFactory.getInjector().getInstance(LinkRepository.class);
		
		Link link = linkRepo.get(action.getLinkId());
		List<Comment> comments = repo.getLinkComments(action.getLinkId());
		
		LoginManager manager =  GuiceFactory.getInjector().getInstance(LoginManager.class);
		if (manager.isGaeLoggedIn()) {
			LocalUser user = manager.getLocalUser();
			if(user != null) {
				String userId = user.getGaeId();
				LinkVoteRepository linkVotes = GuiceFactory.getInjector().getInstance(LinkVoteRepository.class);
				List<Link> justOneLinkCollection = new ArrayList<Link>();
				justOneLinkCollection.add(link);
				List<Boolean> linkVoteMask = linkVotes.getVoteMaskForLinks(justOneLinkCollection, userId);
				CommentVoteRepository commentVoteRepo = GuiceFactory.getInjector().getInstance(CommentVoteRepository.class);
				HashMap<Long, Boolean> commentsVoteMask = commentVoteRepo.getVoteMaskForComments(comments, userId);
				return new GetLinkCommentsResult(link, comments.toArray(new CommentClient[0]), linkVoteMask.get(0), commentsVoteMask);
			}
		}
		
		return new GetLinkCommentsResult(link, comments.toArray(new CommentClient[0]));
	}

	@Override
	public Class<GetLinkComments> getActionType() {
		return GetLinkComments.class;
	}

	@Override
	public void rollback(GetLinkComments arg0, GetLinkCommentsResult arg1,
			ExecutionContext arg2) throws ActionException {}

}
