package io.jutil.coreservice.auth.repository;

import io.jutil.coreservice.auth.dao.UserLoginLogMapper;
import io.jutil.coreservice.auth.entity.UserLoginLogSearch;
import io.jutil.coreservice.core.entity.Pageable;
import io.jutil.springeasy.core.collection.Page;
import io.jutil.springeasy.core.collection.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * @author Jin Zheng
 * @since 2025-08-28
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserLoginLogRepository {
	public static final Map<String, String> SORT_FILED_MAP =
			Map.of("id", "e.id",
					"loginDate", "e.login_date");

	@Autowired
	UserLoginLogMapper userLoginLogMapper;

	public Page search(UserLoginLogSearch search, Page page) {
		var count = userLoginLogMapper.countPage(search);
		if (count == 0) {
			return page;
		}
		page.setTotal(count);
		page.setSortIfAbsent(() -> new Sort("loginDate"));
		var pageable = Pageable.from(page);
		pageable.generateOrderBy(SORT_FILED_MAP);

		var list = userLoginLogMapper.listPage(search, pageable);
		page.setContents(list);
		return page;
	}
}
