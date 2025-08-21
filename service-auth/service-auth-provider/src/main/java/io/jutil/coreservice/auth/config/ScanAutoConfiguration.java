package io.jutil.coreservice.auth.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Jin Zheng
 * @since 2025-08-15
 */
@Configuration("authProviderScanAutoConfiguration")
@ComponentScan({"io.jutil.coreservice.auth.provider",
				"io.jutil.coreservice.auth.service",
				"io.jutil.coreservice.auth.repository"})
@MapperScan("io.jutil.coreservice.auth.dao")
public class ScanAutoConfiguration {
}
