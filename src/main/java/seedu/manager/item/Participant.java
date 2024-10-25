package seedu.manager.item;

import seedu.manager.event.Event;

//@@author jemehgoh
/**
 * Represents an event participant, in the participant list of an {@link Event}.
 */
public class Participant {
    private final String name;
    private boolean isPresent;

    /**
     * Constructs a new Participant with a given name
     *
     * @param name the given name
     */
    public Participant(String name) {
        this.name = name;
        this.isPresent = false;
    }

    /**
     * Returns the name of the participant.
     *
     * @return the name of the participant
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the participant as present or not present.
     *
     * @param isPresent whether the participant is present or not present
     */
    public void setPresent(boolean isPresent) {
        this.isPresent = isPresent;
    }

    /**
     * Returns "X" if isPresent is true, " " otherwise.
     *
     * @return "X" if isPresent is true, " " otherwise.
     */
    public String markIfPresent() {
        return (this.isPresent) ? "X" : " ";
    }

    /**
     * Returns a formatted string of the item
     *
     * @return a formatted string of the item
     */
    @Override
    public String toString() {
        return String.format("%s [%s]", this.name, this.markIfPresent());
    }
}
