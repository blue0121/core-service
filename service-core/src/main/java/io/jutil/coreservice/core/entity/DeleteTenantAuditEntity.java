package io.jutil.coreservice.core.entity;

import io.jutil.coreservice.core.model.DeleteTenantAuditRequest;
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
public class DeleteTenantAuditEntity {
	protected long id;
	protected long tenantId;
	protected long operatorId;

	public static DeleteTenantAuditEntity from(DeleteTenantAuditRequest request) {
		var entity = new DeleteTenantAuditEntity();
		entity.id = request.getId();
		entity.tenantId = request.getTenantId();
		entity.operatorId = request.getOperatorId();
		return entity;
	}
}
