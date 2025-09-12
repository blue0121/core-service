package io.jutil.coreservice.core.entity;

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
public abstract class BaseTenantAuditEntity extends BaseAuditEntity {
	protected long tenantId;

}
