package pl.envelo.moovelo.controller.user;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.envelo.moovelo.Constants;
import pl.envelo.moovelo.config.security.JwtTokens;
import pl.envelo.moovelo.entity.actors.User;
import pl.envelo.moovelo.service.actors.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("api/refreshToken")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refreshToken = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = JwtTokens.algorithm;
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJwt = verifier.verify(refreshToken);
                String email = decodedJwt.getSubject();
                User user = userService.getUserByEmail(email);
                String accessToken = JwtTokens.createAccessToken(user, request);
                refreshToken = JwtTokens.createRefreshToken(user, request);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", accessToken);
                tokens.put("refresh_token", refreshToken);
                response.setContentType(Constants.RESPONSE_CONTENT_TYPE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            } catch (IOException exception) {
                log.error("Error logging in: {}", exception.getMessage());
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                Map<String, String> error = new HashMap<>();
                error.put("error_message", exception.getMessage());
                response.setContentType(Constants.RESPONSE_CONTENT_TYPE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            throw new RuntimeException("Refresh token is missing");
        }
    }
}
