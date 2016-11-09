package ua.edu.sumdu.ta.yaroslavkuts.pr5.db;

import ua.edu.sumdu.ta.yaroslavkuts.pr5.*;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class OracleDB extends DBWriter{

	private static OracleDB instance;
	
	private OracleDB() {
		connections = new ConnectionPool(DataBases.ORACLE);
	}
	
	public static synchronized OracleDB getInstance() {
		if (instance == null) {
			instance = new OracleDB();
		}
		return instance;
	}
	
	@Override
	public Integer addTask(Task task) {
		return null;
	}
	
	@Override
	public Integer modifyTask(Task task) {
		return null;
	}
	
	@Override
	public Integer addTaskInList(Task task, Integer list_id) {
		return null;
	}
	
	@Override
	public int insertList(ArrayTaskList list) {
		return 0;
	}

	public void insertObject(int object_id, int parent_id, int object_type_id, String name) {
		Connection connection = null;
		PreparedStatement preStmt = null;
		
		try {
			connection = connections.getConnection();
			String query = "insert into objects values (?, ?, ?, ?)";
			preStmt = connection.prepareStatement(query);
			LOG.info("Statement prepared");
			preStmt.setInt(1, object_id);
			preStmt.setInt(2, parent_id);
			preStmt.setInt(3, object_type_id);
			preStmt.setString(4, name);
			preStmt.executeUpdate();
			LOG.info("Statement executed");
		} catch (SQLException e) {
			LOG.error(e.getMessage());
		} finally {
			try {
				if (preStmt != null) {
					preStmt.close();
				}
			} catch (SQLException e) {
				LOG.error(e.getMessage());
			}
			connections.returnConnection(connection);
		}
	}
	
	public void insertParam(int param_id, int object_id, int attr_id, String text_value, int number_value, String data_value) {
		Connection connection = null;
		PreparedStatement preStmt = null;
		
		try {
			connection = connections.getConnection();
			String query = "insert into params values (?, ?, ?, ?, ?, ?)";
			preStmt = connection.prepareStatement(query);
			LOG.info("Statement prepared");
			preStmt.setInt(1, param_id);
			preStmt.setInt(2, object_id);
			preStmt.setInt(3, attr_id);
			preStmt.setString(4, text_value);
			preStmt.setInt(5, number_value);
			preStmt.setString(6, data_value);
			preStmt.executeUpdate();
			LOG.info("Statement executed");
		} catch (SQLException e) {
			LOG.error(e.getMessage());
		} finally {
			try {
				if (preStmt != null) {
					preStmt.close();
				}
			} catch (SQLException e) {
				LOG.error(e.getMessage());
			}
			connections.returnConnection(connection);
		}
	}
}