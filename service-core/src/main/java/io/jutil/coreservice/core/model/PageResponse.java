package io.jutil.coreservice.core.model;

import io.jutil.springeasy.core.collection.Page;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author Jin Zheng
 * @since 2023-11-13
 */
@Getter
@Setter
@NoArgsConstructor
public class PageResponse {
	private int pageIndex;
	private int pageSize;
	private int totalPage;
	private int total;
	private List<?> contents;

	public static PageResponse from(Page page) {
		var response = new PageResponse();
		response.setPageIndex(page.getPageIndex());
		response.setPageSize(page.getPageSize());
		response.setTotalPage(page.getTotalPage());
		response.setTotal(page.getTotal());
		return response;
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> getContents() {
		return (List<T>) contents;
	}
}
