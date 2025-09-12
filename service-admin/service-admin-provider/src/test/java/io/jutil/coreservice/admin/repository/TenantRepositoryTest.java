package io.jutil.coreservice.admin.repository;

import io.jutil.coreservice.admin.entity.AuditLogTest;
import io.jutil.coreservice.admin.entity.Tenant;
import io.jutil.coreservice.admin.entity.TenantTest;
import io.jutil.coreservice.admin.util.BusinessType;
import io.jutil.coreservice.core.dict.Operation;
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
		auditLogRepositoryTest.beforeEach();
	}

	@Test
	public void testAddOne() {
		var entity = TenantTest.create();
		var view = repository.addOne(entity);
		TenantTest.verify(view, 1L, "code", "name", 0,
				"remarks");

		var logList = auditLogRepositoryTest.list(entity.getId(), entity.getId(),
				BusinessType.TENANT);
		Assertions.assertEquals(1, logList.size());
		AuditLogTest.verify(logList.getFirst(), entity.getId(), entity.getId(), 1L,
				1);
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

		var logList = auditLogRepositoryTest.list(entity.getId(), entity.getId(),
				BusinessType.TENANT);
		Assertions.assertEquals(2, logList.size());
		for (var log : logList) {
			if (log.getOperation() == Operation.ADD) {
				AuditLogTest.verify(log, entity.getId(), entity.getId(), 1L,1);
			} else {
				AuditLogTest.verify(log, entity.getId(), entity.getId(), 1L,2);
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

		var logList = auditLogRepositoryTest.list(entity.getId(), entity.getId(),
				BusinessType.TENANT);
		Assertions.assertEquals(2, logList.size());
		for (var log : logList) {
			if (log.getOperation() == Operation.ADD) {
				AuditLogTest.verify(log, entity.getId(), entity.getId(), 1L,1);
			} else {
				AuditLogTest.verify(log, entity.getId(), entity.getId(), 1L,3);
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

		var logList = auditLogRepositoryTest.list(entity.getId(), entity.getId(),
				BusinessType.TENANT, Operation.ADD);
		Assertions.assertEquals(1, logList.size());
		AuditLogTest.verify(logList.getFirst(), entity.getId(), entity.getId(), 1L,1);

		logList = auditLogRepositoryTest.list(entity.getId(), entity.getId(),
				BusinessType.TENANT, Operation.DELETE);
		for (var log : logList) {
			if (log.getBusinessId() == entity.getId()) {
				AuditLogTest.verify(log, entity.getId(), entity.getId(), 2L,3);
			} else {
				AuditLogTest.verify(log, entity2.getId(), entity2.getId(), 2L,3);
			}
		}

	}
}
