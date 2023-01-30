package pl.envelo.moovelo.service.event;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.envelo.moovelo.service.LocationService;

@RequiredArgsConstructor
@Service
public class EventInfoService {

    LocationService locationService;
}
