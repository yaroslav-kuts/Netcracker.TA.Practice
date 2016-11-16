package ua.edu.sumdu.ta.yaroslavkuts.pr6.db;

import ua.edu.sumdu.ta.yaroslavkuts.pr6.*;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;

public class MysqlDB extends DBWriter {

	private static MysqlDB instance;
	
	private MysqlDB() {
		connections = new ConnectionPool(DataBases.MYSQL);
	}
	
	public static synchronized MysqlDB getInstance() {
		if (instance == null) {
			instance = new MysqlDB();
		}
		return instance;
	}
	
	@Override
	public Integer addTask(Task task) {
		StringBuilder query = new StringBuilder("insert into tasks (title, time, start, end, rep, isActive, prev, list_id)")
		.append(" values ('").append(task.getTitle())
		.append("', ").append(task.getTime()).append(", ")
		.append(task.getStartTime()).append(", ")
		.append(task.getEndTime()).append(", ")
		.append(task.getRepeatInterval()).append(", ")
		.append(task.isActive()).append(", null, null)");
		String res = query.toString();
		LOG.info("Builded query: " + res);
		return insertTask(res);
	}

	@Override
	public Integer modifyTask(Task task) {
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
		LOG.info("Builded query: " + res);
		return insertTask(res);
	}
	
	@Override
	public Integer addTaskInList(Task task, Integer list_id) {
		StringBuilder query = new StringBuilder("insert into tasks (title, time, start, end, rep, isActive, prev, list_id)");
		query.append(" values ('").append(task.getTitle())
		.append("', ").append(task.getTime())
		.append(", ").append(task.getStartTime())
		.append(", ").append(task.getEndTime())
		.append(", ").append(task.getRepeatInterval())
		.append(", ").append(task.isActive())
		.append(", ").append(task.getTask_id())
		.append(", ").append(list_id).append(")");
		String res = query.toString();
		LOG.info("Builded query: " + res);
		return insertTask(res);
	}
	
	@Override
	public int insertList(ArrayTaskList list) {
		
		int list_id = 0;
		Connection connection = null;
		PreparedStatement preStmt = null;
		
		try {
			connection = connections.getConnection();
			String query = "insert into lists (type, size) values (?, ?)";
			preStmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			LOG.info("Statement prepared");
			preStmt.setString(1, "ArrayTaskList");
			preStmt.setInt(2, list.size());
			preStmt.executeUpdate();
			LOG.info("Statement executed");
			
			ResultSet rs = preStmt.getGeneratedKeys();
		    rs.next();
	        list_id = rs.getInt(1);
			LOG.info("List id: " + list_id);
		} catch (SQLException e) {
			LOG.error(e.getMessage());
		} finally {
			connections.returnConnection(connection);
		}
		return list_id;
	}
	
	private Integer insertTask(String query) {
		Connection connection = null;
		Statement statement = null;
		Integer task_id = null;
		
		try {
			connection = connections.getConnection();
			statement = connection.createStatement();
			LOG.info("Statement created");
			
			statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
			ResultSet rs = statement.getGeneratedKeys();
			rs.next();
			task_id = (Integer) rs.getInt(1);
				
			LOG.info("Statement executed");
			LOG.info("Task id: " + task_id.toString());
		} catch (SQLException e) {
			LOG.error(e.getMessage());
		} finally {
			connections.returnConnection(connection);
		}
		return task_id;
	}
}