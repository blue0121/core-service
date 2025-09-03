package io.jutil.coreservice.auth.model;

import io.jutil.coreservice.core.dict.Realm;
import io.jutil.coreservice.core.model.SearchRequest;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * @author Jin Zheng
 * @since 2025-08-26
 */
@Getter
@Setter
public class UserLoginLogSearchRequest extends SearchRequest<UserLoginLogSearchRequest.SearchRequest> {

	public UserLoginLogSearchRequest() {
		this.filter = new UserLoginLogSearchRequest.SearchRequest();
	}

	@Getter
	@Setter
	@NoArgsConstructor
	public static class SearchRequest {
		@NotNull(message = "域不能为空")
		private Realm realm;
		private long userId;
		private LocalDate startDate;
		private LocalDate endDate;
	}
}
