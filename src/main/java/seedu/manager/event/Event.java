package seedu.manager.event;

import seedu.manager.item.Participant;

import java.util.ArrayList;

/**
 * The Event class represents an event with a name, time, and venue.
 * It provides methods to access and modify the time and venue of the event.
 */
public class Event {
    protected ArrayList<Participant> participantList;
    private final String eventName;
    private String eventTime;
    private String eventVenue;
    private boolean isDone;

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
    public Event(String eventName, String eventTime, String eventVenue) {
        this.eventName = eventName;
        this.eventTime = eventTime;
        this.eventVenue = eventVenue;
        this.participantList = new ArrayList<>();
        this.isDone = false;
    }

    /**
     * Adds a participant to the participant list for the event.
     *
     * @param participantName the name of the participant to be added to the list.
     */
    public void addParticipant(String participantName) {
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

    /**
     * @return the event name
     */
    public String getEventName() {
        return eventName;
    }

    /**
     * @return the event time
     */
    public String getEventTime() {
        return eventTime;
    }

    /**
     * @return the event venue
     */
    public String getEventVenue() {
        return eventVenue;
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
    public void setEventTime(String eventTime) {
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
     * Returns a string representation of the event, indicating its name, time and venue.
     *
     * @return A string that shows the event's name, time and venue.
     */
    @Override
    public String toString(){
        return String.format("Event name: %s / Event time: %s / Event venue: %s / Done: %c", eventName, eventTime,
                eventVenue, markIfDone());
    }
}
