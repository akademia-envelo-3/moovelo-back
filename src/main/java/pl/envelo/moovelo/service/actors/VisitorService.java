package pl.envelo.moovelo.service.actors;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.envelo.moovelo.Constants;
import pl.envelo.moovelo.config.security.JwtTokens;
import pl.envelo.moovelo.controller.dto.actor.VisitorDto;
import pl.envelo.moovelo.service.EmailService;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
@Slf4j
public class VisitorService {

    @Autowired
    private EmailService emailService;

    public void sendMailWithConfirmationLink(VisitorDto visitorDto, Long externalEventId) {
        String token = JwtTokens.createConfirmationToken(visitorDto.getFirstname(), visitorDto.getLastname(), visitorDto.getEmail(), externalEventId);
        String message = Constants.URL + "/api/v1/externalEvent/visitor/" + token;
        emailService.sendSimpleMessage(visitorDto.getEmail(), "Event confirmation", message);
    }

    public Map<String, String> getVisitorDetailsFromToken(String token) {
        log.info("VisitorService - getVisitorDetailsFromToken(token = '{}')", token);
        JWTVerifier verifier = JWT.require(JwtTokens.algorithm).build();
        DecodedJWT decodedJwt = verifier.verify(token);
        Map<String, String> results = new HashMap<>();
        results.put("firstname", decodedJwt.getClaim("firstname").toString());
        results.put("lastname", decodedJwt.getClaim("lastname").toString());
        results.put("email", decodedJwt.getClaim("email").toString());
        results.put("externalEventId", decodedJwt.getClaim("externalEventId").toString());
        log.info("VisitorService - getVisitorDetailsFromToken return {}", results);
        return results;
    }
}
