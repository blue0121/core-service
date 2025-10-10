package io.jutil.coreservice.admin.entity;

import io.jutil.coreservice.core.entity.DeleteListTenantAuditEntity;

import java.util.Collection;

/**
 * @author Jin Zheng
 * @since 2025-10-09
 */
public class DeleteListTenantAuditEntityTest {

	public static DeleteListTenantAuditEntity create(long tenantId, Collection<Long> idList, long operatorId) {
		var entity = new DeleteListTenantAuditEntity();
		entity.setTenantId(tenantId);
		entity.setIdList(idList);
		entity.setOperatorId(operatorId);
		return entity;
	}
}
