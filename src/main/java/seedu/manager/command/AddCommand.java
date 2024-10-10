package seedu.manager.command;

import seedu.manager.event.EventList;

/**
 * Represents an executable add command that adds an event to the EventList.
 */
public class AddCommand extends Command {
    public static final String COMMAND_WORD = "add";
    public static final String ADD_MESSAGE = "Event added successfully";
    protected String eventName;
    protected EventList events;

    /**
     * Constructs an AddCommand with the specified event name and EventList.
     *
     * @param desc The name of the event to be added.
     * @param events The EventList to which the event will be added.
     */
    public AddCommand(String desc, EventList events) {
        this.eventName = desc;
        this.events = events;
    }

    /**
     * Executes the add command to add the specified event to the EventList.
     *
     * @return A CommandOutput object containing the result of the add operation.
     *         The output includes a success message indicating the event was added.
     */
    public CommandOutput execute() {
        this.events.addEvent(this.eventName);
        this.events.getList();
        return new CommandOutput(ADD_MESSAGE, false);
    }
}
