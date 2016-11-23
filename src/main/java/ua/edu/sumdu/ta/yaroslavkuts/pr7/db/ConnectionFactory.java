package ua.edu.sumdu.ta.yaroslavkuts.pr7.db;

import org.apache.log4j.Logger;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionFactory {
	
	private final static Logger LOG = Logger.getLogger(ConnectionFactory.class);
	private DBInfo info;

	public ConnectionFactory(DataBases db) {
		this.info = db.getDBInfo();
	}

	public Connection create() {
		Connection connection = null;
		try {
			LOG.info("Try to create new connection");
			Class.forName(info.getDriver());  
			LOG.info("Driver was found");
			connection = DriverManager.getConnection(info.getUrl(), info.getUser(), info.getPassword());
			LOG.info("Connection created");
		} catch (ClassNotFoundException e) {
			LOG.error(e.getMessage());
		} catch (SQLException e) {
			LOG.error(e.getMessage());
		}
		return connection;
	}
}