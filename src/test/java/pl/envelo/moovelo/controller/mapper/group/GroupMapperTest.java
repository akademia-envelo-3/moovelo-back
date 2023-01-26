package pl.envelo.moovelo.controller.mapper.group;

import org.junit.jupiter.api.Test;
import pl.envelo.moovelo.controller.dto.group.GroupDto;
import pl.envelo.moovelo.controller.mapper.actor.BasicUserMapper;
import pl.envelo.moovelo.controller.mapper.group.groupownership.GroupOwnerMapper;
import pl.envelo.moovelo.entity.actors.BasicUser;
import pl.envelo.moovelo.entity.events.Event;
import pl.envelo.moovelo.entity.events.InternalEvent;
import pl.envelo.moovelo.entity.groups.Group;
import pl.envelo.moovelo.entity.groups.GroupInfo;
import pl.envelo.moovelo.entity.groups.GroupOwner;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class GroupMapperTest {

    @Test
    void map() {
        Group entity = new Group();
        entity.setId(1L);

        GroupOwner groupOwner = new GroupOwner();
        groupOwner.setId(11L);
        groupOwner.setUserId(20L);
        groupOwner.setGroups(List.of(entity));

        entity.setGroupOwner(groupOwner);

        BasicUser basicUser1 = new BasicUser();
        basicUser1.setId(3L);

        BasicUser basicUser2 = new BasicUser();
        basicUser2.setId(4L);

        entity.setMembers(List.of(basicUser1, basicUser2));

        GroupInfo groupInfo = new GroupInfo();
        groupInfo.setId(1L);
        groupInfo.setName("Java");
        groupInfo.setDescription("Nauka Javy");

        entity.setGroupInfo(groupInfo);

        InternalEvent event1 = new InternalEvent();
        event1.setId(1L);
        InternalEvent event2 = new InternalEvent();
        event2.setId(2L);

        entity.setEvents(List.of(event1, event2));



        GroupDto dto = GroupMapper.map(entity);



        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getGroupInfo().getName(), dto.getName());
        assertEquals(entity.getGroupInfo().getDescription(), dto.getDescription());
        assertEquals(GroupOwnerMapper.map(entity.getGroupOwner()), dto.getGroupOwner());
        assertEquals(entity.getMembers().size(), dto.getNumberOfMembers());
        assertEquals(entity.getMembers().stream().map(BasicUserMapper::map).collect(Collectors.toList()), dto.getGroupMembers());
        //TODO: after merging method: EventResponseDto map(Event) in class EventMapper
//        assertEquals(entity.getEvents().stream().map(EventMapper::map).collect(Collectors.toList()), dto.getEvents());


    }
}