package pl.envelo.moovelo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.envelo.moovelo.controller.dto.location.LocationDto;
import pl.envelo.moovelo.service.LocationService;

@RequestMapping("/api/v1")
@RestController
public class LocationController {

    private final LocationService locationService;

    @Autowired
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }


    @GetMapping("/location")
    public ResponseEntity<LocationDto> getLocation(@RequestParam String address) {
        return ResponseEntity.ok(locationService.getLocation(address));
    }
}
