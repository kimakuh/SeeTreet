package com.seetreet.contextlistener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.seetreet.dao.MongoDB;

public class ContextListenerImpl implements ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		MongoDB.getDB();
	}

}
