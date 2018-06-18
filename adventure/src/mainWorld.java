

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * 
 * @author Tr4730 Main class that constructs the world and holds the game
 *         engine.
 *
 */
public class mainWorld extends Thread {

	@Override
	/**
	 * This contains a timer and ends the game if the player has not won before
	 * timer runs out.
	 */
	public void run() {
		// Timer
		for (int i = 900; i > 0; i--) {
			// 15 minutes
			if (i == 900) {
				System.out.println("You have 15 minutes...");
			}
			if (i == 600) {
				System.out.println("You have 10 minutes...");
			}
			if (i == 300) {
				System.out.println("You have 5 minutes! Hope you're almost ready.");
			}
			if (i == 150) {
				System.out.println("You have 2 and a half minutes left. Calling it close, as I typically do.");
			}
			if (i == 60) {
				System.out.println("Last minute! Get Out!");
			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		gameOver();// ends game
	}

	/**
	 * Runs the game
	 * 
	 * @param args
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws IOException, InterruptedException {

		Scanner user = new Scanner(System.in);

		ArrayList<room> world = new ArrayList<room>();// holds the rooms

		ArrayList<String[]> exits = new ArrayList<String[]>();// holds adjacent
																// rooms

		ArrayList<item> objects = new ArrayList<item>();// holds the objects

		ArrayList<task> tasks = new ArrayList<task>();// holds the tasks

		String source = "apt4A";// room file
		String source2 = "4Aobjects";// object file
		String source3 = "tasks";// task file

		File data = new File(source);
		File data2 = new File(source2);
		File data3 = new File(source3);

		// set up the world: rooms with objects and tasks
		setUp(data, world);
		setUp2(data, exits, world);
		objSetUp(data2, objects, world);
		taskSetup(tasks, data3, world, objects);

		// initialize player
		player you = new player(world.get(0));// placing player in first
												// room on the list
		you.getRoom().markExplored();// set start room to explored

		// Timer instance created to time the game
		mainWorld world1 = new mainWorld();
		world1.start();

		System.out.println("Welcome to a day in...well, a morning in the life of Terrance Rose Jr.");
		System.out.println("For a list of instructions, type help.");
		System.out.println("You are in the " + you.getRoomName());

		// game play
		Boolean playing = true;
		while (playing) {
			System.out.println("What do you want to do?");
			String command = user.nextLine();
			String item = "";
			// moving
			if (command.toLowerCase().equals("north") || (command.toLowerCase().equals("south")
					|| (command.toLowerCase().equals("east") || (command.toLowerCase().equals("west"))))) {
				move(you, command, world);
				System.out.println("You are in " + you.getRoomName() + " ");
				checkObjectives(tasks, you, world, command, item);
			} // end of moving

			// look
			if (command.toLowerCase().equals("look")) {
				System.out.println(you.getRoom().getDescription());
				checkObjectives(tasks, you, world, command, item);
			} // end of look

			// pick up item
			if (command.toLowerCase().equals("take") || command.toLowerCase().equals("get")) {
				if (you.getRoom().getItems().isEmpty()) {
					System.out.println("There are no items to pick up.");
				} else {
					System.out.println("What would you like to pick up? " + you.getRoom().itemList());
					item = user.nextLine().toLowerCase();
					if (!you.getRoom().containsItem(item)) {
						System.out.println("That is not an item in this room.");
					} else {
						you.addItem(matchItem(you.getRoom().getItems(), item));
						you.getRoom().removeItem(matchItem(you.getRoom().getItems(), item));
						System.out.println("You picked up the " + item + ". It is now in your inventory.");
					}
				}

				checkObjectives(tasks, you, world, command, item);
			} // end of pick up item

			// drop item
			if (command.toLowerCase().equals("drop")) {
				item = "";
				if (you.getItems().size() == 0) {
					System.out.println("No items in inventory to drop.");
				} else {
					System.out.println("What item would you like to drop? " + you.itemList());
					item = user.nextLine().toLowerCase();
					if (!you.containsItem(item)) {
						System.out.println("That item is not in your inventory");
					} else {
						you.getRoom().addItem(matchItem(you.getItems(), item));
						you.removeItem(matchItem(you.getItems(), item));
						System.out.println("You dropped the " + item);
					}
				}
				checkObjectives(tasks, you, world, command, item);
			} // end of drop item

			// look at object
			if (command.toLowerCase().equals("examine item")) {
				System.out.println("What item would you like to examine?");
				item = user.nextLine().toLowerCase();
				if (you.contains(item)) {
					System.out.print(matchItem(you.getItems(), item).getDescription());
				} else if (you.getRoom().containsItem(item)) {
					System.out.println(matchItem(you.getRoom().getItems(), item).getDescription());
				} else {
					System.out.println("There is no item in your inventory or the current room with that name.");
				}
				checkObjectives(tasks, you, world, command, item);
			}

			// look at inventory
			if (command.toLowerCase().equals("inventory")) {
				System.out.println(you.itemList());
			}
			// look at score
			if (command.toLowerCase().equals("score")) {
				System.out.println("Your current score is " + you.getScore());
			} // end of score

			// showTasks
			if (command.toLowerCase().equals("tasks")) {
				showTasks(tasks);
			}

			// quitting
			if (command.toLowerCase().equals("quit")) {
				System.out.println("Are you sure you want to quit?");
				String answer = user.nextLine().toLowerCase();
				if (answer.equals("yes")) {
					System.out.println("Thank you for playing. Score: " + you.getScore());
					playing = false;
					user.close();
					System.exit(0);
				}
				if (answer.equals("no")) {

				}
			} // end of quitting

			// help
			if (command.toLowerCase().equals("help")) {
				System.out.println("To walk, type either north, south, east or west.");
				System.out.println("To pick up an item, type get or take. On the next option menu," + "\r\n"
						+ "type the name of the item you wish to take.");
				System.out.println("To drop an item, type drop, on next menu, type name of item.");
				System.out.println("To observe a room, type look.");
				System.out.println("To observe a specific item in a room or inventory, " + "\r\n"
						+ "type examine item, on next menu type the name of the item.");
				System.out.println("To all items you are holding, type inventory.");
				System.out.println("To see your score, type score.");
				System.out.println("To see current task, type tasks.");
				System.out.println("To quit, type quit and confirm with yes.");
				System.out.println("To see this message again, type help.");
			}

		} // end of while loop to prompt moves

	}// end of main

	// methods
	/**
	 * Creates the items in the designated file and places them in respective
	 * rooms. Rooms should already be created.
	 * 
	 * @param data2
	 *            File being read from
	 * @param objects
	 *            List to hold all the items
	 * @param world
	 *            the rooms items will be located in
	 * @throws IOException
	 */
	public static void objSetUp(File data2, ArrayList<item> objects, ArrayList<room> world) throws IOException {
		BufferedReader input;
		BufferedWriter output;

		if (data2.exists()) {
			input = new BufferedReader(new FileReader(data2));
			output = new BufferedWriter(new OutputStreamWriter(System.out));
		} else {
			throw new FileNotFoundException("No file name found matching that input. Got " + data2);
		}

		String name = "";
		String description = "";
		String roomLoc = "";
		String placer = input.readLine();

		for (; placer != null;) {
			name = placer.toLowerCase();
			for (String current = placer; current != null; current = input.readLine()) {
				if (current.equals("END")) {
					placer = input.readLine();
					roomLoc = "";
					description = "";
					break;
				}
				if (roomLoc.equals("")) {
					current = input.readLine();
					roomLoc = current;
				}
				current = input.readLine();
				description = description + "\r\n" + current + " ";

				output.flush();
				item newitem = new item(name, description);
				newitem.setLocation(matchRoom(world, roomLoc));
				if (roomLoc.equals("None")) {
					objects.add(newitem);
				} else {
					matchRoom(world, roomLoc).addItem(newitem);
					objects.add(newitem);
				}
			}
		}
		input.close();
	}// end of objSetUp

	/**
	 * Description: Creates all the rooms without their adjacent rooms
	 * 
	 * @param data
	 *            contains rooms and info
	 * @param world
	 *            holds the rooms once they have been created
	 * @throws IOException
	 */
	@SuppressWarnings("resource")
	public static void setUp(File data, ArrayList<room> world) throws IOException {
		BufferedReader input;
		BufferedWriter output;

		if (data.exists()) {
			input = new BufferedReader(new FileReader(data));
			output = new BufferedWriter(new OutputStreamWriter(System.out));
		} else {
			throw new FileNotFoundException("No file name found matching that input. Got " + data);
		}

		String name = "";
		String description = "";
		String placer = input.readLine();
		String access = "";
		// outer loop keeps track of where you are in file
		for (; placer != null;) {
			name = placer;
			description = "";
			access = "";
			for (String current = placer; current != null;) {
				current = input.readLine();

				if (current.equals("END")) {
					placer = input.readLine();
					break;
				}
				if (current.contains(",")) {
					// skip to next line
					current = input.readLine();
				}
				if (access.equals("")) {
					access = current;
					current = input.readLine();
				}

				description = description + current + "\r\n";

			}
			output.flush();
			room newRoom = new room(name, description);
			if (access.equals("locked")) {
				newRoom.setAccessible(false);
			}
			world.add(newRoom);

		}
	}// end of setUp

	/**
	 * Description: Sets up the adjacent rooms for the rooms already created.
	 * Rooms must be set up already
	 * 
	 * @param data
	 *            data containing room information
	 * @param exits
	 *            holds the name of each room that is adjacent/exit
	 * @throws IOException
	 */
	public static void setUp2(File data, ArrayList<String[]> exits, ArrayList<room> world) throws IOException {
		BufferedReader input;

		if (data.exists()) {
			input = new BufferedReader(new FileReader(data));
		} else {
			throw new FileNotFoundException("No file name found matching that input. Got " + data);
		}
		// reading the file for the adjacent rooms
		for (String current = input.readLine(); current != null; current = input.readLine()) {

			if (!current.contains(",")) {
				// do nothing
			} else {
				String adjacent = current;
				String[] temp = adjacent.split(",");
				exits.add(temp);
			}
		}

		// traverses through adjacent room list
		for (int i = 0; i < exits.size(); i++) {
			// stores current array being checked
			String[] roomList = exits.get(i);
			for (int a = 0; a < roomList.length;) {
				if (roomList[a].contains("-")) {
					a = a + 1;
				} else {
					world.get(i).setN(matchRoom(world, roomList[a]));
					a = a + 1;
				}
				if (roomList[a] == null) {
					break;

				}

				if (roomList[a].contains("-")) {
					a = a + 1;
				} else {
					world.get(i).setS(matchRoom(world, roomList[a]));
					a = a + 1;
				}

				if (roomList[a] == null) {
					break;
				}
				if (roomList[a].contains("-")) {
					a = a + 1;
				} else {
					world.get(i).setE(matchRoom(world, roomList[a]));
					a = a + 1;
				}
				if (roomList[a] == null) {
					break;
				}
				if (roomList[a].contains("-")) {
					a = a + 1;
				} else {
					world.get(i).setW(matchRoom(world, roomList[a]));
					a = a + 1;
				}
			}
		}
		input.close();
	}// end of setUp2

	/**
	 * Returns room in a list of rooms that matches the given name and prints
	 * out a message if no match is found
	 * 
	 * @param rooms
	 *            - the list of rooms
	 * @param name
	 *            - the name to check for
	 * @return the room that matches the given name. Return null of no matching
	 *         room is found
	 */
	private static room matchRoom(ArrayList<room> rooms, String name) {

		for (int i = 0; i < rooms.size(); i++) {
			if (rooms.get(i).getName().equals(name)) {
				return rooms.get(i);
			}
		}
		System.out.println("No room found with the name " + name);
		return null;
	}// end of matchRoom

	/**
	 * Sets up the tasks for the game. Rooms and objects should already exist!
	 * 
	 * @param tasks
	 *            - list to store each task as they are created
	 * @param data3
	 *            - file containing tasks
	 * @param world
	 *            - room list for assigning tasks
	 * @param objects
	 *            - object list for assigning task
	 * @throws IOException
	 */
	private static void taskSetup(ArrayList<task> tasks, File data3, ArrayList<room> world, ArrayList<item> objects)
			throws IOException {
		BufferedReader input;
		BufferedWriter output;

		if (data3.exists()) {
			input = new BufferedReader(new FileReader(data3));
			output = new BufferedWriter(new OutputStreamWriter(System.out));
		} else {
			throw new FileNotFoundException("No file name found matching that input. Got " + data3);
		}
		String name = "";
		String placer = input.readLine();// line 1
		String description = "";
		String type = "";
		String destination = "";
		String item = "";

		int points = 0;
		for (; placer != null;) {
			name = placer.toLowerCase();// line 1
			for (String current = placer; current != null;) {

				current = input.readLine();// move to line 2
				type = current;// line 2
				current = input.readLine();// move to line 3
				points = Integer.parseInt(current);// line 3
				current = input.readLine().toLowerCase();// move to line 4
				// line 4
				if (current.equals("none")) {
					// do nothing
				} else if (!current.equals("none")) {
					item = current;
				} // end of line 4
				current = input.readLine();// move to line 5
				if (current.toLowerCase().equals("none")) {
					// do nothing
				} else {
					destination = current;
				} // line 5
				current = input.readLine();// move to line 6
				description = current;// line 6
				current = input.readLine();
				output.flush();
				if (type.equals("visit")) {
					task newtask = new task(name, type, points, matchRoom(world, destination), description);
					newtask.setVisible(false);
					tasks.add(newtask);
				} else if (type.equals("drop")) {
					task newtask = new task(name, type, points, matchItem(objects, item), matchRoom(world, destination),
							description);
					newtask.setVisible(false);
					tasks.add(newtask);
				} else if (type.equals("pick up")) {
					task newtask = new task(name, type, points, matchItem(objects, item), matchRoom(world, destination),
							description);
					newtask.setVisible(false);
					tasks.add(newtask);
				}
				if (current.equals("null")) {
					break;
				} else if (current.equals("END")) {
					placer = input.readLine();
					description = "";
					break;
				}

			}
		}
		input.close();
	}// end of taskSetUp

	/**
	 * Description: Moves the player to a room in the designated direction if
	 * possible
	 * 
	 * @param player
	 *            - player being moved
	 * @param direction
	 *            - direction for player to move N, S, E, W
	 * @param world
	 *            - List of existing rooms
	 */
	private static void move(player player, String direction, ArrayList<room> world) {
		if ((direction.equals("north") || direction.equals("forward")) && (player.getRoom().contains("North"))) {

			if (player.getRoom().getN().lockStatus() == false) {
				System.out.println(player.getRoom().getN().getDescription());
			} else {
				player.setLocation(player.getRoom().getN());
				if (player.getRoom().isDiscovered() == false) {
					player.changeScore(25);
					player.getRoom().markExplored();
				}
			}
		} else if ((direction.equals("south") || direction.equals("back")) && (player.getRoom().contains("South"))) {

			if (player.getRoom().getS().lockStatus() == false) {
				System.out.println(player.getRoom().getS().getDescription());
			} else {
				player.setLocation(player.getRoom().getS());
				if (player.getRoom().isDiscovered() == false) {
					player.changeScore(25);
					player.getRoom().markExplored();
				}
			}
		} else if ((direction.equals("west") || direction.equals("left")) && (player.getRoom().contains("West"))) {

			if (player.getRoom().getW().lockStatus() == false) {
				System.out.println(player.getRoom().getW().getDescription());
			} else {
				player.setLocation(player.getRoom().getW());
				if (player.getRoom().isDiscovered() == false) {
					player.changeScore(25);
					player.getRoom().markExplored();
				}
			}
		} else if ((direction.equals("east") || direction.equals("right")) && (player.getRoom().contains("East"))) {

			if (player.getRoom().getE().lockStatus() == false) {
				System.out.println(player.getRoom().getE().getDescription());
			} else {
				player.setLocation(player.getRoom().getE());
				if (player.getRoom().isDiscovered() == false) {
					player.changeScore(25);
					player.getRoom().markExplored();
				}
			}
		} else {
			System.out.println("That's a wall...Let's try not to walk into those too much.");
		}
	}// end of move

	/**
	 * Description: Finds item in list with name matching the given string and
	 * prints a message if an item is not found.
	 * 
	 * @param itemList
	 *            - list of items to search
	 * @param name
	 *            - name of item
	 * @return - item with matching name. Null if one is not found
	 */
	private static item matchItem(ArrayList<item> itemList, String name) {

		for (int i = 0; i < itemList.size(); i++) {
			if (itemList.get(i).getName().equals(name)) {
				return itemList.get(i);
			}
		}
		System.out.println("No item with the name: " + name + " found.");
		return null;
	}// end of matchItem

	/**
	 * Shows one task at a time in order and if the task is visible.
	 * 
	 * @param tasks
	 *            Task list
	 */
	public static void showTasks(ArrayList<task> tasks) {
		if (tasks.get(0).getCompletionStatus() == false) {
			tasks.get(0).setVisible(true);
		}
		for (int i = 0; i < tasks.size(); i++) {
			if (tasks.get(i).getVisibility() == true) {
				if (tasks.get(i).getType().equals("visit")) {
					System.out.println(tasks.get(i).display() + " Task type: " + tasks.get(i).getType() + " - "
							+ "Room: " + tasks.get(i).getTaskFinishRoom().getName() + ". ");
				} else {
					System.out.println(tasks.get(i).display() + " Task type: " + tasks.get(i).getType() + " - "
							+ "Item: " + tasks.get(i).getTaskItem().getName() + ". ");
				}
			}
		}
	}

	/**
	 * goes through each task in list of tasks and checks to see if game state
	 * matches any objectives
	 * 
	 * @param tasks
	 *            - list holding the tasks
	 */
	public static void checkObjectives(ArrayList<task> tasks, player player, ArrayList<room> world, String command,
			String item) {
		if (tasks.get(0).getCompletionStatus() == false) {
			tasks.get(0).setVisible(true);
		}
		for (int i = 0; i <= tasks.size() - 1; i++) {

			if (tasks.get(i).getType().toLowerCase().equals("drop")) {
				if (tasks.get(i).getVisibility() == true) {
					if (tasks.get(i).getTaskFinishRoom().containsItem(tasks.get(i).getTaskItem().getName())
							&& tasks.get(i).getCompletionStatus() == false) {
						System.out.println("You have completed the task " + tasks.get(i).getName() + "!");
						System.out.println("Points awarded: " + tasks.get(i).getPoints());
						player.changeScore(tasks.get(i).getPoints());
						tasks.get(i).setCompleted();
						tasks.get(i).setVisible(false);
						if (i == tasks.size() - 1) {
							break;
						} else {
							System.out.println("New task unlocked");
							tasks.get(i + 1).setVisible(true);
						}
					}
				}
			} else if (tasks.get(i).getType().toLowerCase().equals("visit")) {
				if (tasks.get(i).getVisibility() == true) {
					if (player.getRoom() == tasks.get(i).getTaskFinishRoom()) {
						System.out.println("You have completed the task " + tasks.get(i).getName() + "!");
						System.out.println("Points awarded: " + tasks.get(i).getPoints());
						player.changeScore(tasks.get(i).getPoints());
						tasks.get(i).setCompleted();
						tasks.get(i).setVisible(false);
						if (i == tasks.size() - 1) {
							break;
						} else {
							System.out.println("New task unlocked");
							tasks.get(i + 1).setVisible(true);
						}
					}
				}

			} else if (tasks.get(i).getType().toLowerCase().equals("pick up")) {
				if (tasks.get(i).getVisibility() == true) {
					if (command.toLowerCase().equals("take") || (command.toLowerCase().equals("get"))) {
						if (item.equals(tasks.get(i).getTaskItem().getName())
								&& player.getRoomName().equals(tasks.get(i).getTaskFinishRoom().getName()) == true) {
							System.out.println("You have completed the task " + tasks.get(i).getName() + "!");
							System.out.println("Points awarded: " + tasks.get(i).getPoints());
							player.changeScore(tasks.get(i).getPoints());
							tasks.get(i).setCompleted();
							tasks.get(i).setVisible(false);
							if (i == tasks.size() - 1) {
								break;
							} else {
								System.out.println("New task unlocked");
								tasks.get(i + 1).setVisible(true);
							}
						}
					}
				}
			}
		}
		// victory condition to be set to the desired condition. Set to
		// finishing last task in task list
		if (tasks.get(tasks.size() - 1).getCompletionStatus() == true) {
			System.out.println("Well, looks like you managed to get me out in time. Great job!" + " Total score: "
					+ player.getScore());
			System.exit(0);
		}
	}// end of checkObjectives

	/**
	 * Closes the game
	 */
	private static void gameOver() {
		System.out.println("Well, Looks like I'm late once again. Good try though. I say give it another shot.");
		System.exit(0);
	}
}// end of mainWorld