package pl.envelo.moovelo.service.actors;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import pl.envelo.moovelo.Constants;
import pl.envelo.moovelo.config.security.JwtTokens;
import pl.envelo.moovelo.controller.dto.actor.VisitorDto;
import pl.envelo.moovelo.entity.actors.Visitor;
import pl.envelo.moovelo.exception.NoContentException;
import pl.envelo.moovelo.exception.VisitorAlreadyAssignedException;
import pl.envelo.moovelo.exception.VisitorAlreadyExistsException;
import pl.envelo.moovelo.repository.actors.VisitorRepository;
import pl.envelo.moovelo.service.EmailService;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@Service
@Slf4j
public class VisitorService {

    private EmailService emailService;
    private VisitorRepository visitorRepository;
    private final TemplateEngine templateEngine;

    public Visitor getVisitor(Long visitorId) {
        return visitorRepository.findById(visitorId)
                .orElseThrow(() -> new NoContentException("Visitor with id = '" + visitorId + "' does not exist!"));
    }

    public Visitor createOrGetExistingVisitor(Map<String, String> visitorDetails) {
        String email = visitorDetails.get("email");
        String firstname = visitorDetails.get("firstname").trim();
        String lastname = visitorDetails.get("lastname").trim();
        Optional<Visitor> optionalVisitor = visitorRepository.findByEmail(email);

        if (optionalVisitor.isPresent()) {
            Visitor visitor = optionalVisitor.get();
            if (visitor.getFirstname().equals(firstname) && visitor.getLastname().equals(lastname)) {
                return visitor;
            } else {
                throw new VisitorAlreadyExistsException("Visitor with email = '" + email + "' already exist!");
            }
        }

        Visitor visitor = new Visitor();
        visitor.setFirstname(visitorDetails.get("firstname"));
        visitor.setLastname(visitorDetails.get("lastname"));
        visitor.setEmail(visitorDetails.get("email"));

        return visitorRepository.save(visitor);
    }

    public void removeVisitorWithNoExternalEvents(Visitor visitor) {
        log.info("VisitorService - removeVisitorWithNoExternalEvents(visitor = '{}')", visitor);

        if (visitor.getEvents().isEmpty()) {
            visitorRepository.delete(visitor);
            log.info("VisitorService - removeVisitorWithNoExternalEvents(visitor = '{}') - visitor removed", visitor);
        }

        log.info("VisitorService - removeVisitorWithNoExternalEvents(visitor = '{}') - contains events", visitor);
    }

    public void sendConfirmationLink(VisitorDto visitorDto, Long externalEventId) throws MessagingException {
        String token = JwtTokens.createConfirmationToken(visitorDto.getFirstname(), visitorDto.getLastname(), visitorDto.getEmail(), externalEventId);
        String url = Constants.URL + "/api/v1/externalEvents/visitor/" + token;
        Context context = new Context();
        context.setVariable("url", url);
        String body = templateEngine.process("email-confirmation-link", context);
        emailService.sendSimpleMessage(visitorDto.getEmail(), "Event confirmation", body);
    }

    public void sendCancellationLink(String visitorEmail, Long visitorId, Long externalEventId) throws MessagingException {
        String token = JwtTokens.createCancellationToken(visitorId, externalEventId);
        String url = Constants.URL + "/api/v1/externalEvents/visitor/" + token + "/remove";
        Context context = new Context();
        context.setVariable("url", url);
        String body = templateEngine.process("email-cancellation-link", context);
        emailService.sendSimpleMessage(visitorEmail, "Successfully add to The event", body);
    }

    public Map<String, String> getVisitorDetailsFromConfirmationToken(String token) {
        log.info("VisitorService - getVisitorDetailsFromToken(token = '{}')", token);
        JWTVerifier verifier = JWT.require(JwtTokens.algorithm).build();
        DecodedJWT decodedJwt = verifier.verify(token);

        if (decodedJwt.getClaims().isEmpty() || decodedJwt.getClaims().size() < 4) {
            throw new NoContentException("Token does not contain correct data!");
        }

        Map<String, String> results = new HashMap<>();
        results.put("firstname", decodedJwt.getClaim("firstname").toString());
        results.put("lastname", decodedJwt.getClaim("lastname").toString());
        results.put("email", decodedJwt.getClaim("email").toString());
        results.put("externalEventId", decodedJwt.getClaim("externalEventId").toString());
        log.info("VisitorService - getVisitorDetailsFromToken return {}", results);
        return results;
    }

    public Map<String, String> getVisitorDetailsFromCancellationToken(String token) {
        log.info("VisitorService - getVisitorDetailsFromCancellationToken(token = '{}')", token);
        JWTVerifier verifier = JWT.require(JwtTokens.algorithm).build();
        DecodedJWT decodedJwt = verifier.verify(token);
        Map<String, String> results = new HashMap<>();
        results.put("visitorId", decodedJwt.getClaim("visitorId").toString());
        results.put("externalEventId", decodedJwt.getClaim("externalEventId").toString());
        log.info("VisitorService - getVisitorDetailsFromCancellationToken return {}", results);
        return results;
    }

}
