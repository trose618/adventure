# adventure
README

This game should be ran in the terminal.

Hello, my name is Terrance Rose, and this is a program created to 
demonstrate how one may manage to show up late to school when they live across the street from it. You get to play as a character called Bobby.

Bobby typically gets up around 8:15 if his mom left too early to early to wake him up. His dad always leaves at 5:30AM. He needs to be walking out the door by 8:25 or he will be late...as usual.

Alrighty! Let's start you off in Bobby's room. It's one of those days where his mom didn't wake him up...or maybe he just fell back asleep, but that means you have 15 minutes to get him ready and out the door.

Time starts once you start the program, so make sure you know how to play first!

How to play
You travel from room to room completing different tasks.


Controls:  
To walk, type either "north", "south", "east" or "west".

To pick up an item, type "get" or "take". On the next option menu, type the name
of the item you wish to take.

To drop an item, type "drop", on next menu, type name of item.

To observe a room, type "look".

To observe a specific item in a room or inventory, type "examine item", on
next menu type the name of the item.

To all items you are holding, type "inventory".

To see your score, type "score".

To see current task, type "tasks". Note the second 's' in tasks.

To quit, type "quit" and confirm with "yes".

To display this list in game, type "help".



FOR CUSTOMIZING YOUR OWN GAME

File Set Up -
IMPORTANT for room files - No lines can have commas in them
as commas are used on the second line to separate adjacent rooms.
Make sure when creating your own files that names are spelled EXACTLY
the same, down to the spaces. A missed space can throw the program off
completely, same with capitalization. 

Separating multiple rooms/objects/tasks
After writing the lines for a particular data type, the last line
should strictly say END

file set up for - rooms
6 lines - don't include line # E.g: line 1 - name, would just be name
line 1 - name
line 2 - adjacent rooms
line 3 - room accessibility ("locked" or "unlocked") 
line 4 - description of room (if locked then description of what to 
display when trying to access room)
line 5 - description continued if need be and etc for longer description.
last line - END

for adjacent rooms, say for example north, south, east, and west are the rooms in the corresponding directions from the one you are in.  They would be entered as: north, south, east, west  with a - meaning no room in that direction. So if there were no west and north, it would look like:

-, south, east, -



file set up - objects
line 1 - name
line 2 - room object is placed
line 3 - description
line 4 - END



file set up - tasks - scoring
line 1 - name of task
line 2 - type - E.g: pick up, drop, visit - if visit, then line 4 should say "None"
line 3 - points awarded - E.g: 50, 100
line 4 - Name of item as appears on objects list.("None" if task type is visit.)
line 5 - Name of room where quest is completed.
line 6 - Description
line 7 - END
