package com.team3.socialnews.server.persistence;

import java.util.Collection;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.team3.socialnews.shared.model.LinkDampVote;

public class LinkDampVoteRepositoryImpl extends AbstractRepository<LinkDampVote> implements
		LinkDampVoteRepository {

	@Override
	public void storeDampVote(LinkDampVote vote) {
		PersistenceManager pm = getPersistenceManager();
		try {
			pm.makePersistent(vote);
		}
		finally {
			pm.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public LinkDampVote getBySourceLinkVoteId(Long sourceLinkVoteId) {
		PersistenceManager pm = getPersistenceManager();
		try {
			Query q = pm.newQuery(LinkDampVote.class, "sourceLinkVoteId == "+sourceLinkVoteId);
			Collection<LinkDampVote> collection = ((Collection<LinkDampVote>)q.execute());
			if(collection.size() > 0) return collection.iterator().next();
			else return null;
		}
		finally {
			pm.close();
		}
	}

	@Override
	public void undampen(LinkDampVote dampVote) {
		PersistenceManager pm = getPersistenceManager();
		try {
			pm.currentTransaction().begin();
			
			// make the dampvote unvoted
			LinkDampVote oldDampVote = get(dampVote.getId());
			oldDampVote.setWasUnvoted(true);
			pm.makePersistent(oldDampVote);
			
			pm.currentTransaction().commit();
		} 
		finally {
			pm.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public LinkDampVote get(Long linkDampVoteId) {
		PersistenceManager pm = getPersistenceManager();
		try {
			Query q = pm.newQuery(LinkDampVote.class, "id == "+linkDampVoteId);
			return ((Collection<LinkDampVote>)q.execute()).iterator().next();
		}
		finally {
			pm.close();
		}
	}
}
