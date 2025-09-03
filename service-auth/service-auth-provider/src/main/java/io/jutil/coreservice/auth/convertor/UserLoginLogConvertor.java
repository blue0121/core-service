package io.jutil.coreservice.auth.convertor;

import io.jutil.coreservice.auth.entity.UserLoginLog;
import io.jutil.coreservice.auth.entity.UserLoginLogSearch;
import io.jutil.coreservice.auth.model.UserLoginLogResponse;
import io.jutil.coreservice.auth.model.UserLoginLogSearchRequest;
import io.jutil.coreservice.core.model.PageResponse;
import io.jutil.springeasy.core.collection.Page;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jin Zheng
 * @since 2025-08-28
 */
public class UserLoginLogConvertor {
	private UserLoginLogConvertor() {}

	public static UserLoginLogSearch toSearch(UserLoginLogSearchRequest request) {
		var filter = request.getFilter();
		var search = new UserLoginLogSearch();
		if (filter == null) {
			return search;
		}

		search.setRealm(filter.getRealm());
		search.setUserId(filter.getUserId());
		search.setStartDate(filter.getStartDate());
		search.setEndDate(filter.getEndDate());
		return search;
	}

	public static UserLoginLogResponse toResponse(UserLoginLog entity) {
		if (entity == null) {
			return null;
		}
		var response = new UserLoginLogResponse();
		response.setId(entity.getId());
		response.setRealm(entity.getRealm());
		response.setUserId(entity.getUserId());
		response.setIp(entity.getIp());
		response.setLoginDate(entity.getLoginDate());
		response.setLoginCount(entity.getLoginCount());
		response.setFirstLoginTime(entity.getFirstLoginTime());
		response.setLastLoginTime(entity.getLastLoginTime());

		response.setUserCode(entity.getUserCode());
		response.setUserName(entity.getUserName());
		response.setUserStatus(entity.getUserStatus());
		return response;
	}

	public static List<UserLoginLogResponse> toResponseList(List<UserLoginLog> entityList) {
		List<UserLoginLogResponse> responseList = new ArrayList<>();
		if (entityList == null || entityList.isEmpty()) {
			return responseList;
		}
		for (var entity : entityList) {
			responseList.add(toResponse(entity));
		}
		return responseList;
	}

	public static PageResponse toPageResponse(Page page) {
		if (page == null) {
			return null;
		}
		var response = PageResponse.from(page);
		response.setContents(toResponseList(page.getContents()));
		return response;
	}
}
