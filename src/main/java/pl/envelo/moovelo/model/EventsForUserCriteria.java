package pl.envelo.moovelo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventsForUserCriteria {
    private boolean acceptedEvents;
    private boolean pendingEvents;
    private boolean rejectedEvents;
    private boolean group;
    private Long groupId;
    private String groupName;
    private boolean owner;
    private String category;
    private String name;
}
