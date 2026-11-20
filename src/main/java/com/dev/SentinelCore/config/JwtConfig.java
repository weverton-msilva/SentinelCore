
package com.dev.SentinelCore.config;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

@Configuration
@EnableConfigurationProperties(JwtProperties.class)
public class JwtConfig {

    private static final String ISSUER = "sentinel-core";

    @Bean
    SecretKey jwtSecretKey(JwtProperties props) {
        byte[] bytes = props.secret().getBytes(StandardCharsets.UTF_8);

        if (bytes.length < 32) {
            throw new IllegalStateException(
                    "JWT_SECRET deve ter pelo menos 32 caracteres"
            );
        }

        return new SecretKeySpec(bytes, "HmacSHA256");
    }

    @Bean
    JwtEncoder jwtEncoder(SecretKey key) {
        return new NimbusJwtEncoder(
                new ImmutableSecret<>(key)
        );
    }

    @Bean
    JwtDecoder jwtDecoder(SecretKey key) {
        NimbusJwtDecoder decoder = NimbusJwtDecoder
                .withSecretKey(key)
                .macAlgorithm(MacAlgorithm.HS256)
                .build();

        decoder.setJwtValidator(
                JwtValidators.createDefaultWithIssuer(
                        com.dev.SentinelCore.security.TokenService.ISSUER
                )
        );

        return decoder;
    }
}
