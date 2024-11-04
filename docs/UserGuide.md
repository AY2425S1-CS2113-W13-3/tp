# User Guide

## Introduction

EventManager CLI is a CLI application that allows organisers of small-scale events to track
the participants and logistics of such events.

## Quick Start

1. Ensure that you have Java 17 or above installed.
2. Down the latest version of `EventManagerCLI` from [here](https://github.com/AY2425S1-CS2113-W13-3/tp/releases).
3. Open a new terminal in the folder that you put the JAR file in, and run the program with the command ```java -jar EventManagerCLI.jar```.
The following message would be printed:
```
Welcome to EventManagerCLI.
Enter a command:
```

## Features

### Notes about the command format:

* Words in `UPPER_CASE` represent parameters that are to be supplied by the user.
* Parameters listed have to be entered in the specified order.
* Extraneous parameters for commands that do not take in parameters (e.g. `list`) will be ignored.

### Viewing the command list: `menu`

Shows a list of all valid user commands in the program.

```
Here are the possible commands:
           
list: List events.
view -e EVENT -y TYPE: View the list of participants or items of an event.
add -e EVENT -t TIME -v VENUE -u PRIORITY: Add an event to the event list.
add -p PARTICIPANT -n NUMBER -email EMAIL -e EVENT: Add a participant to an event.
add -m ITEM -e EVENT: Add an item to an event.
remove -e EVENT: Remove an event from the event list.
remove -p PARTICIPANT -e EVENT: Remove a participant from an event.
remove -m ITEM -e EVENT: Remove an item from an event.
edit -e EVENT -name EVENT_NAME -t TIME -v VENUE -u PRIORITY: Edit event info.
edit -m ITEM > NEW_ITEM -e EVENT: Edit an item in an event.
edit -p PARTICIPANT -n NUMBER -email EMAIL -e EVENT: Edit participant contact info.
mark -e EVENT -s STATUS: Mark an event as done or not done.
mark -p PARTICIPANT -e EVENT -s STATUS: Mark a participant as present or absent.
mark -m ITEM -e EVENT -s STATUS: Mark an item as accounted or unaccounted.
copy FROM_EVENT > TO_EVENT: Copies participant list from one event to another.
sort -by KEYWORD: Sorts events by name/time/priority.
filter -e/-t/-u FILTER_DESCRIPTION: Filters events by name/time/priority.
find -e EVENT -p NAME: Finds all participants with specified name in an event.
exit: Exit program

```

Format: `menu`

### List all events: `list`

Shows a list of all events currently stored in the program.

Format: `list`

### Add an event or participant: `add`

Adds an event to the event list, a participant to an event, or an item to an event.

Format:  

* `add -e EVENT -t TIME -v VENUE` for adding an event to the events list.
* `add -p PARTICIPANT -n NUMBER -email EMAIL -e EVENT` for adding a participant to an event.
* `add -m ITEM -e EVENT` for adding an item to an event.

Examples:

* `add -e Origami workshop -t 2024-10-12 18:00 -v Building A` adds an event with name `Origami workshop`, time `2024-10-12 18:00` and venue `Building A` to the events list.
* `add -p John Tan -n 91583215 -email john@gmail.com -e Origami workshop` adds a participant `John Tan` to the event `Origami workshop`.
* `add -m Origami paper -e Origami workshop` adds an item `Origami paper` to the event `Origami workshop`.

### Remove an event or participant: `remove` 

Removes an event from the event list, a participant from an event, or an item from an event.

Format:

* `remove -e EVENT` for removing an event from the event list.
* `remove -p PARTICIPANT -e EVENT` for removing a participant from an event.
* `remove -m ITEM -e EVENT` for removing an item from an event.

Examples:

* `remove -e Origami workshop` removes the event `Origami workshop` from the event list.
* `remove -p John Tan -e Origami workshop` removes the participant `John Tan` from the event `Origami workshop`.
* `remove -m Origami paper -e Origami workshop` removes the item `Origami paper` from the event `Origami workshop`.

### Edit the information of an event or a participant: `edit`

Edit the information of an event/participant/item.

Format:

* `edit -e EVENT -name EVENT_NAME -t TIME -v VENUE -u PRIORITY` for editing an event's basic information.
* `edit -p PARTICIPANT -n NUMBER -email EMAIL -e EVENT` for editing a participant's contact information in an event.
* `edit -m ITEM > NEW_ITEM -e EVENT` for editing an item's information in an event.

Examples:

* `edit -p Mary -n 9182 3213 -email mary@gmail.com -e CS2113`
* `edit -e CS2113 -name CS2113T -t 2024-10-25 16:00 -v LT16 -u HIGH`
* `edit -m balloon > cake -e anniversary`

### View all participants or items for an event: `view`

Shows a list of all participants or items for an event.

Format: `view -e EVENT -y TYPE`

* The list shown varies based on the value of `TYPE` entered, as follows:
  * A list of participants is shown when `TYPE` is `participant`.
  * A list of items is shown when `TYPE` is `item`.
* `TYPE` is case-insensitive, i.e. `PARTICIPANT` and `participant` will produce the same result.

Examples:

* `view -e Origami workshop -y participant` shows a list of all participants for the event `Origami workshop`.
* `view -e Origami workshop -y item` shows a list of all items for the event `Origami workshop`. 

### Mark an event as done: `mark`

Marks an event in the event list as done or not done.

Format: `mark -e EVENT -s STATUS`

* The status parameter must be either `done` (to mark done) or `undone` (to mark not done).

Examples:

* `mark -e Origami workshop -s done` marks the event `Origami workshop` as done.
* `mark -e Origami workshop -s undone` marks the event `Origami workshop` as not done.

### Marks a participant as present: `mark`

Marks a participant for an event as present or absent.

Format: `mark -p PARTICIPANT -e EVENT -s STATUS`

* The status parameter must be either `present` (to mark present) or `absent` (to mark absent).

Examples:

* `mark -p John Tan -e Origami workshop -s done` marks the participant `John Tan` in the `Origami workshop` event as present.
* `mark -p John Tan -e Origami workshop -s undone` marks the participant `John Tan` in the `Origami workshop` event as absent.

### Marks an item as accounted for: `mark`

Marks an item for an event as accounted for or unaccounted for.

Format: `mark -m ITEM -e EVENT -s STATUS`

* The status parameter must be either `accounted` (to mark accounted) or `unaccounted` (to mark unaccounted).

Examples:

* `mark -m Origami paper -e Origami workshop -s done` marks the item `Origami paper` in the `Origami workshop` event as accounted.
* `mark -m Origami paper -e Origami workshop -s undone` marks the item `Origami paper` in the `Origami workshop` event as unaccounted.

### Copies participant list: `copy`

Copies the participant list from one event to another event.

Format: `copy FROM_EVENT > TO_EVENT`

* Both events must already exist

Examples:

* `copy Origami workshop > Coding workshop` copies the participant list from the `Origami workshop` event to the `Coding workshop` event.

### Sorts event list: `sort`

Sorts events according to event name, event date-time or event priority level.

Format: `sort -by KEYWORD`

* `KEYWORD` is case-insensitive, but must be one of these inputs: `name/time/priority`

Examples:
* `sort -by name` will output a list of all the user's events alphabetically (from A to Z).
* `sort -by time` will output a list of all the user's events chronologically (earliest to latest).
* `sort -by priority` will output a list of all the user's events in terms of urgency (most to least important).

### Filters event list: `filter`

Filters out events from the event list based on name, date-time or priority level.

Format: `filter -e/-t/-u DESCRIPTION`

* `-e/-t/-u` are the flags for name, date-time and priority level respectively.
* `DESCRIPTION` is only case-insensitive when filtering using the priority level flag.

Examples:
* `filter -e workshop` will output all events with `workshop` in their event name.
* `filter -t 2024` will output all events that are occurring in `2024`.
* `filter -u high` with output all events with priority level `HIGH`.

### Find participant: `find`

Finds all participants with a specified name in a specified event.

Format: `find -e EVENT -p NAME`

* The `NAME` keywords are case-insensitive.

Examples:
* `find -e Origami workshop -p john` will output all participants with `john` in their name in the event `Origami workshop`.

### Exiting the program: `exit`

Exits the program.

Format: `exit`

### Saving of program data

The program saves its stored data into three `.csv` files in the same directory as the application `.JAR`.

These files are as follows:

* `events.csv`, which stores data on the `Event`s stored in the program,
* `participants.csv`, which stores data on the `Participant`s for all `Event`s,
* `items.csv`, which stores data on the `Item`s for all `Event`s,

The data is saved after the execution of every command and upon program startup.

### Loading of program data

The saved program data in the aforementioned three files is loaded into the program upon program startup.

If any one of the files do not exist in the directory, the file will be created when the data is saved.

### Editing of saved program data

Experienced users may feel free to edit the saved data in the `.csv` files.

The program data is stored in the following format:

For `events.csv`:

```
EVENT,TIME,VENUE,PRIORITY,STATUS
```

* `TIME` must be in the format `yyyy-mm-dd hh:mm`.
* `PRIORITY` must be either `HIGH`, `MEDIUM`, or `LOW`.
* `STATUS` must be either `Y` or `N`

For `participants.csv`:

```
PARTICIPANT,NUMBER,EMAIL,STATUS,EVENT
```

* `STATUS` must be either `Y` or `N`
* An entry for the `Event` corresponding to `EVENT` must be present in `events.csv`.

For `items.csv`:

```
ITEM,STATUS,EVENT
```

* `STATUS` must be either `Y` or `N`
* An entry for the `Event` corresponding to `EVENT` must be present in `events.csv`.

All fields added to the `.csv` files must also be enclosed within double quotation marks (`" "`) to be properly parsed.

If the above format or parameter constraints are not followed, the `Event`, `Participant` or `Item` corresponding to the file line will not be loaded upon program startup.

## Command Summary

* List all events: `list`
* View all participants for an event: `view -e EVENT`
* Add event: `add -e EVENT -t TIME -v VENUE`
* Add participant to an event: `add -p PARTICIPANT -e EVENT`
* Add item to an event: `add -m ITEM -e EVENT`
* Remove event: `remove -e EVENT`
* Remove participant from an event: `remove -p PARTICIPANT -e EVENT`
* Remove item from an event: `remove -m ITEM -e EVENT`
* View all participants for an event: `view -e EVENT -y TYPE`
* Edit participant of an event: `edit -p PARTICIPANT -n NUMBER -email EMAIL -e EVENT`
* Mark an event as done: `mark -e EVENT -s STATUS`
* Mark a participant as present: `mark -p PARTICIPANT -e EVENT -s STATUS`
* Mark an item as accounted for: `mark -m ITEM -e EVENT -s STATUS`
* Copy participant list: `copy FROM_EVENT > TO_EVENT`
* Sort events: `sort -by KEYWORD`
* Filter events: `filter -e/-t/-u FILTER_DESCRIPTION`
* Find participants: `find -e EVENT -p NAME`
* Exit program: `exit`
