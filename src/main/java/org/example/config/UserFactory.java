package org.example.config;

import lombok.extern.slf4j.Slf4j;
import org.example.property.AuthPropertyConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Slf4j
@Configuration
public class UserFactory {

    private final AuthPropertyConfiguration property;

    public UserFactory(AuthPropertyConfiguration property) {
        this.property = property;
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
        log.info("\n\n User Credentails: {} \n\n", property.toString());
        UserDetails admin = User.builder().username(property.getUsername1()).password(encoder.encode(property.getPassword1())).roles("ADMIN", "USER").build();
        UserDetails user = User.builder().username(property.getUsername2()).password(encoder.encode(property.getPassword2())).roles("USER").build();
        return new InMemoryUserDetailsManager(admin, user);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
