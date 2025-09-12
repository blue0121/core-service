package io.jutil.coreservice.admin.service;

import io.jutil.coreservice.admin.entity.AuditLogSearch;
import io.jutil.coreservice.admin.repository.AuditLogRepository;
import io.jutil.springeasy.core.collection.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Jin Zheng
 * @since 2025-09-12
 */
@Component
public class AuditLogService {
	@Autowired
	AuditLogRepository auditLogRepository;

	public Page search(AuditLogSearch search, Page page) {
		return auditLogRepository.search(search, page);
	}
}
