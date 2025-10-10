package io.jutil.coreservice.admin.dao;

import io.jutil.coreservice.admin.entity.ConfigItem;
import io.jutil.coreservice.admin.entity.ConfigItemTest;
import io.jutil.coreservice.admin.entity.ConfigTest;
import io.jutil.coreservice.core.dict.Status;
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

/**
 * @author Jin Zheng
 * @since 2025-10-08
 */
public abstract class ConfigItemMapperTest {
	@Autowired
	ConfigMapper mapper;

	@Autowired
	ConfigItemMapper itemMapper;

	@Autowired
	JdbcTemplate jdbcTemplate;

	long configId;

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

		var entity = ConfigTest.create();
		Assertions.assertEquals(1, mapper.insertOne(entity));
		configId = entity.getId();
	}

	@Test
	void testInsertOne() {
		var entity = ConfigItemTest.create(configId);
		Assertions.assertEquals(1, itemMapper.insertOne(entity));

		entity.setId(LongIdGenerator.nextId());
		Assertions.assertEquals(0, itemMapper.insertOne(entity));

		Assertions.assertEquals(1, this.count());

		var viewList = itemMapper.listByConfigId(entity.getTenantId(), configId);
		Assertions.assertEquals(1, viewList.size());
		ConfigItemTest.verify(viewList.getFirst(), 1L, configId,  2L, "name",
				"value", 0, "remarks");
	}

	@Test
	public void testUpdateOne1() {
		var entity = ConfigItemTest.create(configId);
		Assertions.assertEquals(1, itemMapper.insertOne(entity));

		var entity2 = new ConfigItem();
		entity2.setId(entity.getId());
		entity2.setTenantId(entity.getTenantId());
		entity2.setOperatorId(22L);
		entity2.setName("name2");
		entity2.setValue("value2");
		entity2.setStatus(Status.DISABLED);
		entity2.setRemarks("remarks2");
		Assertions.assertEquals(1, itemMapper.updateOne(entity2));

		Assertions.assertEquals(1, this.count());

		var viewList = itemMapper.listByConfigId(entity.getTenantId(), configId);
		Assertions.assertEquals(1, viewList.size());
		ConfigItemTest.verify(viewList.getFirst(), 1L, configId,  22L, "name2",
				"value2", 1, "remarks2");
	}

	@Test
	public void testUpdateOne2() {
		var entity = ConfigItemTest.create(configId);
		Assertions.assertEquals(1, itemMapper.insertOne(entity));

		var entity2 = new ConfigItem();
		entity2.setId(entity.getId());
		entity2.setTenantId(entity.getTenantId());
		entity2.setOperatorId(2L);
		entity2.setName("name2");
		Assertions.assertEquals(1, itemMapper.updateOne(entity2));

		Assertions.assertEquals(1, this.count());

		var viewList = itemMapper.listByConfigIdList(entity.getTenantId(), List.of(configId));
		Assertions.assertEquals(1, viewList.size());
		ConfigItemTest.verify(viewList.getFirst(), 1L, configId,  2L, "name2",
				"value", 0, "remarks");
	}

	@Test
	public void testUpdateOne3() {
		var entity = ConfigItemTest.create(configId);
		Assertions.assertEquals(1, itemMapper.insertOne(entity));

		var entity2 = new ConfigItem();
		entity2.setId(entity.getId());
		entity2.setTenantId(2L);
		entity2.setName("name2");
		Assertions.assertEquals(0, itemMapper.updateOne(entity2));
	}

	@CsvSource({"1,1,0", "2,0,1"})
	@ParameterizedTest
	public void testDeleteOne(long tenantId, int result, int count) {
		var entity = ConfigItemTest.create(this.configId);
		Assertions.assertEquals(1, itemMapper.insertOne(entity));
		Assertions.assertEquals(1, this.count());
		Assertions.assertEquals(result, itemMapper.deleteOne(tenantId, entity.getId()));
		Assertions.assertEquals(count, this.count());
	}

	@CsvSource({"1,0,1,0", "2,0,0,1", "1,1,0,1"})
	@ParameterizedTest
	public void testDeleteByConfigId(long tenantId, long configId, int result, int count) {
		var entity = ConfigItemTest.create(this.configId);
		Assertions.assertEquals(1, itemMapper.insertOne(entity));
		Assertions.assertEquals(1, this.count());

		if (configId == 0L) {
			configId = this.configId;
		}

		Assertions.assertEquals(result, itemMapper.deleteByConfigId(tenantId, configId));
		Assertions.assertEquals(count, this.count());
	}

	@CsvSource({"1,2,0", "2,0,2"})
	@ParameterizedTest
	public void testDeleteList(long tenantId, int result, int count) {
		var entity1 = ConfigItemTest.create(this.configId);
		Assertions.assertEquals(1, itemMapper.insertOne(entity1));

		var entity2 = ConfigItemTest.create(configId);
		entity2.setValue("value2");
		Assertions.assertEquals(1, itemMapper.insertOne(entity2));

		Assertions.assertEquals(2, this.count());
		Assertions.assertEquals(result, itemMapper.deleteList(tenantId,
				List.of(entity1.getId(), entity2.getId())));
		Assertions.assertEquals(count, this.count());
	}

	@CsvSource({"1,0,2,0", "2,0,0,2", "1,1,1,1"})
	@ParameterizedTest
	public void testDeleteByConfigIdList(long tenantId, long configId, int result, int count) {
		var entity = ConfigItemTest.create(this.configId);
		Assertions.assertEquals(1, itemMapper.insertOne(entity));

		var configEntity2 = ConfigTest.create();
		configEntity2.setCode("code2");
		Assertions.assertEquals(1, mapper.insertOne(configEntity2));

		var entity2 = ConfigItemTest.create(configEntity2.getId());
		entity2.setValue("value2");
		Assertions.assertEquals(1, itemMapper.insertOne(entity2));
		Assertions.assertEquals(2, this.count());

		if (configId == 0L) {
			configId = this.configId;
		}

		Assertions.assertEquals(result, itemMapper.deleteByConfigIdList(tenantId,
				List.of(configId, configEntity2.getId())));
		Assertions.assertEquals(count, this.count());
	}

	private int count() {
		var sql = "SELECT COUNT(*) FROM adm_config_item";
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}
}
