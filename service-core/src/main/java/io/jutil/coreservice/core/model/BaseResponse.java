package io.jutil.coreservice.core.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author Jin Zheng
 * @since 2025-09-07
 */
@Getter
@Setter
@NoArgsConstructor
public abstract class BaseResponse {
	protected long id;
	protected LocalDateTime createTime;
	protected LocalDateTime updateTime;
}
