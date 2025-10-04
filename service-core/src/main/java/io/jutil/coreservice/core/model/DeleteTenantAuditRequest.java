package io.jutil.coreservice.core.model;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Jin Zheng
 * @since 2025-09-11
 */
@Getter
@Setter
@NoArgsConstructor
public class DeleteTenantAuditRequest {
	@Min(value = 1L, message = "租户ID不能为空")
	protected long tenantId;

	@Min(value = 1L, message = "ID不能为空")
	protected long id;

	@Min(value = 1L, message = "操作人ID不能为空")
	protected long operatorId;

	public static DeleteTenantAuditRequest create(long tenantId, long id, long operatorId) {
		var request = new DeleteTenantAuditRequest();
		request.tenantId = tenantId;
		request.id = id;
		request.operatorId = operatorId;
		return request;
	}
}
