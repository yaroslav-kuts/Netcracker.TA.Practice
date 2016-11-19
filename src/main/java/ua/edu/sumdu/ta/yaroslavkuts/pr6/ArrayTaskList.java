package ua.edu.sumdu.ta.yaroslavkuts.pr6;

import ua.edu.sumdu.ta.yaroslavkuts.pr6.AbstractTaskList;
import ua.edu.sumdu.ta.yaroslavkuts.pr6.db.*;
import org.apache.log4j.Logger;
import java.util.Iterator;

/**
 * Emplement the list of the task in task manager.
 *
 * @version 1.0 12 Oct 2016
 * @author Yaroslav Kuts 
 */
public class ArrayTaskList extends AbstractTaskList implements Cloneable {
	
	private final static Logger LOG = Logger.getLogger(ArrayTaskList.class);
	private DBWriter db = MysqlDB.getInstance();
	private Integer list_id = 0;
	
	public ArrayTaskList() {
		numberOfLists++;
		//list_id = db.insertList(this);
	}
	
	/**
	 * Compare this list with other object
	 * param obj object for comparing
	 * @return true if object has same type and same value of fields
	 */
	@Override
	public boolean equals(Object obj) {
		
		if (obj == null) return false;
		
		if (this == obj) return true;
		
		if (obj instanceof ArrayTaskList) {
			ArrayTaskList another = (ArrayTaskList) obj;
			if (this.size == another.size) {
				for (int i = 0; i < size; i++) {
					if (!this.taskList[i].equals(another.taskList[i])) return false;
				}
				return true;
			}
		} else {
			return false;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return numberOfLists;
	}
	
	/**
	 * Create string value of list
	 * @return string value of list
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(getClass().getSimpleName());
		sb.append(" [");
		if (size() > 0) {
			for (int i = 0; i < size(); i++) {
				//sb.append(taskList[i].toString()).append(", ");
				sb.append(taskList[i].getTitle()).append(", ");
			}
		} else {
			sb.append("  ");
		}
		sb.append("\b\b]");
		
		LOG.info(sb.toString());
		return sb.toString();
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
					ArrayTaskList.this.remove(taskList[index-1]);
					removable = false;
				} else throw new IllegalStateException("remove() using only once after each calling next()");
            }
        };
        return iterator;
	}
	
	/**
	 * Add new task in tasks list if task's link not null. 
	 * @param task
	 * @throws NullPointerException if task is null
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
				//task.setList_id(this.list_id);
				//task.setTask_id(db.addTaskInList(task, this.list_id));
				size++;
			}
		} else {
			throw new NullPointerException("Task cannot be null");
		}
	}
	
	/**
	 * Remove task from tasks list if task exists in list and not null. 
	 * @param task
	 * @throws NullPointerException if task is null
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
					//task.setTask_id(db.addTaskInList(task, null));
					size--;
					break;
				}
			}
		} else {
			throw  new NullPointerException("Task cannot be null");
		}
	}
	
	/**
	 * Create array and add in it tasks from specified interval.
	 * @param from start of tracked interval in seconds
	 * @param to end of tracked interval in seconds 
	 * @return array of tasks from the list, where warnings are between "from" and "to" seconds
	 */
	@Override
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
	
	/*public int getNumberOfLists() {
		return numberOfLists;
	}
	
	public Integer getList_id() {
		return list_id;
	}*/
	
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
}