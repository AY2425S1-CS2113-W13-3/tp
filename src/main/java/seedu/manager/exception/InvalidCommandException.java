package seedu.manager.exception;

//@@author jemehgoh
/**
 * Signals that an entered command is invalid
 */
public class InvalidCommandException extends RuntimeException {

    /**
     * @param message contains information on how the command is invalid
     */
    public InvalidCommandException(String message) {
        super(message);
    }
}
