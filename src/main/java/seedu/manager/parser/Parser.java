package seedu.manager.parser;

import seedu.manager.command.Command;
import seedu.manager.command.AddCommand;
import seedu.manager.command.RemoveCommand;
import seedu.manager.command.ExitCommand;
import seedu.manager.command.MenuCommand;
import seedu.manager.command.EchoCommand;
import seedu.manager.command.ListCommand;
import seedu.manager.event.EventList;

import java.util.Arrays;


/**
 * Represents the command parser for EventManagerCLI
 */
public class Parser {

    /**
     * Returns a command based on the given user command string
     *
     * @param command The given command string from the user
     * @param events Event list of existing events
     */
    public Command parseCommand(String command, EventList events){
        String[] commandParts = command.split(" ");
        String commandWord = commandParts[0];
        String description = String.join(" ", Arrays.copyOfRange(commandParts, 1, commandParts.length)).trim();

        switch (commandWord) {
        case AddCommand.COMMAND_WORD:
            return new AddCommand(description, events);
        case RemoveCommand.COMMAND_WORD:
            return new RemoveCommand(description, events);
        case ListCommand.COMMAND_WORD:
            return new ListCommand();
        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();
        case MenuCommand.COMMAND_WORD:
            return new MenuCommand();

        default:
            return new EchoCommand(command);
        }
    }
}
