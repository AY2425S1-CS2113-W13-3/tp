# User Guide

* [Introduction](#introduction)
* [Quick Start](#quick-start)
* [Features](#features)
  * [Notes about the command format](#notes-about-the-command-format)
  * [Viewing the command list: `menu`](#viewing-the-command-list-menu)
  * [Listing all events: `list`](#listing-all-events-list)
  * [Adding an event, participant or item: `add`](#adding-an-event-participant-or-item-add)
  * [Removing an event, participant or item: `remove`](#removing-an-event-participant-or-item-remove-)
  * [Viewing all participants or items for an event: `view`](#viewing-all-participants-or-items-for-an-event-view)
  * [Editing the information of an event, participant or item: `edit`](#editing-the-information-of-an-event-participant-or-item-edit)
  * [Marking events, participants, or items: `mark`](#marking-events-participants-or-items-mark)
  * [Copying the participant list: `copy`](#copying-the-participant-list-copy)
  * [Sorting the event list: `sort`](#sorting-the-event-list-sort)
  * [Filtering the event list: `filter`](#filtering-the-event-list-filter)
  * [Finding a participant: `find`](#finding-a-participant-find)
  * [Exiting the program: `exit`](#exiting-the-program-exit)
  * [Saving of program data](#saving-of-program-data)
  * [Loading of program data](#loading-of-program-data)
  * [Editing of saved program data](#editing-of-saved-program-data)
* [Command Summary](#command-summary)
<div style="page-break-after: always;"></div>

## Introduction

EventManagerCLI is a CLI application that allows organisers of small-scale events to track
the participants and logistics of such events.

## Quick Start

1. Ensure that you have Java 17 or above installed.
2. Download the latest version of `EventManagerCLI` from [here](https://github.com/AY2425S1-CS2113-W13-3/tp/releases).
3. Open a new terminal in the folder that you put the JAR file in, and run the program with the command ```java -jar manager.jar```.
The following message would be printed:
```
Welcome to EventManagerCLI.
Enter a command:
```

## Features

### Notes about the command format:

* Words in `UPPER_CASE` represent parameters that are to be supplied by the user (unless otherwise specified).
* Parameters listed have to be entered in the specified order.
  * e.g. `add -e Origami workshop -v Building A -t 2024-10-12 18:00 -u HIGH` will be interpreted as an invalid command.
* Extraneous parameters for commands that do not take in parameters (e.g. `list`) will be ignored.
  * e.g. `list 1234` and `list -e event` will be interpreted by the program as `list`.
* Only ASCII characters will be accepted as valid input. Any non-ASCII characters in the input will result in an error.
<div style="page-break-after: always;"></div>

### Viewing the command list: `menu`

Shows a list of all valid user commands in the program.

```
Here are all the possible commands:
           
menu: Displays a list of all commands.
list: Displays a list of all events.
add -e EVENT -t TIME -v VENUE -u PRIORITY: Adds an event to the event list.
add -p PARTICIPANT -email EMAIL -e EVENT: Adds a participant to an event.
add -m ITEM -e EVENT: Adds an item to an event.
remove -e EVENT: Removes an event from the event list.
remove -p PARTICIPANT -e EVENT: Removes a participant from an event.
remove -m ITEM -e EVENT: Removes an item from an event.
edit -e OLD_EVENT -name NEW_EVENT -t TIME -v VENUE -u PRIORITY: Edits an event's info.
edit -p OLD_PARTICIPANT -name NEW_PARTICIPANT -email EMAIL -e EVENT: Edits a participant's info.
edit -m OLD_ITEM > NEW_ITEM -e EVENT: Edits an item's info.
view -e EVENT -y TYPE: Displays the list of participants or items of an event.
mark -e EVENT -s STATUS: Marks an event as done or not done.
mark -p PARTICIPANT -e EVENT -s STATUS: Marks a participant as present or absent.
mark -m ITEM -e EVENT -s STATUS: Marks an item as accounted or unaccounted for.
copy FROM_EVENT > TO_EVENT: Copies the participant list from one event to another.
sort -by KEYWORD: Sorts events by name/time/priority.
filter -e/-d/-t/-x/-u FILTER_DESCRIPTION: Filters events by name/date/time/date-time/priority.
find -e EVENT -p NAME: Finds all participants with a specified name in an event.
exit: Exits program.

```

Format: `menu`

### Listing all events: `list`

Shows a list of all events currently stored in the program.

Format: `list`
<div style="page-break-after: always;"></div>

### Adding an event, participant or item: `add`

Adds an event to the event list, a participant to an event, or an item to an event.

Format:  

* `add -e EVENT -t TIME -v VENUE -u PRIORITY` for adding an event to the events list.
* `add -p PARTICIPANT -email EMAIL -e EVENT` for adding a participant to an event.
* `add -m ITEM -e EVENT` for adding an item to an event.

Remarks:

* `TIME` must be entered in the format `yyyy-mm-dd HH:mm`. 
  * There is no year 0000, no month 0 and no day 0 in the AD Calendar. Using such inputs will throw an error.
  * Using date-time inputs from the past (before the current date-time) will throw an error.
* `PRIORITY` must be either `HIGH`, `MEDIUM`, or `LOW`.
  * The values entered for `PRIORITY` are case-insensitive.
* If the event list has an `Event` with the name `EVENT`, or the specified event has a `Participant` or `Item` with the name of the `Participant`/`Item` to be added, an indexed suffix is added to differentiate the different entries.
  * e.g. adding two `Event`s with the same name `Wood workshop` would result in the second `Event` being named `Wood workshop (1)`.
* The index value increases as more `Event`s, `Item`s or `Participant`s are added.
  
Examples:

* `add -e Origami workshop -t 2024-10-12 18:00 -v Building A -u HIGH` adds an event with name `Origami workshop`, time `2024-10-12 18:00`, venue `Building A` and priority `HIGH` to the events list.
* `add -p John Tan -email john@gmail.com -e Origami workshop` adds a participant `John Tan` to the event `Origami workshop`.
* `add -m Origami paper -e Origami workshop` adds an item `Origami paper` to the event `Origami workshop`.

### Removing an event, participant or item: `remove` 

Removes an event from the event list, a participant from an event, or an item from an event.

Format:

* `remove -e EVENT` for removing an event from the event list.
* `remove -p PARTICIPANT -e EVENT` for removing a participant from an event.
* `remove -m ITEM -e EVENT` for removing an item from an event.

Examples:

* `remove -e Origami workshop` removes the event `Origami workshop` from the event list.
* `remove -p John Tan -e Origami workshop` removes the participant `John Tan` from the event `Origami workshop`.
* `remove -m Origami paper -e Origami workshop` removes the item `Origami paper` from the event `Origami workshop`.
<div style="page-break-after: always;"></div>

### Viewing all participants or items for an event: `view`

Shows a list of all participants or items for an event.

Format: `view -e EVENT -y TYPE`

* The list shown varies based on the value of `TYPE` entered, as follows:
  * A list of participants is shown when `TYPE` is `participant`.
  * A list of items is shown when `TYPE` is `item`.
* `TYPE` is case-insensitive, i.e. `PARTICIPANT` and `participant` will produce the same result.

Examples:

* `view -e Origami workshop -y participant` shows a list of all participants for the event `Origami workshop`.
* `view -e Origami workshop -y item` shows a list of all items for the event `Origami workshop`. 

### Editing the information of an event, participant or item: `edit`

Edits the information of an event/participant/item.

Format:

* `edit -e OLD_EVENT_NAME -name NEW_EVENT_NAME -t TIME -v VENUE -u PRIORITY` for editing an event's basic information.
* `edit -p OLD_PARTICIPANT -name NEW_PARTICIPANT -email EMAIL -e EVENT` for editing a participant's contact information in an event.
* `edit -m OLD_ITEM_NAME > NEW_ITEM_NAME -e EVENT` for editing an item's information in an event.

Remarks:

* If there is already an `Event` with the name `NEW_EVENT_NAME` in the event list, or an `Item` with the name `NEW_ITEM_NAME` in the specified event, an indexed suffix is added to differentiate the events.
  * e.g. given that an `Event` named `Wood workshop` is already present in the list, editing another `Event` to have the name `Wood workshop` would result in it being named `Wood workshop (1)`.
* `TIME` cannot be edited to a date-time in the past.

Examples:

* `edit -e CS2113 -name CS2113T -t 2024-10-25 16:00 -v LT16 -u HIGH` edits the information of the event `CS2113`.
* `edit -p Mary -name Mary -email mary@gmail.com -e CS2113` edits the information of the participant `Mary` in the event `CS2113`.
* `edit -m balloon > cake -e anniversary` edits the item `balloon` in the event `anniversary` to `cake`.
<div style="page-break-after: always;"></div>

### Marking events, participants, or items: `mark`

Marks an event in the event list, a participant in an event, or an item in an event.

Events can be marked as done or not done, participants marked as present or absent, and items marked as accounted for or unaccounted for.

Format: 

* `mark -e EVENT -s STATUS` to mark an event as done or not done.
* `mark -p PARTICIPANT -e EVENT -s STATUS` to mark a participant in an event as present or absent.
* `mark -m ITEM -e EVENT -s STATUS` to mark an item in an event as accounted for or unaccounted for.

Remarks:

* The `STATUS` parameter takes in the following values:
  * For marking events: `done` to mark as done, `undone` to mark as not done.
  * For marking participants: `present` to mark present, `absent` to mark absent.
  * For marking items: `accounted` to mark accounted, `unaccounted` to mark unaccounted.

Examples:

* Marking events:

  * `mark -e Origami workshop -s done` marks the event `Origami workshop` as done.
  * `mark -e Origami workshop -s undone` marks the event `Origami workshop` as not done.
  
* Marking participants:

  * `mark -p John Tan -e Origami workshop -s present` marks the participant `John Tan` in the `Origami workshop` event as present.
  * `mark -p John Tan -e Origami workshop -s absent` marks the participant `John Tan` in the `Origami workshop` event as absent.

* Marking items:

  * `mark -m Origami paper -e Origami workshop -s accounted` marks the item `Origami paper` in the `Origami workshop` event as accounted.
  * `mark -m Origami paper -e Origami workshop -s unaccounted` marks the item `Origami paper` in the `Origami workshop` event as unaccounted.

### Copying the participant list: `copy`

Copies the participant list from one event to another event.

Format: `copy FROM_EVENT > TO_EVENT`

* Both events must already exist.
* The mark status of the `Participant`s in the copy participant list will be reset (i.e. set as absent).
* If the event `TO_EVENT` already has an existing participant list, `TO_EVENT`'s participant list will be overwritten.

Examples:

* `copy Origami workshop > Coding workshop` copies the participant list from the `Origami workshop` event to the `Coding workshop` event.

### Sorting the event list: `sort`

Sorts events according to event name, event date-time or event priority level.

Format: `sort -by KEYWORD`

* `KEYWORD` is case-insensitive, but must be one of these inputs: `name/time/priority`

Examples:
* `sort -by name` will output a list of all the user's events alphabetically (from A to Z).
* `sort -by time` will output a list of all the user's events chronologically (earliest to latest).
* `sort -by priority` will output a list of all the user's events in terms of urgency (most to least important).

### Filtering the event list: `filter`

Filters out events from the event list based on name, date-time or priority level.

Format: `filter -e/-d/-t/-x/-u DESCRIPTION`

* `-e/-d/-t/-x/-u` are the flags for name, date, time, date-time and priority level respectively.
* `DESCRIPTION` is case-insensitive.

Examples:
* `filter -e workshop` will output all events with `workshop` in their event name.
* `filter -d 2024-03-02` will output all events that are occurring in `2024-03-02`.
* `filter -t 12:00` will output all events that are occurring at `12:00`.
* `filter -x 2024-03-02 12:00` will output all events that are occurring at `2024-03-02 12:00`. 
* `filter -u high` with output all events with priority level `HIGH`.
<div style="page-break-after: always;"></div>

### Finding a participant: `find`

Finds all participants with a specified name in a specified event.

Format: `find -e EVENT -p NAME`

* The `NAME` keyword is case-insensitive.

Examples:
* `find -e Origami workshop -p john` will output all participants with `john` in their name in the event `Origami workshop`.

### Exiting the program: `exit`

Exits the program.

Format: `exit`

### Saving of program data

The program saves its stored data into `data.txt` file in the same directory as the application `.JAR`.

This file consolidates information on `Events`, `Participants` and `Items` as follows:

* Each `Event` is stored with fields for its name, datetime, venue, priority, and completion status.
* Each `Participant` entry is tied to a corresponding `Event` and includes the participant’s name, contact number, email, attendance status, and the event name.
* Each `Item` entry, also tied to an `Event`, includes the item’s name, availability status, and event name.

The data is saved after the execution of every command and upon program termination.

### Loading of program data

Upon startup, the program loads data from the `data.txt` file.

If the file does not exist in the directory, it will be automatically created when data is saved.
<div style="page-break-after: always;"></div>

### Editing of saved program data

Amendment of data is strictly restricted to the program.

Direct amendment from the `data.txt` file is not recommended to prevent data from being lost due to incorrect detail inputs.

The program data is stored in the following format:

For Events

```
"EVENT",EVENT_NAME,TIME,VENUE,PRIORITY,STATUS
```

* `TIME` must be in the format `yyyy-mm-dd hh:mm`.
* `PRIORITY` must be either `HIGH`, `MEDIUM`, or `LOW`.
* `STATUS` must be either `Y` or `N`

For Participants:

```
"PARTICIPANT",PARTICIPANT_NAME,EMAIL,EVENT,STATUS
```

* `EMAIL` must follow a similar format such as example@gmail.com
* An entry for the `Event` corresponding to `EVENT` must be present in `data.txt`.
* `STATUS` must be either `Y` or `N`

For Items:

```
"ITEM",ITEM_NAME,EVENT,STATUS
```

* An entry for the `Event` corresponding to `EVENT` must be present in `data.txt`.
* `STATUS` must be either `Y` or `N`

The first field of each entry (`"EVENT"`, `"PARTICIPANT"`, `"ITEM"`) corresponds to the object type being stored, and can only take the values specified in the above format line. 

All fields added to `data.txt` must also be enclosed within double quotation marks (`" "`) to be properly parsed.

If the above format or parameter constraints are not followed, the `Event`, `Participant` or `Item` corresponding to the file line will be ignored upon program startup.
<div style="page-break-after: always;"></div>

## Command Summary

* List possible commands: `menu`
* List all events: `list`
* Add event: `add -e EVENT -t TIME -v VENUE -u PRIORITY`
* Add participant to an event: `add -p PARTICIPANT -email EMAIL -e EVENT`
* Add item to an event: `add -m ITEM -e EVENT`
* Remove event: `remove -e EVENT`
* Remove participant from an event: `remove -p PARTICIPANT -e EVENT`
* Remove item from an event: `remove -m ITEM -e EVENT`
* Edit event: `edit -e OLD_EVENT_NAME -name NEW_EVENT_NAME -t TIME -v VENUE -u PRIORITY`
* Edit participant of an event: `edit -p OLD_PARTICIPANT -name NEW_PARTICIPANT -email EMAIL -e EVENT`
* Edit item of an event: `edit -m OLD_ITEM_NAME > NEW_ITEM_NAME -e EVENT`
* View all participants or items for an event: `view -e EVENT -y TYPE`
* Mark an event as done: `mark -e EVENT -s STATUS`
* Mark a participant as present: `mark -p PARTICIPANT -e EVENT -s STATUS`
* Mark an item as accounted for: `mark -m ITEM -e EVENT -s STATUS`
* Copy participant list: `copy FROM_EVENT > TO_EVENT`
* Sort events: `sort -by KEYWORD`
* Filter events: `filter -e/-t/-u FILTER_DESCRIPTION`
* Find participants: `find -e EVENT -p NAME`
* Exit program: `exit`
