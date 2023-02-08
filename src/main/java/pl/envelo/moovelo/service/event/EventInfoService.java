package pl.envelo.moovelo.service.event;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.envelo.moovelo.entity.Location;
import pl.envelo.moovelo.entity.categories.Category;
import pl.envelo.moovelo.entity.events.EventInfo;
import pl.envelo.moovelo.service.LocationService;
import pl.envelo.moovelo.service.category.CategoryService;

@AllArgsConstructor
@Service
public class EventInfoService {
    private final LocationService locationService;
    private final CategoryService categoryService;

    public EventInfo getEventInfoWithLocationCoordinates(EventInfo eventInfo) {
        Location locationBeforeGeocodingApiRequest = eventInfo.getLocation();
        Location locationAfterGeocodingApiRequest =
                locationService.getLocationFromGeocodingApi(locationBeforeGeocodingApiRequest);
        eventInfo.setLocation(locationAfterGeocodingApiRequest);
        return eventInfo;
    }

    public EventInfo checkIfCategoryExists(EventInfo eventInfo) {
        Category category = categoryService.checkIfCategoryExists(eventInfo.getCategory());
        eventInfo.setCategory(category);
        return eventInfo;
    }
}
