package ua.edu.sumdu.ta.yaroslavkuts.pr7;

import ua.edu.sumdu.ta.yaroslavkuts.pr7.db.*;
/**
 * Describe the data type "task"("event") in task manager.
 *
 * @version 1.0 12 Oct 2016
 * @author Yaroslav Kuts
 */
public class Task implements Cloneable {
	
	private DBWriter db = MysqlDB.getInstance();
	
	private Integer task_id;
	private String title;
	private boolean active;
	private int time;
	private int start;
	private int end;
	private int repeat;
	private Integer list_id;
	
	/**
	 * Create empty task
	 */
	public Task() {
		super();
		//task_id = db.addTask(this);
	}
	
	/**
	 * Create new non repeatable inactive task.
	 * @param title
	 * @param time
	 */
	public Task(String title, int time) {
		setTitle(title);
		setTime(time);
		//task_id = db.addTask(this);
	}
	
	/**
	 * Create new repeatable inactive task.
	 * @param title
	 * @param start
	 * @param end
	 * @param repeat
	 */
	public Task(String title, int start, int end, int repeat) {
		setTitle(title);
		setTime(start, end, repeat);
		//task_id = db.addTask(this);
	}
	
	/**
	 * Calculate next time of task's execution. 
	 * @param time
	 * @return time in seconds of next task's execution after current time
	 * @throws IllegalArgumentException if time has negative value
	 */
	public int nextTimeAfter(int time) throws IllegalArgumentException {
		if (time < 0) {
			throw new IllegalArgumentException("Time must not be negative");
		}
		
		if (isActive()) {
			if (isRepeated()) {
				if (time < start) return start;
				if (time >= start) {
					int result = (((((time - start)/ repeat) * repeat) + repeat) + start);
				    if (result > end) {
				    	return -1;
				    } else return result;
				} else return -1;
			} else {
				if (time < this.time) return this.time;
				else return -1;
			}
		} else return -1;
	}
	
	/**
	 * Creates and returns a copy of this task. 
	 * @return task with the same fields
	 * @throws CloneNotSupportedException if the object's class don't implements Cloneable
	 */
	@Override
	public Task clone() throws CloneNotSupportedException {
		return (Task) super.clone();
	}
	
	/**
	 * Compare two tasks. 
	 * @param obj task instance
	 * @return true if links on objects are equal or values of objects fields are equal
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		
		if (obj == null) return false;

		if (obj instanceof Task) {
			Task another = (Task) obj;
			if (clean(this.title).equals(clean(another.getTitle())) &&
					this.time == another.getTime() &&
					this.start == another.getStartTime() &&
					this.end == another.getEndTime() &&
					this.repeat == another.getRepeatInterval() &&
					this.active == another.isActive()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		return title.hashCode();
	}
	
	/**
	 * Generate task information.
	 * @return string with task's title and time frame or inactive status
	 */
	@Override
	public String toString() {
		if (isActive() && !isRepeated()) {
			return "Task \"" + title + "\"" + " at " + time;
		}
		if (isActive() && isRepeated()) {
			return "Task \"" + title + "\"" + " from " + start + " to " + end + " every " + repeat + " seconds";
		} else {
			return "Task \"" + title + "\"" + " is inactive";
		}
	}
		
	/**
	 * Delete prefix from task's title. 
	 * @param title
	 * @return title string without prefix
	 */
	private static String clean(String title) {
		if (title.startsWith(ArrayTaskList.PREFIX)) {
			title = title.substring(ArrayTaskList.PREFIX.length() + 1);
		}
		return title;
	}
	
	public Integer getTask_id() {
		return task_id;
	}
	
	public void setTask_id(Integer task_id) {
		this.task_id = task_id;
	}
	
	public String getTitle() {
		return title;
	}
	
	/**
	 * Validate and set task's title.
	 * @param title
	 * @throws IllegalArgumentException if title is null or empty
	 */
	public final void setTitle(String title) throws IllegalArgumentException {
		if (title != null && title != "") {
			this.title = title;
			//task_id = db.modifyTask(this);
		} else {
			throw new IllegalArgumentException("Title cannot be empty or null");
		}
	}
	
	public boolean isActive() {
		return active;
	}
	
	public void setActive(boolean active) {
		this.active = active;
		//task_id = db.modifyTask(this);
	}
	
	public int getTime() {
		return time;
	}
	
	/**
	 * Validate and set time of task.
	 * @param time
	 * @throws IllegalArgumentException if time has negative value
	 */
	public final void setTime(int time) throws IllegalArgumentException {
		if (isRepeated()) {
			repeat = 0;
		}
		
		if (time >= 0) {
			this.time = time;
			start = time;
			end = time;
			//task_id = db.modifyTask(this);
		} else {
			throw new IllegalArgumentException("Time must not be negative");
		}
	}
	
	/**
	 * Validate and set time frame and execution interval of task.
	 * @param start
	 * @param end
	 * @param repeat
	 * @throws IllegalArgumentException if some of repeat, end or start values is not validate
	 */
	public final void setTime(int start, int end, int repeat) throws IllegalArgumentException {
		if (!isRepeated()) {
			time = 0;
		}
		
		if (start >= 0) {
			this.start = start;
			time = start;
			if (end > start) {
				this.end = end;
				if (repeat < (end - start) && repeat > 0) {
					this.repeat = repeat;
					//task_id = db.modifyTask(this);
				} else {
					throw new IllegalArgumentException("Value of 'repeat' is not validate");
				}
			} else {
				throw new IllegalArgumentException("Value of 'end' is not validate");
			}
		} else {
			throw new IllegalArgumentException("Value of 'start' is not validate");
		}
	}
	
	public int getStartTime() {
		return start;
	}
	
	public int getEndTime() {
		return end;
	}
	
	public int getRepeatInterval() {
		return repeat;
	}
	
	public boolean isRepeated() {
		return repeat != 0;
	}
	
	public Integer getList_id() {
		return list_id;
	}
	
	public void setList_id(Integer list_id) {
		this.list_id = list_id;
	}
}