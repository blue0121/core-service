package io.jutil.coreservice.admin.entity;

import io.jutil.coreservice.core.dict.Status;
import io.jutil.coreservice.core.entity.BaseTenantAuditEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

/**
 * @author Jin Zheng
 * @since 2025-09-06
 */
@Getter
@Setter
@NoArgsConstructor
public class Account extends BaseTenantAuditEntity {
	private String code;
	private String name;
	private Status status;
	private String imageUrl;
	private String remarks;

	private Collection<Long> tenantIdList;

}
