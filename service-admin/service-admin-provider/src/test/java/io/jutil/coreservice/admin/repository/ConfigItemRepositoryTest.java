package io.jutil.coreservice.admin.repository;

import com.alibaba.fastjson2.JSONObject;
import io.jutil.coreservice.admin.dao.ConfigMapper;
import io.jutil.coreservice.admin.entity.AuditLogTest;
import io.jutil.coreservice.admin.entity.ConfigItem;
import io.jutil.coreservice.admin.entity.ConfigItemTest;
import io.jutil.coreservice.admin.entity.ConfigTest;
import io.jutil.coreservice.admin.entity.DeleteListTenantAuditEntityTest;
import io.jutil.coreservice.admin.entity.DeleteTenantAuditEntityTest;
import io.jutil.coreservice.core.dict.Operation;
import io.jutil.coreservice.core.facade.AuditLogFacade;
import io.jutil.springeasy.mybatis.metadata.DatabaseProduct;
import io.jutil.springeasy.mybatis.metadata.MetadataUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * @author Jin Zheng
 * @since 2025-10-09
 */
public abstract class ConfigItemRepositoryTest {
	@Autowired
	ConfigItemRepository repository;

	@Autowired
	ConfigMapper configMapper;

	@Autowired
	AuditLogRepositoryTest auditLogRepositoryTest;

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
		auditLogRepositoryTest.beforeEach(AuditLogFacade.Business.CONFIG_ITEM);

		var entity = ConfigTest.create();
		Assertions.assertEquals(1, configMapper.insertOne(entity));
		configId = entity.getId();
	}

	@Test
	public void testAddOne() {
		var entity = ConfigItemTest.create(configId);
		var view = repository.addOne(entity);
		ConfigItemTest.verify(view, 1L, configId,  2L, "name",
				"value", 0, "remarks");

		var logList = auditLogRepositoryTest.list(entity.getTenantId(), entity.getId(),
				AuditLogFacade.Business.CONFIG_ITEM);
		Assertions.assertEquals(1, logList.size());
		AuditLogTest.verify(logList.getFirst(), entity.getTenantId(), entity.getId(), 2L,
				1, JSONObject.from(entity));
	}

	@Test
	public void testUpdateOne() {
		var entity = ConfigItemTest.create(configId);
		var view = repository.addOne(entity);
		ConfigItemTest.verify(view, 1L, configId,  2L, "name",
				"value", 0, "remarks");

		var entity2 = new ConfigItem();
		entity2.setId(view.getId());
		entity2.setTenantId(entity.getTenantId());
		entity2.setValue("value2");
		entity2.setOperatorId(22L);
		var view2 = repository.updateOne(entity2);
		ConfigItemTest.verify(view2, 1L, configId, 22L,  "name",
				"value2", 0, "remarks");

		var logList = auditLogRepositoryTest.list(entity.getTenantId(), entity.getId(),
				AuditLogFacade.Business.CONFIG_ITEM);
		Assertions.assertEquals(2, logList.size());
		for (var log : logList) {
			if (log.getOperation() == Operation.ADD) {
				AuditLogTest.verify(log, entity.getTenantId(), entity.getId(), 2L,
						1, JSONObject.from(entity));
			} else {
				AuditLogTest.verify(log, entity.getTenantId(), entity.getId(), 22L,
						2, JSONObject.from(entity2));
			}
		}

	}

	@Test
	public void testDeleteOne() {
		var entity = ConfigItemTest.create(configId);
		var view = repository.addOne(entity);
		ConfigItemTest.verify(view, 1L, configId,  2L, "name",
				"value", 0, "remarks");

		Assertions.assertEquals(1, repository.deleteOne(
				DeleteTenantAuditEntityTest.create(entity.getTenantId(), view.getId(), 22L)));

		var logList = auditLogRepositoryTest.list(entity.getTenantId(), entity.getId(),
				AuditLogFacade.Business.CONFIG_ITEM);
		Assertions.assertEquals(2, logList.size());
		for (var log : logList) {
			if (log.getOperation() == Operation.ADD) {
				AuditLogTest.verify(log, entity.getTenantId(), entity.getId(), 2L,
						1, JSONObject.from(entity));
			} else {
				AuditLogTest.verify(log, entity.getTenantId(), entity.getId(), 22L,
						3, JSONObject.of("id", entity.getId()));
			}
		}

	}

	@Test
	public void testGetAndDeleteList() {
		var entity = ConfigItemTest.create(configId);
		var view = repository.addOne(entity);
		ConfigItemTest.verify(view, 1L, configId,  2L, "name",
				"value", 0, "remarks");
		var entity2 = ConfigItemTest.create(configId);
		entity2.setValue("value2");
		var view2 = repository.addOne(entity2);
		ConfigItemTest.verify(view2, 1L, configId,  2L, "name",
				"value2", 0, "remarks");

		var viewList = repository.listByConfigId(entity.getTenantId(), configId);
		Assertions.assertEquals(2, viewList.size());
		for (var v : viewList) {
			if (v.getId() == entity.getId()) {
				ConfigItemTest.verify(v, 1L, configId,  2L, "name",
						"value", 0, "remarks");
			} else {
				ConfigItemTest.verify(v, 1L, configId,  2L, "name",
						"value2", 0, "remarks");
			}
		}

		Assertions.assertEquals(2, repository.deleteList(
				DeleteListTenantAuditEntityTest.create(entity.getTenantId(),
						List.of(view.getId(), view2.getId()), 22L)));

		var logList = auditLogRepositoryTest.list(entity.getTenantId(), entity.getId(),
				AuditLogFacade.Business.CONFIG_ITEM, Operation.ADD);
		Assertions.assertEquals(1, logList.size());
		AuditLogTest.verify(logList.getFirst(), entity.getTenantId(), entity.getId(),
				2L,1, JSONObject.from(entity));

		logList = auditLogRepositoryTest.list(entity.getTenantId(), entity.getId(),
				AuditLogFacade.Business.CONFIG_ITEM, Operation.DELETE);
		for (var log : logList) {
			if (log.getBusinessId() == entity.getId()) {
				AuditLogTest.verify(log, entity.getTenantId(), entity.getId(),
						22L,3, JSONObject.of("id", log.getBusinessId()));
			} else {
				AuditLogTest.verify(log, entity.getTenantId(), entity2.getId(),
						22L,3, JSONObject.of("id", log.getBusinessId()));
			}
		}

	}
}
