package seedu.manager.command;

import seedu.manager.event.EventList;

/**
 * Represents an executable add command
 */
public class AddCommand extends Command {
    public static final String COMMAND_WORD = "add";
    public static final String ADD_MESSAGE = "Event added successfully";
    protected String eventName;
    protected EventList events;

    public AddCommand(String desc, EventList events) {
        this.eventName = desc;
        this.events = events;
    }

    /**
     * Returns a command output with an add message
     *
     * @return The command output with an add message
     */
    public CommandOutput execute() {
        this.events.addEvent(this.eventName);
        this.events.getList();
        return new CommandOutput(ADD_MESSAGE, false);
    }
}
