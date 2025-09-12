package io.jutil.coreservice.admin.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Jin Zheng
 * @since 2025-08-15
 */
@Configuration("authProviderScanAutoConfiguration")
@ComponentScan({"io.jutil.coreservice.admin.fetcher",
				"io.jutil.coreservice.admin.provider",
				"io.jutil.coreservice.admin.service",
				"io.jutil.coreservice.admin.repository"})
@MapperScan("io.jutil.coreservice.admin.dao")
public class ScanAutoConfiguration {
}
