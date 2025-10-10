package io.jutil.coreservice.admin.covertor;

import io.jutil.coreservice.admin.entity.ConfigItem;
import io.jutil.coreservice.admin.model.ConfigItemRequest;
import io.jutil.coreservice.admin.model.ConfigItemResponse;
import io.jutil.coreservice.core.convertor.BaseRequestConvertor;
import io.jutil.coreservice.core.convertor.BaseResponseConvertor;

import java.util.List;
import java.util.Map;

/**
 * @author Jin Zheng
 * @since 2025-10-09
 */
public class ConfigItemConvertor {
	private ConfigItemConvertor() {}

	public static ConfigItem toEntity(ConfigItemRequest request) {
		var entity = BaseRequestConvertor.toTenantAuditEntity(request, ConfigItem::new);
		entity.setConfigId(request.getConfigId());
		entity.setName(request.getName());
		entity.setValue(request.getValue());
		entity.setStatus(request.getStatus());
		entity.setRemarks(request.getRemarks());
		return entity;
	}

	public static ConfigItemResponse toResponse(ConfigItem entity) {
		if (entity == null) {
			return null;
		}
		var response = BaseResponseConvertor.toTenantAuditResponse(entity, ConfigItemResponse::new);
		response.setConfigId(entity.getConfigId());
		response.setName(entity.getName());
		response.setValue(entity.getValue());
		response.setStatus(entity.getStatus());
		response.setRemarks(entity.getRemarks());
		return response;
	}

	public static List<ConfigItemResponse> toListResponse(List<ConfigItem> entityList) {
		return BaseResponseConvertor.toListResponse(entityList, ConfigItemConvertor::toResponse);
	}

	public static Map<Long, List<ConfigItemResponse>> toMapListResponse(Map<Long, List<ConfigItem>> entityMap) {
		return BaseResponseConvertor.toMapListResponse(entityMap, ConfigItemConvertor::toResponse);
	}
}
