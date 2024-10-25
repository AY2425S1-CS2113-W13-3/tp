# Developer Guide

## Acknowledgements

{list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well}

## Design & implementation

<img src = "images/ArchitectureDiagram.png">

The *Architecture Diagram* given above provides the high-level design of the application.

### Main components

The application comprises the following components:
* `Main`, which handles program startup and shutdown, and also interactions between other components.
* `UI`, which handles user input and showing messages to the user.
* `Storage`, which handles the loading and saving of data upon program startup and shutdown.
* `Parser`, which converts user input into commands.
* `Command`, which are executed to modify the data stored in the program.
* `EventList`, which stores the program's data.

### Interactions between components

<img src = "images/ArchitectureSequenceDiagram.png">

The above *Sequence Diagram* shows how the different components of the system interact with one
another in the scenario when the command `add -e event -t 1200 -v venue` is executed.

## Product scope
### Target user profile

The target user:
* has a need to organise a large number of events
* organises small-scale events, such that he is able to handle all matters on his own
* prefers typing to mouse interactions
* is comfortable using a command-line interface

### Value proposition

The user is able to organise and manage his events more quickly and efficiently than with a mouse/GUI app

## User Stories

| Version | As a ... | I want to ...            | So that I can ...                                      |
|---------|----------|--------------------------|--------------------------------------------------------|
| v1.0    | new user | see usage instructions   | refer to them when I forget how to use the application |
| v2.0    | user     | mark events as completed | easily track all past events                           |
| v2.0    | user     | mark participants present| know exactly who signed up but did not attend the event|

## Non-Functional Requirements

{Give non-functional requirements}

## Glossary

* *glossary item* - Definition

## Instructions for manual testing

{Give instructions on how to do a manual product testing e.g., how to load sample data to be used for testing}
