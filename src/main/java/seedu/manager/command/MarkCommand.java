package seedu.manager.command;

//@@author jemehgoh
/**
 * Represents an executable mark command
 */
public abstract class MarkCommand extends Command {
    public static final String COMMAND_WORD = "mark";

    protected static final String INVALID_EVENT_MESSAGE = "Event not found!";

    protected String eventName;
    protected boolean toMark;

    /**
     * Constructs a new MarkCommand with the given event name.
     *
     * @param eventName the given event name.
     * @param toMark true if the item is to be marked, false if it is to be unmarked.
     */
    public MarkCommand(String eventName, boolean toMark) {
        super(false);
        this.eventName = eventName;
        this.toMark = toMark;
    }

    /**
     * Executes a mark command.
     */
    @Override
    public abstract void execute();
}
