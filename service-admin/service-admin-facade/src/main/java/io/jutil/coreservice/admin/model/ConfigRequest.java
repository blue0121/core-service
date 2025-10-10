package io.jutil.coreservice.admin.model;

import io.jutil.coreservice.core.dict.Status;
import io.jutil.coreservice.core.dict.YesOrNo;
import io.jutil.coreservice.core.model.BaseTenantAuditRequest;
import io.jutil.springeasy.core.validation.group.AddOperation;
import io.jutil.springeasy.core.validation.group.UpdateOperation;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class ConfigRequest extends BaseTenantAuditRequest {
	@NotEmpty(groups = {AddOperation.class}, message = "标识不能为空")
	@Size(max = 100, groups = {AddOperation.class, UpdateOperation.class}, message = "标识最大长度100")
	private String code;

	@NotEmpty(groups = {AddOperation.class}, message = "名称不能为空")
	@Size(max = 50, groups = {AddOperation.class, UpdateOperation.class}, message = "名称最大长度50")
	private String name;

	@NotEmpty(groups = {AddOperation.class}, message = "名称不能为空")
	@Size(max = 200, groups = {AddOperation.class, UpdateOperation.class}, message = "名称最大长度200")
	private String value;

	@NotNull(groups = {AddOperation.class}, message = "状态不能为空")
	private Status status;

	@NotNull(groups = {AddOperation.class}, message = "状态不能为空")
	private YesOrNo multiValue;

	@Size(max = 500, groups = {AddOperation.class, UpdateOperation.class}, message = "备注最大长度500")
	private String remarks;
}
