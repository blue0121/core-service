package io.jutil.coreservice.admin.entity;

import io.jutil.coreservice.core.dict.Status;
import io.jutil.coreservice.core.entity.BaseAuditEntity;
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
public class Menu extends BaseAuditEntity {
	private long parentId;
	private String name;
	private String url;
	private Status status;
	private String remarks;
	private int sort;
}
