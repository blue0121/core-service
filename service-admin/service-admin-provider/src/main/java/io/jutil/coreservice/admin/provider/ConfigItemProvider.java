package io.jutil.coreservice.admin.provider;

import io.jutil.coreservice.admin.covertor.ConfigItemConvertor;
import io.jutil.coreservice.admin.facade.ConfigItemFacade;
import io.jutil.coreservice.admin.model.ConfigItemRequest;
import io.jutil.coreservice.admin.model.ConfigItemResponse;
import io.jutil.coreservice.admin.service.ConfigItemService;
import io.jutil.coreservice.core.entity.DeleteListTenantAuditEntity;
import io.jutil.coreservice.core.entity.DeleteTenantAuditEntity;
import io.jutil.coreservice.core.model.DeleteListTenantAuditRequest;
import io.jutil.coreservice.core.model.DeleteTenantAuditRequest;
import io.jutil.coreservice.core.util.AssertUtil;
import io.jutil.springeasy.core.validation.group.AddOperation;
import io.jutil.springeasy.core.validation.group.UpdateOperation;
import io.jutil.springeasy.spring.exception.BaseErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author Jin Zheng
 * @since 2025-10-09
 */
@Component
public class ConfigItemProvider implements ConfigItemFacade {
	@Autowired
	ConfigItemService configItemService;

	@Override
	public ConfigItemResponse addOne(ConfigItemRequest request) {
		request.check(AddOperation.class);
		var entity = ConfigItemConvertor.toEntity(request);
		entity = configItemService.addOne(entity);
		if (entity == null) {
			throw BaseErrorCode.EXISTS.newException(request.getValue());
		}
		return ConfigItemConvertor.toResponse(entity);
	}

	@Override
	public ConfigItemResponse updateOne(ConfigItemRequest request) {
		request.check(UpdateOperation.class);
		var entity = ConfigItemConvertor.toEntity(request);
		entity = configItemService.updateOne(entity);
		return ConfigItemConvertor.toResponse(entity);
	}

	@Override
	public int deleteOne(DeleteTenantAuditRequest request) {
		request.check();
		var entity = DeleteTenantAuditEntity.from(request);
		return configItemService.deleteOne(entity);
	}

	@Override
	public int deleteList(DeleteListTenantAuditRequest request) {
		request.check();
		var entity = DeleteListTenantAuditEntity.from(request);
		return configItemService.deleteList(entity);
	}

	@Override
	public List<ConfigItemResponse> listByConfigId(long tenantId, long configId) {
		AssertUtil.validId(tenantId, "tenantId");
		AssertUtil.validId(configId, "configId");
		var list = configItemService.listByConfigId(tenantId, configId);
		return ConfigItemConvertor.toListResponse(list);
	}

	@Override
	public Map<Long, List<ConfigItemResponse>> listByConfigIdList(long tenantId, Collection<Long> configIdList) {
		AssertUtil.validId(tenantId, "tenantId");
		AssertUtil.validIdList(configIdList, "configIdList");
		var map = configItemService.listByConfigIdList(tenantId, configIdList);
		return ConfigItemConvertor.toMapListResponse(map);
	}
}
