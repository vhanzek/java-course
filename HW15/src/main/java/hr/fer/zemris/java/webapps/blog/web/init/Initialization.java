package hr.fer.zemris.java.webapps.blog.web.init;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import hr.fer.zemris.java.webapps.blog.dao.jpa.JPAEMFProvider;

/**
 * The class whose job is to create new {@link EntityManagerFactory} when the
 * web application starts and close it when it's shutting down.
 * 
 * @author Vjeran
 */
@WebListener
public class Initialization implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		EntityManagerFactory emf = 
			Persistence.createEntityManagerFactory("baza.podataka.za.blog");  
		sce.getServletContext().setAttribute("application.emf", emf);
		JPAEMFProvider.setEmf(emf);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		JPAEMFProvider.setEmf(null);
		EntityManagerFactory emf = 
			(EntityManagerFactory) sce.getServletContext().getAttribute("application.emf");
		
		if(emf != null) emf.close();
	}

}
