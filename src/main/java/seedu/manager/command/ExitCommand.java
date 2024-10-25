package seedu.manager.command;

//@@author jemehgoh
/**
 * Represents an executable exit command
 */
public class ExitCommand extends Command {
    public static final String COMMAND_WORD = "exit";
    private static final String EXIT_MESSAGE = "Thank you for using EventManagerCLI. Goodbye!";

    /**
     * Constructs a new ExitCommand
     */
    public ExitCommand() {
        super(true);
    }

    /**
     * Executes the exit command
     */
    public void execute() {
        this.message = EXIT_MESSAGE;
    }
}
