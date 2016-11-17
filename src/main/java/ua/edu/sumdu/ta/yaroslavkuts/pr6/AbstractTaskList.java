package ua.edu.sumdu.ta.yaroslavkuts.pr6;

import java.util.Arrays;
import java.util.Iterator;

/**
 * Describe the list of the task in task manager.
 *
 * @version 1.0 12 Oct 2016
 * @author Yaroslav Kuts
 */
public abstract class AbstractTaskList implements Cloneable, Iterable<Task> {
	
	static final int START_SIZE = 5;
	static final int EXTRA_SIZE = 5;
	static final String PREFIX = "[EDUCTR][TA]";
	static int numberOfLists;
	Task[] taskList = new Task[START_SIZE];
	int size;
	
	public int size() {
		return size;
	}
	
	/**
	 * Creates and returns a copy of list. 
	 * @return copy with the same fields
	 * @throws CloneNotSupportedException if the object's class don't implements Cloneable
	 */
	@Override
	public AbstractTaskList clone() throws CloneNotSupportedException {
		AbstractTaskList list = (AbstractTaskList) super.clone();
		list.taskList = Arrays.copyOf(taskList, taskList.length);
		for (int i = 0; i < list.taskList.length; i++) {
			if (list.taskList[i] != null) {
				list.taskList[i] = list.taskList[i].clone();
			}
		}
		return list;
	}
	
	/**
	 * Create iterator for elements with type 'Task'. 
	 * @return task's iterator
	 */
	@Override
	public Iterator<Task> iterator() {
		Iterator<Task> iterator = new Iterator<Task>() {

            private int index;
			private boolean removable;

            @Override
            public boolean hasNext() {
				if (index < size) return true;
				else {
					removable = false;
					return false;
				}
            }

            @Override
            public Task next() {
				removable = true;
                return taskList[index++];
            }

            @Override
            public void remove() throws IllegalStateException {
				if (removable) {
					AbstractTaskList.this.remove(taskList[index-1]);
					removable = false;
				} else throw new IllegalStateException("remove() using only once after each calling next()");
            }
        };
        return iterator;
	}
	
	public abstract void add(Task task);
	
	public abstract void remove(Task task);
	
	public abstract Task[] incoming(int from, int to);
}