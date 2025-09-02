package io.jutil.coreservice.auth.model;

import io.jutil.coreservice.core.model.SearchRequest;
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
@NoArgsConstructor
public class UserLoginLogSearchRequest extends SearchRequest<UserLoginLogSearchRequest.SearchRequest> {

	@Getter
	@Setter
	@NoArgsConstructor
	public static class SearchRequest {
		private long userId;
		private LocalDate startDate;
		private LocalDate endDate;
	}
}
