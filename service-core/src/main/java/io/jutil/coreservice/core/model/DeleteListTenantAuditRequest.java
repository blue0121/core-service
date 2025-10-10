package io.jutil.coreservice.core.model;

import io.jutil.springeasy.core.validation.ValidationUtil;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

/**
 * @author Jin Zheng
 * @since 2025-09-11
 */
@Getter
@Setter
@NoArgsConstructor
public class DeleteListTenantAuditRequest {
	@Min(value = 1L, message = "租户ID不能为空")
	protected long tenantId;

	@NotEmpty(message = "ID列表不能为空")
	protected Collection<Long> idList;

	@Min(value = 1L, message = "操作人ID不能为空")
	protected long operatorId;

	public static DeleteListTenantAuditRequest create(long tenantId, Collection<Long> idList, long operatorId) {
		var request = new DeleteListTenantAuditRequest();
		request.tenantId = tenantId;
		request.idList = idList;
		request.operatorId = operatorId;
		return request;
	}

	public void check() {
		ValidationUtil.valid(this);
	}
}
