package io.jutil.coreservice.core.model;

import io.jutil.springeasy.core.validation.group.AddOperation;
import io.jutil.springeasy.core.validation.group.UpdateOperation;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Jin Zheng
 * @since 2025-09-07
 */
@Getter
@Setter
@NoArgsConstructor
public abstract class BaseTenantAuditRequest extends BaseAuditRequest {
	@Min(value = 1L, groups = {AddOperation.class, UpdateOperation.class}, message = "租户ID不能为空")
	private long tenantId;
}
