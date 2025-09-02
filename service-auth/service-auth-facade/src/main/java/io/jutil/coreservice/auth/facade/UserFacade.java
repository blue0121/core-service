package io.jutil.coreservice.auth.facade;

import io.jutil.coreservice.auth.model.UserRequest;
import io.jutil.coreservice.auth.model.UserResponse;
import io.jutil.coreservice.auth.model.UserSearchRequest;
import io.jutil.coreservice.core.facade.BaseFacade;
import io.jutil.coreservice.core.model.PageResponse;

/**
 * @author Jin Zheng
 * @since 2025-08-15
 */
public interface UserFacade extends BaseFacade<UserRequest, UserResponse> {

	PageResponse search(UserSearchRequest request);

}
