package pl.envelo.moovelo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.envelo.moovelo.controller.dto.location.LocationDto;
import pl.envelo.moovelo.entity.Location;
import pl.envelo.moovelo.service.LocationService;

import javax.validation.Valid;

@RequestMapping("/api/v1")
@RestController
public class LocationController {

    private final LocationService locationService;

    @Autowired
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @PostMapping("/location")
    public ResponseEntity<Location> saveLocation(@RequestBody Location location){
        return ResponseEntity.ok(locationService.saveLocation(location));
    }

    @GetMapping("/location")
    public ResponseEntity<LocationDto> getLocation(@RequestParam String address) {
        return ResponseEntity.ok(locationService.getLocation(address));
    }
}
