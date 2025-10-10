package io.jutil.coreservice.admin.dao;

import io.jutil.coreservice.admin.entity.Config;
import io.jutil.coreservice.admin.entity.ConfigTest;
import io.jutil.coreservice.admin.entity.PageTest;
import io.jutil.coreservice.core.dict.Status;
import io.jutil.coreservice.core.dict.YesOrNo;
import io.jutil.springeasy.core.collection.Sort;
import io.jutil.springeasy.mybatis.id.LongIdGenerator;
import io.jutil.springeasy.mybatis.metadata.DatabaseProduct;
import io.jutil.springeasy.mybatis.metadata.MetadataUtil;
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
 * @since 2025-10-08
 */
public abstract class ConfigMapperTest {
	@Autowired
	ConfigMapper mapper;

	@Autowired
	JdbcTemplate jdbcTemplate;

	@BeforeEach
	public void beforeEach() {
		var dataSource = jdbcTemplate.getDataSource();
		var database = MetadataUtil.getDatabaseInfo(dataSource);
		if (database.product() == DatabaseProduct.MYSQL) {
			jdbcTemplate.update("DELETE FROM adm_config_item");
			jdbcTemplate.update("DELETE FROM adm_config");
		} else {
			jdbcTemplate.update("TRUNCATE TABLE adm_config CASCADE");
		}
	}

	@Test
	public void testInsertOne() {
		var entity = ConfigTest.create();
		Assertions.assertEquals(1, mapper.insertOne(entity));
		var id = entity.getId();

		entity.setId(LongIdGenerator.nextId());
		Assertions.assertEquals(0, mapper.insertOne(entity));

		Assertions.assertEquals(1, this.count());

		var view = mapper.selectOne(entity.getTenantId(), id);
		ConfigTest.verify(view, 1L, 2L,  "code", "name",
				"value", 0, 0,"remarks");
	}

	@Test
	public void testUpdateOne1() {
		var entity = ConfigTest.create();
		Assertions.assertEquals(1, mapper.insertOne(entity));

		var entity2 = new Config();
		entity2.setId(entity.getId());
		entity2.setTenantId(entity.getTenantId());
		entity2.setOperatorId(22L);
		entity2.setCode("code2");
		entity2.setName("name2");
		entity2.setValue("value2");
		entity2.setStatus(Status.DISABLED);
		entity2.setMultiValue(YesOrNo.YES);
		entity2.setRemarks("remarks2");
		Assertions.assertEquals(1, mapper.updateOne(entity2));

		Assertions.assertEquals(1, this.count());

		var view = mapper.selectOne(entity.getTenantId(), entity.getId());
		ConfigTest.verify(view, 1L, 22L, "code2", "name2",
				"value2", 1, 1, "remarks2");
	}

	@Test
	public void testUpdateOne2() {
		var entity = ConfigTest.create();
		Assertions.assertEquals(1, mapper.insertOne(entity));

		var entity2 = new Config();
		entity2.setId(entity.getId());
		entity2.setTenantId(entity.getTenantId());
		entity2.setOperatorId(2L);
		entity2.setCode("code2");
		Assertions.assertEquals(1, mapper.updateOne(entity2));

		Assertions.assertEquals(1, this.count());

		var view = mapper.selectOne(entity.getTenantId(), entity.getId());
		ConfigTest.verify(view, 1L, 2L,  "code2", "name",
				null, 0, 0,"remarks");
	}

	@Test
	public void testUpdateOne3() {
		var entity = ConfigTest.create();
		Assertions.assertEquals(1, mapper.insertOne(entity));

		var entity2 = new Config();
		entity2.setId(entity.getId());
		entity2.setTenantId(2L);
		entity2.setCode("code2");
		Assertions.assertEquals(0, mapper.updateOne(entity2));
	}

	@Test
	public void testSelectList() {
		var entity1 = ConfigTest.create();
		Assertions.assertEquals(1, mapper.insertOne(entity1));

		var entity2 = ConfigTest.create();
		entity2.setCode("code2");
		Assertions.assertEquals(1, mapper.insertOne(entity2));

		var viewList = mapper.selectList(entity1.getTenantId(),
				List.of(entity1.getId(), entity2.getId()));
		Assertions.assertEquals(2, viewList.size());
		for (var view : viewList) {
			if (view.getCode().equals("code")) {
				ConfigTest.verify(view, 1L, 2L,  "code", "name",
						"value", 0, 0,"remarks");
			} else {
				ConfigTest.verify(view, 1L, 2L,  "code2", "name",
						"value", 0, 0,"remarks");
			}
		}
	}

	@CsvSource({"1,1,0", "2,0,1"})
	@ParameterizedTest
	public void testDeleteOne(long tenantId, int result, int count) {
		var entity = ConfigTest.create();
		Assertions.assertEquals(1, mapper.insertOne(entity));
		Assertions.assertEquals(1, this.count());
		Assertions.assertEquals(result, mapper.deleteOne(tenantId, entity.getId()));
		Assertions.assertEquals(count, this.count());
	}

	@CsvSource({"1,2,0", "2,0,2"})
	@ParameterizedTest
	public void testDeleteList(long tenantId, int result, int count) {
		var entity1 = ConfigTest.create();
		Assertions.assertEquals(1, mapper.insertOne(entity1));

		var entity2 = ConfigTest.create();
		entity2.setCode("code2");
		Assertions.assertEquals(1, mapper.insertOne(entity2));

		Assertions.assertEquals(2, this.count());
		Assertions.assertEquals(result, mapper.deleteList(tenantId,
				List.of(entity1.getId(), entity2.getId())));
		Assertions.assertEquals(count, this.count());
	}

	@CsvSource({"operatorId", "status", "multiValue", "code", "name", "idList"})
	@ParameterizedTest
	public void testCountPage(String type) {
		var entity = ConfigTest.create();
		Assertions.assertEquals(1, mapper.insertOne(entity));

		var search = this.getSearch(type, entity);
		var count = mapper.countPage(search);
		Assertions.assertEquals(1, count);
	}

	private Config.Search getSearch(String type, Config entity) {
		var search = new Config.Search();
		search.tenantId = entity.getTenantId();
		switch (type) {
			case "operatorId" -> search.operatorId = entity.getOperatorId();
			case "status" -> search.status = entity.getStatus();
			case "multiValue" -> search.multiValue = entity.getMultiValue();
			case "code" -> search.code = entity.getCode();
			case "name" -> search.name = entity.getName();
			case "idList" -> search.idList = List.of(entity.getId());
		}
		return search;
	}

	@CsvSource({"operatorId", "status", "multiValue", "code", "name", "idList"})
	@ParameterizedTest
	public void testListPage(String type) {
		var entity = ConfigTest.create();
		Assertions.assertEquals(1, mapper.insertOne(entity));

		var search = this.getSearch(type, entity);
		var pageable = PageTest.createPageable(0, 10, new Sort("id"),
				Map.of("id", "e.id"));
		var list = mapper.listPage(search, pageable);
		Assertions.assertEquals(1, list.size());
		ConfigTest.verify(list.getFirst(), 1L, 2L,  "code", "name",
				"value", 0, 0,"remarks");
	}

	private int count() {
		var sql = "SELECT COUNT(*) FROM adm_config";
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}
}
