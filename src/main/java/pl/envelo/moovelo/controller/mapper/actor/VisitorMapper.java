package pl.envelo.moovelo.controller.mapper.actor;

import pl.envelo.moovelo.controller.dto.actor.VisitorDto;
import pl.envelo.moovelo.entity.actors.Visitor;

public class VisitorMapper {

    public static VisitorDto map(Visitor visitor) {
        return VisitorDto
                .builder()
                .id(visitor.getId())
                .firstname(visitor.getFirstname())
                .lastname(visitor.getLastname())
                .email(visitor.getEmail())
                .build();
    }
}
