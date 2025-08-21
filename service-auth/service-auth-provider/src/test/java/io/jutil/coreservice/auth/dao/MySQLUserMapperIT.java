package io.jutil.coreservice.auth.dao;

import io.jutil.coreservice.auth.MySQLTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author Jin Zheng
 * @since 2025-08-18
 */
class MySQLUserMapperIT extends MySQLTest {
	@Autowired
	UserMapper mapper;

	@Autowired
	JdbcTemplate jdbcTemplate;

	UserMapperTest mapperTest;

	@BeforeEach
	void beforeEach() {
		jdbcTemplate.update("TRUNCATE TABLE auth_user");
		mapperTest = new UserMapperTest(mapper, jdbcTemplate);
	}

	@Test
	void testInsertOne() {
		mapperTest.testInsertOne();
	}

	@Test
	void testSelectList() {
		mapperTest.testSelectList();
	}

	@Test
	void testUpdateOne1() {
		mapperTest.testUpdateOne1();
	}

	@Test
	void testUpdateOne2() {
		mapperTest.testUpdateOne2();
	}

	@Test
	void testDeleteOne() {
		mapperTest.testDeleteOne();
	}

	@Test
	void testDeleteList() {
		mapperTest.testDeleteList();
	}

	@Test
	void testLogin() {
		mapperTest.testLogin();
	}
}
