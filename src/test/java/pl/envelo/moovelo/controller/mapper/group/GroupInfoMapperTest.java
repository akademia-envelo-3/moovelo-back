package pl.envelo.moovelo.controller.mapper.group;

import org.junit.jupiter.api.Test;
import pl.envelo.moovelo.controller.dto.group.GroupInfoDto;
import pl.envelo.moovelo.entity.groups.GroupInfo;

import static org.junit.jupiter.api.Assertions.*;

class GroupInfoMapperTest {

    @Test
    void map() {
        GroupInfoDto dto = GroupInfoDto.builder()
                .name("Java")
                .description("nauka Javy")
                .build();

        GroupInfo entity = GroupInfoMapper.map(dto);

        assertEquals(entity.getName(), dto.getName());
        assertEquals(entity.getDescription(), dto.getDescription());
    }
}