package io.jutil.coreservice.admin.covertor;

import io.jutil.coreservice.admin.entity.Config;
import io.jutil.coreservice.admin.model.ConfigRequest;
import io.jutil.coreservice.admin.model.ConfigResponse;
import io.jutil.coreservice.admin.model.ConfigSearchRequest;
import io.jutil.coreservice.core.convertor.BaseRequestConvertor;
import io.jutil.coreservice.core.convertor.BaseResponseConvertor;
import io.jutil.coreservice.core.model.PageResponse;
import io.jutil.springeasy.core.collection.Page;

import java.util.List;
import java.util.Map;

/**
 * @author Jin Zheng
 * @since 2025-10-09
 */
public class ConfigConvertor {
	private ConfigConvertor() {}

	public static Config.Search toSearch(ConfigSearchRequest request) {
		var search = new Config.Search();
		var filter = request.getFilter();
		search.tenantId = filter.getTenantId();
		search.operatorId = filter.getOperatorId();
		search.code = filter.getCode();
		search.name = filter.getName();
		search.status = filter.getStatus();
		search.multiValue = filter.getMultiValue();
		search.idList = filter.getIdList();
		return search;
	}

	public static Config toEntity(ConfigRequest request) {
		var entity = BaseRequestConvertor.toTenantAuditEntity(request, Config::new);
		entity.setCode(request.getCode());
		entity.setName(request.getName());
		entity.setValue(request.getValue());
		entity.setStatus(request.getStatus());
		entity.setMultiValue(request.getMultiValue());
		entity.setRemarks(request.getRemarks());
		return entity;
	}

	public static ConfigResponse toResponse(Config entity) {
		if (entity == null) {
			return null;
		}
		var response = BaseResponseConvertor.toTenantAuditResponse(entity, ConfigResponse::new);
		response.setCode(entity.getCode());
		response.setName(entity.getName());
		response.setValue(entity.getValue());
		response.setStatus(entity.getStatus());
		response.setMultiValue(entity.getMultiValue());
		response.setRemarks(entity.getRemarks());
		return response;
	}

	public static List<ConfigResponse> toListResponse(List<Config> entityList) {
		return BaseResponseConvertor.toListResponse(entityList, ConfigConvertor::toResponse);
	}

	public static Map<Long, ConfigResponse> toMapResponse(Map<Long, Config> entityMap) {
		return BaseResponseConvertor.toMapResponse(entityMap, ConfigConvertor::toResponse);
	}

	public static PageResponse toPageResponse(Page page) {
		return BaseResponseConvertor.toPageResponse(page, ConfigConvertor::toListResponse);
	}
}

