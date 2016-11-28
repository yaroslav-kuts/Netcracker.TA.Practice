package ua.edu.sumdu.ta.yaroslavkuts.pr7.tests;

import org.junit.Before;
import ua.edu.sumdu.ta.yaroslavkuts.pr7.*;

public class LinkedTaskListTest extends TaskListTest {

    @Before
    public void before()
    {
        tasks = new LinkedTaskList();
    }
}
