package pl.envelo.moovelo.controller.mapper;

import pl.envelo.moovelo.controller.dto.event.EventInfoListResponseDto;
import pl.envelo.moovelo.entity.events.EventInfo;

public class EventInfoListResponseMapper {

    public static EventInfoListResponseDto mapEventInfoToEventInfoListResponseDto(EventInfo eventInfo) {
        return EventInfoListResponseDto.builder()
                .name(eventInfo.getName())
                .category(CategoryListResponseMapper.mapCategoryToCategoryListResponseDto(eventInfo.getCategory()))
                .build();
    }
}
