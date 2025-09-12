package io.jutil.coreservice.core.model;

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
public abstract class BaseAuditResponse extends BaseResponse {
	protected long operatorId;
	protected String operatorCode;
	protected String operatorName;
}
