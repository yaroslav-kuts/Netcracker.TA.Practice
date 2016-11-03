package ua.edu.sumdu.ta.yaroslavkuts.pr4;

import org.apache.log4j.Logger;
import java.util.List;
import java.util.ArrayList;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

class ConnectionPool {
	
	private static final int SIZE = 5;
	private final static Logger LOG = Logger.getLogger(DBConnector.class);
	private List<Connection> pool;
	
	public ConnectionPool() {
		pool = new ArrayList<Connection>();
		
		for (int i = 0; i < SIZE; i++) {
			pool.add(createConnection());
			LOG.info("Connection added in pool");
		}
	}
	
	public synchronized Connection getConnection() {
		if (pool.size() != 0) {
			Connection connection = pool.get(1);
			pool.remove(1);
			return connection;
		} else {
			LOG.info("Pool is empty");
			pool.add(createConnection());
			LOG.info("New connection added in pool");
			return getConnection();
		}
	}
	
	public synchronized void returnConnection(Connection connection) {
		pool.add(connection);
	}
	
	private Connection createConnection() {
		Connection connection = null;
		try {
			LOG.info("Try to create new connection");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/TaskManager","root", "root");
			LOG.info("Connection created");
		} catch (SQLException e) {
			LOG.error(e.getMessage());
		}
		return connection;
	}
}