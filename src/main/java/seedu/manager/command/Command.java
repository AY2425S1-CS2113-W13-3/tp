package seedu.manager.command;

import seedu.manager.event.EventList;

//@@author jemehgoh
/**
 * Represents an executable command
 */
public abstract class Command {
    protected EventList eventList;
    protected String message;
    protected boolean canExit;

    /**
     * Constructs a new Command with whether the program can be exited from
     *
     * @param canExit whether the program can be exited from
     */
    protected Command(boolean canExit) {
        this.canExit = canExit;
    }

    //@@author MatchaRRR
    /**
     * Sets the command's event list to a specified event list
     *
     * @param events the specified event list
     */
    public void setData(EventList events) {
        this.eventList = events;
    }

    //@@jemehgoh
    /**
     * Executes the command
     */
    public abstract void execute();

    /**
     * Returns the command's message
     *
     * @return the command's message
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * Returns true if the program can be exited from, returns false otherwise
     *
     * @return true if the program can be exited from, false otherwise
     */
    public boolean getCanExit() {
        return canExit;
    }
}
