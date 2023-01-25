package pl.envelo.moovelo.controller.event;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.envelo.moovelo.controller.dto.event.EventOwnerDto;
import pl.envelo.moovelo.controller.mapper.event.EventOwnerMapper;
import pl.envelo.moovelo.entity.events.Event;
import pl.envelo.moovelo.entity.events.EventOwner;
import pl.envelo.moovelo.service.event.EventService;

@RequiredArgsConstructor
@RestController
public class EventController {

}
