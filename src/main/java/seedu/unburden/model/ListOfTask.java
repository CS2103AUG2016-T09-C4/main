package seedu.unburden.model;

import javafx.collections.ObservableList;
import seedu.unburden.model.tag.Tag;
import seedu.unburden.commons.exceptions.*;
import seedu.unburden.model.tag.UniqueTagList;
import seedu.unburden.model.task.ReadOnlyTask;
import seedu.unburden.model.task.Task;
import seedu.unburden.model.task.UniqueTaskList;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .equals comparison)
 */

public class ListOfTask implements ReadOnlyListOfTask {

    private final UniqueTaskList tasks;
    private final UniqueTagList tags;
    
    public static int todayCounter;
    public static int tomorrowCounter;
    public static int doneCounter;
    public static int undoneCounter;
    public static int overdueCounter;
    
    private Calendar calendar = Calendar.getInstance();
    private Calendar calendar_tmr = Calendar.getInstance();
    //private Calendar calendar_nextWeek = Calendar.getInstance();
    
    private static final SimpleDateFormat DATEFORMATTER = new SimpleDateFormat("dd-MM-yyyy");

    {
        tasks = new UniqueTaskList();
        tags = new UniqueTagList();
    }

    public ListOfTask() {}

    /**
     * Persons and Tags are copied into this addressbook
     */
    public ListOfTask(ReadOnlyListOfTask toBeCopied) {
        this(toBeCopied.getUniqueTaskList(), toBeCopied.getUniqueTagList());
    }

    /**
     * Persons and Tags are copied into this addressbook
     */
    public ListOfTask(UniqueTaskList persons, UniqueTagList tags) {
        resetData(persons.getInternalList(), tags.getInternalList());
    }

    public static ReadOnlyListOfTask getEmptyAddressBook() {
        return new ListOfTask();
    }

//// list overwrite operations

    public ObservableList<Task> getTasks() {
        return tasks.getInternalList();
    }

    public void setTasks(List<Task> tasks) {
        this.tasks.getInternalList().setAll(tasks);
    }

    public void setTags(Collection<Tag> tags) {
        this.tags.getInternalList().setAll(tags);
    }

    public void resetData(Collection<? extends ReadOnlyTask> newPersons, Collection<Tag> newTags) {
        setTasks(newPersons.stream().map(t -> {
			try {
				return new Task(t);
			} catch (IllegalValueException e) {
				e.printStackTrace();
			}
			return null;
		}).collect(Collectors.toList()));
        setTags(newTags);
    }

    public void resetData(ReadOnlyListOfTask newData) {
        resetData(newData.getTaskList(), newData.getTagList());
        Counter();
    }

//// person-level operations

    /**
     * Adds a person to the address book.
     * Also checks the new person's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the person to point to those in {@link #tags}.
     *
     * @throws UniqueTaskList.DuplicatePersonException if an equivalent person already exists.
     */
    public void addTask(Task p) throws UniqueTaskList.DuplicateTaskException {
        syncTagsWithMasterList(p);
        tasks.add(p);
        Counter();
    }

    /**
     * Ensures that every tag in this person:
     *  - exists in the master list {@link #tags}
     *  - points to a Tag object in the master list
     */
    private void syncTagsWithMasterList(Task task) {
        final UniqueTagList taskTags = task.getTags();
        tags.mergeFrom(taskTags);

        // Create map with values = tag object references in the master list
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        for (Tag tag : tags) {
            masterTagObjects.put(tag, tag);
        }

        // Rebuild the list of person tags using references from the master list
        final Set<Tag> commonTagReferences = new HashSet<>();
        for (Tag tag : taskTags) {
            commonTagReferences.add(masterTagObjects.get(tag));
        }
        task.setTags(new UniqueTagList(commonTagReferences));
    }

    public boolean removeTask(ReadOnlyTask key) throws UniqueTaskList.TaskNotFoundException {
        if (tasks.remove(key)) {
        	Counter();
            return true;
        } else {
            throw new UniqueTaskList.TaskNotFoundException();
        }
    }
    
    //@@author A0139714B
    public boolean editTask(ReadOnlyTask key, Task toEdit) 
    		throws UniqueTaskList.TaskNotFoundException, IllegalValueException{
        if (tasks.edit(key, toEdit)){
        	Counter();
            return true;
        }
        else {
            throw new UniqueTaskList.TaskNotFoundException();
        }
                    
    }
    //@@author A0143095H
    public void doneTask(ReadOnlyTask key, boolean isDone){
    	tasks.done(key,isDone);
    	Counter();
    }
    
    //@@author A0143095H
    public void undoneTask(ReadOnlyTask key, boolean isDone){
    	tasks.done(key,isDone);
    	Counter();
    }
    
//// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    	Counter();
    }

//// util methods

    @Override
    public String toString() {
        return tasks.getInternalList().size() + " Tasks, " 	+ tags.getInternalList().size() +  " tags";
        // TODO: refine later
    }

    @Override
    public List<ReadOnlyTask> getTaskList() {
        return Collections.unmodifiableList(tasks.getInternalList());
    }

    @Override
    public List<Tag> getTagList() {
        return Collections.unmodifiableList(tags.getInternalList());
    }

    @Override
    public UniqueTaskList getUniqueTaskList() {
        return this.tasks;
    }

    @Override
    public UniqueTagList getUniqueTagList() {
        return this.tags;
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListOfTask // instanceof handles nulls
                && this.tasks.equals(((ListOfTask) other).tasks)
                && this.tags.equals(((ListOfTask) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(tasks, tags);
    }
    
    //@@author A0143095H
    //Method counts the number of each type of tasks
    public void Counter(){
    	int today = 0;
    	int tomorrow = 0;
    	int overdue = 0;
    	int done = 0;
    	int undone = 0;
    	
    	calendar_tmr.setTime(calendar.getTime());
    	calendar_tmr.add(Calendar.DAY_OF_YEAR, 1);
    	
//    	calendar_nextWeek.setTime(calendar.getTime());
//    	calendar_nextWeek.add(Calendar.WEEK_OF_YEAR, 1);
    	
    	for(int i=0; i<tasks.getInternalList().size(); i++) {
    		
    		if(tasks.getInternalList().get(i).getDone()){
    			done++;
    		}
    		
    		if(!tasks.getInternalList().get(i).getDone()){
    			undone++;
    		}
    		
    		//Checks if date of task matches the date "today" and ensures that task is still undone 
    		if(tasks.getInternalList().get(i).getDate().getFullDate().equals(DATEFORMATTER.format(calendar.getTime())) && (tasks.getInternalList().get(i).getDone() == false)){
    			today++;
    		}
    		
    		//Checks if date of task matches the date "tomorrow" and ensures that task is still undone 
    		else if(tasks.getInternalList().get(i).getDate().getFullDate().equals(DATEFORMATTER.format(calendar_tmr.getTime())) && (tasks.getInternalList().get(i).getDone() == false)){
    			tomorrow++;
    		}
    		
    		//Checks if date of task matches the date "next week" and ensures that task is still undone 
//    		else if(tasks.getInternalList().get(i).getDate().getFullDate().equals(DATEFORMATTER.format(calendar_nextWeek.getTime())) && (tasks.getInternalList().get(i).getDone() == false)){
//    			nextWeek++;
//    		}
    		
    		else if(tasks.getInternalList().get(i).getOverdue()){
    			overdue++;
    		}
    		
    	}
    	
    	todayCounter = today;
    	tomorrowCounter = tomorrow;
    	overdueCounter = overdue;
    	doneCounter = done;
    	undoneCounter = undone;
    	
    }
    
}
