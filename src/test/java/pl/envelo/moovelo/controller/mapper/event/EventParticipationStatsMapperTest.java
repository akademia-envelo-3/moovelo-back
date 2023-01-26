package pl.envelo.moovelo.controller.mapper.event;

import org.junit.jupiter.api.Test;
import pl.envelo.moovelo.controller.dto.event.participation.EventParticipationStatsDto;
import pl.envelo.moovelo.controller.mapper.actor.BasicUserMapper;
import pl.envelo.moovelo.controller.mapper.actor.VisitorMapper;
import pl.envelo.moovelo.entity.actors.BasicUser;
import pl.envelo.moovelo.entity.actors.Visitor;
import pl.envelo.moovelo.entity.events.Event;
import pl.envelo.moovelo.entity.events.ExternalEvent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class EventParticipationStatsMapperTest {

    @Test
    void shouldMapEventToEventParticipationStatsDto() {
        //given
        Event event = new Event();
        event.setAcceptedStatusUsers(createAccepted());
        event.setPendingStatusUsers(createPending());
        event.setRejectedStatusUsers(createRejected());
        EventParticipationStatsDto expectedEventParticipationStatsDto = EventParticipationStatsDto.builder()
                .accepted(createAccepted().stream().map(BasicUserMapper::map).collect(Collectors.toList()))
                .pending(createPending().stream().map(BasicUserMapper::map).collect(Collectors.toList()))
                .rejected(createRejected().stream().map(BasicUserMapper::map).collect(Collectors.toList()))
                .build();

        //when
        EventParticipationStatsDto eventParticipationStatsDto =
                EventParticipationStatsMapper.mapEventToEventParticipationStatsDto(event);

        //then
        assertEquals(expectedEventParticipationStatsDto.getAccepted(), eventParticipationStatsDto.getAccepted());
        assertEquals(expectedEventParticipationStatsDto.getPending(), eventParticipationStatsDto.getPending());
        assertEquals(expectedEventParticipationStatsDto.getRejected(), eventParticipationStatsDto.getRejected());

    }

    private Set<BasicUser> createRejected() {
        HashSet<BasicUser> rejected = new HashSet<>();
        BasicUser basicUser4 = new BasicUser();
        basicUser4.setId(1L);
        basicUser4.setFirstname("Marta");
        basicUser4.setLastname("Tarta");
        rejected.add(basicUser4);
        return rejected;
    }

    private Set<BasicUser> createPending() {
        HashSet<BasicUser> pending = new HashSet<>();
        BasicUser basicUser2 = new BasicUser();
        basicUser2.setId(2L);
        basicUser2.setFirstname("Jan");
        basicUser2.setLastname("Kowal");
        BasicUser basicUser3 = new BasicUser();
        basicUser3.setId(4L);
        basicUser3.setFirstname("Karolina");
        basicUser3.setLastname("Kot");
        pending.add(basicUser2);
        pending.add(basicUser3);
        return pending;
    }

    private HashSet<BasicUser> createAccepted() {
        HashSet<BasicUser> accepted = new HashSet<>();
        BasicUser basicUser = new BasicUser();
        basicUser.setId(10L);
        basicUser.setFirstname("Lech");
        basicUser.setLastname("Premium");
        BasicUser basicUser1 = new BasicUser();
        basicUser1.setId(50L);
        basicUser1.setFirstname("Roch");
        basicUser1.setLastname("Rochowy");
        accepted.add(basicUser);
        accepted.add(basicUser1);
        return accepted;
    }

    @Test
    void shouldMapExternalEventToEventParticipationStatsDto() {
        // given
        ExternalEvent externalEvent = new ExternalEvent();
        externalEvent.setAcceptedStatusUsers(createAccepted());
        externalEvent.setPendingStatusUsers(createPending());
        externalEvent.setRejectedStatusUsers(createRejected());
        externalEvent.setVisitors(createVisitors());
        EventParticipationStatsDto expectedEventParticipationStatsDto = EventParticipationStatsDto.builder()
                .accepted(createAccepted().stream().map(BasicUserMapper::map).collect(Collectors.toList()))
                .pending(createPending().stream().map(BasicUserMapper::map).collect(Collectors.toList()))
                .rejected(createRejected().stream().map(BasicUserMapper::map).collect(Collectors.toList()))
                .visitors(createVisitors().stream().map(VisitorMapper::map).collect(Collectors.toList()))
                .build();

        //when
        EventParticipationStatsDto eventParticipationStatsDto
                = EventParticipationStatsMapper.mapExternalEventToEventParticipationStatsDto(externalEvent);


        //then
        assertEquals(expectedEventParticipationStatsDto.getAccepted(), eventParticipationStatsDto.getAccepted());
        assertEquals(expectedEventParticipationStatsDto.getPending(), eventParticipationStatsDto.getPending());
        assertEquals(expectedEventParticipationStatsDto.getRejected(), eventParticipationStatsDto.getRejected());
        assertEquals(expectedEventParticipationStatsDto.getVisitors(), eventParticipationStatsDto.getVisitors());

    }

    private List<Visitor> createVisitors() {
        List<Visitor> visitors = new ArrayList<>();
        Visitor visitor = new Visitor();
        visitor.setId(1L);
        visitor.setFirstname("Kamil");
        visitor.setLastname("Krokodyl");
        visitor.setEmail("krok@kok.pl");
        Visitor visitor1 = new Visitor();
        visitor.setId(2L);
        visitor1.setFirstname("Kamila");
        visitor1.setLastname("Krokodyla");
        visitor1.setEmail("kroka@koka.pl");
        visitors.add(visitor);
        visitors.add(visitor1);
        return visitors;
    }
}