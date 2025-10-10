package io.jutil.coreservice.admin.entity;

import io.jutil.coreservice.core.entity.DeleteTenantAuditEntity;

/**
 * @author Jin Zheng
 * @since 2025-10-09
 */
public class DeleteTenantAuditEntityTest {

	public static DeleteTenantAuditEntity create(long tenantId, long id, long operatorId) {
		var entity = new DeleteTenantAuditEntity();
		entity.setTenantId(tenantId);
		entity.setId(id);
		entity.setOperatorId(operatorId);
		return entity;
	}
}
