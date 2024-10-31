package seedu.manager.command;

import seedu.manager.enumeration.Priority;
import seedu.manager.exception.DuplicateDataException;


import java.time.LocalDateTime;

//@@author KuanHsienn
/**
 * Represents a command to add an event to the event list or a participant to an event.
 * The AddCommand class stores details of an event, including its name, time, venue,
 * and priority, or details of a participant, including their name, contact number, and email.
 */
public class AddCommand extends Command {
    public static final String COMMAND_WORD = "add";
    private static final String ADD_EVENT_MESSAGE = "Event added successfully";
    private static final String ADD_PARTICIPANT_MESSAGE = "Participant added successfully";
    private static final String ADD_ITEM_MESSAGE = "Item added successfully";
    private static final String ADD_FAILURE_MESSAGE = "Event not found!";

    protected String eventName;
    protected LocalDateTime time;
    protected String venue;
    protected Priority priority;
    protected String participantName;
    protected String participantNumber;
    protected String participantEmail;
    protected String itemName;

    /**
     * Constructs an AddCommand object with the specified event details.
     *
     * @param eventName The name of the event to be added.
     * @param time The time of the event to be added.
     * @param venue The venue of the event to be added.
     * @param priority The priority level of the event to be added
     */
    public AddCommand(String eventName, LocalDateTime time, String venue, Priority priority) {
        super(false);
        this.eventName = eventName;
        this.time = time;
        this.venue = venue;
        this.priority = priority;
    }

    //@@author LTK-1606
    /**
     * Constructs an {@code AddCommand} with the specified participant name and event name.
     *
     * @param participantName the name of the participant to be added to the event.
     *                        If null, a new event will be created instead.
     * @param eventName      the name of the event to which the participant will be added
     *                       or the event to be created if no participant name is provided.
     */
    public AddCommand(String participantName, String participantNumber, String participantEmail, String eventName) {
        super(false);
        this.participantName = participantName;
        this.participantNumber = participantNumber;
        this.participantEmail = participantEmail;
        this.eventName = eventName;
    }

    /**
     * Constructs an {@link AddCommand} with the specified item and event name.
     *
     * @param itemName the name of the item to be added to the event.
     * @param eventName the name of the event the item is to be added to.
     */
    public AddCommand(String itemName, String eventName) {
        super(false);
        this.itemName = itemName;
        this.eventName = eventName;
    }

    //@@author KuanHsienn
    /**
     * Executes the command to add an event or a participant to an event.
     * <p>
     * If no participant name is provided, this method adds a new event to the
     * event list with the specified event name, time, and venue.
     * If a participant name is provided, it adds the participant with their
     * email and contact number to the specified event in the event list.
     * </p>
     *
     * <p>
     * After execution, a message indicates the result of the operation:
     * an event was added, a participant was added, or the addition of a
     * participant failed.
     * </p>
     */
    @Override
    public void execute() throws DuplicateDataException {
        if (participantName != null) {
            boolean isAdded = eventList.addParticipantToEvent(
                    participantName,
                    participantEmail,
                    participantNumber,
                    eventName
            );
            message = (isAdded) ? ADD_PARTICIPANT_MESSAGE : ADD_FAILURE_MESSAGE;
        } else if (itemName != null) {
            boolean isAdded = eventList.addItemToEvent(itemName, eventName);
            message = (isAdded) ? ADD_ITEM_MESSAGE : ADD_FAILURE_MESSAGE;
        } else {
            eventList.addEvent(eventName, time, venue, priority);
            message = ADD_EVENT_MESSAGE;
        }
    }
}
