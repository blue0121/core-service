package io.jutil.coreservice.admin.entity;

import io.jutil.coreservice.core.dict.Status;
import io.jutil.coreservice.core.entity.BaseAuditEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Jin Zheng
 * @since 2025-09-06
 */
@Getter
@Setter
@NoArgsConstructor
public class Tenant extends BaseAuditEntity {
	private String code;
	private String name;
	private Status status;
	private String remarks;
}
