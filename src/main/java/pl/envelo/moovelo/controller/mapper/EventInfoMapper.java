package pl.envelo.moovelo.controller.mapper;

import pl.envelo.moovelo.controller.dto.category.CategoryDto;
import pl.envelo.moovelo.controller.dto.event.EventInfoDto;
import pl.envelo.moovelo.entity.events.EventInfo;

public class EventInfoMapper {

    public static EventInfoDto mapEventInfoToEventInfoDto(EventInfo eventInfo) {
        return EventInfoDto.builder()
                .name(eventInfo.getName())
                .category(CategoryMapper.mapCategoryToCategoryDto(eventInfo.getCategory()))
                .startDate(eventInfo.getStartDate().toString())
                .build();
    }
}
