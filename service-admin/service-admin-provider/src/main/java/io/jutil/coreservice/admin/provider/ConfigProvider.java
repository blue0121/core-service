package io.jutil.coreservice.admin.provider;

import io.jutil.coreservice.admin.covertor.ConfigConvertor;
import io.jutil.coreservice.admin.facade.ConfigFacade;
import io.jutil.coreservice.admin.model.ConfigRequest;
import io.jutil.coreservice.admin.model.ConfigResponse;
import io.jutil.coreservice.admin.model.ConfigSearchRequest;
import io.jutil.coreservice.admin.service.ConfigService;
import io.jutil.coreservice.core.entity.DeleteListTenantAuditEntity;
import io.jutil.coreservice.core.entity.DeleteTenantAuditEntity;
import io.jutil.coreservice.core.facade.AuditLogFacade;
import io.jutil.coreservice.core.model.AuditLogSearchRequest;
import io.jutil.coreservice.core.model.DeleteListTenantAuditRequest;
import io.jutil.coreservice.core.model.DeleteTenantAuditRequest;
import io.jutil.coreservice.core.model.PageResponse;
import io.jutil.coreservice.core.util.AssertUtil;
import io.jutil.springeasy.core.validation.ValidationUtil;
import io.jutil.springeasy.core.validation.group.AddOperation;
import io.jutil.springeasy.core.validation.group.UpdateOperation;
import io.jutil.springeasy.spring.exception.BaseErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;

/**
 * @author Jin Zheng
 * @since 2025-10-09
 */
@Component
public class ConfigProvider implements ConfigFacade {
	@Autowired
	AuditLogFacade auditLogFacade;

	@Autowired
	ConfigService configService;

	@Override
	public ConfigResponse getOne(long tenantId, long id) {
		AssertUtil.validId(tenantId, "租户ID");
		AssertUtil.validId(id, "ID");
		var entity = configService.getOne(tenantId, id);
		return ConfigConvertor.toResponse(entity);
	}

	@Override
	public Map<Long, ConfigResponse> getList(long tenantId, Collection<Long> idList) {
		AssertUtil.validId(tenantId, "租户ID");
		AssertUtil.validIdList(idList, "ID列表");
		var map = configService.getList(tenantId, idList);
		return ConfigConvertor.toMapResponse(map);
	}

	@Override
	public ConfigResponse addOne(ConfigRequest request) {
		request.check(AddOperation.class);
		var entity = ConfigConvertor.toEntity(request);
		entity = configService.addOne(entity);
		if (entity == null) {
			throw BaseErrorCode.EXISTS.newException(request.getCode());
		}

		return ConfigConvertor.toResponse(entity);
	}

	@Override
	public ConfigResponse updateOne(ConfigRequest request) {
		request.check(UpdateOperation.class);
		var entity = ConfigConvertor.toEntity(request);
		entity = configService.updateOne(entity);
		return ConfigConvertor.toResponse(entity);
	}

	@Override
	public int deleteOne(DeleteTenantAuditRequest request) {
		request.check();
		var entity = DeleteTenantAuditEntity.from(request);
		return configService.deleteOne(entity);
	}

	@Override
	public int deleteList(DeleteListTenantAuditRequest request) {
		request.check();
		var entity = DeleteListTenantAuditEntity.from(request);
		return configService.deleteList(entity);
	}

	@Override
	public PageResponse search(ConfigSearchRequest searchRequest) {
		ValidationUtil.valid(searchRequest);
		var search = ConfigConvertor.toSearch(searchRequest);
		var page = searchRequest.toPage();
		page = configService.search(search, page);
		return ConfigConvertor.toPageResponse(page);
	}

	@Override
	public PageResponse searchAuditLog(AuditLogSearchRequest searchRequest) {
		return auditLogFacade.search(searchRequest, AuditLogFacade.Business.ACCOUNT);
	}
}
