package ua.edu.sumdu.ta.yaroslavkuts.pr6.tests;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import ua.edu.sumdu.ta.yaroslavkuts.pr6.*;
import java.util.Iterator;

public class IteratorTest {

	@Test
	public void testArrayTaskListForEach() {
		ArrayTaskList list = new ArrayTaskList();
		list.add(new Task("Read book", 9));
		list.add(new Task("Train", 27));
		
		for (Task task : list) {
			assertTrue(task != null);
		}
	}
	
	@Test
	public void testLinkedTaskListForEach() {
		LinkedTaskList list = new LinkedTaskList();
		list.add(new Task("Read book", 9));
		list.add(new Task("Train", 27));
		
		for (Task task : list) {
			assertTrue(task != null);
		}
	}
	
	@Test
	public void testArrayTaskListIterator() {
		ArrayTaskList list = new ArrayTaskList();
		list.add(new Task("Read book", 9));
		list.add(new Task("Train", 27));
		
		Iterator<Task> i = list.iterator();
		assertTrue(i.hasNext());
		
		while(i.hasNext()) {
			assertTrue(i.next() != null);
		}
		
		Task[] tasks = new Task[2];
		
		int j = 0;
		for (Task task : list) {
			tasks[j] = task;
			j++;
		}
		
		assertEquals("[EDUCTR][TA] Read book", tasks[0].getTitle());
		assertEquals("[EDUCTR][TA] Train", tasks[1].getTitle());
	}
	
	@Test(expected = IllegalStateException.class)
	public void testRemoveInArrayTaskList1() {
		ArrayTaskList list = new ArrayTaskList();
		list.add(new Task("Read book", 9));
		list.add(new Task("Train", 27));
		
		Iterator<Task> i = list.iterator();
		i.remove();
	}
	
	@Test(expected = IllegalStateException.class)
	public void testRemoveInArrayTaskList2() {
		ArrayTaskList list = new ArrayTaskList();
		list.add(new Task("Read book", 9));
		list.add(new Task("Train", 27));
		
		Iterator<Task> i = list.iterator();
		
		while(i.hasNext()) {
			assertTrue(i.next() != null);
		}
		
		i.remove();
	}
	
	@Test(expected = IllegalStateException.class)
	public void testRemoveInArrayTaskList3() {
		ArrayTaskList list = new ArrayTaskList();
		list.add(new Task("Read book", 9));
		list.add(new Task("Train", 27));
		
		Iterator<Task> i = list.iterator();
		
		while(i.hasNext()) {
			assertTrue(i.next() != null);
			i.remove();
			i.remove();
		}
	}
	
	@Test
	public void testRemoveInArrayTaskList4() {
		ArrayTaskList list = new ArrayTaskList();
		list.add(new Task("Read book", 9));
		list.add(new Task("Train", 27));
		list.add(new Task("English", 57));
		
		Iterator<Task> it = list.iterator();
		
		int j = 0;
		while(it.hasNext()) {
			it.next();
			if (j == 1) {
				it.remove();
			}
			j++;
		}
		
		assertEquals(2, list.size());
		
		Task[] tasks = new Task[2];
		
		int i = 0;
		for (Task task : list) {
			tasks[i] = task;
			i++;
		}
		
		assertEquals("[EDUCTR][TA] Read book", tasks[0].getTitle());
		assertEquals("[EDUCTR][TA] English", tasks[1].getTitle());
	}
	
	@Test(expected = IllegalStateException.class)
	public void testRemoveInLinkedTaskList1() {
		LinkedTaskList list = new LinkedTaskList();
		list.add(new Task("Read book", 9));
		list.add(new Task("Train", 27));
		
		Iterator<Task> i = list.iterator();
		i.remove();
	}
	
	@Test(expected = IllegalStateException.class)
	public void testRemoveInLinkedTaskList2() {
		LinkedTaskList list = new LinkedTaskList();
		list.add(new Task("Read book", 9));
		list.add(new Task("Train", 27));
		
		Iterator<Task> i = list.iterator();
		
		while(i.hasNext()) {
			assertTrue(i.next() != null);
		}
		
		i.remove();
	}
	
	@Test(expected = IllegalStateException.class)
	public void testRemoveInLinkedTaskList3() {
		LinkedTaskList list = new LinkedTaskList();
		list.add(new Task("Read book", 9));
		list.add(new Task("Train", 27));
		
		Iterator<Task> i = list.iterator();
		
		while(i.hasNext()) {
			assertTrue(i.next() != null);
			i.remove();
			i.remove();
		}
	}
	
	@Test
	public void testRemoveInLinkedTaskList4() {
		LinkedTaskList list = new LinkedTaskList();
		list.add(new Task("Read book", 9));
		list.add(new Task("Train", 27));
		list.add(new Task("English", 57));
		
		Iterator<Task> it = list.iterator();
		
		int j = 0;
		while(it.hasNext()) {
			it.next();
			if (j == 1) {
				it.remove();
			}
			j++;
		}
		
		assertEquals(2, list.size());
		
		Task[] tasks = new Task[2];
		
		int i = 0;
		for (Task task : list) {
			tasks[i] = task;
			i++;
		}
		
		assertEquals("Read book", tasks[0].getTitle());
		assertEquals("English", tasks[1].getTitle());
	}
	
	@Test
	public void parallelIterationInLinkedList() {
		
		LinkedTaskList list = new LinkedTaskList();
		list.add(new Task("Read book", 9));
		list.add(new Task("Train", 27));
		
		String[] controlArr = {"Read book:Read book", "Read book:Train", "Train:Read book", "Train:Train"};
		String[] testArr = new String[list.size() * list.size()];
			
		int j = 0;
		for (Task t1 : list) {
			for (Task t2 : list) {
				testArr[j] = t1.getTitle() + ":" + t2.getTitle();
				j++;
			}
		}
		
		for (int i = 0; i < controlArr.length; i++) {
			assertEquals(controlArr[i], testArr[i]);
		}
		
	}
	
	@Test
	public void parallelIterationInArrayList() {
		
		ArrayTaskList list = new ArrayTaskList();
		list.add(new Task("Read book", 9));
		list.add(new Task("Train", 27));
		
		String[] controlArr = {"[EDUCTR][TA] Read book:[EDUCTR][TA] Read book", "[EDUCTR][TA] Read book:[EDUCTR][TA] Train",
		"[EDUCTR][TA] Train:[EDUCTR][TA] Read book", "[EDUCTR][TA] Train:[EDUCTR][TA] Train"};
		String[] testArr = new String[list.size() * list.size()];
			
		int j = 0;
		for (Task t1 : list) {
			for (Task t2 : list) {
				testArr[j] = t1.getTitle() + ":" + t2.getTitle();
				j++;
			}
		}
		
		for (int i = 0; i < controlArr.length; i++) {
			assertEquals(controlArr[i], testArr[i]);
		}
		
	}
}