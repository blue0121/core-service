package io.jutil.coreservice.auth.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Jin Zheng
 * @since 2025-08-15
 */
@Configuration("authTokenAutoConfiguration")
@EnableConfigurationProperties(TokenProperties.class)
public class TokenAutoConfiguration {
}
