package pl.envelo.moovelo.service.actors;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.envelo.moovelo.Constants;
import pl.envelo.moovelo.config.security.JwtTokens;
import pl.envelo.moovelo.controller.dto.actor.VisitorDto;
import pl.envelo.moovelo.service.EmailService;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class VisitorService {

    @Autowired
    private EmailService emailService;

    public void sendMailWithConfirmationLink(VisitorDto visitorDto, Long externalEventId) {
        String token = JwtTokens.createConfirmationToken(visitorDto.getFirstname(), visitorDto.getLastname(), visitorDto.getEmail(), externalEventId);
        String message = Constants.URL + "/api/v1/externalEvent/visitor/" + token;
        emailService.sendSimpleMessage(visitorDto.getEmail(), "Event confirmation", message);
    }
}
