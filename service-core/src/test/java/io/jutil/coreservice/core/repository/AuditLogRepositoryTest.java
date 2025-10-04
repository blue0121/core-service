package io.jutil.coreservice.core.repository;

import com.alibaba.fastjson2.JSONObject;
import io.jutil.coreservice.core.dict.Operation;
import io.jutil.coreservice.core.entity.AuditLog;
import io.jutil.coreservice.core.entity.AuditLogSearch;
import io.jutil.coreservice.core.entity.AuditLogTest;
import io.jutil.coreservice.core.facade.AuditLogFacade;
import io.jutil.springeasy.core.collection.Page;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * @author Jin Zheng
 * @since 2025-09-11
 */
public abstract class AuditLogRepositoryTest {
	@Autowired
	private AuditLogRepository repository;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@BeforeEach
	public void beforeEach() {
		jdbcTemplate.update("TRUNCATE TABLE adm_tenant_audit_log");
	}

	@CsvSource({"add", "update", "delete", "deleteList"})
	@ParameterizedTest
	public void testAddOneAndSearch(String operation) {
		var entity = AuditLogTest.create();
		var business = AuditLogFacade.Business.TENANT;
		var content = JSONObject.of("key", "value");
		int op = 0;
		int count = 0;
		switch (operation) {
			case "add" -> {
				op = 1;
				count = repository.addOperation(business, entity.getTenantId(),
					entity.getBusinessId(), entity.getOperatorId(), content);
			}
			case "update" -> {
				op = 2;
				count = repository.updateOperation(business, entity.getTenantId(),
					entity.getBusinessId(), entity.getOperatorId(), content);
			}
			case "delete" -> {
				op = 3;
				count = repository.deleteOperation(business, entity.getTenantId(),
					entity.getBusinessId(), entity.getOperatorId());
			}
			case "deleteList" -> {
				op = 3;
				count = repository.deleteListOperation(business, entity.getTenantId(),
					List.of(entity.getBusinessId()), entity.getOperatorId());
			}
		};
		Assertions.assertEquals(1, count);
		var viewList = this.list(entity.getTenantId(), entity.getBusinessId(), entity.getBusiness());
		Assertions.assertEquals(1, viewList.size());
		AuditLogTest.verify(viewList.getFirst(), entity.getTenantId(), entity.getBusinessId(),
				entity.getOperatorId(), op);
		if (op == 1 || op == 2) {
			Assertions.assertEquals(content, viewList.getFirst().getContent());
		} else {
			var json = viewList.getFirst().getContent();
			Assertions.assertEquals(1, json.size());
			Assertions.assertEquals(entity.getBusinessId(), json.getIntValue("id"));
		}
	}

	public List<AuditLog> list(long tenantId, long businessId, String business) {
		return this.list(tenantId, businessId, business, null);
	}

	public List<AuditLog> list(long tenantId, long businessId, String business,
	                           Operation operation) {
		var search = new AuditLogSearch();
		search.setTenantId(tenantId);
		search.setBusinessId(businessId);
		search.setBusiness(business);
		search.setOperation(operation);

		var page = new Page(1, 100);
		page = repository.search(search, page);
		return page.getContents();
	}
}
