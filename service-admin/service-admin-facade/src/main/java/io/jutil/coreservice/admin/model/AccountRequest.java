package io.jutil.coreservice.admin.model;

import io.jutil.coreservice.core.dict.Status;
import io.jutil.coreservice.core.model.BaseTenantAuditRequest;
import io.jutil.springeasy.core.validation.group.AddOperation;
import io.jutil.springeasy.core.validation.group.UpdateOperation;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

/**
 * @author Jin Zheng
 * @since 2025-09-07
 */
@Getter
@Setter
@NoArgsConstructor
public class AccountRequest extends BaseTenantAuditRequest {
	@NotEmpty(groups = {AddOperation.class}, message = "标识不能为空")
	@Size(max = 50, groups = {AddOperation.class, UpdateOperation.class}, message = "标识最大长度50")
	private String code;

	@NotEmpty(groups = {AddOperation.class}, message = "名称不能为空")
	@Size(max = 50, groups = {AddOperation.class, UpdateOperation.class}, message = "名称最大长度50")
	private String name;

	@NotEmpty(groups = {AddOperation.class}, message = "密码不能为空")
	private String password;
	private String oldPassword;

	@NotNull(groups = {AddOperation.class}, message = "状态不能为空")
	private Status status;

	@Size(max = 200, groups = {AddOperation.class, UpdateOperation.class}, message = "图片URL最大长度200")
	private String imageUrl;

	@Size(max = 500, groups = {AddOperation.class, UpdateOperation.class}, message = "备注最大长度500")
	private String remarks;

	@NotEmpty(groups = {AddOperation.class}, message = "租户ID列表不能为空")
	private Collection<Long> tenantIdList;
}
