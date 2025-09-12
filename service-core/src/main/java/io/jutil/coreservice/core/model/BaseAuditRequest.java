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
public abstract class BaseAuditRequest extends BaseRequest {
	@Min(value = 1L, groups = {AddOperation.class, UpdateOperation.class}, message = "操作人ID不能为空")
	protected long operatorId;
}
