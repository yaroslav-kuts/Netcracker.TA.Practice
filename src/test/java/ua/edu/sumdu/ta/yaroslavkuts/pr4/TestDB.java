package ua.edu.sumdu.ta.yaroslavkuts.pr4.tests;

import ua.edu.sumdu.ta.yaroslavkuts.pr4.*;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import org.junit.*;
import static org.junit.Assert.*;

public class TestDB {
	/*@Test
	public void testConnection() {
		Connection connection = DBConnector.getConnection();
		
		assertTrue(connection != null);
	}*/
	
	@Test
	public void testInsertTask() {
		Task task = new Task("read book", 7);
		
		assertTrue(task.getTask_id() != 0);
	}
	
	@Test
	public void testModifyTask() {
		Task task = new Task("read book", 7);
		int old_id = task.getTask_id();
		
		task.setTitle("Do practice");
		int new_id = task.getTask_id();
		
		assertTrue(old_id != new_id);
	}
	
	@Test
	public void testAddTaskInList() {
		Task task = new Task("Training", 40);
		int old_id = task.getTask_id();
		
		ArrayTaskList list = new ArrayTaskList();
		
		list.add(task);
		int new_id = task.getTask_id();
		
		assertTrue(old_id != new_id);
		assertEquals(task.getList_id(), list.getList_id());
	}

	@Test
	public void testInsertList() {
		ArrayTaskList list = new ArrayTaskList();
		
		assertTrue(list.getList_id() != 0);
	}
}