package ua.edu.sumdu.ta.yaroslavkuts.pr5.db;

import org.apache.log4j.Logger;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionFactory {
	
	private final static Logger LOG = Logger.getLogger(ConnectionFactory.class);
	private String driver;
	private String dsn;
	private String usr;
	private String pwd;

	public ConnectionFactory(String driver, String dsn, String usr, String pwd) {
		this.driver = driver;
		this.dsn = dsn;
		this.usr = usr;
		this.pwd = pwd;
	}

	public Connection create() {
		Connection connection = null;
		try {
			LOG.info("Try to create new connection");
			Class.forName(driver);  
			LOG.info("Driver was found");
			connection = DriverManager.getConnection(dsn, usr, pwd);
			LOG.info("Connection created");
		} catch (ClassNotFoundException e) {
			LOG.error(e.getMessage());
		} catch (SQLException e) {
			LOG.error(e.getMessage());
		}
		return connection;
	}
}