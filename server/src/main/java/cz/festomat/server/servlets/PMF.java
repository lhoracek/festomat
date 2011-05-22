/**
 * Copyright 2011 (c) ninjanuts.cz project pluto
 */
package cz.festomat.server.servlets;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

/**
 * @author Luboš Horáček
 */
public class PMF {
	private static final PersistenceManagerFactory	pmfInstance	= JDOHelper
																		.getPersistenceManagerFactory("transactions-optional");

	private PMF() {
	}

	public static PersistenceManager getManager() {
		return PMF.pmfInstance.getPersistenceManager();
	}
}
