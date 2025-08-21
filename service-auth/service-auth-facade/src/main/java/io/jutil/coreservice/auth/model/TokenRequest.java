package io.jutil.coreservice.auth.model;

import io.jutil.coreservice.auth.dict.Realm;
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
public class TokenRequest {
	@NotNull(message = "域不能为空")
	private Realm realm;

	@NotEmpty(message = "标识不能为空")
	private String code;

	@NotEmpty(message = "密码不能为空")
	private String password;

	@NotEmpty(message = "IP不能为空")
	@Size(max = 50, message = "IP最大长度50")
	private String ip;
}
