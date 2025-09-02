package io.jutil.coreservice.auth.entity;

import io.jutil.coreservice.core.entity.Pageable;
import io.jutil.coreservice.core.model.PageResponse;
import io.jutil.springeasy.core.collection.Page;
import io.jutil.springeasy.core.collection.Sort;
import org.junit.jupiter.api.Assertions;

import java.util.Map;

/**
 * @author Jin Zheng
 * @since 2025-08-28
 */
public class PageTest {

	public static Page createPage() {
		return new Page();
	}

	public static Page createPage(int index, int size) {
		return new Page(index, size);
	}

	public static Pageable createPageable(int offset, int size, Sort sort,
	                                      Map<String, String> fieldMap) {
		var pageable = new Pageable();
		pageable.setOffset(offset);
		pageable.setSize(size);
		pageable.setSort(sort);
		if (fieldMap != null && !fieldMap.isEmpty()) {
			pageable.generateOrderBy(fieldMap);
		}
		return pageable;
	}

	public static void verify(Page page, int index, int size, int offset, int total,
	                          int totalPage) {
		Assertions.assertNotNull(page);
		Assertions.assertEquals(index, page.getPageIndex());
		Assertions.assertEquals(size, page.getPageSize());
		Assertions.assertEquals(offset, page.getOffset());
		Assertions.assertEquals(total, page.getTotal());
		Assertions.assertEquals(totalPage, page.getTotalPage());
	}

	public static void verify(PageResponse page, int index, int size, int total, int totalPage) {
		Assertions.assertNotNull(page);
		Assertions.assertEquals(index, page.getPageIndex());
		Assertions.assertEquals(size, page.getPageSize());
		Assertions.assertEquals(total, page.getTotal());
		Assertions.assertEquals(totalPage, page.getTotalPage());
	}
}
