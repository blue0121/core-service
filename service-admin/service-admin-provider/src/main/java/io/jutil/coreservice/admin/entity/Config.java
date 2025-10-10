package io.jutil.coreservice.admin.entity;

import io.jutil.coreservice.core.dict.Status;
import io.jutil.coreservice.core.dict.YesOrNo;
import io.jutil.coreservice.core.entity.BaseTenantAuditEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

/**
 * @author Jin Zheng
 * @since 2025-10-04
 */
@Getter
@Setter
@NoArgsConstructor
public class Config extends BaseTenantAuditEntity {
	private String code;
	private String name;
	private String value;
	private Status status;
	private YesOrNo multiValue;
	private String remarks;


	public static class Search {
		public long tenantId;
		public long operatorId;
		public String code;
		public String name;
		public Status status;
		public YesOrNo multiValue;
		public Collection<Long> idList;
	}
}
