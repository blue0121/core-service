package io.jutil.coreservice.core.entity;

import io.jutil.springeasy.core.collection.Page;
import io.jutil.springeasy.core.collection.Sort;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

/**
 * @author Jin Zheng
 * @since 2025-08-28
 */
@Getter
@Setter
@NoArgsConstructor
public class Pageable {
	private int offset;
	private int size;
	private Sort sort;
	private String orderBy = "";

	public static Pageable from(Page page) {
		var pageable = new Pageable();
		pageable.setOffset(page.getOffset());
		pageable.setSize(page.getPageSize());
		pageable.setSort(page.getSort());
		return pageable;
	}

	public void generateOrderBy(Map<String, String> fieldMap) {
		if (sort == null) {
			return;
		}
		this.orderBy = sort.toOrderByString(fieldMap);
	}
}
