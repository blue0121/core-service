package io.jutil.coreservice.auth.dao;

import io.jutil.coreservice.auth.entity.PageTest;
import io.jutil.coreservice.auth.entity.User;
import io.jutil.coreservice.auth.entity.UserSearch;
import io.jutil.coreservice.auth.entity.UserTest;
import io.jutil.coreservice.core.dict.Realm;
import io.jutil.coreservice.core.dict.Status;
import io.jutil.springeasy.core.collection.Sort;
import io.jutil.springeasy.core.util.DateUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

/**
 * @author Jin Zheng
 * @since 2025-08-18
 */
public abstract class UserMapperTest {
	@Autowired
	UserMapper mapper;

	@Autowired
	JdbcTemplate jdbcTemplate;

	@BeforeEach
	void beforeEach() {
		jdbcTemplate.update("TRUNCATE TABLE auth_user");
	}

	@Test
	public void testInsertOne() {
		var loginTime = DateUtil.now();
		var entity = UserTest.create(loginTime);
		entity.setId(1L);
		Assertions.assertEquals(1, mapper.insertOne(entity));

		entity.setId(2L);
		Assertions.assertEquals(0, mapper.insertOne(entity));

		Assertions.assertEquals(1, this.count());

		var view = mapper.selectOne(Realm.ADMIN, 1L);
		UserTest.verify(view, 1, "code", "name", "password",
				0, "remarks", "extension", "ip", loginTime);
	}

	@Test
	public void testUpdateOne1() {
		var loginTime = DateUtil.now();
		var entity = UserTest.create(loginTime);
		Assertions.assertEquals(1, mapper.insertOne(entity));

		var entity2 = new User();
		var loginTime2 = loginTime.plusHours(1);
		entity2.setId(entity.getId());
		entity2.setRealm(Realm.ADMIN);
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

		var view = mapper.selectOne(Realm.ADMIN, entity.getId());
		UserTest.verify(view, 1, "code2", "name2", "password2",
				1, "remarks2", "extension2", "ip2", loginTime2);
	}

	@Test
	public void testUpdateOne2() {
		var loginTime = DateUtil.now();
		var entity = UserTest.create(loginTime);
		Assertions.assertEquals(1, mapper.insertOne(entity));

		var entity2 = new User();
		entity2.setId(entity.getId());
		entity2.setRealm(Realm.ADMIN);
		entity2.setCode("code");
		entity2.setPassword("password2");
		Assertions.assertEquals(1, mapper.updateOne(entity2));

		Assertions.assertEquals(1, this.count());

		var view = mapper.selectOne(Realm.ADMIN, entity.getId());
		UserTest.verify(view, 1, "code", "name", "password2",
				0, "remarks", "extension", "ip", loginTime);
	}

	@Test
	public void testUpdateOne3() {
		var loginTime = DateUtil.now();
		var entity = UserTest.create(loginTime);
		Assertions.assertEquals(1, mapper.insertOne(entity));

		var entity2 = new User();
		entity2.setId(entity.getId());
		entity2.setRealm(Realm.USER);
		entity2.setCode("code2");
		entity2.setPassword("password2");
		Assertions.assertEquals(0, mapper.updateOne(entity2));

		Assertions.assertEquals(1, this.count());

		var view = mapper.selectOne(Realm.ADMIN, entity.getId());
		UserTest.verify(view, 1, "code", "name", "password",
				0, "remarks", "extension", "ip", loginTime);
	}

	@Test
	public void testDeleteOne() {
		var loginTime = DateUtil.now();
		var entity = UserTest.create(loginTime);
		Assertions.assertEquals(1, mapper.insertOne(entity));

		Assertions.assertEquals(1, this.count());

		Assertions.assertEquals(1, mapper.deleteOne(Realm.ADMIN, entity.getId()));

		Assertions.assertEquals(0, this.count());
	}

	@Test
	public void testDeleteList() {
		var loginTime = DateUtil.now();
		var entity1 = UserTest.create(loginTime);
		Assertions.assertEquals(1, mapper.insertOne(entity1));

		var entity2 = UserTest.create(loginTime);
		entity2.setRealm(Realm.USER);
		Assertions.assertEquals(1, mapper.insertOne(entity2));

		Assertions.assertEquals(2, this.count());

		Assertions.assertEquals(1, mapper.deleteList(Realm.ADMIN,
				List.of(entity1.getId(), entity2.getId())));

		Assertions.assertEquals(1, this.count());
	}

	@Test
	public void testSelectList() {
		var loginTime = DateUtil.now();
		var entity1 = UserTest.create(loginTime);
		Assertions.assertEquals(1, mapper.insertOne(entity1));

		var entity2 = UserTest.create(loginTime);
		entity2.setRealm(Realm.USER);
		Assertions.assertEquals(1, mapper.insertOne(entity2));

		var viewList = mapper.selectList(Realm.ADMIN,
				List.of(entity1.getId(), entity2.getId()));
		Assertions.assertEquals(1, viewList.size());
		UserTest.verify(viewList.getFirst(), 1, "code", "name", "password",
				0, "remarks", "extension", "ip", loginTime);
	}

	@Test
	public void testLogin() {
		var loginTime = DateUtil.now();
		var entity = UserTest.create(loginTime);
		Assertions.assertEquals(1, mapper.insertOne(entity));

		var view = mapper.login(entity.getRealm(), entity.getCode(), entity.getPassword());
		UserTest.verify(view, 1, "code", "name", "password",
				0, "remarks", "extension", "ip", loginTime);
	}

	@CsvSource({"realm", "status", "code", "name", "idList"})
	@ParameterizedTest
	public void testCountPage(String type) {
		var entity = UserTest.create(DateUtil.now());
		Assertions.assertEquals(1, mapper.insertOne(entity));

		var search = this.getSearch(type, entity);
		var count = mapper.countPage(search);
		Assertions.assertEquals(1, count);
	}

	private UserSearch getSearch(String type, User entity) {
		var search = new UserSearch();
		search.setRealm(entity.getRealm());
		switch (type) {
			case "realm":
				search.setRealm(entity.getRealm());
				break;
			case "status":
				search.setStatus(entity.getStatus());
				break;
			case "code":
				search.setCode(entity.getCode());
				break;
			case "name":
				search.setName(entity.getName());
				break;
			case "idList":
				search.setIdList(List.of(entity.getId()));
				break;
		}
		return search;
	}

	@CsvSource({"realm", "status", "code", "name", "idList"})
	@ParameterizedTest
	public void testListPage(String type) {
		var loginTime = DateUtil.now();
		var entity = UserTest.create(loginTime);
		Assertions.assertEquals(1, mapper.insertOne(entity));

		var search = this.getSearch(type, entity);
		var pageable = PageTest.createPageable(0, 10, new Sort("id"),
				Map.of("id", "e.id"));
		var list = mapper.listPage(search, pageable);
		Assertions.assertEquals(1, list.size());
		UserTest.verify(list.getFirst(), 1, "code", "name", "password",
				0, "remarks", "extension", "ip", loginTime);
	}

	private int count() {
		var sql = "SELECT COUNT(*) FROM auth_user";
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}

}
