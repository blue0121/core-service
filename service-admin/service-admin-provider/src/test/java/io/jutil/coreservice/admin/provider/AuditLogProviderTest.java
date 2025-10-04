package io.jutil.coreservice.admin.provider;

import io.jutil.coreservice.core.dict.Operation;
import io.jutil.coreservice.core.facade.AuditLogFacade;
import io.jutil.coreservice.core.model.AuditLogResponse;
import io.jutil.coreservice.core.model.AuditLogSearchRequest;
import io.jutil.coreservice.core.model.SearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Jin Zheng
 * @since 2025-09-19
 */
@Component
public class AuditLogProviderTest {
	@Autowired
	AuditLogFacade facade;

	@Autowired
	JdbcTemplate jdbcTemplate;

	public void beforeEach() {
		jdbcTemplate.update("TRUNCATE TABLE adm_tenant_audit_log");
	}

	public List<AuditLogResponse> list(long tenantId, long businessId, AuditLogFacade.Business business) {
		return this.list(tenantId, businessId, business, null);
	}

	public List<AuditLogResponse> list(long tenantId, long businessId, AuditLogFacade.Business business,
	                                   Operation operation) {
		var search = new AuditLogSearchRequest();
		var page = new SearchRequest.PageRequest();
		search.setPage(page);
		page.setSize(100);
		var filter = search.getFilter();
		filter.setTenantId(tenantId);
		filter.setBusinessId(businessId);
		filter.setOperation(operation);

		var response = facade.search(search, business);
		return response.getContents();
	}
}
