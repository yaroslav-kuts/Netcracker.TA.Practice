package ua.edu.sumdu.ta.yaroslavkuts.pr5;

import ua.edu.sumdu.ta.yaroslavkuts.pr5.AbstractTaskList;
import ua.edu.sumdu.ta.yaroslavkuts.pr5.db.*;

/**
 * Emplement the list of the task in task manager.
 *
 * @version 1.0 12 Oct 2016
 * @author Yaroslav Kuts 
 */
public class ArrayTaskList extends AbstractTaskList {
	
	private DBWriter db = MysqlDB.getInstance();
	
	private Integer list_id = 0;
	
	public ArrayTaskList() {
		numberOfLists++;
		list_id = db.insertList(this);
	}
	
	/**
	 * Create array and add in it tasks from specified interval.
	 * @param from start of tracked interval in seconds
	 * @param to end of tracked interval in seconds 
	 * @return array of tasks from the list, where warnings are between "from" and "to" seconds
	 */
	public Task[] incoming(int from, int to) {
		Task[] incomingTasks = new Task[size()];
		int position = 0;
		
		for (int i = 0; i < size(); i++) {
			Task task = taskList[i];
			if (task.isActive()) {
				if (task.isRepeated() && task.nextTimeAfter(from) != -1) {
					if (task.nextTimeAfter(from) > from && task.nextTimeAfter(from) <= to) {
						incomingTasks[position] = task;
						position++;
					}
					if (task.getStartTime() > from && task.getStartTime() <= from) {
						incomingTasks[position] = task;
						position++;
					}
				}
				else if (task.getTime() > from && task.getTime() <= to) {
					incomingTasks[position] = task;
					position++;
				}
			}
		}
		
		int numberOfTasks = 0;
		for (int i = 0; i < incomingTasks.length; i++) {
			if (incomingTasks[i] != null) {
				numberOfTasks++;
			}
		}
		
		Task[] result = new Task[numberOfTasks];
		for (int i = 0; i < result.length; i++) {
			result[i] = incomingTasks[i];
		}
		
		return result;
	}
	
	/**
	 * Add new task in tasks list if task's link not null. 
	 * @param task
	 */
	@Override
	public void add(Task task) throws NullPointerException {
		if (task != null) {
			if (taskList.length == size()) {
				extendList();
				add(task);
			} else {
				task.setTitle(PREFIX + " " + task.getTitle());
				taskList[size()] = task;
				task.setList_id(this.list_id);
				task.setTask_id(db.addTaskInList(task, this.list_id));
				size++;
			}
		} else {
			throw new NullPointerException("Task cannot be null");
		}
	}
	
	/**
	 * Create new array with bigger length and overwrite tasks. 
	 */
	private void extendList() {
		Task[] tempList = taskList;
		taskList = new Task[taskList.length + EXTRA_SIZE];
		for (int i = 0; i < tempList.length; i++) {
			taskList[i] = tempList[i];
		}
	}
	
	/**
	 * Remove task from tasks list if task exists in list and not null. 
	 * @param task
	 */
	@Override
	public void remove(Task task) throws NullPointerException {
		if (task != null) {
			for (int i = 0; i < size(); i++) {
				if (task.equals(taskList[i])) {
					while (i < size() - 1) {
						taskList[i] = taskList[++i];
					}
					taskList[size() - 1] = null;
					task.setList_id(null);
					task.setTask_id(db.addTaskInList(task, null));
					size--;
					break;
				}
			}
		} else {
			throw  new NullPointerException("Task cannot be null");
		}
	}
	
	@Override
	public Task getTask(int index) throws IndexOutOfBoundsException {
		Task result = null;
		if (index >= 0 && index < size()) {
			result = taskList[index];
		} else {
			throw new IndexOutOfBoundsException("Index value out of interval from 0 to " + size());
		}
		return result;
	}
	
	public int getNumberOfLists() {
		return numberOfLists;
	}
	
	public Integer getList_id() {
		return list_id;
	}
}