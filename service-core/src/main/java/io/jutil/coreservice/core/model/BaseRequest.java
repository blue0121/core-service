package io.jutil.coreservice.core.model;

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
public abstract class BaseRequest {
	@Min(value = 1L, groups = {UpdateOperation.class}, message = "ID不能为空")
	protected long id;

}
