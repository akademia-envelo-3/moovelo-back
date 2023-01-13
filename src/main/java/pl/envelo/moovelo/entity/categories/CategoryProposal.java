package pl.envelo.moovelo.entity.categories;

import pl.envelo.moovelo.entity.actors.BasicUser;

import java.time.LocalDateTime;

public class CategoryProposal {
    Long id;
    BasicUser basicUser;
    String name;
    String description;
    LocalDateTime date;
}
