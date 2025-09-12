package io.jutil.coreservice.admin.covertor;

import io.jutil.coreservice.admin.entity.AuditLog;
import io.jutil.coreservice.admin.entity.AuditLogSearch;
import io.jutil.coreservice.core.model.AuditLogResponse;
import io.jutil.coreservice.core.model.AuditLogSearchRequest;
import io.jutil.coreservice.core.model.PageResponse;
import io.jutil.springeasy.core.collection.Page;

import java.util.ArrayList;
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
		List<AuditLogResponse> responseList = new ArrayList<>();
		if (entityList == null || entityList.isEmpty()) {
			return responseList;
		}
		for (var entity : entityList) {
			responseList.add(toResponse(entity));
		}
		return responseList;
	}

	public static PageResponse toPageResponse(Page page) {
		var response = PageResponse.from(page);
		response.setContents(toListResponse(page.getContents()));
		return response;
	}
}
