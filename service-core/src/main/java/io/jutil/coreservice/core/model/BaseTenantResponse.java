package io.jutil.coreservice.core.model;

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
public abstract class BaseTenantResponse extends BaseResponse {
	protected long tenantId;
}
