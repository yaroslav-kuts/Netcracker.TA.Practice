package ua.edu.sumdu.ta.yaroslavkuts.pr7.tests;

import ua.edu.sumdu.ta.yaroslavkuts.pr7.*;
import java.util.*;
import org.junit.*;
import org.junit.rules.*;
import static org.junit.Assert.*;

public class TaskListTest {

    protected AbstractTaskList tasks;
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();

    private static void assertEqualTasks(Task[] tasks, AbstractTaskList  list) {
        Task[] actual = new Task[list.size()];
		{
			int i = 0;
			for (Task task : list) {
				actual[i] = task;
				i++;
			}
		}
        assertArrayEquals(tasks, actual);
    }

    private static void assertContains(Task[] expected, Task[] actual) {
        List<Task> expectedList = new ArrayList<Task>(Arrays.asList(expected));
        for (Task task : actual)
            if (expectedList.contains(task))
                expectedList.remove(task);
            else
                fail("Expected elements: "+ Arrays.toString(expected) +
                        ", actual elements: "+ Arrays.toString(actual));
        if (! expectedList.isEmpty())
            fail("Expected elements: "+ Arrays.toString(expected) +
                    ", actual elements: "+ Arrays.toString(actual));
    }

    @Test
    public void testAdd() {
        assertEquals(0, tasks.size());
        Task[] ts = {new Task("a",0), new Task("b",1), new Task("c",2)};
        for (Task t : ts)
            tasks.add(t);
        assertEqualTasks(ts, tasks);
    }
    @Test
    public void testRemove() {
        Task[] ts = {new Task("a",0), new Task("b",1), new Task("c",2)};
        tasks.add(ts[0]);
        Task t = new Task("some",0);
        tasks.add(t);
        tasks.add(ts[1]);
        tasks.add(ts[2]);
        tasks.remove(t);
        assertEqualTasks(ts, tasks);
    }
    @Test
    public void testAddMoreTasks() {
        Task[] ts = new Task[100];
        for (int i = 0; i < 100; i++) {
            ts[i] = new Task("Task#"+ i, i);
            tasks.add(ts[i]);
        }
        assertEqualTasks(ts, tasks);
    }
    /**Additional method*/
    @Test
    public void testIncomingInactive() {
        Task[] ts = {new Task("a",0), new Task("b",1), new Task("c",2)};
        for (Task t : ts) {
            t.setActive(false);
            tasks.add(t);
        }
        assertArrayEquals(new Task[0], tasks.incoming(0, 1000));
    }
    /**Additional method*/
    @Test
    public void testIncoming() {
        Task[] incoming = {new Task("b", 20), new Task("c", 30), new Task("e", 0, 100, 5)};
        tasks.add(new Task("a", 10));
        tasks.add(incoming[0]);
        tasks.add(incoming[1]);
        tasks.add(new Task("d", 40));
        tasks.add(incoming[2]);
		for (Task task : tasks) {
			task.setActive(true);
		}
        assertContains(incoming, tasks.incoming(11, 36));
    }
	
	@Test
	public void testAddNullTask() {
		thrown.expect(NullPointerException.class);
		thrown.expectMessage("Task cannot be null");
		
		tasks.add(null);
	}
	
	@Test
	public void testRemoveNullTask() {
		thrown.expect(NullPointerException.class);
		thrown.expectMessage("Task cannot be null");
		
		tasks.remove(null);
	}
}
