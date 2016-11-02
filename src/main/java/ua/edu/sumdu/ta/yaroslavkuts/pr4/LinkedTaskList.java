package ua.edu.sumdu.ta.yaroslavkuts.pr4;

public class LinkedTaskList extends AbstractTaskList {
	
	private Entry header;
	
	public LinkedTaskList() {
		size = 0;
		header = new Entry();
	}
	
	@Override
	public void add(Task task) {
		if (task != null) {
			Entry entry = new Entry(task, header, header.prev);
			entry.prev.next = entry;
			entry.next.prev = entry;
			size++;
		} else System.out.println("Can not add 'null'");
	}
	
	public void add(int index, Task task) {
		if (index == size) {
			add(task);
		} else {
			if (task != null) {
				Entry element = findEntry(index);
				Entry entry = new Entry(task, element, element.prev);
				entry.prev.next = entry;
				entry.next.prev = entry;
				size++;
			} else System.out.println("Can not add 'null'");
		}
	}
	
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
	
	@Override
	public void remove(Task task) {
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
		} else System.out.println("Can not remove 'null'");
	}
	
	@Override
	public Task getTask(int index) {
		return findEntry(index).element;
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
	
	/*public void printList() {
		Entry e = header;
		for (int i = 0; i < size; i++) {
			e = e.next;
			System.out.println(e.element.getTitle());
		}
	}*/
	
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