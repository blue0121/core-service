package io.jutil.coreservice.admin.provider;

import io.jutil.coreservice.admin.covertor.AuditLogConvertor;
import io.jutil.coreservice.admin.covertor.TenantConvertor;
import io.jutil.coreservice.admin.facade.TenantFacade;
import io.jutil.coreservice.admin.model.TenantRequest;
import io.jutil.coreservice.admin.model.TenantResponse;
import io.jutil.coreservice.admin.model.TenantSearchRequest;
import io.jutil.coreservice.admin.service.AuditLogService;
import io.jutil.coreservice.admin.service.TenantService;
import io.jutil.coreservice.admin.util.BusinessType;
import io.jutil.coreservice.core.model.AuditLogSearchRequest;
import io.jutil.coreservice.core.model.PageResponse;
import io.jutil.coreservice.core.util.AssertUtil;
import io.jutil.springeasy.core.validation.ValidationUtil;
import io.jutil.springeasy.core.validation.group.AddOperation;
import io.jutil.springeasy.core.validation.group.UpdateOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * @author Jin Zheng
 * @since 2025-09-12
 */
@Component
public class TenantProvider implements TenantFacade {
	private static final Set<String> SORT_FIELD_SET = Set.of("id", "createTime", "updateTime");

	@Autowired
	TenantService tenantService;

	@Autowired
	AuditLogService auditLogService;

	@Override
	public TenantResponse getOne(long id) {
		AssertUtil.validId(id, "ID");
		var entity = tenantService.getOne(id);
		return TenantConvertor.toResponse(entity);
	}

	@Override
	public Map<Long, TenantResponse> getList(Collection<Long> idList) {
		AssertUtil.validIdList(idList, "ID列表");
		var map = tenantService.getList(idList);
		return TenantConvertor.toMapResponse(map);
	}

	@Override
	public TenantResponse addOne(TenantRequest request) {
		ValidationUtil.valid(request, AddOperation.class);
		var entity = TenantConvertor.toEntity(request);
		entity = tenantService.addOne(entity);
		return TenantConvertor.toResponse(entity);
	}

	@Override
	public TenantResponse updateOne(TenantRequest request) {
		ValidationUtil.valid(request, UpdateOperation.class);
		var entity = TenantConvertor.toEntity(request);
		entity = tenantService.updateOne(entity);
		return TenantConvertor.toResponse(entity);
	}

	@Override
	public int deleteOne(long id, long operatorId) {
		AssertUtil.validId(id, "ID");
		AssertUtil.validId(operatorId, "操作人ID");
		return tenantService.deleteOne(id, operatorId);
	}

	@Override
	public int deleteList(Collection<Long> idList, long operatorId) {
		AssertUtil.validIdList(idList, "ID列表");
		AssertUtil.validId(operatorId, "操作人ID");
		return tenantService.deleteList(idList, operatorId);
	}

	@Override
	public PageResponse search(TenantSearchRequest searchRequest) {
		ValidationUtil.valid(searchRequest);
		var search = TenantConvertor.toSearch(searchRequest);
		var page = searchRequest.toPage();
		page.check(SORT_FIELD_SET);
		page = tenantService.search(search, page);
		return TenantConvertor.toPageResponse(page);
	}

	@Override
	public PageResponse searchAuditLog(AuditLogSearchRequest searchRequest) {
		ValidationUtil.valid(searchRequest);
		var search = AuditLogConvertor.toSearch(searchRequest, BusinessType.TENANT);
		var page = searchRequest.toPage();
		page = auditLogService.search(search, page);
		return AuditLogConvertor.toPageResponse(page);
	}
}
