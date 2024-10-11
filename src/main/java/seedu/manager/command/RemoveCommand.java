package seedu.manager.command;

public class RemoveCommand extends Command {
    public static final String COMMAND_WORD = "remove";
    public static final String REMOVE_SUCCESS = "Event removed successfully";
    public static final String REMOVE_FAILURE = "Event not found";
    protected String eventName;


    public RemoveCommand(String eventName) {
        this.eventName = eventName;
    }

    public CommandOutput execute() {
        boolean isRemoved = this.eventList.removeEvent(eventName);
        if (isRemoved) {
            return new CommandOutput(REMOVE_SUCCESS, false);
        } else {
            return new CommandOutput(REMOVE_FAILURE, false);
        }
    }
}
