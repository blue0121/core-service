package io.jutil.coreservice.admin.dao;

import io.jutil.coreservice.admin.entity.Account;
import io.jutil.coreservice.admin.entity.AccountSearch;
import io.jutil.coreservice.admin.entity.AccountTest;
import io.jutil.coreservice.admin.entity.PageTest;
import io.jutil.coreservice.core.dict.Status;
import io.jutil.springeasy.core.collection.Sort;
import io.jutil.springeasy.core.util.DateUtil;
import io.jutil.springeasy.mybatis.id.LongIdGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @author Jin Zheng
 * @since 2025-09-30
 */
public abstract class AccountMapperTest {
	@Autowired
	AccountMapper mapper;

	@Autowired
	AccountTenantMapper accountTenantMapper;

	@Autowired
	JdbcTemplate jdbcTemplate;

	final LocalDateTime now = DateUtil.now();

	@BeforeEach
	public void beforeEach() {
		jdbcTemplate.update("TRUNCATE TABLE adm_account");
	}

	@Test
	public void testInsertOne() {
		var entity = AccountTest.create(now);
		Assertions.assertEquals(1, mapper.insertOne(entity));
		var id = entity.getId();

		entity.setId(LongIdGenerator.nextId());
		Assertions.assertEquals(0, mapper.insertOne(entity));

		Assertions.assertEquals(1, this.count());

		var view = mapper.selectOne(entity.getTenantId(), id);
		AccountTest.verify(view, 1L, 2L,  "code", "name",
				0, "remarks");
	}

	@Test
	public void testUpdateOne1() {
		var entity = AccountTest.create(now);
		Assertions.assertEquals(1, mapper.insertOne(entity));

		var entity2 = new Account();
		entity2.setId(entity.getId());
		entity2.setTenantId(entity.getTenantId());
		entity2.setOperatorId(22L);
		entity2.setCode("code2");
		entity2.setName("name2");
		entity2.setStatus(Status.DISABLED);
		entity2.setRemarks("remarks2");
		Assertions.assertEquals(1, mapper.updateOne(entity2));

		Assertions.assertEquals(1, this.count());

		var view = mapper.selectOne(entity.getTenantId(), entity.getId());
		AccountTest.verify(view, 1L, 22L, "code2", "name2",
				1, "remarks2");
	}

	@Test
	public void testUpdateOne2() {
		var entity = AccountTest.create(now);
		Assertions.assertEquals(1, mapper.insertOne(entity));

		var entity2 = new Account();
		entity2.setId(entity.getId());
		entity2.setTenantId(entity.getTenantId());
		entity2.setOperatorId(2L);
		entity2.setCode("code2");
		Assertions.assertEquals(1, mapper.updateOne(entity2));

		Assertions.assertEquals(1, this.count());

		var view = mapper.selectOne(entity.getTenantId(), entity.getId());
		AccountTest.verify(view, 1L, 2L, "code2", "name",
				0, "remarks");
	}

	@Test
	public void testUpdateOne3() {
		var entity = AccountTest.create(now);
		Assertions.assertEquals(1, mapper.insertOne(entity));

		var entity2 = new Account();
		entity2.setId(entity.getId());
		entity2.setTenantId(2L);
		entity2.setCode("code2");
		Assertions.assertEquals(0, mapper.updateOne(entity2));
	}

	@Test
	public void testDeleteOne1() {
		var entity = AccountTest.create(now);
		Assertions.assertEquals(1, mapper.insertOne(entity));
		Assertions.assertEquals(1, this.count());
		Assertions.assertEquals(1, mapper.deleteOne(entity.getTenantId(), entity.getId()));
		Assertions.assertEquals(0, this.count());
	}

	@Test
	public void testDeleteOne2() {
		var entity = AccountTest.create(now);
		Assertions.assertEquals(1, mapper.insertOne(entity));
		Assertions.assertEquals(1, this.count());
		Assertions.assertEquals(0, mapper.deleteOne(2L, entity.getId()));
		Assertions.assertEquals(1, this.count());
	}

	@Test
	public void testDeleteList1() {
		var entity1 = AccountTest.create(now);
		Assertions.assertEquals(1, mapper.insertOne(entity1));

		var entity2 = AccountTest.create(now);
		entity2.setCode("code2");
		Assertions.assertEquals(1, mapper.insertOne(entity2));
		Assertions.assertEquals(2, this.count());
		Assertions.assertEquals(2, mapper.deleteList(entity1.getTenantId(),
				List.of(entity1.getId(), entity2.getId())));
		Assertions.assertEquals(0, this.count());
	}

	@Test
	public void testDeleteList2() {
		var entity1 = AccountTest.create(now);
		Assertions.assertEquals(1, mapper.insertOne(entity1));

		var entity2 = AccountTest.create(now);
		entity2.setCode("code2");
		Assertions.assertEquals(1, mapper.insertOne(entity2));
		Assertions.assertEquals(2, this.count());
		Assertions.assertEquals(0, mapper.deleteList(2L,
				List.of(entity1.getId(), entity2.getId())));
		Assertions.assertEquals(2, this.count());
	}

	@Test
	public void testSelectList() {
		var entity1 = AccountTest.create(now);
		Assertions.assertEquals(1, mapper.insertOne(entity1));

		var entity2 = AccountTest.create(now);
		entity2.setCode("code2");
		Assertions.assertEquals(1, mapper.insertOne(entity2));

		var viewList = mapper.selectList(entity1.getTenantId(),
				List.of(entity1.getId(), entity2.getId()));
		Assertions.assertEquals(2, viewList.size());
		for (var view : viewList) {
			if (view.getCode().equals("code")) {
				AccountTest.verify(view, 1L, 2L, "code", "name",
						0, "remarks");
			} else {
				AccountTest.verify(view, 1L, 2L, "code2", "name",
						0, "remarks");
			}
		}
	}

	@CsvSource({"operatorId", "status", "code", "name", "idList"})
	@ParameterizedTest
	public void testCountPage(String type) {
		var entity = AccountTest.create(now);
		Assertions.assertEquals(1, mapper.insertOne(entity));

		Assertions.assertEquals(1, accountTenantMapper.insert(entity.getId(),
				List.of(entity.getTenantId())));

		var search = this.getSearch(type, entity);
		var count = mapper.countPage(search);
		Assertions.assertEquals(1, count);
	}

	private AccountSearch getSearch(String type, Account entity) {
		var search = new AccountSearch();
		search.setTenantId(entity.getTenantId());
		switch (type) {
			case "operatorId" -> search.setOperatorId(entity.getOperatorId());
			case "status" -> search.setStatus(entity.getStatus());
			case "code" -> search.setCode(entity.getCode());
			case "name" -> search.setName(entity.getName());
			case "idList" -> search.setIdList(List.of(entity.getId()));
		}
		return search;
	}

	@CsvSource({"operatorId", "status", "code", "name", "idList"})
	@ParameterizedTest
	public void testListPage(String type) {
		var entity = AccountTest.create(now);
		Assertions.assertEquals(1, mapper.insertOne(entity));

		Assertions.assertEquals(1, accountTenantMapper.insert(entity.getId(),
				List.of(entity.getTenantId())));

		var search = this.getSearch(type, entity);
		var pageable = PageTest.createPageable(0, 10, new Sort("id"),
				Map.of("id", "e.id"));
		var list = mapper.listPage(search, pageable);
		Assertions.assertEquals(1, list.size());
		AccountTest.verify(list.getFirst(), 1L, 2L, "code", "name",
				0, "remarks");
	}

	private int count() {
		var sql = "SELECT COUNT(*) FROM adm_account";
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}
}
