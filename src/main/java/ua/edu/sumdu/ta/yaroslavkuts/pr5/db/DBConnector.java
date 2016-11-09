package ua.edu.sumdu.ta.yaroslavkuts.pr5.db;

import ua.edu.sumdu.ta.yaroslavkuts.pr5.*;
import org.apache.log4j.Logger;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;

public class DBConnector {
	
	private final static Logger log = Logger.getLogger(DBConnector.class);
	private static ConnectionPool connections = new ConnectionPool(DataBases.MYSQL);
		
	/*public static Integer insertTask(Task task) {
		StringBuilder query = new StringBuilder("insert into tasks (title, time, start, end, rep, isActive, prev, list_id)" 
				+ " values ('" + task.getTitle() + "', " + task.getTime() + ", " + task.getStartTime() + 
				", " + task.getEndTime() + ", " + task.getRepeatInterval() + ", " + task.isActive() + ", null, null)");
		log.info("Builded query: " + query.toString());
		return addTask(query.toString());
	}*/
	
	public static Integer insertTask(Task task) {
		StringBuilder query = new StringBuilder("insert into tasks (title, time, start, end, rep, isActive, prev, list_id)")
		.append(" values ('").append(task.getTitle())
		.append("', ").append(task.getTime()).append(", ")
		.append(task.getStartTime()).append(", ")
		.append(task.getEndTime()).append(", ")
		.append(task.getRepeatInterval()).append(", ")
		.append(task.isActive()).append(", null, null)");
		String res = query.toString();
		log.info("Builded query: " + res);
		return addTask(res);
	}
	
	/*public static Integer modifyTask(Task task) {
		StringBuilder query = new StringBuilder("insert into tasks (title, time, start, end, rep, isActive, prev, list_id)" 
				+ " values ('" + task.getTitle() + "', " + task.getTime() + ", " + task.getStartTime() + 
				", " + task.getEndTime() + ", " + task.getRepeatInterval() + ", " + task.isActive() + 
				", " + task.getTask_id() + ", " + task.getList_id() + ")");
		log.info("Builded query: " + query.toString());
		return addTask(query.toString());
	}*/
	
	public static Integer modifyTask(Task task) {
		StringBuilder query = new StringBuilder("insert into tasks (title, time, start, end, rep, isActive, prev, list_id)");
		query.append(" values ('").append(task.getTitle())
		.append("', ").append(task.getTime())
		.append(", ").append(task.getStartTime())
		.append(", ").append(task.getEndTime())
		.append(", ").append(task.getRepeatInterval())
		.append(", ").append(task.isActive())
		.append(", ").append(task.getTask_id())
		.append(", ").append(task.getList_id()).append(")");
		String res = query.toString();
		log.info("Builded query: " + res);
		return addTask(res);
	}
	
	public static Integer addTaskInList(Task task, Integer list_id) {
		StringBuilder query = new StringBuilder("insert into tasks (title, time, start, end, rep, isActive, prev, list_id)" 
				+ " values ('" + task.getTitle() + "', " + task.getTime() + ", " + task.getStartTime() + 
				", " + task.getEndTime() + ", " + task.getRepeatInterval() + ", " + task.isActive() + 
				", " + task.getTask_id() + ", " + list_id + ")");
		log.info("Builded query: " + query.toString());
		return addTask(query.toString());
	}
	
	private static Integer addTask(String query) {
		Connection connection = null;
		Statement statement = null;
		Integer task_id = null;
		
		try {
			connection = connections.getConnection();
			statement = connection.createStatement();
			log.info("Statement created");
			
			statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
			ResultSet rs = statement.getGeneratedKeys();
			rs.next();
			task_id = (Integer) rs.getInt(1);
				
			log.info("Statement executed");
			log.info("Task id: " + task_id.toString());
		} catch (SQLException e) {
			log.error(e.getMessage());
		} finally {
			connections.returnConnection(connection);
		}
		return task_id;
	}
	
	public static int insertList(ArrayTaskList list) {
		
		int list_id = 0;
		Connection connection = null;
		PreparedStatement preStmt = null;
		
		try {
			connection = connections.getConnection();
			String query = "insert into lists (type, size) values (?, ?)";
			preStmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			log.info("Statement prepared");
			preStmt.setString(1, "ArrayTaskList");
			preStmt.setInt(2, list.size());
			preStmt.executeUpdate();
			log.info("Statement executed");
			
			ResultSet rs = preStmt.getGeneratedKeys();
		    rs.next();
	        list_id = rs.getInt(1);
			log.info("List id: " + list_id);
		} catch (SQLException e) {
			log.error(e.getMessage());
		} finally {
			connections.returnConnection(connection);
		}
		return list_id;
	}
	
	public static void insertObject(int object_id, int parent_id, int object_type_id, String name) {
		Connection connection = null;
		PreparedStatement preStmt = null;
		
		try {
			connection = connections.getConnection();
			String query = "insert into objects values (?, ?, ?, ?)";
			preStmt = connection.prepareStatement(query);
			log.info("Statement prepared");
			preStmt.setInt(1, object_id);
			preStmt.setInt(2, parent_id);
			preStmt.setInt(3, object_type_id);
			preStmt.setString(4, name);
			preStmt.executeUpdate();
			log.info("Statement executed");
		} catch (SQLException e) {
			log.error(e.getMessage());
		} finally {
			try {
				if (preStmt != null) {
					preStmt.close();
				}
			} catch (SQLException e) {
				log.error(e.getMessage());
			}
			connections.returnConnection(connection);
		}
	}
	
	public static void insertParam(int param_id, int object_id, int attr_id, String text_value, int number_value, String data_value) {
		Connection connection = null;
		PreparedStatement preStmt = null;
		
		try {
			connection = connections.getConnection();
			String query = "insert into params values (?, ?, ?, ?, ?, ?)";
			preStmt = connection.prepareStatement(query);
			log.info("Statement prepared");
			preStmt.setInt(1, param_id);
			preStmt.setInt(2, object_id);
			preStmt.setInt(3, attr_id);
			preStmt.setString(4, text_value);
			preStmt.setInt(5, number_value);
			preStmt.setString(6, data_value);
			preStmt.executeUpdate();
			log.info("Statement executed");
		} catch (SQLException e) {
			log.error(e.getMessage());
		} finally {
			try {
				if (preStmt != null) {
					preStmt.close();
				}
			} catch (SQLException e) {
				log.error(e.getMessage());
			}
			connections.returnConnection(connection);
		}
	}
	
}