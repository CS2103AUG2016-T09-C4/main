package seedu.unburden.model;

import java.util.Set;

import com.google.common.base.Predicate;

import seedu.unburden.commons.core.UnmodifiableObservableList;
import seedu.unburden.commons.exceptions.*;
import seedu.unburden.model.task.ReadOnlyTask;
import seedu.unburden.model.task.Task;
import seedu.unburden.model.task.UniqueTaskList;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyListOfTask newData);

    /** Returns the ListOfTask */
    ReadOnlyListOfTask getListOfTask();

    /** Deletes the given person. */
    void deleteTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException;

    /** Adds the given person */
    void addTask(Task task) throws UniqueTaskList.DuplicateTaskException;
    
    void editTask(ReadOnlyTask target, String args) throws UniqueTaskList.TaskNotFoundException, IllegalValueException;

    /** Returns the filtered person list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();

    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredTaskList(Set<String> keywords);
    
    /** Updates the filter of the filtered task list to filter by the given date*/
	void updateFilteredTaskListForDate(Set<String> keywords);
	
	//void updateFilteredListToShowAllDatesBefore(Predicate<Task> allDatesBefore);
	
	/** Updates the filter of the filtered task list to filter all tasks with dates before the given date*/
	void updateFilteredListToShowAllDatesBefore(java.util.function.Predicate<? super Task> predicate);

}
