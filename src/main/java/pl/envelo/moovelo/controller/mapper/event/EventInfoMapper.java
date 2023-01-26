package pl.envelo.moovelo.controller.mapper.event;

import pl.envelo.moovelo.controller.dto.event.EventInfoDto;
import pl.envelo.moovelo.controller.mapper.category.CategoryMapper;
import pl.envelo.moovelo.entity.events.Event;
import pl.envelo.moovelo.entity.events.EventInfo;
import java.time.LocalDateTime;
import static pl.envelo.moovelo.Constants.DATE_FORMAT;

public class EventInfoMapper {

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

    public static EventInfo mapEventInfoDtoToEventInfo(EventInfoDto eventInfoDto, Event event) {
        EventInfo eventInfo = new EventInfo();
        eventInfo.setId(eventInfoDto.getId());
        eventInfo.setEvent(event);
        eventInfo.setDescription(eventInfoDto.getDescription());
        eventInfo.setStartDate(LocalDateTime.parse(eventInfoDto.getStartDate(), DATE_FORMAT));
        //   eventInfo.setLocation(LocationMapper.mapLocationDtoToLocation(eventInfoDto.getLocation()));
        eventInfo.setIsConfirmationRequired(eventInfoDto.isConfirmationRequired());
        eventInfo.setCategory(CategoryMapper.mapCategoryDtoToCategory(eventInfoDto.getCategory()));
        //    eventInfo.setFiles(eventInfoDto.getAttachments().stream().map(attachmentDto -> AttachmentMapper.mapAttachmentDtoToAttachment(attachmentDto)));
        return eventInfo;
    }
}
