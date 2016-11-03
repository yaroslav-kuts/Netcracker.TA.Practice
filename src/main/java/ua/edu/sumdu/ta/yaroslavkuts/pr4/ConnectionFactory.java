package ua.edu.sumdu.ta.yaroslavkuts.pr4;

import org.apache.log4j.Logger;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

class ConnectionFactory {
	
	private final static Logger LOG = Logger.getLogger(ConnectionFactory.class);
	private String dsn;
	private String usr;
	private String pwd;

	public ConnectionFactory(String dsn, String usr, String pwd) {
		this.dsn = dsn;
		this.usr = usr;
		this.pwd = pwd;
	}

	public Connection create() {
		Connection connection = null;
		try {
			LOG.info("Try to create new connection");
			connection = DriverManager.getConnection(dsn, usr, pwd);
			LOG.info("Connection created");
		} catch (SQLException e) {
			LOG.error(e.getMessage());
		}
		return connection;
	}
}