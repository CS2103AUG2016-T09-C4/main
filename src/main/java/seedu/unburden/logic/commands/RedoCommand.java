package seedu.unburden.logic.commands;
import java.util.NoSuchElementException;
import seedu.unburden.commons.exceptions.IllegalValueException;
import seedu.unburden.model.tag.UniqueTagList.DuplicateTagException;

/**
 * Redo an undo action.
 * @@author A0139714B
 */
public class RedoCommand extends Command {
	
	public static final String COMMAND_WORD = "redo";

	public static final String MESSAGE_USAGE = COMMAND_WORD + ": redo the most recent undo command. \n "
			+ "Example: " + COMMAND_WORD;

	public static final String MESSAGE_SUCCESS = "Redo success!";
	public static final String MESSAGE_EMPTY_STACK = "No recent undo commands can be found.";
	

	
	public CommandResult execute() throws DuplicateTagException, IllegalValueException {
		try {
			assert model != null;
			model.loadFromUndoHistory();
			
			return new CommandResult(MESSAGE_SUCCESS);
		} catch (NoSuchElementException ee) {
			return new CommandResult(MESSAGE_EMPTY_STACK);
		}
	}

}
