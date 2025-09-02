package io.jutil.coreservice.auth.entity;

import io.jutil.coreservice.core.model.SearchRequest;
import io.jutil.springeasy.core.collection.Sort;

import java.util.List;

/**
 * @author Jin Zheng
 * @since 2025-09-02
 */
public class SearchRequestTest {

	public static void setPage(SearchRequest<?> request, String sortField, Sort.Direction direction) {
		var page = new SearchRequest.PageRequest();
		page.setIndex(1);
		page.setSize(10);
		request.setPage(page);

		var sort = new SearchRequest.SortRequest();
		sort.setField(sortField);
		request.setSorts(List.of(sort));
	}

}
