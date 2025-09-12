package io.jutil.coreservice.core.model;

import io.jutil.coreservice.core.dict.Operation;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * @author Jin Zheng
 * @since 2025-08-29
 */
@Getter
@Setter
public class AuditLogSearchRequest extends SearchRequest<AuditLogSearchRequest.SearchRequest> {

	public AuditLogSearchRequest() {
		this.filter = new SearchRequest();
	}

	@Getter
	@Setter
	@NoArgsConstructor
	public static class SearchRequest {
		@Min(value = 1L, message = "租户ID不能为空")
		protected long tenantId;
		@Min(value = 1L, message = "业务ID不能为空")
		protected long businessId;
		protected long operatorId;
		protected Operation operation;
		protected LocalDate startDate;
		protected LocalDate endDate;
	}
}
