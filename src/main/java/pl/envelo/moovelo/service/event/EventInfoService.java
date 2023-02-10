package pl.envelo.moovelo.service.event;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.envelo.moovelo.entity.Location;
import pl.envelo.moovelo.entity.categories.Category;
import pl.envelo.moovelo.entity.events.EventInfo;
import pl.envelo.moovelo.repository.event.EventInfoRepository;
import pl.envelo.moovelo.service.LocationService;
import pl.envelo.moovelo.service.category.CategoryService;

@AllArgsConstructor
@Service
public class EventInfoService {
    private final LocationService locationService;
    private final CategoryService categoryService;
    private final EventInfoRepository eventInfoRepository;

    public EventInfo validateEventInfo(EventInfo eventInfo) {
        Location locationBeforeValidation = eventInfo.getLocation();
        Location validatedLocation = locationService.validateLocation(locationBeforeValidation);

        eventInfo.setLocation(validatedLocation);
        eventInfoRepository.save(eventInfo);
        return eventInfo;
    }

    public EventInfo checkIfCategoryExists(EventInfo eventInfo) {
        Category category = categoryService.checkIfCategoryExists(eventInfo.getCategory());
        eventInfo.setCategory(category);
        return eventInfo;
    }
}
