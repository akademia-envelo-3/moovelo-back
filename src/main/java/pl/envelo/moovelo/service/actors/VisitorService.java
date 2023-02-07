package pl.envelo.moovelo.service.actors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.envelo.moovelo.config.security.JwtTokens;
import pl.envelo.moovelo.controller.dto.actor.VisitorDto;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class VisitorService {
    public LocalDateTime sendMailWithConfirmationLink(VisitorDto visitorDto, Long externalEventId, LocalDateTime linkCreationDate) {
        return null;
    }
}
