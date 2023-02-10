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
        Location validatedLocation = locationService.validateLocation(eventInfo.getLocation());
        Category validatedCategory = categoryService.validateCategory(eventInfo.getCategory());

        eventInfo.setLocation(validatedLocation);
        eventInfo.setCategory(validatedCategory);

        return eventInfoRepository.save(eventInfo);
    }
}
