package io.jutil.coreservice.auth.model;

import io.jutil.coreservice.core.dict.Realm;
import io.jutil.coreservice.core.dict.Status;
import io.jutil.springeasy.core.validation.group.AddOperation;
import io.jutil.springeasy.core.validation.group.UpdateOperation;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Jin Zheng
 * @since 2025-08-15
 */
@Getter
@Setter
@NoArgsConstructor
public class UserRequest {
	@Min(value = 1L, groups = {UpdateOperation.class}, message = "ID不能为空")
	private long id;

	@NotNull(groups = {AddOperation.class, UpdateOperation.class}, message = "域不能为空")
	private Realm realm;

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

	@Size(max = 500, groups = {AddOperation.class, UpdateOperation.class}, message = "备注最大长度500")
	private String remarks;

	@Size(max = 500, groups = {AddOperation.class, UpdateOperation.class}, message = "扩展最大长度500")
	private byte[] extension;

}
