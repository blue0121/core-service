package io.jutil.coreservice.core.convertor;

import io.jutil.coreservice.core.entity.BaseAuditEntity;
import io.jutil.coreservice.core.entity.BaseEntity;
import io.jutil.coreservice.core.entity.BaseTenantAuditEntity;
import io.jutil.coreservice.core.entity.BaseTenantEntity;
import io.jutil.coreservice.core.model.BaseAuditResponse;
import io.jutil.coreservice.core.model.BaseResponse;
import io.jutil.coreservice.core.model.BaseTenantAuditResponse;
import io.jutil.coreservice.core.model.BaseTenantResponse;
import io.jutil.coreservice.core.model.PageResponse;
import io.jutil.springeasy.core.collection.Page;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author Jin Zheng
 * @since 2025-09-11
 */
public class BaseResponseConvertor {
	private BaseResponseConvertor() {}

	public static <E extends BaseEntity, R extends BaseResponse> R toBaseResponse(E entity, Supplier<R> f) {
		var response = f.get();
		response.setId(entity.getId());
		response.setCreateTime(entity.getCreateTime());
		response.setUpdateTime(entity.getUpdateTime());
		return response;
	}

	public static <E extends BaseAuditEntity, R extends BaseAuditResponse> R toAuditResponse(E entity, Supplier<R> f) {
		var response = toBaseResponse(entity, f);
		response.setOperatorId(entity.getOperatorId());
		response.setOperatorCode(entity.getOperatorCode());
		response.setOperatorName(entity.getOperatorName());
		return response;
	}

	public static <E extends BaseTenantEntity, R extends BaseTenantResponse> R toTenantResponse(E entity, Supplier<R> f) {
		var response = toBaseResponse(entity, f);
		response.setTenantId(entity.getTenantId());
		return response;
	}

	public static <E extends BaseTenantAuditEntity, R extends BaseTenantAuditResponse> R toTenantAuditResponse(E entity, Supplier<R> f) {
		var response = toAuditResponse(entity, f);
		response.setTenantId(entity.getTenantId());
		return response;
	}

	public static <E, R> List<R> toListResponse(List<E> entityList, Function<E, R> f) {
		List<R> responseList = new ArrayList<>();
		if (entityList == null || entityList.isEmpty()) {
			return responseList;
		}
		for (E entity : entityList) {
			var response = f.apply(entity);
			if (response != null) {
				responseList.add(response);
			}
		}
		return responseList;
	}

	public static <E, R> Map<Long, R> toMapResponse(Map<Long, E> entityMap, Function<E, R> f) {
		Map<Long, R> responseMap = new HashMap<>();
		if (entityMap == null || entityMap.isEmpty()) {
			return responseMap;
		}
		for (var entry : entityMap.entrySet()) {
			var response = f.apply(entry.getValue());
			if (response != null) {
				responseMap.put(entry.getKey(), response);
			}
		}
		return responseMap;
	}

	public static <E, R> Map<Long, List<R>> toMapListResponse(Map<Long, List<E>> entityMap, Function<E, R> f) {
		Map<Long, List<R>> responseMap = new HashMap<>();
		if (entityMap == null || entityMap.isEmpty()) {
			return responseMap;
		}
		for (var entry : entityMap.entrySet()) {
			var responseList = toListResponse(entry.getValue(), f);
			responseMap.put(entry.getKey(), responseList);
		}
		return responseMap;
	}

	public static <E, R> PageResponse toPageResponse(Page page, Function<List<E>, List<R>> f) {
		var response = PageResponse.from(page);
		List<E> entityList = page.getContents();
		response.setContents(f.apply(entityList));
		return response;
	}
}
