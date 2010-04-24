package com.team3.socialnews.server.persistence;

import java.util.Collection;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.team3.socialnews.shared.model.LocalUser;

public class LocalUserRepositoryImpl extends AbstractRepository<LocalUser> implements LocalUserRepository {
	@SuppressWarnings("unchecked")
	public LocalUser getByGAE(String gaeId) {
		PersistenceManager pm = getPersistenceManager();
		try {
			Query q = pm.newQuery(LocalUser.class, "gaeId == \""+gaeId+"\"");
			Collection<LocalUser> collection = (Collection<LocalUser>)q.execute();
			return collection.isEmpty() ? null : collection.iterator().next();
		}
		finally {
			pm.close();
		}
	}
	
	public LocalUser create(String gaeId, String nickname) {
		PersistenceManager pm = getPersistenceManager();
		try {
			pm.currentTransaction().begin();
			LocalUser lu = new LocalUser(gaeId, nickname);
			pm.makePersistent(lu);
			pm.currentTransaction().commit();
			return lu;
		}
		finally {
			pm.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public Boolean isNicknameAvailable(String nickname) {
		PersistenceManager pm = getPersistenceManager();
		try {
			Query q = pm.newQuery(LocalUser.class, "nickname == \""+nickname+"\"");
			return !((Collection<LocalUser>)q.execute()).iterator().hasNext();
		}
		finally {
			pm.close();
		}
	}
}
