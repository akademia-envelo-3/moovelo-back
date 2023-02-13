package pl.envelo.moovelo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import pl.envelo.moovelo.entity.actors.User;
import pl.envelo.moovelo.service.actors.UserService;

@Component
public class AuthenticatedUser {

    @Autowired
    private UserService userService;

    public User getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getPrincipal().toString();
        return userService.getUserByEmail(email);
    }
}
