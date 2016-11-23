package ua.edu.sumdu.ta.yaroslavkuts.pr7.tests;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import ua.edu.sumdu.ta.yaroslavkuts.pr7.*;

public class CloneTest {
	
	@Test
	public void testTaskClone() throws CloneNotSupportedException {
		Task original = new Task("Read book", 15);
		Task clone = original.clone();
		
		assertFalse(original == clone);
		assertTrue(original.getClass() == clone.getClass());
		assertTrue(original.equals(clone));
		
		clone.setTime(25);
		
		assertEquals(original.getTime(), 15);
	}
	
	@Test
	public void testArrayTaskListClone() throws CloneNotSupportedException {
		ArrayTaskList original = new ArrayTaskList();
		original.add(new Task("Read book", 9));
		original.add(new Task("Train", 27));
		ArrayTaskList clone = (ArrayTaskList) original.clone();
		
		assertFalse(original == clone);
		assertTrue(original.getClass() == clone.getClass());
		assertTrue(original.equals(clone));
		assertEquals(original.toString(), clone.toString());
		
		clone.add(new Task("Workout", 60));
		
		assertEquals(original.size(), 2);
		assertEquals(clone.size(), 3);
		
		//Task first = clone.getTask(0);
		//first.setTime(55);
		
		//assertEquals(55, clone.getTask(0).getTime());
		//assertEquals(9, original.getTask(0).getTime());
	}
	
	@Test
	public void testLinkedTaskListClone() throws CloneNotSupportedException {
		LinkedTaskList original = new LinkedTaskList();
		original.add(new Task("Read book", 9));
		original.add(new Task("Train", 27));
		LinkedTaskList clone = original.clone();
		
		assertFalse(original == clone);
		assertTrue(original.getClass() == clone.getClass());
		assertTrue(original.equals(clone));
		assertEquals(original.toString(), clone.toString());
		
		clone.add(new Task("Workout", 60));
		
		assertEquals(original.size(), 2);
		assertEquals(clone.size(), 3);
		
		//Task first = clone.getTask(0);
		//first.setTime(55);
		
		//assertEquals(55, clone.getTask(0).getTime());
		//assertEquals(9, original.getTask(0).getTime());
		
		for (Task task : clone) {
			task.setTime(250);
		}
		
		for (Task task : clone) {
			assertEquals(task.getTime(), 250);
		}

		for (Task task : original) {
			assertTrue(task.getTime() != 250);
		}
		
		Task[] tasks = new Task[original.size()];
		
		int j = 0;
		for (Task task : original) {
			tasks[j] = task;
			j++;
		}
		
		assertEquals(tasks[0].getTime(), 9);
		assertEquals(tasks[1].getTime(), 27);
		assertEquals(tasks[0].getTitle(), "Read book");
		assertEquals(tasks[1].getTitle(), "Train");
	}
	
	@Test
	public void testArrayTaskListEqualsViaCloning() throws CloneNotSupportedException {
		ArrayTaskList list1 = new ArrayTaskList();
		list1.add(new Task("Read book", 9));
		list1.add(new Task("Train", 27));
		ArrayTaskList list2 = (ArrayTaskList) list1.clone();
		ArrayTaskList list3 = (ArrayTaskList) list2.clone();
		
		assertTrue(list1.equals(list2));
		assertTrue(list2.equals(list1));
		assertTrue(list1.equals(list1));
		assertTrue(list2.equals(list3));
		assertTrue(list1.equals(list3));
	}
	
	@Test
	public void testLinkedTaskListEqualsViaCloning() throws CloneNotSupportedException {
		LinkedTaskList original = new LinkedTaskList();
		original.add(new Task("Read book", 9));
		original.add(new Task("Train", 27));
		LinkedTaskList clone = original.clone();
		LinkedTaskList anotherClone = clone.clone();
		
		assertTrue(original.equals(clone));
		assertTrue(clone.equals(original));
		assertTrue(original.equals(original));
		assertTrue(original.equals(anotherClone));
	}
	
	@Test
	public void testLinkedTaskListEqualsViaCreatingObjects() {
		LinkedTaskList list1 = new LinkedTaskList();
		list1.add(new Task("Read book", 9));
		list1.add(new Task("Train", 27));
		
		LinkedTaskList list2 = new LinkedTaskList();
		list2.add(new Task("Read book", 9));
		list2.add(new Task("Train", 27));
		
		LinkedTaskList list3 = new LinkedTaskList();
		list3.add(new Task("Read book", 9));
		list3.add(new Task("Train", 27));
		
		assertTrue(list1.equals(list2));
		assertTrue(list2.equals(list1));
		assertTrue(list1.equals(list1));
		assertTrue(list2.equals(list3));
		assertTrue(list1.equals(list3));
	}
	
	@Test
	public void testArrayTaskListEqualsViaCreatingObjects() {
		ArrayTaskList list1 = new ArrayTaskList();
		list1.add(new Task("Read book", 9));
		list1.add(new Task("Train", 27));
		
		ArrayTaskList list2 = new ArrayTaskList();
		list2.add(new Task("Read book", 9));
		list2.add(new Task("Train", 27));
		
		ArrayTaskList list3 = new ArrayTaskList();
		list3.add(new Task("Read book", 9));
		list3.add(new Task("Train", 27));
		
		assertTrue(list1.equals(list2));
		assertTrue(list2.equals(list1));
		assertTrue(list1.equals(list1));
		assertTrue(list2.equals(list3));
		assertTrue(list1.equals(list3));
	}
}