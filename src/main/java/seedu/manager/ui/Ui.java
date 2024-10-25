package seedu.manager.ui;

import seedu.manager.command.Command;

import java.util.Scanner;

//@@author jemehgoh
/**
 * Represents the program's user interface
 */
public class Ui {
    private static final String WELCOME_MESSAGE = "Welcome to EventManagerCLI.";
    private static final String COMMAND_PROMPT_MESSAGE = "Enter a command: ";
    private static final String SEPARATOR = "------------------------";

    private final Scanner userInput;

    /**
     * Constructs a new Ui
     */
    public Ui() {
        this.userInput = new Scanner(System.in);
    }

    /**
     * Greets user upon program startup
     */
    public void greetUser() {
        System.out.println(WELCOME_MESSAGE);
    }

    /**
     * Gets input from the user, and executes commands based on that input
     */
    public String getCommand() {
        System.out.print(COMMAND_PROMPT_MESSAGE);
        return this.userInput.nextLine();
    }

    //@@author MatchaRRR
    /**
     * Shows the output message of a command to the users.
     */
    public void showOutputToUser(Command command) {
        System.out.println(command.getMessage());
        System.out.println(SEPARATOR);
    }

    //@@author jemehgoh
    /**
     * Shows the error message of an exception to the user.
     */
    public void showErrorMessageToUser(Exception exception) {
        System.out.println(exception.getMessage());
        System.out.println(SEPARATOR);
    }

    //@@author KuanHsienn
    /**
     * Shows a message to the user.
     *
     * @param message The message to display to the user.
     */
    public void showMessage(String message) {
        System.out.println(message);
        System.out.println(SEPARATOR);
    }
}
