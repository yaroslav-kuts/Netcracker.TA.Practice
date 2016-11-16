package ua.edu.sumdu.ta.yaroslavkuts.pr6;

import org.apache.log4j.Logger;
import java.util.Iterator;

/**
 * Emplement the linked list of the task in task manager.
 *
 * @version 1.0 31 Oct 2016
 * @author Yaroslav Kuts
 */
public class LinkedTaskList extends AbstractTaskList implements Cloneable, Iterable<Task> {
	
	private final static Logger LOG = Logger.getLogger(LinkedTaskList.class);
	private Entry header;
	
	public LinkedTaskList() {
		size = 0;
		header = new Entry();
	}
	
	/**
	 * Creates and returns a copy of list. 
	 * @return copy with the same fields
	 * @throws CloneNotSupportedException if the object's class don't implements Cloneable
	 */
	@Override
	public LinkedTaskList clone() throws CloneNotSupportedException {
		LinkedTaskList linkedList = (LinkedTaskList) super.clone();
		linkedList.header = header.clone();
		Entry e = header;
		for (int i = 0; i < linkedList.size; i++) {
			e = e.next;
			Entry entry = e.clone();
			entry.element = entry.element.clone();
			entry.prev.next = entry;
			entry.next.prev = entry;
		}
		return linkedList;
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
		
		if (obj instanceof LinkedTaskList) {
			LinkedTaskList another = (LinkedTaskList) obj;
			if (this.size == another.size()) {
				Entry thisEntry = this.header;
				Entry anotherEntry = another.getHeader();
				for (int i = 0; i < size; i++) {
					thisEntry = thisEntry.next;
					anotherEntry = anotherEntry.next;
					if (!thisEntry.element.equals(anotherEntry.element)) return false;
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
			Entry e = header;
			for (int i = 0; i < size(); i++) {
				e = e.next;
				//sb.append(e.element.toString()).append(", ");
				sb.append(e.element.getTitle()).append(", ");
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
				Entry e = get(index++);
                return e.element;
            }

            @Override
            public void remove() throws IllegalStateException {
                if (removable) {
					LinkedTaskList.this.remove(get(index-1).element);
					removable = false;
				} else throw new IllegalStateException();
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
				Entry element = get(index);
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
	
	public Entry getHeader() {
		return header;
	}
	
	/**
	 * Find entry in task list by specified position. 
	 * @param index
	 * @return entry on particular position
	 */
	private Entry get(int index) throws IndexOutOfBoundsException {
		Entry e = null;
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
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
	private class Entry implements Cloneable {
		
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
		
		@Override
		public Entry clone() throws CloneNotSupportedException {
			return (Entry) super.clone();
		}
	}
}