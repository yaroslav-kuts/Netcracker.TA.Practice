package ua.edu.sumdu.ta.yaroslavkuts.pr5;

/**
 * Emplement the linked list of the task in task manager.
 *
 * @version 1.0 31 Oct 2016
 * @author Yaroslav Kuts
 */
public class LinkedTaskList extends AbstractTaskList {
	
	private Entry header;
	
	public LinkedTaskList() {
		size = 0;
		header = new Entry();
	}
	
	/**
	 * Add new task in tasks list if task's link not null. 
	 * @param task
	 * @throws NullPointerException if task is null
	 */
	@Override
	public void add(Task task) throws NullPointerException {
		if (task != null) {
			Entry entry = new Entry(task, header, header.prev);
			entry.prev.next = entry;
			entry.next.prev = entry;
			size++;
		} else {
			throw new NullPointerException("Task cannot be null");
		}
	}
	
	/**
	 * Add new task on the particular position in tasks list if task's link not null. 
	 * @param index, task
	 * @throws NullPointerException if task is null
	 */
	public void add(int index, Task task) throws NullPointerException {
		if (index == size) {
			add(task);
		} else {
			if (task != null) {
				Entry element = findEntry(index);
				Entry entry = new Entry(task, element, element.prev);
				entry.prev.next = entry;
				entry.next.prev = entry;
				size++;
			} else {
				throw new NullPointerException("Task cannot be null");
			}
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
			Entry e = header;
			label: for (int i = 0; i < size; i++) {
				e = e.next;
				if (e.element.equals(task)) {
					e.prev.next = e.next;
					e.next.prev = e.prev;
					e.next = null;
					e.prev = null;
					e.element = null;
					size--;
					break label;
				}
			}
		} else {
			throw new NullPointerException("Task cannot be null");
		}
	}
	
	/**
	 * Find and return task by index in linked list. 
	 * @param index
	 * @return task by passed index
	 * @throws IndexOutOfBoundsException if index < 0 or >= then list's size
	 */
	@Override
	public Task getTask(int index) throws IndexOutOfBoundsException{
		Task result = null;
		if (index >= 0 && index < size()) {
			result = findEntry(index).element;
		} else {
			throw new IndexOutOfBoundsException("Index value out of interval from 0 to " + size());
		}
		return result;
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
		
		Entry e = header;
		for (int i = 0; i < size(); i++) {
			Task task = e.next.element;
			System.out.println(task.toString());
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
			e = e.next;
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
	 * Find entry in task list by specified position. 
	 * @param index
	 * @return entry on particular position
	 */
	private Entry findEntry(int index) {
		Entry e = null;
		if (index < 0 || index >= size) {
			System.out.println("Index out of list's bounds");
		} else {
			e = header;	
			if (index < size / 2) {
				for (int i = 0; i <= index; i++) {
					e = e.next;
				}
			} else {
				for (int i = size; i > index; i--) {
					e = e.prev;
				}
			}
		}
		return e;
	}
	
	/**
	 * Describe nodes of the linked list.
	 *
	 * @version 1.0 31 Oct 2016
	 * @author Yaroslav Kuts
	 */
	private class Entry {
		
		Task element;
		Entry next;
		Entry prev;
		
		Entry() {
			element = null;
			next = this;
			prev = this;
		}
		
		Entry(Task element, Entry next, Entry prev) {
			this.element = element;
			this.next = next;
			this.prev = prev;
		}
	}
}