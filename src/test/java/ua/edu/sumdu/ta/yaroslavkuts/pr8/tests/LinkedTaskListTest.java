package ua.edu.sumdu.ta.yaroslavkuts.pr8.tests;

import org.junit.Before;
import ua.edu.sumdu.ta.yaroslavkuts.pr8.*;

public class LinkedTaskListTest extends TaskListTest {

    @Before
    public void before()
    {
        tasks = new LinkedTaskList();
    }
}
