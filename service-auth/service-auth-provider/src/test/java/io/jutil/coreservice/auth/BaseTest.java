package io.jutil.coreservice.auth;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Jin Zheng
 * @since 2025-08-18
 */
@SpringBootTest(classes = BaseTest.Application.class)
public interface BaseTest {

	@SpringBootApplication
	class Application {
	}
}
