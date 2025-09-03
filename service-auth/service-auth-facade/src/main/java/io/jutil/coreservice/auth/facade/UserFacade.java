package io.jutil.coreservice.auth.facade;

import io.jutil.coreservice.auth.model.UserRequest;
import io.jutil.coreservice.auth.model.UserResponse;
import io.jutil.coreservice.auth.model.UserSearchRequest;
import io.jutil.coreservice.core.dict.Realm;
import io.jutil.coreservice.core.model.PageResponse;

import java.util.Collection;
import java.util.Map;

/**
 * @author Jin Zheng
 * @since 2025-08-15
 */
public interface UserFacade {

	UserResponse getOne(Realm realm, long id);

	Map<Long, UserResponse> getList(Realm realm, Collection<Long> idList);

	UserResponse addOne(UserRequest request);

	UserResponse updateOne(UserRequest request);

	int deleteOne(Realm realm, long id);

	int deleteList(Realm realm, Collection<Long> idList);

	PageResponse search(UserSearchRequest request);

}
