package io.jutil.coreservice.core.convertor;

import io.jutil.coreservice.core.entity.AuditLog;
import io.jutil.coreservice.core.entity.AuditLogSearch;
import io.jutil.coreservice.core.model.AuditLogResponse;
import io.jutil.coreservice.core.model.AuditLogSearchRequest;
import io.jutil.coreservice.core.model.PageResponse;
import io.jutil.springeasy.core.collection.Page;

import java.util.List;

/**
 * @author Jin Zheng
 * @since 2025-09-12
 */
public class AuditLogConvertor {
	private AuditLogConvertor() {}

	public static AuditLogSearch toSearch(AuditLogSearchRequest request, String business) {
		var filter = request.getFilter();
		var search = new AuditLogSearch();
		search.setTenantId(filter.getTenantId());
		search.setBusinessId(filter.getBusinessId());
		search.setOperatorId(filter.getOperatorId());
		search.setOperation(filter.getOperation());
		search.setStartDate(filter.getStartDate());
		search.setEndDate(filter.getEndDate());
		search.setBusiness(business);
		return search;
	}

	public static AuditLogResponse toResponse(AuditLog entity) {
		if (entity == null) {
			return null;
		}
		var response = new AuditLogResponse();
		response.setId(entity.getId());
		response.setTenantId(entity.getTenantId());
		response.setBusinessId(entity.getBusinessId());
		response.setOperatorId(entity.getOperatorId());
		response.setOperation(entity.getOperation());
		response.setContent(entity.getContent());
		response.setCreateTime(entity.getCreateTime());
		response.setOperatorCode(entity.getOperatorCode());
		response.setOperatorName(entity.getOperatorName());
		return response;
	}

	public static List<AuditLogResponse> toListResponse(List<AuditLog> entityList) {
		return BaseResponseConvertor.toListResponse(entityList, AuditLogConvertor::toResponse);
	}

	public static PageResponse toPageResponse(Page page) {
		return BaseResponseConvertor.toPageResponse(page, AuditLogConvertor::toListResponse);
	}
}
