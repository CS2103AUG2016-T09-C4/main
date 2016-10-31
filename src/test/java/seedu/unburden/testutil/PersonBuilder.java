package seedu.unburden.testutil;

import seedu.unburden.commons.exceptions.IllegalValueException;
import seedu.unburden.model.tag.Tag;
import seedu.unburden.model.task.*;

/**
 * A class which builds testing tasks
 * @@author A0147986H
 */
public class PersonBuilder {

    private TestTask person;

    public PersonBuilder() {
        this.person = new TestTask();
    }

    public PersonBuilder withName(String name) throws IllegalValueException {
        this.person.setName(new Name(name));
        return this;
    }

    public PersonBuilder withDate(String date) throws IllegalValueException {
        this.person.setDate(new Date(date));
        return this;
    }

    public PersonBuilder withStartTime(String startTime) throws IllegalValueException {
        this.person.setStartTime(new Time(startTime));
        return this;
    }
    
    public PersonBuilder withEndTime(String endTime) throws IllegalValueException {
        this.person.setEndTime(new Time(endTime));
        return this;
    }
    
    public PersonBuilder withTaskDescription(String taskD) throws IllegalValueException {
        this.person.setTaskDescription(new TaskDescription(taskD));
        return this;
    }
    
    public PersonBuilder withTags(String ... tags) throws IllegalValueException {
        for (String tag: tags) {
            person.getTags().add(new Tag(tag));
        }
        return this;
    }
   
    public TestTask build() {
        return this.person;
    }

}
