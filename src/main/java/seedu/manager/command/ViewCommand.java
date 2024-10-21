package seedu.manager.command;

import seedu.manager.event.Event;
import seedu.manager.item.Participant;

import java.util.Optional;

/**
 * Represents a command to view the list of participants in an event.
 * The view command will search for an event by its name and display all its participants if found.
 */
public class ViewCommand extends Command {
    public static final String COMMAND_WORD = "view";
    private static final String VIEW_MESSAGE = "There are %d participants in %s! " +
            "Here are your participants:";
    private static final String INVALID_EVENT_MESSAGE = "Event not found!";

    protected String eventName;

    /**
     * Constructs an ViewCommand object with the for the specified event.
     *
     * @param eventName The name of the event to be viewed.
     */
    public ViewCommand(String eventName) {
        super(false);
        this.eventName = eventName;
    }

    /**
     * Executes the command to view the participants for an event.
     */
    public void execute() {
        Optional<Event> eventToView = eventList.getEventByName(this.eventName);

        if (eventToView.isPresent()) {
            StringBuilder outputMessage = new StringBuilder(
                    String.format(VIEW_MESSAGE, eventToView.get().getParticipantCount(), eventName) + "\n");
            int count = 1;
            for (Participant participant : eventToView.get().getParticipantList()) {
                outputMessage.append(String.format("%d. %s\n", count, participant.toString()));
                count++;
            }

            this.message = outputMessage.toString();
        } else {
            this.message = INVALID_EVENT_MESSAGE;
        }
    }
}
