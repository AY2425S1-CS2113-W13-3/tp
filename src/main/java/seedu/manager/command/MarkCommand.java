package seedu.manager.command;

import seedu.manager.event.Event;

import java.util.Optional;

/**
 * Represents an executable mark command
 */
public class MarkCommand extends Command {
    public static final String COMMAND_WORD = "mark";

    private static final String EVENT_MARK_MESSAGE = "Event marked as done";
    private static final String EVENT_UNMARK_MESSAGE = "Event marked not done";
    private static final String INVALID_EVENT_MESSAGE = "Event not found!";

    protected String eventName;
    protected boolean toMark;

    /**
     * Constructs a new MarkCommand with the given event name
     *
     * @param eventName the given event name
     */
    public MarkCommand(String eventName, boolean toMark) {
        super(false);
        this.eventName = eventName;
        this.toMark = toMark;
    }

    /**
     * Executes a mark command by marking the specified event as done or not done,
     * depending on the value of toMark.
     */
    @Override
    public void execute() {
        Optional<Event> eventToMark = this.eventList.getEventByName(this.eventName);

        if (eventToMark.isPresent()) {
            eventToMark.get().setDone(this.toMark);
            this.message = (this.toMark) ? EVENT_MARK_MESSAGE : EVENT_UNMARK_MESSAGE;
        } else {
            this.message = INVALID_EVENT_MESSAGE;
        }
    }
}
