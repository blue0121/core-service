package io.jutil.coreservice.admin.dao;

import io.jutil.coreservice.admin.entity.PageTest;
import io.jutil.coreservice.admin.entity.Tenant;
import io.jutil.coreservice.admin.entity.TenantSearch;
import io.jutil.coreservice.admin.entity.TenantTest;
import io.jutil.coreservice.core.dict.Status;
import io.jutil.springeasy.core.collection.Sort;
import io.jutil.springeasy.mybatis.id.LongIdGenerator;
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
public abstract class TenantMapperTest {
	@Autowired
	TenantMapper mapper;

	@Autowired
	JdbcTemplate jdbcTemplate;

	@BeforeEach
	public void beforeEach() {
		jdbcTemplate.update("TRUNCATE TABLE adm_tenant");
	}

	@Test
	public void testInsertOne() {
		var entity = TenantTest.create();
		Assertions.assertEquals(1, mapper.insertOne(entity));
		var id = entity.getId();

		entity.setId(LongIdGenerator.nextId());
		Assertions.assertEquals(0, mapper.insertOne(entity));

		Assertions.assertEquals(1, this.count());

		var view = mapper.selectOne(id);
		TenantTest.verify(view, 1L, "code", "name",
				0, "remarks");
	}

	@Test
	public void testUpdateOne1() {
		var entity = TenantTest.create();
		Assertions.assertEquals(1, mapper.insertOne(entity));

		var entity2 = new Tenant();
		entity2.setId(entity.getId());
		entity2.setOperatorId(2L);
		entity2.setCode("code2");
		entity2.setName("name2");
		entity2.setStatus(Status.DISABLED);
		entity2.setRemarks("remarks2");
		Assertions.assertEquals(1, mapper.updateOne(entity2));

		Assertions.assertEquals(1, this.count());

		var view = mapper.selectOne(entity.getId());
		TenantTest.verify(view, 2L, "code2", "name2",
				1, "remarks2");
	}

	@Test
	public void testUpdateOne2() {
		var entity = TenantTest.create();
		Assertions.assertEquals(1, mapper.insertOne(entity));

		var entity2 = new Tenant();
		entity2.setId(entity.getId());
		entity2.setCode("code2");
		Assertions.assertEquals(1, mapper.updateOne(entity2));

		Assertions.assertEquals(1, this.count());

		var view = mapper.selectOne(entity.getId());
		TenantTest.verify(view, 1L, "code2", "name",
				0, "remarks");
	}

	@Test
	public void testDeleteOne() {
		var entity = TenantTest.create();
		Assertions.assertEquals(1, mapper.insertOne(entity));

		Assertions.assertEquals(1, this.count());

		Assertions.assertEquals(1, mapper.deleteOne(entity.getId()));

		Assertions.assertEquals(0, this.count());
	}

	@Test
	public void testDeleteList() {
		var entity1 = TenantTest.create();
		Assertions.assertEquals(1, mapper.insertOne(entity1));

		var entity2 = TenantTest.create();
		entity2.setCode("code2");
		Assertions.assertEquals(1, mapper.insertOne(entity2));

		Assertions.assertEquals(2, this.count());

		Assertions.assertEquals(2, mapper.deleteList(
				List.of(entity1.getId(), entity2.getId())));

		Assertions.assertEquals(0, this.count());
	}

	@Test
	public void testSelectList() {
		var entity1 = TenantTest.create();
		Assertions.assertEquals(1, mapper.insertOne(entity1));

		var entity2 = TenantTest.create();
		entity2.setCode("code2");
		Assertions.assertEquals(1, mapper.insertOne(entity2));

		var viewList = mapper.selectList(
				List.of(entity1.getId(), entity2.getId()));
		Assertions.assertEquals(2, viewList.size());
		for (var view : viewList) {
			if (view.getCode().equals("code")) {
				TenantTest.verify(view, 1L, "code", "name",
						0, "remarks");
			} else {
				TenantTest.verify(view, 1L, "code2", "name",
						0, "remarks");
			}
		}
	}

	@CsvSource({"status", "code", "name", "idList"})
	@ParameterizedTest
	public void testCountPage(String type) {
		var entity = TenantTest.create();
		Assertions.assertEquals(1, mapper.insertOne(entity));

		var search = this.getSearch(type, entity);
		var count = mapper.countPage(search);
		Assertions.assertEquals(1, count);
	}

	private TenantSearch getSearch(String type, Tenant entity) {
		var search = new TenantSearch();
		switch (type) {
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
		var entity = TenantTest.create();
		Assertions.assertEquals(1, mapper.insertOne(entity));

		var search = this.getSearch(type, entity);
		var pageable = PageTest.createPageable(0, 10, new Sort("id"),
				Map.of("id", "e.id"));
		var list = mapper.listPage(search, pageable);
		Assertions.assertEquals(1, list.size());
		TenantTest.verify(list.getFirst(), 1L, "code", "name",
				0, "remarks");
	}

	private int count() {
		var sql = "SELECT COUNT(*) FROM adm_tenant";
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}

}
