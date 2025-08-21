package io.jutil.coreservice.auth.dao;

import io.jutil.coreservice.auth.entity.UserLoginLogTest;
import io.jutil.coreservice.auth.entity.UserTest;
import io.jutil.springeasy.core.util.DateUtil;
import org.junit.jupiter.api.Assertions;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;

/**
 * @author Jin Zheng
 * @since 2025-08-19
 */
class UserLoginLogMapperTest {
	final UserLoginLogMapper mapper;
	final UserMapper userMapper;
	final JdbcTemplate jdbcTemplate;

	UserLoginLogMapperTest(UserLoginLogMapper mapper, UserMapper userMapper,
	                       JdbcTemplate jdbcTemplate) {
		this.mapper = mapper;
		this.userMapper = userMapper;
		this.jdbcTemplate = jdbcTemplate;
	}

	void testInsert() {
		var userEntity = UserTest.create(DateUtil.now());
		Assertions.assertEquals(1, userMapper.insertOne(userEntity));

		var entity = UserLoginLogTest.create(userEntity.getId());
		Assertions.assertEquals(1, mapper.insertOrUpdate(entity));

		var view = mapper.selectOne(entity.getId());
		UserLoginLogTest.verify(view, userEntity.getId(), "ip", LocalDate.now(),
				1, "code", "name");
	}

	void testUpdate(int retVal) {
		var userEntity = UserTest.create(DateUtil.now());
		Assertions.assertEquals(1, userMapper.insertOne(userEntity));

		var entity1 = UserLoginLogTest.create(userEntity.getId());
		Assertions.assertEquals(1, mapper.insertOrUpdate(entity1));

		var entity2 = UserLoginLogTest.create(userEntity.getId());
		entity2.setIp("ip2");
		Assertions.assertEquals(retVal, mapper.insertOrUpdate(entity2));

		var view = mapper.selectOne(entity1.getId());
		UserLoginLogTest.verify(view, userEntity.getId(), "ip2", LocalDate.now(),
				2, "code", "name");
	}
}
