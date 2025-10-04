package io.jutil.coreservice.admin.provider;

import io.jutil.coreservice.admin.covertor.AccountConvertor;
import io.jutil.coreservice.admin.facade.AccountFacade;
import io.jutil.coreservice.admin.model.AccountRequest;
import io.jutil.coreservice.admin.model.AccountResponse;
import io.jutil.coreservice.admin.model.AccountSearchRequest;
import io.jutil.coreservice.admin.service.AccountService;
import io.jutil.coreservice.core.convertor.BaseRequestConvertor;
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
 * @since 2025-09-17
 */
@Component
public class AccountProvider implements AccountFacade {
	@Autowired
	AccountService accountService;

	@Override
	public AccountResponse getOne(long tenantId, long id) {
		AssertUtil.validId(tenantId, "租户ID");
		AssertUtil.validId(id, "ID");
		var entity = accountService.getOne(tenantId, id);
		return AccountConvertor.toResponse(entity);
	}

	@Override
	public Map<Long, AccountResponse> getList(long tenantId, Collection<Long> idList) {
		AssertUtil.validId(tenantId, "租户ID");
		AssertUtil.validIdList(idList, "ID列表");
		var map = accountService.getList(tenantId, idList);
		return AccountConvertor.toMapResponse(map);
	}

	@Override
	public AccountResponse addOne(AccountRequest request) {
		ValidationUtil.valid(request, AddOperation.class);
		var entity = AccountConvertor.toEntity(request);
		entity = accountService.addOne(entity);
		if (entity == null) {
			throw BaseErrorCode.EXISTS.newException(request.getCode());
		}

		return AccountConvertor.toResponse(entity);
	}

	@Override
	public AccountResponse updateOne(AccountRequest request) {
		ValidationUtil.valid(request, UpdateOperation.class);
		var entity = AccountConvertor.toEntity(request);
		entity = accountService.updateOne(entity);
		return AccountConvertor.toResponse(entity);
	}

	@Override
	public int deleteOne(DeleteTenantAuditRequest request) {
		ValidationUtil.valid(request);
		var entity = BaseRequestConvertor.toDeleteTenantAuditEntity(request);
		return accountService.deleteOne(entity);
	}

	@Override
	public int deleteList(DeleteListTenantAuditRequest request) {
		ValidationUtil.valid(request);
		var entity = BaseRequestConvertor.toDeleteListTenantAuditEntity(request);
		return accountService.deleteList(entity);
	}

	@Override
	public PageResponse search(AccountSearchRequest searchRequest) {
		ValidationUtil.valid(searchRequest);
		return this.listAll(searchRequest);
	}

	@Override
	public PageResponse listAll(AccountSearchRequest searchRequest) {
		var search = AccountConvertor.toSearch(searchRequest);
		var page = searchRequest.toPage();
		page = accountService.search(search, page);
		return AccountConvertor.toPageResponse(page);
	}

	@Override
	public PageResponse searchAuditLog(AuditLogSearchRequest searchRequest) {
		ValidationUtil.valid(searchRequest);
		return null;
	}
}
