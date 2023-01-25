package pl.envelo.moovelo.controller.mapper.actor;

import pl.envelo.moovelo.controller.dto.actor.BasicUserDto;
import pl.envelo.moovelo.entity.actors.BasicUser;

public class BasicUserMapper {

    public static BasicUserDto map(BasicUser basicUser) {

        return new BasicUserDto(basicUser.getFirstname(), basicUser.getLastname());
    }
}
