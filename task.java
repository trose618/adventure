package adventure;

/**
 * This class represents the task a player may be given.
 * 
 * @author Tr4730
 *
 */
public class task {

	private String name_ = "";// name of task
	private String type_ = ""; // type of task E.g drops, visit, pick up
	private int points_ = 0; // points task awards
	private item taskItem_; // item if the task involves one
	private room end_; // room attached to completion of room
	private String description_ = ""; // description of task
	private boolean completed_;// true if task has been completed, false if not.
	private boolean visible_;//

	/**
	 * Creates new task object with its type, points, start, item included and
	 * end. For drop and pick up tasks
	 * 
	 * @param name
	 *            - name of task
	 * @param type
	 *            - type of task. E.g: drop, visit, pick up
	 * @param points
	 *            - points to be awarded upon completion
	 * @param taskItem
	 *            - item related to task
	 * @param end
	 *            - Room related to task goal
	 * @param description
	 *            - description of task
	 */
	public task(String name, String type, int points, item taskItem, room end, String description) {
		type_ = type;
		points_ = points;
		taskItem_ = taskItem;
		end_ = end;
		completed_ = false;
		description_ = description;
		name_ = name;
		visible_ = false;
	}

	/**
	 * Creates new task object that does not need an item. For visit tasks
	 * 
	 * @param name
	 *            - name of task
	 * @param type
	 *            - type of task E.g: drop, visit, pick up
	 * @param points
	 *            - points awarded upon completion
	 * @param end
	 *            - Room related to task goal
	 * @param description
	 *            - description of task
	 */
	public task(String name, String type, int points, room end, String description) {
		type_ = type;
		points_ = points;
		end_ = end;
		completed_ = false;
		description_ = description;
		name_ = name;
		visible_ = false;
	}

	/**
	 * Returns a string containing a description of the task
	 * 
	 * @return description of the task
	 */
	public String display() {
		return description_;
	}

	/**
	 * Returns type of task
	 * 
	 * @return type as string
	 */
	public String getType() {
		return type_;
	}

	/**
	 * Returns item associated with task
	 * 
	 * @return the item itself
	 */
	public item getTaskItem() {
		return taskItem_;
	}

	/**
	 * Returns room related to task goal
	 * 
	 * @return the room itself
	 */
	public room getTaskFinishRoom() {
		return end_;
	}

	/**
	 * Returns the points awarded for completing this task
	 * 
	 * @return an int as the points
	 */
	public int getPoints() {
		return points_;
	}

	/**
	 * Returns name of this task
	 * 
	 * @return name as string
	 */
	public String getName() {
		return name_;
	}

	/**
	 * Marks a task as completed
	 */
	public void setCompleted() {
		completed_ = true;
	}

	/**
	 * Toggle the visibility of this task. true mean visible
	 * 
	 * @param ToF
	 *            - true means task is visible. False means task is not visible
	 */
	public void setVisible(boolean ToF) {
		visible_ = ToF;
	}

	/**
	 * Returns visibility as a boolean, true if visible, false if not.
	 * 
	 * @return true for is visible and false for is not.
	 */
	public boolean getVisibility() {
		return visible_;
	}

	/**
	 * Returns boolean representing completion of task. True if task has been
	 * completed
	 * 
	 * @return true if task has been completed, false if not.
	 */
	public boolean getCompletionStatus() {
		return completed_;
	}
}// end of task class
