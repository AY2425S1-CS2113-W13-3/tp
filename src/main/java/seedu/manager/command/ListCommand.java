package seedu.manager.command;


public class ListCommand extends Command {
    public static final String COMMAND_WORD = "list";
    private static final String LIST_MESSAGE = "Here are your scheduled events:";

    /**
     * Returns a command output with a list message
     *
     * @return The command output with a list message
     */
    public CommandOutput execute() {
        return new CommandOutput(LIST_MESSAGE, false);
    }
}
