package io.jutil.coreservice.core.model;

import io.jutil.springeasy.core.collection.Page;
import io.jutil.springeasy.core.collection.Sort;
import io.jutil.springeasy.core.validation.annotation.EnumIn;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import java.util.List;

/**
 * @author Jin Zheng
 * @since 2025-08-26
 */
@Getter
@Setter
@NoArgsConstructor
public abstract class SearchRequest<T> {
	private T filter;

	@Valid
	private PageRequest page;

	@Valid
	private List<SortRequest> sorts;


	public Page toPage() {
		Page page;
		if (this.page != null) {
			page = new Page(this.page.getIndex(), this.page.getSize());
		} else {
			page = new Page();
		}
		if (sorts != null && !sorts.isEmpty()) {
			var sort = new Sort();
			for (var s : sorts) {
				sort.add(s.field, s.direction);
			}
			page.setSort(sort);
		}
		return page;
	}

	@Getter
	@Setter
	@NoArgsConstructor
	public static class PageRequest {
		@Min(value = 1, message = "分页索引不能小于1")
		private int index = 1;

		@Range(min = 1, max = 100, message = "分页记录数值[1-100]")
		private int size = 10;
	}

	@Getter
	@Setter
	@NoArgsConstructor
	public static class SortRequest {
		@NotEmpty(message = "分页字段不能为空")
		private String field;

		@EnumIn(value = Sort.Direction.class, message = "无效的排序值")
		private String direction = "DESC";
	}
}
