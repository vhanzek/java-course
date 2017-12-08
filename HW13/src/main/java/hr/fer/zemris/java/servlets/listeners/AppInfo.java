package hr.fer.zemris.java.servlets.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * The {@link ServletContextListener} which remembers time of the application start
 * which is used for tracking for how long the web application has been running.
 * 
 * @author Vjeran
 */
@WebListener
public class AppInfo implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		sce.getServletContext().setAttribute("start", System.currentTimeMillis());
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}
}
