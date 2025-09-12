package io.jutil.coreservice.admin.model;

import io.jutil.coreservice.core.dict.Status;
import io.jutil.coreservice.core.model.SearchRequest;
import jakarta.validation.constraints.Min;
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
public class AccountSearchRequest extends SearchRequest<AccountSearchRequest.SearchRequest> {

	public AccountSearchRequest() {
		this.filter = new AccountSearchRequest.SearchRequest();
	}

	@Getter
	@Setter
	@NoArgsConstructor
	public static class SearchRequest {
		@Min(value = 1L, message = "租户ID不能为空")
		private long tenantId;
		private long operatorId;
		private Status status;
		private String code;
		private String name;
		private Collection<Long> idList;
	}
}
