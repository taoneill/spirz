package com.team3.socialnews.server.persistence;

import net.apptao.highway.server.Highway;

import com.google.inject.Inject;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.team3.socialnews.shared.model.LocalUser;

public class LocalUserRepositoryHwyImpl extends AbstractRepository<LocalUser> implements LocalUserRepository {
	
	private Highway hwy;

	@Inject
	public LocalUserRepositoryHwyImpl(Highway hwy) {
		this.hwy = hwy;
	}
	
	public LocalUser getByGAE(String gaeId) {
		return hwy.dao().query(LocalUser.class).filter("gaeId", gaeId).get();
	}
	
	public LocalUser create(String gaeId, String nickname) {
		Objectify ofy = hwy.dao(true);
		try {
			LocalUser lu = new LocalUser(gaeId, nickname);
			ofy.put(lu);
			ofy.getTxn().commit();
			return lu;
		}
		finally {
			if(ofy.getTxn().isActive())
				ofy.getTxn().rollback();
		}
	}
	
	public Boolean isNicknameAvailable(String nickname) {
		Key<LocalUser> keyOfUserWithNickname = hwy.dao().query(LocalUser.class).filter("nickname", nickname).getKey();
		return keyOfUserWithNickname == null;
	}
}
