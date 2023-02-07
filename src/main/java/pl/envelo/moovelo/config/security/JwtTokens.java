package pl.envelo.moovelo.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.core.GrantedAuthority;
import pl.envelo.moovelo.Constants;
import pl.envelo.moovelo.entity.actors.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class JwtTokens {

    public static String createAccessToken(User user, HttpServletRequest request, Algorithm algorithm) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + Constants.ACCESS_TOKEN_DURATION_TIME))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .sign(algorithm);
    }

    public static String createRefreshToken(User user, HttpServletRequest request, Algorithm algorithm) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + Constants.RESET_TOKEN_DURATION_TIME))
                .withIssuer(request.getRequestURL().toString())
                .sign(algorithm);
    }
}
