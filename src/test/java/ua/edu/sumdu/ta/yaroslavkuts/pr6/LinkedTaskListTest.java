package ua.edu.sumdu.ta.yaroslavkuts.pr6.tests;

import org.junit.Before;
import ua.edu.sumdu.ta.yaroslavkuts.pr6.*;

public class LinkedTaskListTest extends TaskListTest {

    @Before
    public void before()
    {
        tasks = new LinkedTaskList();
    }
}
