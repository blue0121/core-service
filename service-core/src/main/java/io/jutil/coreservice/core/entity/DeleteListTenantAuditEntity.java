package io.jutil.coreservice.core.entity;

import io.jutil.coreservice.core.model.DeleteListTenantAuditRequest;
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
public class DeleteListTenantAuditEntity {
	protected Collection<Long> idList;
	protected long tenantId;
	protected long operatorId;

	public static DeleteListTenantAuditEntity from(DeleteListTenantAuditRequest request) {
		var entity = new DeleteListTenantAuditEntity();
		entity.idList = request.getIdList();
		entity.tenantId = request.getTenantId();
		entity.operatorId = request.getOperatorId();
		return entity;
	}
}
