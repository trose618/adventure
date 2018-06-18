

/**
 * This class represents a custom item in the game.
 * 
 * @author Tr4730
 *
 */
public class item {

	private String name_;
	private String description_;
	private room location_;

	/**
	 * Constructs an item object
	 * 
	 * @param name
	 *            - name of item
	 * @param description
	 *            - item description
	 */
	public item(String name, String description) {

		name_ = name;
		description_ = description;

	}

	/**
	 * Sets room item is initialized in
	 * 
	 * @param location
	 *            - room item will be located
	 */
	public void setLocation(room location) {

		location_ = location;
	}

	/**
	 * Returns item name
	 * 
	 * @return name of item as a string
	 */
	public String getName() {
		return name_;
	}

	/**
	 * Returns name of room item is located
	 * 
	 * @return returns room item is located as a string
	 */
	public String getLocation() {
		if (location_ != null) {
			return location_.getName();
		} else
			return "No location assigned.";
	}

	/**
	 * Returns description of item
	 * 
	 * @return returns description as string
	 */
	public String getDescription() {
		return description_;
	}

}
