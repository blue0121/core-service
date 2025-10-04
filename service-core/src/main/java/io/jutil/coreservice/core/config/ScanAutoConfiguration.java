package io.jutil.coreservice.core.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Jin Zheng
 * @since 2025-08-15
 */
@Configuration("coreScanAutoConfiguration")
@ComponentScan({"io.jutil.coreservice.core.fetcher",
				"io.jutil.coreservice.core.provider",
				//"io.jutil.coreservice.core.service",
				"io.jutil.coreservice.core.repository"})
@MapperScan("io.jutil.coreservice.core.dao")
public class ScanAutoConfiguration {
}
