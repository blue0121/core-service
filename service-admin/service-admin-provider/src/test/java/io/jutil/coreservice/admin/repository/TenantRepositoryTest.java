package io.jutil.coreservice.admin.repository;

import com.alibaba.fastjson2.JSONObject;
import io.jutil.coreservice.admin.entity.AuditLogTest;
import io.jutil.coreservice.admin.entity.PageTest;
import io.jutil.coreservice.admin.entity.Tenant;
import io.jutil.coreservice.admin.entity.TenantSearch;
import io.jutil.coreservice.admin.entity.TenantTest;
import io.jutil.coreservice.core.dict.Operation;
import io.jutil.coreservice.core.dict.Status;
import io.jutil.coreservice.core.facade.AuditLogFacade;
import io.jutil.coreservice.core.util.Const;
import io.jutil.springeasy.core.collection.Page;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * @author Jin Zheng
 * @since 2025-09-11
 */
public abstract class TenantRepositoryTest {
	@Autowired
	TenantRepository repository;

	@Autowired
	AuditLogRepositoryTest auditLogRepositoryTest;

	@Autowired
	JdbcTemplate jdbcTemplate;

	@BeforeEach
	public void beforeEach() {
		jdbcTemplate.update("TRUNCATE TABLE adm_tenant");
		auditLogRepositoryTest.beforeEach(AuditLogFacade.Business.TENANT);
	}

	@Test
	public void testAddOne() {
		var entity = TenantTest.create();
		var view = repository.addOne(entity);
		TenantTest.verify(view, 1L, "code", "name", 0,
				"remarks");

		var logList = auditLogRepositoryTest.list(Const.DEFAULT_TENANT_ID, entity.getId(),
				AuditLogFacade.Business.TENANT);
		Assertions.assertEquals(1, logList.size());
		AuditLogTest.verify(logList.getFirst(), Const.DEFAULT_TENANT_ID, entity.getId(), 1L,
				1, JSONObject.from(entity));
	}

	@Test
	public void testUpdateOne() {
		var entity = TenantTest.create();
		var view = repository.addOne(entity);
		TenantTest.verify(view, 1L, "code", "name", 0,
				"remarks");

		var entity2 = new Tenant();
		entity2.setId(view.getId());
		entity2.setCode("code2");
		entity2.setOperatorId(1L);
		var view2 = repository.updateOne(entity2);
		TenantTest.verify(view2, 1L, "code2", "name", 0,
				"remarks");

		var logList = auditLogRepositoryTest.list(Const.DEFAULT_TENANT_ID, entity.getId(),
				AuditLogFacade.Business.TENANT);
		Assertions.assertEquals(2, logList.size());
		for (var log : logList) {
			if (log.getOperation() == Operation.ADD) {
				AuditLogTest.verify(log, Const.DEFAULT_TENANT_ID, entity.getId(), 1L,
						1, JSONObject.from(entity));
			} else {
				AuditLogTest.verify(log, Const.DEFAULT_TENANT_ID, entity.getId(), 1L,
						2, JSONObject.from(entity2));
			}
		}

	}

	@Test
	public void testDeleteOne() {
		var entity = TenantTest.create();
		var view = repository.addOne(entity);
		TenantTest.verify(view, 1L, "code", "name", 0,
				"remarks");

		Assertions.assertEquals(1, repository.deleteOne(view.getId(), 1L));

		var logList = auditLogRepositoryTest.list(Const.DEFAULT_TENANT_ID, entity.getId(),
				AuditLogFacade.Business.TENANT);
		Assertions.assertEquals(2, logList.size());
		for (var log : logList) {
			if (log.getOperation() == Operation.ADD) {
				AuditLogTest.verify(log, Const.DEFAULT_TENANT_ID, entity.getId(), 1L,
						1, JSONObject.from(entity));
			} else {
				AuditLogTest.verify(log, Const.DEFAULT_TENANT_ID, entity.getId(), 1L,
						3, JSONObject.of("id", entity.getId()));
			}
		}

	}

	@Test
	public void testGetAndDeleteList() {
		var entity = TenantTest.create();
		var view = repository.addOne(entity);
		TenantTest.verify(view, 1L, "code", "name", 0,
				"remarks");
		var entity2 = TenantTest.create();
		entity2.setCode("code2");
		var view2 = repository.addOne(entity2);
		TenantTest.verify(view2, 1L, "code2", "name", 0,
				"remarks");

		var viewList = repository.getList(List.of(entity.getId(), entity2.getId()));
		Assertions.assertEquals(2, viewList.size());
		for (var v : viewList) {
			if (v.getId() == entity.getId()) {
				TenantTest.verify(v, 1L, "code", "name", 0,
						"remarks");
			} else {
				TenantTest.verify(v, 1L, "code2", "name", 0,
						"remarks");
			}
		}

		Assertions.assertEquals(2,
				repository.deleteList(List.of(entity.getId(), entity2.getId()), 2L));

		var logList = auditLogRepositoryTest.list(Const.DEFAULT_TENANT_ID, entity.getId(),
				AuditLogFacade.Business.TENANT, Operation.ADD);
		Assertions.assertEquals(1, logList.size());
		AuditLogTest.verify(logList.getFirst(), Const.DEFAULT_TENANT_ID, entity.getId(),
				1L,1, JSONObject.from(entity));

		logList = auditLogRepositoryTest.list(Const.DEFAULT_TENANT_ID, entity.getId(),
				AuditLogFacade.Business.TENANT, Operation.DELETE);
		for (var log : logList) {
			if (log.getBusinessId() == entity.getId()) {
				AuditLogTest.verify(log, Const.DEFAULT_TENANT_ID, entity.getId(),
						2L,3, JSONObject.of("id", log.getBusinessId()));
			} else {
				AuditLogTest.verify(log, Const.DEFAULT_TENANT_ID, entity2.getId(),
						2L,3, JSONObject.of("id", log.getBusinessId()));
			}
		}

	}

	@Test
	public void testSearch() {
		var entity = TenantTest.create();
		var view = repository.addOne(entity);
		TenantTest.verify(view, 1L, "code", "name", 0,
				"remarks");

		var search = new TenantSearch();
		search.setStatus(Status.ACTIVE);
		var page = new Page();
		page = repository.search(search, page);
		PageTest.verify(page, 1, 10, 0, 1, 1);
		List<Tenant> viewList = page.getContents();
		TenantTest.verify(viewList.getFirst(), 1L, "code", "name", 0,
				"remarks");
	}
}
