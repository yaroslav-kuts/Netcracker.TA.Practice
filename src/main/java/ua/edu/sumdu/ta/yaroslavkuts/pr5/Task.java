package ua.edu.sumdu.ta.yaroslavkuts.pr5;

import ua.edu.sumdu.ta.yaroslavkuts.pr5.db.*;
/**
 * Describe the data type "task"("event") in task manager.
 *
 * @version 1.0 12 Oct 2016
 * @author Yaroslav Kuts
 */
public class Task {
	
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
	 */
	public int nextTimeAfter(int time) throws IllegalArgumentException {
		if (time < 0) {
			throw new IllegalArgumentException();
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
	
	@Override
	public int hashCode() {
		return title.hashCode();
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
	 */
	public final void setTitle(String title) throws IllegalArgumentException {
		if (title != null && title != "") {
			this.title = title;
			//task_id = db.modifyTask(this);
		} else {
			throw new IllegalArgumentException();
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
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * Validate and set time frame and execution interval of task.
	 * @param start
	 * @param end
	 * @param repeat
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
					//System.out.println("The value of 'repeat' does not validate");
					throw new IllegalArgumentException();
				}
			} else {
				//System.out.println("The value of 'end' does not validate");
				throw new IllegalArgumentException();
			}
		} else {
			//System.out.println("The value of 'start' does not validate");
			throw new IllegalArgumentException();
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