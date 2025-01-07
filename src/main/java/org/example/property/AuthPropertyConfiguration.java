package org.example.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "auth")
public class AuthPropertyConfiguration {
    private String username1;
    private String password1;
    private String username2;
    private String password2;
}
