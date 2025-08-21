package io.jutil.coreservice.auth.dao;

import io.jutil.coreservice.auth.PostgreSQLTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author Jin Zheng
 * @since 2025-08-19
 */
public class PostgreSQLUserLoginLogMapperIT extends PostgreSQLTest {
	@Autowired
	UserMapper userMapper;

	@Autowired
	UserLoginLogMapper mapper;

	@Autowired
	JdbcTemplate jdbcTemplate;

	UserLoginLogMapperTest mapperTest;

	@BeforeEach
	void beforeEach() {
		jdbcTemplate.update("TRUNCATE TABLE auth_user");
		jdbcTemplate.update("TRUNCATE TABLE auth_user_login_log");
		mapperTest = new UserLoginLogMapperTest(mapper, userMapper, jdbcTemplate);
	}

	@Test
	void testInsert() {
		mapperTest.testInsert();
	}

	@Test
	void testUpdate() {
		mapperTest.testUpdate(1);
	}
}
