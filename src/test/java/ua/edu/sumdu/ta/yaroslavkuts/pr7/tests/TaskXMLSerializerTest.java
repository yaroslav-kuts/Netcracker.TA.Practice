package ua.edu.sumdu.ta.yaroslavkuts.pr7.tests;

import org.junit.Assert;
import org.junit.Test;
import ua.edu.sumdu.ta.yaroslavkuts.pr7.ArrayTaskList;
import ua.edu.sumdu.ta.yaroslavkuts.pr7.Task;
import ua.edu.sumdu.ta.yaroslavkuts.pr7.TaskXMLSerializer;

import java.io.File;
import java.io.IOException;

/**
 * Created by Ярослав on 28.11.2016.
 */
public class TaskXMLSerializerTest {

    @Test
    public void saveTest() throws IOException {
        ArrayTaskList list = new ArrayTaskList();
		Task task1 = new Task("Wake up", 28800);
		task1.setActive(true);
        list.add(task1);
		Task task2 = new Task("Eat", 30000, 64800, 14400);
		task2.setActive(true);
        list.add(task2);

        TaskXMLSerializer.save(list, "result.xml");
		ArrayTaskList result = (ArrayTaskList) TaskXMLSerializer.load("result.xml");
        Assert.assertTrue(list.equals(result));
    }
}
