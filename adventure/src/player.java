

import java.util.ArrayList;

/**
 * This class represents a player in the game
 * 
 * @author Tr4730
 *
 */
public class player {

	private ArrayList<item> inventory_ = new ArrayList<item>();// All the items
																// a player is
																// carrying
	private int score_;// player score
	private room location_;// current location of player

	/**
	 * Creates a player object and initializes them in the specified room.
	 * 
	 * @param room
	 *            - room where player will be placed
	 */
	public player(room room) {
		score_ = 0;
		location_ = room;
	}

	/**
	 * Adds an item in the room to player's inventory
	 * 
	 * @param item
	 *            - item being added
	 */
	public void addItem(item item) {
		inventory_.add(item);
	}

	/**
	 * Sets the player's location to the designated room
	 * 
	 * @param room
	 *            - room player will be placed
	 */
	public void setLocation(room room) {
		location_ = room;
	}

	/**
	 * Returns the room the player is in
	 * 
	 * @return room player is in
	 */
	public room getRoom() {
		return location_;
	}

	/**
	 * Returns name of room player is in
	 * 
	 * @return name of current room as string
	 */
	public String getRoomName() {
		System.out.println("");
		return location_.getName();
	}

	/**
	 * Returns the player's score
	 * 
	 * @return an int as the score
	 */
	public int getScore() {
		return score_;
	}

	/**
	 * Returns a string containing all inventory items
	 * 
	 * @return a string with inventory item names
	 */
	public String itemList() {
		if (inventory_.size() == 0) {
			return "No items in your inventory.";
		}
		String list = inventory_.get(0).getName();
		for (int i = 1; i < inventory_.size(); i++) {
			list = list + ", " + inventory_.get(i).getName();
		}
		return ("Your inventory contains a " + list);
	}

	/**
	 * Checks if a specified item is in inventory
	 * 
	 * @param item
	 *            - item to check for
	 * @return true if item is in inventory, false if not
	 */
	public boolean contains(String item) {
		for (int i = 0; i < inventory_.size(); i++) {
			if (inventory_.get(i).getName().equals(item)) {
				return true;
			}
		}
		return false;
	}// end of contains

	/**
	 * Returns a list of the items in player's inventory
	 * 
	 * @return an ArrayList of type item with the items in player's inventory
	 */
	public ArrayList<item> getItems() {
		return inventory_;
	}

	/**
	 * Checks if the specified item is in player's inventory
	 * 
	 * @param item
	 *            - item to check for
	 * @return true if item is in inventory, false if not.
	 */
	public boolean containsItem(String item) {
		for (int i = 0; i < inventory_.size(); i++) {
			if (inventory_.get(i).getName().equals(item)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Removes specified item from inventory
	 * 
	 * @param item
	 *            - item being removed
	 */
	public void removeItem(item item) {
		inventory_.remove(item);
	}

	/**
	 * Changes player's score based on the specified amount
	 * 
	 * @param amount
	 *            - integer representing amount to add or take away from
	 *            player's score
	 */
	public void changeScore(int amount) {
		score_ = score_ + amount;
	}

}
