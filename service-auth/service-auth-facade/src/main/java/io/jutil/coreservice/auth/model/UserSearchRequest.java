package io.jutil.coreservice.auth.model;

import io.jutil.coreservice.core.dict.Realm;
import io.jutil.coreservice.core.dict.Status;
import io.jutil.coreservice.core.model.SearchRequest;
import jakarta.validation.constraints.NotNull;
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
public class UserSearchRequest extends SearchRequest<UserSearchRequest.SearchRequest> {

	public UserSearchRequest() {
		this.filter = new UserSearchRequest.SearchRequest();
	}

	@Getter
	@Setter
	@NoArgsConstructor
	public static class SearchRequest {
		@NotNull(message = "域不能为空")
		private Realm realm;
		private Status status;
		private String code;
		private String name;
		private Collection<Long> idList;
	}
}
