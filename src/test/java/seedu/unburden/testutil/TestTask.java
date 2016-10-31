package seedu.unburden.testutil;

import seedu.unburden.model.tag.UniqueTagList;
import seedu.unburden.model.task.*;

/**
 * A mutable person object. For testing only.
 * @@author A0147986H
 */

public class TestTask implements ReadOnlyTask {

    private Name name;
    private TaskDescription taskD;
    private Date date;
    private Time startTime;
    private Time endTime;
    private UniqueTagList tags;
    private boolean done;

    public TestTask() {
        tags = new UniqueTagList();
    }

    public void setName(Name name) {
        this.name = name;
    }

    @Override
    public Name getName() {
        return name;
    }
    
    public void setTaskDescription(TaskDescription taskD) {
        this.taskD = taskD;
    }
        
    @Override
    public TaskDescription getTaskDescription() {
        return taskD;
    }
    
    
    public void setDate(Date date) {
        this.date = date;
    }
    
    @Override
    public Date getDate() {
        return date;
    }
    
    
    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }
    
    @Override
    public Time getStartTime() {
        return startTime;
    }
    
    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }
    
    @Override
    public Time getEndTime() {
        return endTime;
    }

    public void getTags(UniqueTagList tags) {
        this.tags = tags;
    }
    
    @Override
    public UniqueTagList getTags() {
        return tags;
    }

    @Override
    public String toString() {
        return getAsText();
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getName().fullName + " ");
        this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }

    @Override
    public boolean getDone() {
        return done;
    }

    @Override
    public String getDoneString() {
        if(done == true){
            return "Task Done!";
        }
        else{
            return "Task unDone!";
        }
    }
}
