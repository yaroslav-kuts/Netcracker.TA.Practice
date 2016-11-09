package ua.edu.sumdu.ta.yaroslavkuts.pr5.tests;

import org.junit.Test;
import ua.edu.sumdu.ta.yaroslavkuts.pr5.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public abstract class TaskListTest {

    protected AbstractTaskList tasks;
        
    // utility functions --------------------------------------------------

    private static void assertContains(Task[] expected, Task[] actualA) {
        assertEquals("Unexpected size", expected.length, actualA.length);
        List<Task> actual = new ArrayList<Task>();
        for (Task task : actualA)
            actual.add(task);
            
        for (Task task : expected)
            if (actual.contains(task))
                actual.remove(task);
            else
                fail("Task "+ task +" expected to be in list");
                
        if (! actual.isEmpty())
            fail("Tasks "+ actual +" are unexpected in list");
    }

    private void assertContains(Task[] expected) {
        Task[] actual = new Task[tasks.size()];
        for (int i = 0; i < tasks.size(); i++)
            actual[i] = tasks.getTask(i);
        assertContains(expected, actual);
    }

    private static Task task(String title) {
        return new Task(title, 0);
    }
    
    private static Task task(String title, int time, boolean active) {
        Task t = new Task(title, time);
        t.setActive(active);
        return t;
    }

    private static Task task(String title, int from, int to, int interval, boolean active) {
        Task t = new Task(title, from, to, interval);
        t.setActive(active);
        return t;
    }
    
    private void addAll(Task[] ts) {
        for (Task t : ts)
            tasks.add(t);
    }        

    // tests --------------------------------------------------------------

    @Test
    public void testSizeAddGet() {
        assertEquals(0, tasks.size());
        Task[] ts = {task("A"), task("B"), task("C")};
        addAll(ts);
        assertEquals(ts.length, tasks.size());
        assertContains(ts);
    }    
    
    @Test(expected = RuntimeException.class)
    public void testWrongAdd() {
        tasks.add(null);
    }
    
    @Test
    public void testInvalidGet() {
        tasks.add(task("Task"));
        Exception e1 = null, e2 = null;
        try {
            tasks.getTask(-1);
            fail("On getTask(-1) exception expected");
        }
        catch (RuntimeException e) {
            e1 = e;
        }
        try {
            tasks.getTask(tasks.size());
            fail("On getTask(size) exception expected");
        }
        catch (RuntimeException e) {
            e2 = e;
        }
        assertEquals("Exception of index < 0 and index >= size must be the same",
            e1.getClass(), e2.getClass());
    }
    
    @Test
    public void testRemove() {
        Task[] ts = {task("A"),task("B"),task("C"),task("D"),task("E")};
        addAll(ts);
            
        // remove first
        tasks.remove(tasks.getTask(0));
        assertContains(new Task[] { ts[1], ts[2], ts[3], ts[4] });
        
        // remove last
        tasks.remove(tasks.getTask(tasks.size() - 1));
        assertContains(new Task[] { ts[1], ts[2], ts[3] });

        // remove middle
        tasks.remove(tasks.getTask(1));
        assertContains(new Task[] { ts[1], ts[3] });
    }
    
    @Test(expected = RuntimeException.class)
    public void testInvalidRemove() {
        tasks.remove(null);
    }
    
    @Test
    public void testAddManyTasks() {
        Task[] ts = new Task[1000];
        for (int i = 0; i < ts.length; i++) {
            ts[i] = new Task("Task#"+ i, i);
            tasks.add(ts[i]);
        }
        assertContains(ts);
    }
    /**Additional method*/
    @Test
    public void testIncomingInactive() {
        Task[] ts = {task("A",0,false), task("B",1,false), task("C",2,false)};
        addAll(ts);
        assertArrayEquals(new Task[0], tasks.incoming(0, 1000));
    }
    /**Additional method*/
    @Test
    public void testIncoming() {
        // range: 50 60
        Task[] ts = {
            task("Simple IN", 55, true), 
            task("Simple OUT", 10, true),
            task("Inactive OUT", 0, 1000, 1, false), 
            task("Simple bound OUT", 50, true), 
            task("Simple bound IN", 60, true),
            task("Repeat inside IN", 51, 58, 2, true),
            task("Repeat outside IN", 0, 100, 5, true),
            task("Repeat outside OUT", 0, 100, 22, true),
            task("Repeat left OUT", 0, 40, 1, true),
            task("Repeat right OUT", 65, 100, 1, true),
            task("Repeat left intersect IN", 0, 55, 13, true),
            task("Repeat left intersect IN", 0, 60, 30, true),
            task("Repeat left intersect OUT", 0, 55, 22, true),
            task("Repeat right intersect IN", 55, 100, 20, true)
        };
        addAll(ts);
        List<Task> incoming = new ArrayList<Task>();
        for (Task t : ts)
            if (t.getTitle().contains("IN"))
                incoming.add(t);
        
        assertContains(incoming.toArray(new Task[0]), tasks.incoming(50, 60));
    }
}












