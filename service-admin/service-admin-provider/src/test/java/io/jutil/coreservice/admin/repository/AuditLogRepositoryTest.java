package io.jutil.coreservice.admin.repository;

import io.jutil.coreservice.core.dict.Operation;
import io.jutil.coreservice.core.entity.AuditLog;
import io.jutil.coreservice.core.entity.AuditLogSearch;
import io.jutil.coreservice.core.facade.AuditLogFacade;
import io.jutil.coreservice.core.repository.AuditLogRepository;
import io.jutil.springeasy.core.collection.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.List;

/**
 * @author Jin Zheng
 * @since 2025-09-11
 */
@Component
public class AuditLogRepositoryTest {
	@Autowired
	private AuditLogRepository repository;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public void beforeEach(AuditLogFacade.Business business) {
		jdbcTemplate.update(MessageFormat.format("TRUNCATE TABLE {0}_audit_log", business.getLabel()));
	}

	public List<AuditLog> list(long tenantId, long businessId, AuditLogFacade.Business business) {
		return this.list(tenantId, businessId, business, null);
	}

	public List<AuditLog> list(long tenantId, long businessId, AuditLogFacade.Business business,
	                           Operation operation) {
		var search = new AuditLogSearch();
		search.setTenantId(tenantId);
		search.setBusinessId(businessId);
		search.setBusiness(business.getLabel());
		search.setOperation(operation);

		var page = new Page(1, 100);
		page = repository.search(search, page);
		return page.getContents();
	}
}
