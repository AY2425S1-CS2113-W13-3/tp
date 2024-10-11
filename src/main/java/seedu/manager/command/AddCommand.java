package seedu.manager.command;

/**
 * Represents an executable add command
 */
public class AddCommand extends Command {
    public static final String COMMAND_WORD = "add";
    private static final String ADD_MESSAGE = "Event added successfully";
    protected String eventName;
    protected String time;
    protected String venue;

    public AddCommand(String eventName, String time, String venue) {
        this.eventName = eventName;
        this.time = time;
        this.venue = venue;
    }

    /**
     * Returns a command output with an add message
     *
     * @return The command output with an add message
     */
    public CommandOutput execute() {
        this.eventList.addEvent(this.eventName, this.time, this.venue);
        //this.eventList.getList();
        return new CommandOutput(ADD_MESSAGE, false);
    }
}
