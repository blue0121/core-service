package io.jutil.coreservice.core.entity;

import io.jutil.springeasy.mybatis.entity.LongIdEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author Jin Zheng
 * @since 2025-08-15
 */
@Getter
@Setter
@NoArgsConstructor
public abstract class BaseEntity extends LongIdEntity {
	protected LocalDateTime createTime;
	protected LocalDateTime updateTime;
}
