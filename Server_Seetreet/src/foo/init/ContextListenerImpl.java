package foo.init;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.seetreet.dao.MongoDB;

public class ContextListenerImpl implements ServletContextListener{
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println(">> APP end");
	}
	
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println(">> APP start");
		
		MongoDB.getDB();
	}
}
