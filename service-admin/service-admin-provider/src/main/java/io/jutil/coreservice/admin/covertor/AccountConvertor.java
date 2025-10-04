package io.jutil.coreservice.admin.covertor;

import io.jutil.coreservice.admin.entity.Account;
import io.jutil.coreservice.admin.entity.AccountSearch;
import io.jutil.coreservice.admin.model.AccountRequest;
import io.jutil.coreservice.admin.model.AccountResponse;
import io.jutil.coreservice.admin.model.AccountSearchRequest;
import io.jutil.coreservice.auth.model.UserRequest;
import io.jutil.coreservice.core.convertor.BaseRequestConvertor;
import io.jutil.coreservice.core.convertor.BaseResponseConvertor;
import io.jutil.coreservice.core.dict.Realm;
import io.jutil.coreservice.core.model.PageResponse;
import io.jutil.springeasy.core.collection.Page;

import java.util.List;
import java.util.Map;

/**
 * @author Jin Zheng
 * @since 2025-09-17
 */
public class AccountConvertor {
	private AccountConvertor() {}

	public static AccountSearch toSearch(AccountSearchRequest request) {
		var filter = request.getFilter();
		var search = new AccountSearch();
		search.setTenantId(filter.getTenantId());
		search.setOperatorId(filter.getOperatorId());
		search.setStatus(filter.getStatus());
		search.setCode(filter.getCode());
		search.setName(filter.getName());
		search.setIdList(filter.getIdList());
		return search;
	}

	public static Account toEntity(AccountRequest request) {
		var entity = BaseRequestConvertor.toTenantAuditEntity(request, Account::new);
		entity.setCode(request.getCode());
		entity.setName(request.getName());
		entity.setStatus(request.getStatus());
		entity.setImageUrl(request.getImageUrl());
		entity.setRemarks(request.getRemarks());
		entity.setTenantIdList(request.getTenantIdList());
		return entity;
	}

	public static UserRequest toUserRequest(AccountRequest request) {
		var userRequest = new UserRequest();
		userRequest.setId(request.getId());
		userRequest.setRealm(Realm.ADMIN);
		userRequest.setCode(request.getCode());
		userRequest.setName(request.getName());
		userRequest.setPassword(request.getPassword());
		userRequest.setOldPassword(request.getOldPassword());
		userRequest.setStatus(request.getStatus());
		userRequest.setRemarks(request.getRemarks());
		return userRequest;
	}

	public static AccountResponse toResponse(Account entity) {
		if (entity == null) {
			return null;
		}
		var response = BaseResponseConvertor.toTenantAuditResponse(entity, AccountResponse::new);
		response.setCode(entity.getCode());
		response.setName(entity.getName());
		response.setStatus(entity.getStatus());
		response.setImageUrl(entity.getImageUrl());
		response.setRemarks(entity.getRemarks());
		return response;
	}

	public static List<AccountResponse> toListResponse(List<Account> entityList) {
		return BaseResponseConvertor.toListResponse(entityList, AccountConvertor::toResponse);
	}

	public static Map<Long, AccountResponse> toMapResponse(Map<Long, Account> entityMap) {
		return BaseResponseConvertor.toMapResponse(entityMap, AccountConvertor::toResponse);
	}

	public static PageResponse toPageResponse(Page page) {
		return BaseResponseConvertor.toPageResponse(page, AccountConvertor::toListResponse);
	}
}
