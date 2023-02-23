package pl.envelo.moovelo.controller.mapper.event;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.envelo.moovelo.controller.dto.event.eventInfo.EventInfoDto;
import pl.envelo.moovelo.entity.Location;
import pl.envelo.moovelo.entity.categories.Category;
import pl.envelo.moovelo.entity.events.EventInfo;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

class EventInfoMapperTest {

    @Test
    void shouldMapEventInfoToEventInfoDtoAndOtherWay() {
        //given
        Category category = new Category();
        category.setId(20L);
        category.setName("kuligowanie");
        category.setVisible(true);
        Location location = new Location();
        location.setId(10L);
        location.setAltitude(20.0);
        location.setLatitude(40.0);
        location.setPostcode("20-670");
        location.setCity("Sosnowiec");
        location.setStreet("Kubusia Puchatka");
        location.setStreetNumber("40");
        location.setApartmentNumber("10");
        EventInfo eventInfo = new EventInfo();
        eventInfo.setId(5L);
        eventInfo.setName("kulig bez śniegu");
        eventInfo.setDescription("bez śniegu bo nie ma zimy");
        eventInfo.setStartDate(LocalDateTime.now(ZoneId.systemDefault()).truncatedTo(ChronoUnit.SECONDS));
        eventInfo.setLocation(location);
        eventInfo.setIsConfirmationRequired(true);
        eventInfo.setCategory(category);

        //when

        EventInfoDto eventInfoDto = EventInfoMapper.mapEventInfoToEventInfoDto(eventInfo);
        EventInfo eventInfo1 = EventInfoMapper.mapEventInfoDtoToEventInfo(eventInfoDto);

        //then

        Assertions.assertEquals(eventInfo, eventInfo1);
    }
}