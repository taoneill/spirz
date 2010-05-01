package com.team3.socialnews.server.dispatch;

import net.apptao.highway.server.dispatch.HwyHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.ActionException;

import com.team3.socialnews.server.LoginManager;
import com.team3.socialnews.server.RequiresLogin;
import com.team3.socialnews.server.guice.GuiceFactory;
import com.team3.socialnews.server.persistence.CommentRepository;
import com.team3.socialnews.server.persistence.LinkRepository;
import com.team3.socialnews.shared.dispatch.CreateComment;
import com.team3.socialnews.shared.dispatch.CreateCommentResult;
import com.team3.socialnews.shared.model.Comment;


public class CreateCommentHandler implements HwyHandler<CreateComment, CreateCommentResult> {

	@Override
	@RequiresLogin
	public CreateCommentResult execute(CreateComment action, ExecutionContext context)
			throws ActionException {
		CommentRepository commentRepo = GuiceFactory.getInjector().getInstance(CommentRepository.class);
		LinkRepository linkRepo = GuiceFactory.getInjector().getInstance(LinkRepository.class);
		LoginManager loginManager = GuiceFactory.getInjector().getInstance(LoginManager.class);
		String nickname = loginManager.getLocalUser().getNickname();
		Long parentId = action.getParentId();
		
		if (linkRepo.get(action.getLinkId()) == null)
			throw new ActionException("Posted comment for inexistant link.");
		if (parentId != null && commentRepo.get(parentId) == null)
			throw new ActionException("Posted reply for inexistant comment.");
		
		Comment comment = commentRepo.create(action.getLinkId(), action.getBody(), nickname, parentId);
		linkRepo.newComment(action.getLinkId());
		return new CreateCommentResult(comment);
	}

	@Override
	public Class<CreateComment> getActionType() {
		return CreateComment.class;
	}

	@Override
	public void rollback(CreateComment arg0, CreateCommentResult arg1,
			ExecutionContext arg2) throws ActionException {
		// TODO Auto-generated method stub	
	}

}
