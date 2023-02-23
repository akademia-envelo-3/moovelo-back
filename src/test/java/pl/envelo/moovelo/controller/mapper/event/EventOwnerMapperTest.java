package pl.envelo.moovelo.controller.mapper.event;

import org.junit.jupiter.api.Test;
import pl.envelo.moovelo.controller.dto.event.ownership.EventOwnerDto;
import pl.envelo.moovelo.controller.mapper.event.manager.EventMapper;
import pl.envelo.moovelo.entity.actors.BasicUser;
import pl.envelo.moovelo.entity.events.Event;
import pl.envelo.moovelo.entity.events.EventOwner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EventOwnerMapperTest {

    @Test
    void shouldMapEventOwnerToEventOwnerDto() {
        //given
        BasicUser basicUser = new BasicUser();
        basicUser.setId(1L);
        basicUser.setFirstname("John");
        basicUser.setLastname("Doe");
        EventOwner eventOwner = new EventOwner();
        eventOwner.setId(10L);
        eventOwner.setUserId(basicUser.getId());
        Event event = new Event();
        event.setId(40L);
        Event event1 = new Event();
        event1.setId(50L);
        eventOwner.setEvents(List.of(event, event1));
        EventOwnerDto eventOwnerExpectedDto = EventOwnerDto.builder()
                .id(10L)
                .firstname("John")
                .lastname("Doe")
                .userId(1L)
                .events(List.of(EventMapper.mapEventToEventIdDto(event), EventMapper.mapEventToEventIdDto(event1)))
                .build();

        //when
        EventOwnerDto eventOwnerDto = EventOwnerMapper.mapEventOwnerToEventOwnerDto(eventOwner, basicUser);


        //then
        assertEquals(eventOwnerExpectedDto, eventOwnerDto);
    }

    @Test
    void shouldMapEventOwnerDtoToEventOwner() {
        //given
        EventOwnerDto eventOwnerDto = EventOwnerDto.builder()
                .userId(40)
                .build();

        //when
        EventOwner eventOwner = EventOwnerMapper.mapEventOwnerDtoToEventOwner(eventOwnerDto);

        //then
        assertEquals(eventOwnerDto.getUserId(), eventOwner.getUserId());
    }
}