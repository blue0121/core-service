package io.jutil.coreservice.admin.covertor;

import io.jutil.coreservice.admin.entity.Tenant;
import io.jutil.coreservice.admin.entity.TenantSearch;
import io.jutil.coreservice.admin.model.TenantRequest;
import io.jutil.coreservice.admin.model.TenantResponse;
import io.jutil.coreservice.admin.model.TenantSearchRequest;
import io.jutil.coreservice.core.convertor.BaseRequestConvertor;
import io.jutil.coreservice.core.convertor.BaseResponseConvertor;
import io.jutil.coreservice.core.model.PageResponse;
import io.jutil.springeasy.core.collection.Page;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jin Zheng
 * @since 2025-09-12
 */
public class TenantConvertor {
	private TenantConvertor() {}

	public static TenantSearch toSearch(TenantSearchRequest request) {
		var filter = request.getFilter();
		var search = new TenantSearch();
		search.setStatus(filter.getStatus());
		search.setCode(filter.getCode());
		search.setName(filter.getName());
		search.setIdList(filter.getIdList());
		return search;
	}

	public static Tenant toEntity(TenantRequest request) {
		var entity = BaseRequestConvertor.toAuditEntity(request, Tenant::new);
		entity.setCode(request.getCode());
		entity.setName(request.getName());
		entity.setStatus(request.getStatus());
		entity.setRemarks(request.getRemarks());
		return entity;
	}

	public static TenantResponse toResponse(Tenant entity) {
		if (entity == null) {
			return null;
		}
		var response = BaseResponseConvertor.toAuditResponse(entity, TenantResponse::new);
		response.setCode(entity.getCode());
		response.setName(entity.getName());
		response.setStatus(entity.getStatus());
		response.setRemarks(entity.getRemarks());
		return response;
	}

	public static Map<Long, TenantResponse> toMapResponse(Map<Long, Tenant> entityMap) {
		Map<Long, TenantResponse> responseMap = new HashMap<>();
		if (entityMap == null || entityMap.isEmpty()) {
			return responseMap;
		}
		for (var entry : entityMap.entrySet()) {
			responseMap.put(entry.getKey(), toResponse(entry.getValue()));
		}
		return responseMap;
	}

	public static List<TenantResponse> toListResponse(List<Tenant> entityList) {
		List<TenantResponse> responseList = new ArrayList<>();
		if (entityList == null || entityList.isEmpty()) {
			return responseList;
		}
		for (var entry : entityList) {
			responseList.add(toResponse(entry));
		}
		return responseList;
	}

	public static PageResponse toPageResponse(Page page) {
		var response = PageResponse.from(page);
		response.setContents(toListResponse(page.getContents()));
		return response;
	}
}
