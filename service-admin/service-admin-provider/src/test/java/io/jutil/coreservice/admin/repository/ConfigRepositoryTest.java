package io.jutil.coreservice.admin.repository;

import com.alibaba.fastjson2.JSONObject;
import io.jutil.coreservice.admin.entity.AuditLogTest;
import io.jutil.coreservice.admin.entity.Config;
import io.jutil.coreservice.admin.entity.ConfigTest;
import io.jutil.coreservice.admin.entity.DeleteListTenantAuditEntityTest;
import io.jutil.coreservice.admin.entity.DeleteTenantAuditEntityTest;
import io.jutil.coreservice.admin.entity.PageTest;
import io.jutil.coreservice.core.dict.Operation;
import io.jutil.coreservice.core.dict.Status;
import io.jutil.coreservice.core.facade.AuditLogFacade;
import io.jutil.springeasy.core.collection.Page;
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
public abstract class ConfigRepositoryTest {
	@Autowired
	ConfigRepository repository;

	@Autowired
	AuditLogRepositoryTest auditLogRepositoryTest;

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
		auditLogRepositoryTest.beforeEach(AuditLogFacade.Business.CONFIG);
	}

	@Test
	public void testAddOne() {
		var entity = ConfigTest.create();
		var view = repository.addOne(entity);
		ConfigTest.verify(view, 1L, 2L,  "code", "name",
				"value", 0, 0,"remarks");

		var logList = auditLogRepositoryTest.list(entity.getTenantId(), entity.getId(),
				AuditLogFacade.Business.CONFIG);
		Assertions.assertEquals(1, logList.size());
		AuditLogTest.verify(logList.getFirst(), entity.getTenantId(), entity.getId(), 2L,
				1, JSONObject.from(entity));
	}

	@Test
	public void testUpdateOne() {
		var entity = ConfigTest.create();
		var view = repository.addOne(entity);
		ConfigTest.verify(view, 1L, 2L,  "code", "name",
				"value", 0, 0,"remarks");

		var entity2 = new Config();
		entity2.setId(view.getId());
		entity2.setTenantId(entity.getTenantId());
		entity2.setCode("code2");
		entity2.setOperatorId(22L);
		var view2 = repository.updateOne(entity2);
		ConfigTest.verify(view2, 1L, 22L,  "code2", "name",
				null, 0, 0,"remarks");

		var logList = auditLogRepositoryTest.list(entity.getTenantId(), entity.getId(),
				AuditLogFacade.Business.CONFIG);
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
		var entity = ConfigTest.create();
		var view = repository.addOne(entity);
		ConfigTest.verify(view, 1L, 2L,  "code", "name",
				"value", 0, 0,"remarks");

		Assertions.assertEquals(1, repository.deleteOne(
				DeleteTenantAuditEntityTest.create(entity.getTenantId(), view.getId(), 22L)));

		var logList = auditLogRepositoryTest.list(entity.getTenantId(), entity.getId(),
				AuditLogFacade.Business.CONFIG);
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
		var entity = ConfigTest.create();
		var view = repository.addOne(entity);
		ConfigTest.verify(view, 1L, 2L,  "code", "name",
				"value", 0, 0,"remarks");
		var entity2 = ConfigTest.create();
		entity2.setCode("code2");
		var view2 = repository.addOne(entity2);
		ConfigTest.verify(view2, 1L, 2L,  "code2", "name",
				"value", 0, 0,"remarks");

		var viewList = repository.getList(entity.getTenantId(), List.of(entity.getId(), entity2.getId()));
		Assertions.assertEquals(2, viewList.size());
		for (var v : viewList) {
			if (v.getId() == entity.getId()) {
				ConfigTest.verify(v, 1L, 2L,  "code", "name",
						"value", 0, 0,"remarks");
			} else {
				ConfigTest.verify(v, 1L, 2L,  "code2", "name",
						"value", 0, 0,"remarks");
			}
		}

		Assertions.assertEquals(2, repository.deleteList(
				DeleteListTenantAuditEntityTest.create(entity.getTenantId(),
						List.of(view.getId(), view2.getId()), 22L)));

		var logList = auditLogRepositoryTest.list(entity.getTenantId(), entity.getId(),
				AuditLogFacade.Business.CONFIG, Operation.ADD);
		Assertions.assertEquals(1, logList.size());
		AuditLogTest.verify(logList.getFirst(), entity.getTenantId(), entity.getId(),
				2L,1, JSONObject.from(entity));

		logList = auditLogRepositoryTest.list(entity.getTenantId(), entity.getId(),
				AuditLogFacade.Business.CONFIG, Operation.DELETE);
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

	@Test
	public void testSearch() {
		var entity = ConfigTest.create();
		var view = repository.addOne(entity);
		ConfigTest.verify(view, 1L, 2L,  "code", "name",
				"value", 0, 0,"remarks");

		var search = new Config.Search();
		search.tenantId = entity.getTenantId();
		search.status = Status.ACTIVE;
		var page = new Page();
		page = repository.search(search, page);
		PageTest.verify(page, 1, 10, 0, 1, 1);
		List<Config> viewList = page.getContents();
		ConfigTest.verify(viewList.getFirst(), 1L, 2L, "code", "name",
				"value", 0, 0,"remarks");
	}
}
