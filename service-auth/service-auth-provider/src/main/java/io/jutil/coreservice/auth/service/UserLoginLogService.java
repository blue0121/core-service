package io.jutil.coreservice.auth.service;

import io.jutil.coreservice.auth.entity.UserLoginLogSearch;
import io.jutil.coreservice.auth.repository.UserLoginLogRepository;
import io.jutil.springeasy.core.collection.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Jin Zheng
 * @since 2025-08-28
 */
@Component
public class UserLoginLogService {
	@Autowired
	UserLoginLogRepository userLoginLogRepository;

	public Page search(UserLoginLogSearch search, Page page) {
		return userLoginLogRepository.search(search, page);
	}

}
