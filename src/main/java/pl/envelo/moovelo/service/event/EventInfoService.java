package pl.envelo.moovelo.service.event;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.envelo.moovelo.entity.Location;
import pl.envelo.moovelo.entity.categories.Category;
import pl.envelo.moovelo.entity.events.EventInfo;
import pl.envelo.moovelo.repository.event.EventInfoRepository;
import pl.envelo.moovelo.service.LocationService;
import pl.envelo.moovelo.service.category.CategoryService;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;
import java.util.Optional;

@AllArgsConstructor
@Service
public class EventInfoService {
    private final LocationService locationService;
    private final CategoryService categoryService;
    private final EventInfoRepository eventInfoRepository;

    public EventInfo validateEventInfoForCreateEvent(EventInfo eventInfo) {
        getEventInfoWithValidatedCategoryAndLocation(eventInfo);
        return eventInfoRepository.save(eventInfo);
    }

    public EventInfo validateEventInfoForUpdateEvent(EventInfo eventInfo, Long eventInfoInDbId) {
        EventInfo eventInfoInDb = getEventInfoById(eventInfoInDbId);
        EventInfo eventInfoWithValidatedCategoryAndLocation = getEventInfoWithValidatedCategoryAndLocation(eventInfo);
        setAllEventInfoFields(eventInfo, eventInfoInDb, eventInfoWithValidatedCategoryAndLocation);
        return eventInfoInDb;
    }

    private void setAllEventInfoFields(EventInfo eventInfo, EventInfo eventInfoInDb,
                                       EventInfo eventInfoWithValidatedCategoryAndLocation) {
        eventInfoInDb.setLocation(eventInfoWithValidatedCategoryAndLocation.getLocation());
        eventInfoInDb.setCategory(eventInfoWithValidatedCategoryAndLocation.getCategory());
        eventInfoInDb.setName(eventInfo.getName());
        eventInfoInDb.setDescription(eventInfo.getDescription());
        eventInfoInDb.setStartDate(eventInfo.getStartDate());
        eventInfoInDb.setIsConfirmationRequired(eventInfo.getIsConfirmationRequired());
    }

    private EventInfo getEventInfoWithValidatedCategoryAndLocation(EventInfo eventInfo) {
        Location validatedLocation = locationService.validateLocation(eventInfo.getLocation());
        Category validatedCategory = categoryService.validateCategory(eventInfo.getCategory());
        eventInfo.setLocation(validatedLocation);
        eventInfo.setCategory(validatedCategory);
        return eventInfo;
    }

    public EventInfo getEventInfoById(Long id) {
        Optional<EventInfo> eventInfoOptional = eventInfoRepository.findById(id);
        if (eventInfoOptional.isPresent()) {
            return eventInfoOptional.get();
        } else {
            throw new NoSuchElementException("No eventInfo with id: " + id);
        }
    }
}
