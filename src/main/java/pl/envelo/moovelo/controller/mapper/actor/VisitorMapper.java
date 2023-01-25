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

    public static Visitor map(VisitorDto visitorDto) {
        Visitor visitor = new Visitor();
        visitor.setEmail(visitorDto.getEmail());
        visitor.setFirstname(visitorDto.getFirstname());
        visitor.setLastname(visitorDto.getLastname());
        return visitor;
    }
}
