package pl.envelo.moovelo.entity.actors;


public abstract class User extends Person {
    Long id;
    String login;
    String password;
    Role role;
}
