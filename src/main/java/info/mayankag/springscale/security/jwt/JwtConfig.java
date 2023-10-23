package info.mayankag.springscale.security.jwt;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "application.jwt")
@RequiredArgsConstructor
@Getter
@Setter
public class JwtConfig {
    @NotNull
    private String secretKey="sample_secret_key_sample_secret_key_sample_secret_key_sample_secret_key";

    private String tokenPrefix;
    private Integer tokenExpirationAfterDays;
    private String claimUserEmail;
    private String claimUserScopes;
}
