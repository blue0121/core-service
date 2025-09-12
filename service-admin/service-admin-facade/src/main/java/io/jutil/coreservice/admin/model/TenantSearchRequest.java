package io.jutil.coreservice.admin.model;

import io.jutil.coreservice.core.dict.Status;
import io.jutil.coreservice.core.model.SearchRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

/**
 * @author Jin Zheng
 * @since 2025-08-29
 */
@Getter
@Setter
public class TenantSearchRequest extends SearchRequest<TenantSearchRequest.SearchRequest> {

	public TenantSearchRequest() {
		this.filter = new TenantSearchRequest.SearchRequest();
	}

	@Getter
	@Setter
	@NoArgsConstructor
	public static class SearchRequest {
		private Status status;
		private String code;
		private String name;
		private Collection<Long> idList;
	}
}
