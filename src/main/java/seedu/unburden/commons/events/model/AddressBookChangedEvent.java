package seedu.unburden.commons.events.model;

import seedu.unburden.commons.events.BaseEvent;
import seedu.unburden.model.ReadOnlyListOfTask;

/** Indicates the ListOfTask in the model has changed*/
public class AddressBookChangedEvent extends BaseEvent {

    public final ReadOnlyListOfTask data;

    public AddressBookChangedEvent(ReadOnlyListOfTask data){
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of persons " + data.getTaskList().size() + ", number of tags " + data.getTagList().size();
    }
}
