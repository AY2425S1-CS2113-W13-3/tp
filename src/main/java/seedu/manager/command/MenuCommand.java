package seedu.manager.command;

/**
 * Represents an executable menu command
 */
public class MenuCommand extends Command {
    public static final String COMMAND_WORD = "menu";
    private static final String MENU_MESSAGE = """
            Here are the possible commands:
           
            add -e EVENT_NAME -t TIME -v VENUE: Add an event to the event list.
            list: List events.
            remove -e EVENT_NAME: Remove an event from the event list.
            add -p PARTICIPANT_NAME -e EVENT_NAME: Add a participant to an event.
            view -e EVENT_NAME: View the list of participants of an event.
            remove -p PARTICIPANT_NAME -e EVENT_NAME: Remove a participant from an event.""";

    /**
     * Constructs a new MenuCommand
     */
    public MenuCommand() {
        super(false);
    }

    /**
     * Executes the menu command
     */
    @Override
    public void execute() {
        this.message = MENU_MESSAGE;
    }
}
