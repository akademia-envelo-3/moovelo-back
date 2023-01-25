package pl.envelo.moovelo.controller.event;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.envelo.moovelo.controller.dto.event.ownership.EventOwnershipRequestDto;
import pl.envelo.moovelo.entity.events.Event;
import pl.envelo.moovelo.service.event.EventService;

@RequiredArgsConstructor
@RestController
public class EventController {

    @PatchMapping("api/v1/events/{eventId}/ownership")
    public void updateEventOwnerById(@PathVariable(required = true) Long eventId,
                                     @RequestBody EventOwnershipRequestDto eventOwnershipRequestDto) {
        eventService.updateEventOwnershipById(eventId, eventOwnershipRequestDto.getUserId(), eventOwnershipRequestDto.getNewOwnerId());
    }

}
