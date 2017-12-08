package hr.fer.zemris.webapps.webapp_baza.listeners;

import java.beans.PropertyVetoException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

/**
 * The {@link ServletContextListener} which initializes the {@link ComboPooledDataSource} object.
 * And, after the connection between the client and the server ends, the source is destroyed.
 * 
 * @author Vjeran
 */
@WebListener
public class DatabaseInitialization implements ServletContextListener {
	
	/** The path to database settings file. */
	private static final String DB_SETTINGS = "/WEB-INF/dbsettings.properties";
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		String confPath = sce.getServletContext().getRealPath(DB_SETTINGS);
		if(Files.notExists(Paths.get(confPath))){
			throw new RuntimeException(
				"Database configuration file does not exist."
			);
		}
		Properties conf = new Properties();
		try {
			conf.load(
				new InputStreamReader(new FileInputStream(confPath), StandardCharsets.UTF_8)
			);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String connectionURL = 
			"jdbc:derby://"
			+ conf.getProperty("host") + ":"
			+ conf.getProperty("port") + "/" 
			+ conf.getProperty("name") + ";user=" 
			+ conf.getProperty("user") + ";password="
			+ conf.getProperty("password");

		ComboPooledDataSource cpds = new ComboPooledDataSource();
		try {
			cpds.setDriverClass("org.apache.derby.jdbc.ClientDriver");
		} catch (PropertyVetoException e) {
			throw new RuntimeException(
				"Error initializing data pool.", e
			);
		}
		cpds.setJdbcUrl(connectionURL);

		sce.getServletContext().setAttribute("dbpool", cpds);
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ComboPooledDataSource cpds = 
			(ComboPooledDataSource) sce.getServletContext().getAttribute("dbpool");
		if(cpds != null) {
			try {
				DataSources.destroy(cpds);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
