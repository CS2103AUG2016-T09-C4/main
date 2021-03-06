package guitests;

import org.junit.Test;

import seedu.unburden.commons.core.Messages;
import seedu.unburden.commons.exceptions.IllegalValueException;
import seedu.unburden.testutil.TestTask;

import static org.junit.Assert.assertTrue;

public class FindCommandTest extends ListOfTaskGuiTest {


    //@Test
    public void find_nonEmptyList() throws IllegalValueException {
        assertFindResult("find Mark"); //no results
        assertFindResult("find Meier", td.benson, td.daniel); //multiple results

        //find after deleting one result
        commandBox.runCommand("delete 1");
        assertFindResult("find Meier",td.daniel);
    }


    //@Test
    public void find_emptyList() throws IllegalValueException {
        commandBox.runCommand("clear");
        assertFindResult("find Jean"); //no results
    }

    //@Test
    public void find_invalidCommand_fail() {
        commandBox.runCommand("findgeorge");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertFindResult(String command, TestTask... expectedHits ) throws IllegalValueException {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " persons listed!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
