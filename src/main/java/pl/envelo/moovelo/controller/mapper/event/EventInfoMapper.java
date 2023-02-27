package pl.envelo.moovelo.controller.mapper.event;

import pl.envelo.moovelo.controller.dto.event.eventInfo.EventInfoDto;
import pl.envelo.moovelo.controller.dto.event.eventInfo.EventInfoListResponseDto;
import pl.envelo.moovelo.controller.mapper.LocationMapper;
import pl.envelo.moovelo.controller.mapper.category.CategoryMapper;
import pl.envelo.moovelo.entity.events.EventInfo;

import java.time.LocalDateTime;

import static pl.envelo.moovelo.Constants.DATE_FORMAT;

public class EventInfoMapper {

    public static EventInfoDto mapEventInfoToEventInfoDto(EventInfo eventInfo) {
        return EventInfoDto.builder()
                .id(eventInfo.getId())
                .name(eventInfo.getName())
                .description(eventInfo.getDescription())
                .category(CategoryMapper.mapCategoryToCategoryListResponseDto(eventInfo.getCategory()))
                .startDate(eventInfo.getStartDate().format(DATE_FORMAT))
                .isConfirmationRequired(eventInfo.getIsConfirmationRequired())
                .location(LocationMapper.mapFromLocationEntityToLocationDto(eventInfo.getLocation()))
                // TODO
//                .attachments()
                .build();
    }

    public static EventInfoListResponseDto mapEventInfoToEventInfoListResponseDto(EventInfo eventInfo) {
        return EventInfoListResponseDto.builder()
                .name(eventInfo.getName())
                .category(CategoryMapper.mapCategoryToCategoryListResponseDto(eventInfo.getCategory()))
                .build();
    }

    public static EventInfo mapEventInfoDtoToEventInfo(EventInfoDto eventInfoDto) {
        EventInfo eventInfo = new EventInfo();
        eventInfo.setId(eventInfoDto.getId());
        eventInfo.setName(eventInfoDto.getName());
        eventInfo.setDescription(eventInfoDto.getDescription());
        eventInfo.setStartDate(LocalDateTime.parse(eventInfoDto.getStartDate(), DATE_FORMAT));
        eventInfo.setLocation(LocationMapper.mapFromLocationDtoToLocationEntity(
                eventInfoDto.getLocation().getId(), eventInfoDto.getLocation()));
        eventInfo.setIsConfirmationRequired(eventInfoDto.isConfirmationRequired());
        eventInfo.setCategory(CategoryMapper.mapCategoryListDtoToCategory(eventInfoDto.getCategory()));
        /// TODO: 26.01.2023
        //    eventInfo.setFiles(eventInfoDto.getAttachments().stream().map(attachmentDto -> AttachmentMapper.mapAttachmentDtoToAttachment(attachmentDto)));
        return eventInfo;
    }
}
