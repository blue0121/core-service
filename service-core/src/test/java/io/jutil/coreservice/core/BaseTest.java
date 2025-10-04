package io.jutil.coreservice.core;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Jin Zheng
 * @since 2025-09-11
 */
@SpringBootTest(classes = BaseTest.Application.class)
public interface BaseTest {

	@SpringBootApplication
	class Application {
	}
}
