package io.jutil.coreservice.auth.dao;

import io.jutil.coreservice.auth.entity.PageTest;
import io.jutil.coreservice.auth.entity.User;
import io.jutil.coreservice.auth.entity.UserLoginLogSearch;
import io.jutil.coreservice.auth.entity.UserLoginLogTest;
import io.jutil.coreservice.auth.entity.UserTest;
import io.jutil.springeasy.core.collection.Sort;
import io.jutil.springeasy.core.util.DateUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.util.Map;

/**
 * @author Jin Zheng
 * @since 2025-08-19
 */
public abstract class UserLoginLogMapperTest {
	@Autowired
	UserLoginLogMapper mapper;

	@Autowired
	UserMapper userMapper;

	@Autowired
	JdbcTemplate jdbcTemplate;

	@BeforeEach
	public void beforeEach() {
		jdbcTemplate.update("TRUNCATE TABLE auth_user");
		jdbcTemplate.update("TRUNCATE TABLE auth_user_login_log");
	}

	@Test
	public void testInsert() {
		var userEntity = UserTest.create(DateUtil.now());
		Assertions.assertEquals(1, userMapper.insertOne(userEntity));

		var entity = UserLoginLogTest.create(userEntity.getId());
		Assertions.assertEquals(1, mapper.insertOrUpdate(entity));

		var view = mapper.selectOne(entity.getId());
		UserLoginLogTest.verify(view, userEntity.getId(), "ip", LocalDate.now(),
				1, "code", "name", 0);
	}

	@Test
	public void testUpdate() {
		var userEntity = UserTest.create(DateUtil.now());
		Assertions.assertEquals(1, userMapper.insertOne(userEntity));

		var entity1 = UserLoginLogTest.create(userEntity.getId());
		Assertions.assertEquals(1, mapper.insertOrUpdate(entity1));

		var entity2 = UserLoginLogTest.create(userEntity.getId());
		entity2.setIp("ip2");
		mapper.insertOrUpdate(entity2);

		var view = mapper.selectOne(entity1.getId());
		UserLoginLogTest.verify(view, userEntity.getId(), "ip2", LocalDate.now(),
				2, "code", "name", 0);
	}

	@CsvSource({"userId", "startDate", "endDate"})
	@ParameterizedTest
	public void testCountPage(String type) {
		var userEntity = UserTest.create(DateUtil.now());
		Assertions.assertEquals(1, userMapper.insertOne(userEntity));

		var entity = UserLoginLogTest.create(userEntity.getId());
		Assertions.assertEquals(1, mapper.insertOrUpdate(entity));

		var search = this.getSearch(type, userEntity);
		var count = mapper.countPage(search);
		Assertions.assertEquals(1, count);
	}

	private UserLoginLogSearch getSearch(String type, User entity) {
		var search = new UserLoginLogSearch();
		switch (type) {
			case "userId":
				search.setUserId(entity.getId());
				break;
			case "startDate":
				search.setStartDate(LocalDate.now());
				break;
			case "endDate":
				search.setEndDate(LocalDate.now().plusDays(1));
				break;
		}
		return search;
	}

	@CsvSource({"userId", "startDate", "endDate"})
	@ParameterizedTest
	public void testListPage(String type) {
		var userEntity = UserTest.create(DateUtil.now());
		Assertions.assertEquals(1, userMapper.insertOne(userEntity));

		var entity = UserLoginLogTest.create(userEntity.getId());
		Assertions.assertEquals(1, mapper.insertOrUpdate(entity));

		var search = this.getSearch(type, userEntity);
		var pageable = PageTest.createPageable(0, 10, new Sort("loginDate"),
				Map.of("loginDate", "e.login_date"));
		var list = mapper.listPage(search, pageable);
		Assertions.assertEquals(1, list.size());
		UserLoginLogTest.verify(list.getFirst(), userEntity.getId(), "ip", LocalDate.now(),
				1, "code", "name", 0);
	}
}
