package seedu.manager.command;

import seedu.manager.event.EventList;

/**
 * Represents a command to remove an event from the EventList.
 */
public class RemoveCommand extends Command {
    public static final String COMMAND_WORD = "remove";
    public static final String REMOVE_SUCCESS = "Event removed successfully";
    public static final String REMOVE_FAILURE = "Event not found";
    protected String eventName;
    protected EventList events;

    /**
     * Constructs a RemoveCommand with the specified event name and EventList.
     *
     * @param desc The name of the event to be removed.
     * @param events The EventList from which the event will be removed.
     */
    public RemoveCommand(String desc, EventList events) {
        this.eventName = desc;
        this.events = events;
    }

    /**
     * Executes the removal of the specified event from the EventList.
     *
     * @return A CommandOutput object containing the result of the removal operation.
     *         If the event is successfully removed, the output will contain a success message;
     *         otherwise, it will contain a failure message.
     */
    public CommandOutput execute() {
        boolean isRemoved = events.removeEvent(eventName);
        this.events.getList();
        if (isRemoved) {
            return new CommandOutput(REMOVE_SUCCESS, false);
        } else {
            return new CommandOutput(REMOVE_FAILURE, false);
        }
    }
}
