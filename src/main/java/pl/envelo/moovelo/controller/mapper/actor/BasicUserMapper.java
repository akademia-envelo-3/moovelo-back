package pl.envelo.moovelo.controller.mapper.actor;

import pl.envelo.moovelo.controller.dto.actor.BasicUserDto;
import pl.envelo.moovelo.entity.actors.BasicUser;

public class BasicUserMapper {

    public static BasicUserDto map(BasicUser basicUser) {

        return new BasicUserDto(basicUser.getId(), basicUser.getFirstname(), basicUser.getLastname());
    }

    public static BasicUser map(BasicUserDto basicUserDto) {
        BasicUser basicUser = new BasicUser();
        basicUser.setId(basicUserDto.getId());
        basicUser.setFirstname(basicUserDto.getFirstname());
        basicUser.setLastname(basicUserDto.getLastname());
        return basicUser;
    }
}
