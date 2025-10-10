package io.jutil.coreservice.admin.entity;

import io.jutil.coreservice.core.dict.Status;
import io.jutil.coreservice.core.entity.BaseTenantAuditEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Jin Zheng
 * @since 2025-10-04
 */
@Getter
@Setter
@NoArgsConstructor
public class Role extends BaseTenantAuditEntity {
	private String name;
	private Status status;
	private String remarks;


	public static class Search {
		public long tenantId;
		public String name;
		public Status status;
	}
}
