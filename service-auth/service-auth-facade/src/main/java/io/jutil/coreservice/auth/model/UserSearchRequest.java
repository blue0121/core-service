package io.jutil.coreservice.auth.model;

import io.jutil.coreservice.auth.dict.Realm;
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
@NoArgsConstructor
public class UserSearchRequest extends SearchRequest<UserSearchRequest.SearchRequest> {

	@Getter
	@Setter
	@NoArgsConstructor
	public static class SearchRequest {
		private Realm realm;
		private Status status;
		private String code;
		private String name;
		private Collection<Long> idList;
	}
}
