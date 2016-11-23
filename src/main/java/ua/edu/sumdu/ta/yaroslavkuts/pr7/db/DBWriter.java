package ua.edu.sumdu.ta.yaroslavkuts.pr7.db;

import ua.edu.sumdu.ta.yaroslavkuts.pr7.*;
import org.apache.log4j.Logger;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;

public abstract class DBWriter {
	
	final static Logger LOG = Logger.getLogger(DBWriter.class);
	ConnectionPool connections;
	
	public abstract Integer addTask(Task task);
	
	public abstract Integer modifyTask(Task task);
	
	public abstract Integer addTaskInList(Task task, Integer list_id);
	
	public abstract int insertList(ArrayTaskList list);
}