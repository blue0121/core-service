package io.jutil.coreservice.admin.model;

import io.jutil.coreservice.core.dict.Status;
import io.jutil.coreservice.core.dict.YesOrNo;
import io.jutil.coreservice.core.model.BaseTenantAuditResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Jin Zheng
 * @since 2025-10-09
 */
@Getter
@Setter
@NoArgsConstructor
public class ConfigResponse extends BaseTenantAuditResponse {
	private String code;
	private String name;
	private String value;
	private Status status;
	private YesOrNo multiValue;
	private String remarks;
}
