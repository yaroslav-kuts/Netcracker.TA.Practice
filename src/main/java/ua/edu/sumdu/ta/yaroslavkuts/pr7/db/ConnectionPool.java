package ua.edu.sumdu.ta.yaroslavkuts.pr7.db;

import org.apache.log4j.Logger;
import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;

class ConnectionPool {
	
	private static final int SIZE = 5;
	private final static Logger LOG = Logger.getLogger(ConnectionPool.class);
	private List<Connection> pool;
	private ConnectionFactory cf;
	
	public ConnectionPool(DataBases db) {
		pool = new ArrayList<Connection>();
		
		if (db.equals(DataBases.ORACLE)) {
			cf = new ConnectionFactory(DataBases.ORACLE);
		} else if (db.equals(DataBases.MYSQL)) {
			cf = new ConnectionFactory(DataBases.MYSQL);
		} else {
			LOG.error("Such db not exist");
			throw new IllegalArgumentException();
		}
		
		for (int i = 0; i < SIZE; i++) {
			pool.add(cf.create());
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
			pool.add(cf.create());
			LOG.info("New connection added in pool");
			return getConnection();
		}
	}
	
	public synchronized void returnConnection(Connection connection) {
		pool.add(connection);
	}
}