package pl.envelo.moovelo.entity.events;

import pl.envelo.moovelo.entity.Attachment;
import pl.envelo.moovelo.entity.Location;
import pl.envelo.moovelo.entity.categories.Category;

import java.time.LocalDateTime;
import java.util.List;

public class EventInfo {
    Long id;
    Event event;
    String name;
    String description;
    LocalDateTime startDate;
    Location location;
    Boolean isConfirmationRequired;
    Category category;
    List<Attachment> files;
}
