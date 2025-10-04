package io.jutil.coreservice.core.provider;

import io.jutil.coreservice.core.convertor.AuditLogConvertor;
import io.jutil.coreservice.core.facade.AuditLogFacade;
import io.jutil.coreservice.core.model.AuditLogSearchRequest;
import io.jutil.coreservice.core.model.PageResponse;
import io.jutil.coreservice.core.repository.AuditLogRepository;
import io.jutil.coreservice.core.util.AssertUtil;
import io.jutil.springeasy.core.validation.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Jin Zheng
 * @since 2025-09-18
 */
@Component
public class AuditLogProvider implements AuditLogFacade {
	@Autowired
	AuditLogRepository auditLogRepository;

	@Override
	public PageResponse search(AuditLogSearchRequest searchRequest, Business business) {
		ValidationUtil.valid(searchRequest);
		AssertUtil.validNotNull(business, "business");
		var search = AuditLogConvertor.toSearch(searchRequest, business.getLabel());
		var page = searchRequest.toPage();
		page = auditLogRepository.search(search, page);
		return AuditLogConvertor.toPageResponse(page);
	}
}
