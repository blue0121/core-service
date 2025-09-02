package io.jutil.coreservice.auth.facade;

import io.jutil.coreservice.auth.model.UserLoginLogSearchRequest;
import io.jutil.coreservice.core.model.PageResponse;

/**
 * @author Jin Zheng
 * @since 2025-08-28
 */
public interface UserLoginLogFacade {

	PageResponse search(UserLoginLogSearchRequest request);

}
