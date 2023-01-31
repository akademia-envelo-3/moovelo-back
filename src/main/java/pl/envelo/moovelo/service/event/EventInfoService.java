package pl.envelo.moovelo.service.event;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.envelo.moovelo.entity.Location;
import pl.envelo.moovelo.entity.events.EventInfo;
import pl.envelo.moovelo.service.LocationService;

@AllArgsConstructor
@Service
public class EventInfoService {
    private final LocationService locationService;

    public EventInfo getEventInfoWithLocationCoordinates(EventInfo eventInfo) {
        Location locationBeforeApiRequest = eventInfo.getLocation();
        Location locationAfterGeocodingApiRequest = locationService.getLocationFromGeocodingApi(locationBeforeApiRequest);
        eventInfo.setLocation(locationAfterGeocodingApiRequest);
        return eventInfo;
    }
}
