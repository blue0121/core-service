package io.jutil.coreservice.auth;

import io.jutil.springeasy.test.container.PostgreSQLTestContainer;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * @author Jin Zheng
 * @since 2025-08-18
 */
@ActiveProfiles("postgresql")
@Testcontainers
public abstract class PostgreSQLTest extends BaseTest {

	@Container
	protected static final PostgreSQLTestContainer POSTGRESQL = PostgreSQLTestContainer.CONTAINER;

	@DynamicPropertySource
	static void configureProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.driver-class-name", POSTGRESQL::getDriverClassName);
		registry.add("spring.datasource.url", POSTGRESQL::getJdbcUrl);
		registry.add("spring.datasource.username", POSTGRESQL::getUsername);
		registry.add("spring.datasource.password", POSTGRESQL::getPassword);
	}

}
