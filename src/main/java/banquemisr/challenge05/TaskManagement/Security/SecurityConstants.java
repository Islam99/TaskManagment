package banquemisr.challenge05.TaskManagement.Security;


import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

import static io.jsonwebtoken.security.Keys.secretKeyFor;


@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "spring.security.security-constants")
public class SecurityConstants {
    private String AUTH_TOKEN_EXPIRATION;
    private String SECRET_KEY = String.valueOf(secretKeyFor(SignatureAlgorithm.HS256));
    private String REFRESH_TOKEN_EXPIRATION;
    private String AUTH_TOKEN;
    private String REFRESH_TOKEN;
    private String AUTHENTICATE_PATH;
    private String PUBLIC;
    private String H2_DATABASE;
    private String H2_PATTERN;
    private String PUBLIC_PATH;
    private String AUTHENTICATE_PATTERN;
    private String[] SWAGGER_PATTERN;
    private String SWAGGER_CONFIG;
    private String SWAGGER;
}
