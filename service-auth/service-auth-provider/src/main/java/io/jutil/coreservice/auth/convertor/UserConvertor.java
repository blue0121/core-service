package io.jutil.coreservice.auth.convertor;

import io.jutil.coreservice.auth.entity.User;
import io.jutil.coreservice.auth.entity.UserSearch;
import io.jutil.coreservice.auth.model.UserRequest;
import io.jutil.coreservice.auth.model.UserResponse;
import io.jutil.coreservice.auth.model.UserSearchRequest;
import io.jutil.coreservice.core.model.PageResponse;
import io.jutil.springeasy.core.collection.Page;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jin Zheng
 * @since 2025-08-15
 */
public class UserConvertor {
	private UserConvertor() {
	}

	public static UserSearch toSearch(UserSearchRequest request) {
		var filter = request.getFilter();
		var search = new UserSearch();
		if (filter == null) {
			return search;
		}

		search.setRealm(filter.getRealm());
		search.setStatus(filter.getStatus());
		search.setCode(filter.getCode());
		search.setName(filter.getName());
		search.setIdList(filter.getIdList());
		return search;
	}

	public static User toEntity(UserRequest request) {
		var entity = new User();
		entity.setId(request.getId());
		entity.setRealm(request.getRealm());
		entity.setCode(request.getCode());
		entity.setName(request.getName());
		entity.setPassword(request.getPassword());
		entity.setOldPassword(request.getOldPassword());
		entity.setStatus(request.getStatus());
		entity.setRemarks(request.getRemarks());
		entity.setExtension(request.getExtension());
		return entity;
	}

	public static UserResponse toResponse(User entity) {
		if (entity == null) {
			return null;
		}
		var response = new UserResponse();
		response.setId(entity.getId());
		response.setRealm(entity.getRealm());
		response.setCode(entity.getCode());
		response.setName(entity.getName());
		response.setStatus(entity.getStatus());
		response.setRemarks(entity.getRemarks());
		response.setExtension(entity.getExtension());
		response.setIp(entity.getIp());
		response.setLoginTime(entity.getLoginTime());
		response.setCreateTime(entity.getCreateTime());
		response.setUpdateTime(entity.getUpdateTime());
		return response;
	}

	public static List<UserResponse> toListResponse(List<User> entityList) {
		List<UserResponse> responseList = new ArrayList<>();
		if (entityList == null || entityList.isEmpty()) {
			return responseList;
		}
		for (var entity : entityList) {
			responseList.add(toResponse(entity));
		}
		return responseList;
	}

	public static Map<Long, UserResponse> toMapResponse(Map<Long, User> entityMap) {
		Map<Long, UserResponse> responseMap = new HashMap<>();
		if (entityMap == null || entityMap.isEmpty()) {
			return responseMap;
		}
		for (var entry : entityMap.entrySet()) {
			responseMap.put(entry.getKey(), toResponse(entry.getValue()));
		}
		return responseMap;
	}

	public static PageResponse toPageResponse(Page page) {
		if (page == null) {
			return null;
		}
		var response = PageResponse.from(page);
		response.setContents(toListResponse(page.getContents()));
		return response;
	}
}
