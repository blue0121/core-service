package io.jutil.coreservice.core.repository;

import io.jutil.coreservice.core.dao.PageableMapper;
import io.jutil.coreservice.core.entity.Pageable;
import io.jutil.springeasy.core.collection.Page;
import io.jutil.springeasy.core.collection.Sort;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @author Jin Zheng
 * @since 2025-09-11
 */
@Slf4j
public class PageableRepository {

	public static <S, E> Page search(PageableMapper<S, E> mapper, Map<String, String> sortFieldMap,
	                                 S search, Page page) {
		var count = mapper.countPage(search);
		if (count == 0) {
			return page;
		}
		page.setTotal(count);
		page.setSortIfAbsent(() -> new Sort("id"));
		var pageable = Pageable.from(page);
		pageable.generateOrderBy(sortFieldMap);

		var list = mapper.listPage(search, pageable);
		page.setContents(list);
		return page;
	}
}
