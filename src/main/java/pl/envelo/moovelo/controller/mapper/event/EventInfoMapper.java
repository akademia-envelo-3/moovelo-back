package pl.envelo.moovelo.controller.mapper.event;

import pl.envelo.moovelo.controller.dto.event.EventInfoDto;
import pl.envelo.moovelo.controller.mapper.category.CategoryMapper;
import pl.envelo.moovelo.entity.events.Event;
import pl.envelo.moovelo.entity.events.EventInfo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EventInfoMapper {
    private final static DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static EventInfoDto mapEventInfoToEventInfoDto(EventInfo eventInfo) {
        return EventInfoDto.builder()
                .id(eventInfo.getId())
                .eventId(eventInfo.getId())
                .name(eventInfo.getName())
                .description(eventInfo.getDescription())
                .category(CategoryMapper.mapCategoryToCategoryDto(eventInfo.getCategory()))
                .startDate(eventInfo.getStartDate().format(DATE_FORMAT))
                .isConfirmationRequired(eventInfo.getIsConfirmationRequired())
//                .location()
//                .attachments()
                .build();

    }

    public static EventInfo mapEventInfoDtoToEventInfo(EventInfoDto eventInfoDto, long id, Event event) {
        EventInfo eventInfo = new EventInfo();
        eventInfo.setId(id);
        eventInfo.setEvent(event);
        eventInfo.setDescription(eventInfoDto.getDescription());
        eventInfo.setStartDate(LocalDateTime.parse(eventInfoDto.getStartDate(), DATE_FORMAT));
        //   eventInfo.setLocation(LocationMapper.mapLocationDtoToLocation(eventInfoDto.getLocation()));
        eventInfo.setIsConfirmationRequired(eventInfoDto.isConfirmationRequired());
        eventInfo.setCategory(CategoryMapper.mapCategoryDtoToCategory(eventInfoDto.getCategory(), eventInfoDto.getCategory().getId()));
        //    eventInfo.setFiles(eventInfoDto.getAttachments().stream().map(attachmentDto -> AttachmentMapper.mapAttachmentDtoToAttachment(attachmentDto)));
        return eventInfo;
    }
}
