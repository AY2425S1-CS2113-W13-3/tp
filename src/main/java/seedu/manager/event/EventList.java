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
            System.out.println(event);
        }
        return eventList;
    }

    /**
     * @return The size of the event list.
     */
    public int getListSize() {
        return eventList.size();
    }

    public void addEvent(String eventName) {
        Event newEvent = new Event(eventName);
        eventList.add(newEvent);
    }
}
