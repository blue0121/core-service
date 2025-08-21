package io.jutil.coreservice.auth.dao;

import io.jutil.coreservice.auth.dict.Realm;
import io.jutil.coreservice.auth.entity.User;
import io.jutil.coreservice.auth.entity.UserTest;
import io.jutil.coreservice.core.dict.Status;
import io.jutil.springeasy.core.util.DateUtil;
import org.junit.jupiter.api.Assertions;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * @author Jin Zheng
 * @since 2025-08-18
 */
class UserMapperTest {
	final UserMapper mapper;
	final JdbcTemplate jdbcTemplate;

	UserMapperTest(UserMapper mapper, JdbcTemplate jdbcTemplate) {
		this.mapper = mapper;
		this.jdbcTemplate = jdbcTemplate;
	}

	void testInsertOne() {
		var loginTime = DateUtil.now();
		var entity = UserTest.create(loginTime);
		entity.setId(1L);
		Assertions.assertEquals(1, mapper.insertOne(entity));

		entity.setId(2L);
		Assertions.assertEquals(0, mapper.insertOne(entity));

		Assertions.assertEquals(1, this.count());

		var realm = jdbcTemplate.queryForObject("select realm from auth_user where id = ?",
				Integer.class, 1L);
		System.out.println(">>>>>>> realm = " + realm);

		var view = mapper.selectOne(1L);
		UserTest.verify(view, 1, "code", "name", "password",
				0, "remarks", "extension", "ip", loginTime);
	}

	void testUpdateOne1() {
		var loginTime = DateUtil.now();
		var entity = UserTest.create(loginTime);
		Assertions.assertEquals(1, mapper.insertOne(entity));

		var entity2 = new User();
		var loginTime2 = loginTime.plusHours(1);
		entity2.setId(entity.getId());
		entity2.setRealm(Realm.USER);
		entity2.setCode("code2");
		entity2.setName("name2");
		entity2.setPassword("password2");
		entity2.setStatus(Status.DISABLED);
		entity2.setRemarks("remarks2");
		entity2.setExtension("extension2".getBytes());
		entity2.setIp("ip2");
		entity2.setLoginTime(loginTime2);
		Assertions.assertEquals(1, mapper.updateOne(entity2));

		Assertions.assertEquals(1, this.count());

		var view = mapper.selectOne(entity.getId());
		UserTest.verify(view, 1, "code2", "name2", "password2",
				1, "remarks2", "extension2", "ip2", loginTime2);
	}

	void testUpdateOne2() {
		var loginTime = DateUtil.now();
		var entity = UserTest.create(loginTime);
		Assertions.assertEquals(1, mapper.insertOne(entity));

		var entity2 = new User();
		entity2.setId(entity.getId());
		entity2.setRealm(Realm.USER);
		entity2.setCode("code");
		entity2.setPassword("password2");
		Assertions.assertEquals(1, mapper.updateOne(entity2));

		Assertions.assertEquals(1, this.count());

		var view = mapper.selectOne(entity.getId());
		UserTest.verify(view, 1, "code", "name", "password2",
				0, "remarks", "extension", "ip", loginTime);
	}

	void testDeleteOne() {
		var loginTime = DateUtil.now();
		var entity = UserTest.create(loginTime);
		Assertions.assertEquals(1, mapper.insertOne(entity));

		Assertions.assertEquals(1, this.count());

		Assertions.assertEquals(1, mapper.deleteOne(entity.getId()));

		Assertions.assertEquals(0, this.count());
	}

	void testDeleteList() {
		var loginTime = DateUtil.now();
		var entity1 = UserTest.create(loginTime);
		Assertions.assertEquals(1, mapper.insertOne(entity1));

		var entity2 = UserTest.create(loginTime);
		entity2.setRealm(Realm.USER);
		Assertions.assertEquals(1, mapper.insertOne(entity2));

		Assertions.assertEquals(2, this.count());

		Assertions.assertEquals(2, mapper.deleteList(List.of(entity1.getId(), entity2.getId())));

		Assertions.assertEquals(0, this.count());
	}

	void testSelectList() {
		var loginTime = DateUtil.now();
		var entity1 = UserTest.create(loginTime);
		Assertions.assertEquals(1, mapper.insertOne(entity1));

		var entity2 = UserTest.create(loginTime);
		entity2.setRealm(Realm.USER);
		Assertions.assertEquals(1, mapper.insertOne(entity2));

		var viewList = mapper.selectList(List.of(entity1.getId(), entity2.getId()));
		Assertions.assertEquals(2, viewList.size());
		for (var view : viewList) {
			if (view.getRealm() == Realm.ADMIN) {
				UserTest.verify(view, 1, "code", "name", "password",
						0, "remarks", "extension", "ip", loginTime);
			} else {
				UserTest.verify(view, 2, "code", "name", "password",
						0, "remarks", "extension", "ip", loginTime);
			}
		}
	}

	void testLogin() {
		var loginTime = DateUtil.now();
		var entity = UserTest.create(loginTime);
		Assertions.assertEquals(1, mapper.insertOne(entity));

		var view = mapper.login(entity.getRealm(), entity.getCode(), entity.getPassword());
		UserTest.verify(view, 1, "code", "name", "password",
				0, "remarks", "extension", "ip", loginTime);
	}

	int count() {
		var sql = "SELECT COUNT(*) FROM auth_user";
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}

}
