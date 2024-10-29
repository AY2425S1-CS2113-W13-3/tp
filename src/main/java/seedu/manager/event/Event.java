package seedu.manager.event;

import seedu.manager.enumeration.Priority;
import seedu.manager.exception.DuplicateDataException;
import seedu.manager.item.Participant;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;

//@@author MatchaRRR
/**
 * The Event class represents an event with a name, time, and venue.
 * It provides methods to access and modify the time and venue of the event.
 */
public class Event {
    protected ArrayList<Participant> participantList;
    private final String eventName;
    private LocalDateTime eventTime;
    private String eventVenue;
    private boolean isDone;
    private Priority eventPriority;

    /**
     * Constructs an Event with the specified name.
     *
     * @param eventName the name of the event
     */
    public Event(String eventName) {
        this.eventName = eventName;
        this.participantList = new ArrayList<>();
    }

    /**
     * Constructs an Event with the specified name, time, and venue.
     *
     * @param eventName  the name of the event
     * @param eventTime  the time duration of the event
     * @param eventVenue the venue of the event
     */
    public Event(String eventName, LocalDateTime eventTime, String eventVenue) {
        this.eventName = eventName;
        this.eventTime = eventTime;
        this.eventVenue = eventVenue;
        this.participantList = new ArrayList<>();
        this.isDone = false;
    }

    //@@author LTK-1606
    /**
     * Constructs an Event with the specified name, time, venue and priority.
     *
     * @param eventName  The name of the event.
     * @param eventTime  The time duration of the event.
     * @param eventVenue The venue of the event.
     * @param eventPriority The priority level of the event.
     */
    public Event(String eventName, LocalDateTime eventTime, String eventVenue, Priority eventPriority) {
        this.eventName = eventName;
        this.eventTime = eventTime;
        this.eventVenue = eventVenue;
        this.eventPriority = eventPriority;
        this.participantList = new ArrayList<>();
        this.isDone = false;
    }

    //@@author LTK-1606
    /**
     * Adds a participant to the participant list for the event.
     *
     * @param participantName the name of the participant to be added to the list.
     * @throws DuplicateDataException if a participant with the same name exists in the list.
     */
    public void addParticipant(String participantName) throws DuplicateDataException {
        if (getParticipantByName(participantName).isPresent()) {
            throw new DuplicateDataException("Duplicate participant!");
        }

        Participant participant = new Participant(participantName);
        this.participantList.add(participant);
    }

    /**
     * Removes a participant from the participant list.
     *
     * <p>
     * This method attempts to remove the specified participant from the list of
     * participants associated with the event. It returns {@code true} if the
     * participant was successfully removed, and {@code false} if the participant
     * was not found in the list.
     * </p>
     *
     * @param participantName the name of the participant to be removed from the list.
     * @return {@code true} if the participant was successfully removed;
     *         {@code false} if the participant was not found in the list.
     */
    public boolean removeParticipant(String participantName) {
        return this.participantList.removeIf((participant) ->
                (participant.getName().equalsIgnoreCase(participantName)));
    }

    /**
     * Retrieves the number of participants in the participant list.
     *
     * @return the count of participants currently in the list.
     */
    public int getParticipantCount() {
        return this.participantList.size();
    }

    //@@author MatchaRRR
    /**
     * @return the event name
     */
    public String getEventName() {
        return eventName;
    }

    /**
     * @return the event time
     */
    public LocalDateTime getEventTime() {
        return eventTime;
    }

    /**
     * @return the event venue
     */
    public String getEventVenue() {
        return eventVenue;
    }

    //@@author LTK-1606
    /**
     * @return the event priority
     */
    public Priority getEventPriority() {
        return eventPriority;
    }

    public ArrayList<Participant> getParticipantList() {
        return participantList;
    }

    /**
     * @return true if the event is marked done, false otherwise
     */
    public boolean isDone() {
        return isDone;
    }

    /**
     * Sets a new time for the event.
     *
     * @param eventTime the new event time
     */
    public void setEventTime(LocalDateTime eventTime) {
        this.eventTime = eventTime;
    }

    /**
     * Sets a new venue for the event.
     *
     * @param eventVenue the new event venue
     */
    public void setEventVenue(String eventVenue) {
        this.eventVenue = eventVenue;
    }

    /**
     * Sets a new priority level for the event.
     *
     * @param eventPriority the new event priority level
     */
    public void setEventPriority(Priority eventPriority) {
        this.eventPriority = eventPriority;
    }

    //@@author jemehgoh
    /**
     * Sets if the event is done or not done
     *
     * @param isDone if the event is done
     */
    public void setDone(boolean isDone) {
        this.isDone = isDone;
    }

    /**
     * @return 'Y' if event is marked done, 'N' otherwise
     */
    public char markIfDone() {
        return (this.isDone) ? 'Y' : 'N';
    }

    /**
     * Returns true if the participant with the given name can be marked present or absent.
     *         Returns false otherwise.
     *
     * @param participantName the participant name.
     * @param isPresent true if participant is to be marked present, false if he is to be marked absent.
     * @return {@code true} if the participant with participantName has been marked present or absent,
     *         {@code false} otherwise.
     */
    public boolean markParticipantByName(String participantName, boolean isPresent) {
        Optional<Participant> participant = getParticipantByName(participantName);
        return markParticipant(participant, isPresent);
    }

    //@author LTK-1606
    /**
     * Finds participants in the event whose names contain the specified person name.
     * <p>
     * This method iterates through the list of participants and checks if their names
     * contain the given {@code personName}, ignoring case and leading/trailing spaces.
     * If a match is found, the participant is added to the result list.
     * </p>
     *
     * @param personName the name or part of the name of the participant to search for
     * @return a list of {@code Participant} objects whose names contain the specified {@code personName}
     */
    public ArrayList<Participant> findParticipants(String personName) {
        ArrayList<Participant> participants = new ArrayList<>();
        for (Participant participant : this.participantList) {
            if (participant.getName().toLowerCase().contains(personName.trim().toLowerCase())) {
                participants.add(participant);
            }
        }
        return participants;
    }

    //@@author glenn-chew
    /**
     * Formats eventTime to a string in "yyyy-MM-dd HH:mm" format
     *
     * @return eventTime as a formated {@link String} object.
     */
    public String getEventTimeString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return formatter.format(eventTime);
    }

    //@@author MatchaRRR
    /**
     * Returns a string representation of the event, indicating its name, time and venue.
     *
     * @return A string that shows the event's name, time and venue.
     */
    @Override
    public String toString(){
        String eventTimeString = getEventTimeString();
        return String.format("Event name: %s / Event time: %s / Event venue: %s / Event Priority: %s / Done: %c",
                eventName, eventTimeString, eventVenue, eventPriority, markIfDone());
    }

    /**
     * Returns the participant in the participant list with the given name.
     * If the participant is not in the participant list, returns null.
     *
     * @param participantName the name of the participant.
     * @return the participant in the participant list with participantName, or null if
     *     no such participant exists.
     */
    private Optional<Participant> getParticipantByName(String participantName) {
        for (Participant participant : participantList) {
            if (participant.getName().equalsIgnoreCase(participantName)) {
                return Optional.of(participant);
            }
        }

        return Optional.empty();
    }

    //@@author jemehgoh
    /**
     * Returns true if the given participant can be marked present or absent. Returns false otherwise.
     *
     * @param participant the participant.
     * @param isPresent true if participant is to be marked present, false if he is to be marked absent.
     * @return {@code true} if the participant with participantName has been marked present or absent,
     *     {@code false} otherwise.
     */
    private boolean markParticipant(Optional<Participant> participant, boolean isPresent) {
        if (participant.isEmpty()) {
            return false;
        }

        participant.get().setPresent(isPresent);
        return true;
    }
}
