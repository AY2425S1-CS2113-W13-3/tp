package seedu.manager.command;

public class ListCommand extends Command {
    public static final String COMMAND_WORD = "list";
    private static final String LIST_MESSAGE = "There are %1$d events in your list! " +
            "Here are your scheduled events:";

    /**
     * Returns a command output with a list message
     *
     * @return The command output with a list message
     */
    public CommandOutput execute() {
        StringBuilder outputMessage = new StringBuilder(String.format(LIST_MESSAGE, eventList.getListSize()) + "\n");
        for (int i = 0; i < eventList.getListSize(); i++) {
            outputMessage.append(String.format("%d. %s\n", i + 1, eventList.getEvent(i).toString()));
        }

        return new CommandOutput(outputMessage.toString(), false);
    }
}
