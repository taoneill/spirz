package com.team3.socialnews.server.persistence;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

public abstract class AbstractRepository<T> {


	private static final PersistenceManagerFactory PMF = JDOHelper
			.getPersistenceManagerFactory("transactions-optional");
	
	public PersistenceManager getPersistenceManager(){
		return PMF.getPersistenceManager();
	}

}
