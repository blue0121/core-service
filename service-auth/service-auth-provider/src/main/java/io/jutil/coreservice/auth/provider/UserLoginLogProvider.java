package io.jutil.coreservice.auth.provider;

import io.jutil.coreservice.auth.convertor.UserLoginLogConvertor;
import io.jutil.coreservice.auth.facade.UserLoginLogFacade;
import io.jutil.coreservice.auth.model.UserLoginLogSearchRequest;
import io.jutil.coreservice.auth.service.UserLoginLogService;
import io.jutil.coreservice.core.model.PageResponse;
import io.jutil.springeasy.core.validation.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Jin Zheng
 * @since 2025-08-28
 */
@Component
public class UserLoginLogProvider implements UserLoginLogFacade {
	public static final List<String> SORT_FIELD_LIST = List.of("id", "loginDate");

	@Autowired
	UserLoginLogService userLoginLogService;

	@Override
	public PageResponse search(UserLoginLogSearchRequest request) {
		ValidationUtil.valid(request);
		var search = UserLoginLogConvertor.toSearch(request);
		var page = request.toPage();
		page.check(SORT_FIELD_LIST);
		page = userLoginLogService.search(search, page);
		return UserLoginLogConvertor.toPageResponse(page);
	}
}
