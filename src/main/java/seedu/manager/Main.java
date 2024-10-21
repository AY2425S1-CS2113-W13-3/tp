package seedu.manager;

import seedu.manager.command.Command;
import seedu.manager.event.EventList;
import seedu.manager.exception.InvalidCommandException;
import seedu.manager.parser.Parser;
import seedu.manager.ui.Ui;

public class Main {
    private static final Ui ui = new Ui();
    private static EventList events = new EventList();

    /**
     * Main entry-point for the EventManagerCLI application.
     */
    public static void main(String[] args) {
        ui.greetUser();
        runCommandLoop();
        System.exit(0);
    }

    /**
     * Run command loop to get command from users
     * Parse the command and execute it
     * The loop ends when ExitCommand is triggered
     */
    private static void runCommandLoop() {
        Command command;
        boolean isGettingCommands = true;
        while (isGettingCommands){
            try {
                String userCommandText = ui.getCommand();
                command = new Parser().parseCommand(userCommandText);
                command.setData(events);
                command.execute();
                ui.showOutputToUser(command);

                isGettingCommands = !command.getCanExit();
            } catch (InvalidCommandException exception) {
                ui.showErrorMessageToUser(exception);
            }
        }
    }
}
