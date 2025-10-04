package io.jutil.coreservice.admin.repository;

import com.alibaba.fastjson2.JSONObject;
import io.jutil.coreservice.admin.dao.AccountTenantMapper;
import io.jutil.coreservice.admin.entity.Account;
import io.jutil.coreservice.admin.entity.AccountSearch;
import io.jutil.coreservice.admin.entity.AccountTest;
import io.jutil.coreservice.admin.entity.AuditLogTest;
import io.jutil.coreservice.admin.entity.PageTest;
import io.jutil.coreservice.core.dict.Operation;
import io.jutil.coreservice.core.dict.Status;
import io.jutil.coreservice.core.facade.AuditLogFacade;
import io.jutil.springeasy.core.collection.Page;
import io.jutil.springeasy.core.util.DateUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Jin Zheng
 * @since 2025-10-02
 */
public abstract class AccountRepositoryTest {
	@Autowired
	AccountRepository repository;

	@Autowired
	AccountTenantMapper accountTenantMapper;

	@Autowired
	AuditLogRepositoryTest auditLogRepositoryTest;

	@Autowired
	JdbcTemplate jdbcTemplate;

	final LocalDateTime now = DateUtil.now();

	@BeforeEach
	public void beforeEach() {
		jdbcTemplate.update("TRUNCATE TABLE adm_account");
		jdbcTemplate.update("TRUNCATE TABLE adm_account_tenant");
		auditLogRepositoryTest.beforeEach(AuditLogFacade.Business.ACCOUNT);
	}

	@Test
	public void testAddOne() {
		var entity = AccountTest.create(now);
		var view = repository.addOne(entity);
		AccountTest.verify(view, 1L, 2L, "code", "name", 0,
				"remarks");

		var tenantIdList = accountTenantMapper.listByAccountId(entity.getId());
		Assertions.assertEquals(entity.getTenantIdList(), tenantIdList);

		var logList = auditLogRepositoryTest.list(entity.getTenantId(), entity.getId(),
				AuditLogFacade.Business.ACCOUNT);
		Assertions.assertEquals(1, logList.size());
		AuditLogTest.verify(logList.getFirst(), entity.getTenantId(), entity.getId(), 2L,
				1, JSONObject.from(entity));
	}

	@Test
	public void testUpdateOne() {
		var entity = AccountTest.create(now);
		var view = repository.addOne(entity);
		AccountTest.verify(view, 1L, 2L, "code", "name", 0,
				"remarks");

		var entity2 = new Account();
		entity2.setId(view.getId());
		entity2.setTenantId(entity.getTenantId());
		entity2.setCode("code2");
		entity2.setOperatorId(1L);
		entity2.setTenantIdList(List.of(1L, 11L, 111L));
		var view2 = repository.updateOne(entity2);
		AccountTest.verify(view2, 1L, 1L, "code2", "name", 0,
				"remarks");

		var tenantIdList = accountTenantMapper.listByAccountId(entity2.getId());
		Assertions.assertEquals(entity2.getTenantIdList(), tenantIdList);

		var logList = auditLogRepositoryTest.list(entity.getTenantId(), entity.getId(),
				AuditLogFacade.Business.ACCOUNT);
		Assertions.assertEquals(2, logList.size());
		for (var log : logList) {
			if (log.getOperation() == Operation.ADD) {
				AuditLogTest.verify(log, entity.getTenantId(), entity.getId(), 2L,
						1, JSONObject.from(entity));
			} else {
				AuditLogTest.verify(log, entity.getTenantId(), entity.getId(), 1L,
						2, JSONObject.from(entity2));
			}
		}

	}

	@Test
	public void testDeleteOne() {
		var entity = AccountTest.create(now);
		var view = repository.addOne(entity);
		AccountTest.verify(view, 1L, 2L, "code", "name", 0,
				"remarks");

		Assertions.assertEquals(1, repository.deleteOne(entity.getTenantId(),
				view.getId(), 1L));
		Assertions.assertTrue(accountTenantMapper.listByAccountId(entity.getId()).isEmpty());

		var logList = auditLogRepositoryTest.list(entity.getTenantId(), entity.getId(),
				AuditLogFacade.Business.ACCOUNT);
		Assertions.assertEquals(2, logList.size());
		for (var log : logList) {
			if (log.getOperation() == Operation.ADD) {
				AuditLogTest.verify(log, entity.getTenantId(), entity.getId(), 2L,
						1, JSONObject.from(entity));
			} else {
				AuditLogTest.verify(log, entity.getTenantId(), entity.getId(), 1L,
						3, JSONObject.of("id", entity.getId()));
			}
		}

	}

	@Test
	public void testGetAndDeleteList() {
		var entity = AccountTest.create(now);
		var view = repository.addOne(entity);
		AccountTest.verify(view, 1L, 2L, "code", "name", 0,
				"remarks");
		var entity2 = AccountTest.create(now);
		entity2.setCode("code2");
		var view2 = repository.addOne(entity2);
		AccountTest.verify(view2, 1L, 2L, "code2", "name", 0,
				"remarks");

		var viewList = repository.getList(entity.getTenantId(), List.of(entity.getId(), entity2.getId()));
		Assertions.assertEquals(2, viewList.size());
		for (var v : viewList) {
			if (v.getId() == entity.getId()) {
				AccountTest.verify(v, 1L, 2L, "code", "name", 0,
						"remarks");
			} else {
				AccountTest.verify(v, 1L, 2L, "code2", "name", 0,
						"remarks");
			}
		}

		Assertions.assertEquals(2, repository.deleteList(entity.getTenantId(),
				List.of(entity.getId(), entity2.getId()), 2L));

		Assertions.assertTrue(accountTenantMapper.listByAccountId(entity.getId()).isEmpty());
		Assertions.assertTrue(accountTenantMapper.listByAccountId(entity2.getId()).isEmpty());

		var logList = auditLogRepositoryTest.list(entity.getTenantId(), entity.getId(),
				AuditLogFacade.Business.ACCOUNT, Operation.ADD);
		Assertions.assertEquals(1, logList.size());
		AuditLogTest.verify(logList.getFirst(), entity.getTenantId(), entity.getId(),
				2L,1, JSONObject.from(entity));

		logList = auditLogRepositoryTest.list(entity.getTenantId(), entity.getId(),
				AuditLogFacade.Business.ACCOUNT, Operation.DELETE);
		for (var log : logList) {
			if (log.getBusinessId() == entity.getId()) {
				AuditLogTest.verify(log, entity.getTenantId(), entity.getId(),
						2L,3, JSONObject.of("id", log.getBusinessId()));
			} else {
				AuditLogTest.verify(log, entity.getTenantId(), entity2.getId(),
						2L,3, JSONObject.of("id", log.getBusinessId()));
			}
		}

	}

	@Test
	public void testSearch() {
		var entity = AccountTest.create(now);
		var view = repository.addOne(entity);
		AccountTest.verify(view, 1L, 2L, "code", "name", 0,
				"remarks");

		var search = new AccountSearch();
		search.setTenantId(entity.getTenantId());
		search.setStatus(Status.ACTIVE);
		var page = new Page();
		page = repository.search(search, page);
		PageTest.verify(page, 1, 10, 0, 1, 1);
		List<Account> viewList = page.getContents();
		AccountTest.verify(viewList.getFirst(), 1L, 2L, "code", "name",
				0, "remarks");
	}
}
