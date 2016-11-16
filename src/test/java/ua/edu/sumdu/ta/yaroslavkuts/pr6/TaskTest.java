package ua.edu.sumdu.ta.yaroslavkuts.pr6.tests;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;
import ua.edu.sumdu.ta.yaroslavkuts.pr6.*;

public class TaskTest {

    @BeforeClass
    public static void testDescription() {
        System.out.println("--- description test ---");
        Task task = new Task("Inactive repeated", 10, 100, 5);
        System.out.println(task.toString());
        task = new Task("Active one-time", 50);
        task.setActive(true);
        System.out.println(task.toString());
        System.out.println("------------------------");
    }

    @Test
    public void testTitle() {
        Task task = new Task("test", 0);
        assertEquals("test", task.getTitle());
        task.setTitle("other");
        assertEquals("other", task.getTitle());
    }
    
    @Test(expected = RuntimeException.class)
    public void testWrongTitle() {
        Task task = new Task(null, 0);
    }        

    @Test(expected = RuntimeException.class)
    public void testWrongTitle2() {
        Task task = new Task("OK", 0);
        task.setTitle(null);
    }  

	@Test(expected = RuntimeException.class)
    public void testWrongTitle3() {
        Task task = new Task("", 0);
    }  	
	
	@Test(expected = RuntimeException.class)
    public void testWrongTitle4() {
        Task task = new Task("OK", 0);
		task.setTitle("");
    }	
    
    @Test
    public void testActive() {
        Task task = new Task("test", 0);
        assertFalse(task.isActive());
        task.setActive(true);
        assertTrue(task.isActive());
    }
    @Test
    public void testConstructorNonrepeated() {
        Task task = new Task("test", 10);
        assertFalse("active", task.isActive());
        assertEquals("time", 10, task.getTime());
        assertEquals("start", 10, task.getStartTime());
        assertEquals("end", 10, task.getEndTime());
        assertFalse("repeated", task.isRepeated());
		assertEquals("repeatInterval", 0, task.getRepeatInterval());
    }
    @Test
    public void testConstructorRepeated() {
        Task task = new Task("test", 10, 100, 5);
        assertFalse("active", task.isActive());
        assertEquals("time", 10, task.getTime());
        assertEquals("start", 10, task.getStartTime());
        assertEquals("end", 100, task.getEndTime());
        assertTrue("repeated", task.isRepeated());
        assertEquals("repeatInterval", 5, task.getRepeatInterval());
    }
    @Test
    public void testTimeNonRepeated() {
        Task task = new Task("test", 0, 100, 15);
        task.setTime(50);
        assertEquals("time", 50, task.getTime());
        assertEquals("start", 50, task.getStartTime());
        assertEquals("end", 50, task.getEndTime());
        assertFalse("repeated", task.isRepeated());
		assertEquals("repeatInterval", 0, task.getRepeatInterval());
    }
    @Test
    public void testTimeRepeated() {
        Task task = new Task("test", 10);
        task.setTime(5, 20, 1);
        assertEquals("time", 5, task.getTime());
        assertEquals("start", 5, task.getStartTime());
        assertEquals("end", 20, task.getEndTime());
        assertTrue("repeated", task.isRepeated());
        assertEquals("repeatInterval", 1, task.getRepeatInterval());
    }
    
    @Test(expected = RuntimeException.class)
    public void testWrongTime() {
        Task task = new Task("Title", -1);
    }

    @Test(expected = RuntimeException.class)
    public void testWrongStart() {
        Task task = new Task("Title", -1, 10, 5);
    }

    @Test(expected = RuntimeException.class)
    public void testWrongEnd() {
        Task task = new Task("Title", 10, 6, 3);
    }

    @Test(expected = RuntimeException.class)
    public void testWrongInterval() {
        Task task = new Task("Title", 10, 100, -1);
    }

    @Test(expected = RuntimeException.class)
    public void testWrongSetTime() {
        Task task = new Task("Title", 0);
        task.setTime(-1);
    }

    @Test(expected = RuntimeException.class)
    public void testWrongSetStart() {
        Task task = new Task("Title", 0);
        task.setTime(-1, 10, 3);
    }

    @Test(expected = RuntimeException.class)
    public void testWrongSetEnd() {
        Task task = new Task("Title", 10);
        task.setTime(10, 6, 3);
    }

    @Test(expected = RuntimeException.class)
    public void testWrongSetInterval() {
        Task task = new Task("Title", 10);
        task.setTime(10, 100, -1);
    }
    /**Additional method*/
    @Test(expected = RuntimeException.class)
    public void testWrongTimeAfter() {
        Task task = new Task("some", 10);
        task.nextTimeAfter(-1);
    }
    /**Additional method*/
    @Test
    public void testNextNonRepeative() {
        Task task = new Task("some", 10);
        task.setActive(true);
        assertEquals(10, task.nextTimeAfter(0));
        assertEquals(10, task.nextTimeAfter(9));
        assertEquals(-1, task.nextTimeAfter(10));
        assertEquals(-1, task.nextTimeAfter(100));
    }
    /**Additional method*/
    @Test
    public void testNextRepeative() {
        Task task = new Task("some", 10, 100, 20);
        task.setActive(true);
        assertEquals(10, task.nextTimeAfter(0));
        assertEquals(10, task.nextTimeAfter(9));
        assertEquals(50, task.nextTimeAfter(30));
        assertEquals(50, task.nextTimeAfter(40));
        assertEquals(30, task.nextTimeAfter(10));
        assertEquals(-1, task.nextTimeAfter(95));
        assertEquals(-1, task.nextTimeAfter(100));
    }
    /**Additional method*/
    @Test
    public void testNextInactive() {
        Task task = new Task("some", 10);
        task.setActive(false);
        assertEquals(-1, task.nextTimeAfter(0));
    }
}










