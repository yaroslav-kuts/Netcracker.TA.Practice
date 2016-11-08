package ua.edu.sumdu.ta.yaroslavkuts.pr5;

/**
 * Describe the list of the task in task manager.
 *
 * @version 1.0 12 Oct 2016
 * @author Yaroslav Kuts
 */
public abstract class AbstractTaskList {
	
	static final int START_SIZE = 5;
	static final int EXTRA_SIZE = 5;
	static final String PREFIX = "[EDUCTR][TA]";
	static int numberOfLists;
	Task[] taskList = new Task[START_SIZE];
	int size;
	
	public int size() {
		return size;
	}
	
	public abstract void add(Task task);
	
	public abstract void remove(Task task);
	
	public abstract Task getTask(int index);
	
	public abstract Task[] incoming(int from, int to);
}