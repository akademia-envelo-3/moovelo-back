package pl.envelo.moovelo.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Profile("dev")
public class WebSecurityConfig {

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    private static final String[] AUTH_WHITELIST = {
        "/api/login/**",
        "/api/refreshToken/**",
        "/api/v1/externalEvents/**",
        "/v2/api-docs",
        "/v3/api-docs",
        "/swagger-resources/**",
        "/swagger-ui/**",
        "/h2/**"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationConfiguration.getAuthenticationManager());
        jwtAuthenticationFilter.setFilterProcessesUrl("/api/login");

        return http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests().antMatchers(AUTH_WHITELIST).permitAll()
                .and().authorizeRequests().anyRequest().authenticated()
                .and().headers().frameOptions().disable()
                .and().addFilter(jwtAuthenticationFilter)
                .addFilterBefore(new JwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
