package pl.envelo.moovelo.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.core.GrantedAuthority;
import pl.envelo.moovelo.Constants;
import pl.envelo.moovelo.entity.actors.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class JwtTokens {

    public static final Algorithm algorithm = Algorithm.HMAC256(Constants.SECRET_KEY_FOR_JWT_ALGORITHM.getBytes());

    public static String createAccessToken(User user, HttpServletRequest request) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + Constants.ACCESS_TOKEN_DURATION_TIME))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("role", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .withClaim("userId", user.getId())
                .sign(algorithm);
    }

    public static String createRefreshToken(User user, HttpServletRequest request) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + Constants.RESET_TOKEN_DURATION_TIME))
                .withIssuer(request.getRequestURL().toString())
                .sign(algorithm);
    }

    public static String createConfirmationToken(String firstname, String lastname, String email, Long externalEventId) {
        return JWT.create()
                .withClaim("firstname", firstname)
                .withClaim("lastname", lastname)
                .withClaim("email", email)
                .withClaim("externalEventId", externalEventId)
                .withExpiresAt(new Date(System.currentTimeMillis() + Constants.VISITOR_CONFIRM_TOKEN_DURATION_TIME))
                .sign(algorithm);
    }

    public static String createCancellationToken(Long visitorId, Long externalEventId) {
        return JWT.create()
                .withClaim("visitorId", visitorId)
                .withClaim("externalEventId", externalEventId)
                .sign(algorithm);
    }
}
