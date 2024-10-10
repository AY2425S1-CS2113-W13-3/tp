package seedu.manager.event;

import java.util.ArrayList;


/**
 * The EventList class manages a list of Event objects.
 * It provides methods to manage an event list.
 */
public class EventList  {
    private final ArrayList<Event> eventList;

    /**
     * Constructor that initializes EventList with a given list of event.
     *
     * @param eventList The initial list of tasks.
     */
    public EventList(ArrayList<Event> eventList) {
        this.eventList = eventList;
    }

    /**
     * Default constructor that initializes an empty event list.
     */
    public EventList(){
        eventList = new ArrayList<>();
    }

    /**
     * @return The list of events.
     */
    public ArrayList<Event> getList() {
        for (Event event : eventList) {
            System.out.println(event.getEventName());
        }
        return eventList;
    }

    /**
     * @return The size of the event list.
     */
    public int getListSize() {
        return eventList.size();
    }

    /**
     * Adds a new event to the event list.
     *
     * @param eventName The name of the event to be added.
     */
    public void addEvent(String eventName) {
        Event newEvent = new Event(eventName);
        eventList.add(newEvent);
    }

    /**
     * Removes an event from the event list by its name.
     *
     * @param eventName The name of the event to be removed.
     * @return true if the event was found and removed; false otherwise.
     */
    public boolean removeEvent(String eventName) {
        for (Event event : eventList) {
            if (event.getEventName().equals(eventName)) {
                eventList.remove(event);
                return true; // Event found and removed
            }
        }
        return false; // Event not found
    }
}
