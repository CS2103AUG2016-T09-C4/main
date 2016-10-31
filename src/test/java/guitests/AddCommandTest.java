package guitests;

import guitests.guihandles.TaskCardHandle;
import org.junit.Test;
import seedu.unburden.commons.core.Messages;
import seedu.unburden.logic.commands.AddCommand;
import seedu.unburden.testutil.TestTask;
import seedu.unburden.testutil.TestUtil;

import static org.junit.Assert.assertTrue;

/**
 * AddCommandTest
 * @@author A0147986H
 */

public class AddCommandTest extends ListOfTaskGuiTest {

    //@Test
    public void add() {
        //add one task
        TestTask[] currentList = td.getTypicalPersons();
        TestTask taskToAdd = td.hoon;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add another task
        taskToAdd = td.ida;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add duplicate task
        commandBox.runCommand(td.hoon.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(personListPanel.isListMatching(currentList));

        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(td.alice);

        //invalid command
        commandBox.runCommand("adds Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertAddSuccess(TestTask personToAdd, TestTask... currentList) {
        commandBox.runCommand(personToAdd.getAddCommand());

        //confirm the new card contains the right data
        TaskCardHandle addedCard = personListPanel.navigateToPerson(personToAdd.getName().fullName);
        assertMatching(personToAdd, addedCard);

        //confirm the list now contains all previous persons plus the new person
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, personToAdd);
        assertTrue(personListPanel.isListMatching(expectedList));
    }

}
