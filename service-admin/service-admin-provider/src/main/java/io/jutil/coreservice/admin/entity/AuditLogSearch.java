package io.jutil.coreservice.admin.entity;

import io.jutil.coreservice.core.dict.Operation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * @author Jin Zheng
 * @since 2025-09-10
 */
@Getter
@Setter
@NoArgsConstructor
public class AuditLogSearch {
	private long tenantId;
	private long businessId;
	private long operatorId;
	private Operation operation;
	private String business;
	private LocalDate startDate;
	private LocalDate endDate;
}
