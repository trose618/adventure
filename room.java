package adventure;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class represents a custom room
 * 
 * @author Tr4730
 *
 */
public class room {
	private boolean accessibility_;// determines if room is accessible or not
	private boolean explored_;// determines if the room has been
								// explored already
	private ArrayList<item> items_ = new ArrayList<item>();// items in the room
	private String name_ = "";// name of room
	private String description_ = "";// description of room
	private HashMap<String, room> rooms_ = new HashMap<String, room>(4);// holds
																		// adjacent
																		// rooms

	/**
	 * Constructs a room object and initializes it as unexplored
	 * 
	 * @param name
	 *            - name of room
	 * @param description
	 *            - description of room
	 */
	public room(String name, String description) {
		accessibility_ = true;
		explored_ = false;
		name_ = name;
		description_ = description;
	}

	/**
	 * Sets the north exit of a room
	 * 
	 * @param room
	 *            - room to set as exit
	 */
	public void setN(room room) {
		rooms_.put("North", room);

	}

	/**
	 * Sets the south exit of a room
	 * 
	 * @param room
	 *            - room to set as exit
	 */
	public void setS(room room) {
		rooms_.put("South", room);

	}

	/**
	 * Sets the east exit of a room
	 * 
	 * @param room
	 *            - room to set as exit
	 */
	public void setE(room room) {
		rooms_.put("East", room);

	}

	/**
	 * Sets the west exit of a room
	 * 
	 * @param room
	 *            - room to set as exit
	 */
	public void setW(room room) {
		rooms_.put("West", room);

	}

	/**
	 * returns name of room
	 * 
	 * @return name of room as string
	 */
	public String getName() {
		return this.name_;
	}

	/**
	 * Returns description of room and any items it may contain
	 * 
	 * @return description as string
	 */
	public String getDescription() {
		if (this.items_.isEmpty()) {
			return description_;
		}
		return (description_ + "\r\n" + "Contains these items: " + this.itemList());
	}

	/**
	 * Adds an item to the room
	 * 
	 * @param item
	 *            - item to add
	 */
	public void addItem(item item) {
		this.items_.add(item);
	}

	/**
	 * Removes item from room
	 * 
	 * @param item
	 *            - item to remove
	 */
	public void removeItem(item item) {
		items_.remove(item);
	}

	/**
	 * Returns a list of the items in the room
	 * 
	 * @return list of items as a string
	 */
	public String itemList() {
		String list = items_.get(0).getName();
		for (int i = 1; i < items_.size(); i++) {
			list = list + ", " + items_.get(i).getName();
		}
		return list;
	}

	/**
	 * Marks a room as explored
	 */
	public void markExplored() {
		if (explored_ == false) {
			explored_ = true;
		}
	}

	/**
	 * checks if room contains a room in a specified direction
	 * 
	 * @param direction
	 *            - direction to check for room in
	 * @return true if a room exists in that direction. False if not.
	 *         PreCondition: Direction should be either north, south, east, or
	 *         west
	 */
	public boolean contains(String direction) {
		if (rooms_.containsKey(direction)) {
			return true;
		}
		return false;
	}

	/**
	 * if room has a north exit, it returns the north room.
	 * 
	 * @return room in direction specified
	 */
	public room getN() {
		if (this.contains("North")) {
			return rooms_.get("North");
		}
		return null;
	}

	/**
	 * if room has a south exit, it returns that room.
	 * 
	 * @return room in direction specified
	 */
	public room getS() {
		if (this.contains("South")) {
			return rooms_.get("South");
		}
		return null;
	}

	/**
	 * if room has a west exit, it returns that room.
	 * 
	 * @return room in direction specified
	 */
	public room getW() {
		if (this.contains("West")) {
			return rooms_.get("West");
		}
		return null;
	}

	/**
	 * if room has a east exit, it returns that room.
	 * 
	 * @return room in direction specified
	 */
	public room getE() {
		if (this.contains("East")) {
			return rooms_.get("East");
		}
		return null;
	}

	/**
	 * Checks if room contains an item
	 * 
	 * @param item
	 *            - item to check for
	 * @return true if item is present. false if not.
	 */
	public boolean containsItem(String item) {
		for (int i = 0; i < items_.size(); i++) {
			if (items_.get(i).getName().equals(item)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns the list of items
	 * 
	 * @return the items as an ArrayList of type item.
	 */
	public ArrayList<item> getItems() {
		return items_;
	}

	/**
	 * Returns the room's explored status
	 * 
	 * @return true if room has been marked explored, false if not.
	 */
	public boolean isDiscovered() {
		return explored_;
	}

	/**
	 * Return true if door is unlocked and false if it is locked
	 * 
	 * @return Return true if door is unlocked and false if it is locked
	 */
	public boolean lockStatus() {
		return accessibility_;
	}

	/**
	 * if false, door is locked making it unaccessible. true unlocks it
	 * 
	 * @param choice
	 *            true or false
	 */
	public void setAccessible(boolean choice) {
		accessibility_ = choice;
	}

	/**
	 * Changes the description of a room to the desired text.
	 * 
	 * @param text
	 *            - Text to serve as the new description. Typically used when
	 *            changing the accessibility of a room.
	 */
	public void changeDescription(String text) {
		description_ = text;
	}
}// end of room
