package seedu.manager.command;

import seedu.manager.event.EventList;

public class RemoveCommand extends Command {
    public static final String COMMAND_WORD = "remove";
    public static final String REMOVE_SUCCESS = "Event removed successfully";
    public static final String REMOVE_FAILURE = "Event not found";
    protected String eventName;
    protected EventList events;

    public RemoveCommand(String desc, EventList events) {
        this.eventName = desc;
        this.events = events;
    }

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
