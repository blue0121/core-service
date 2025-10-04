package io.jutil.coreservice.admin.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Jin Zheng
 * @since 2025-10-02
 */
@Getter
@Setter
@NoArgsConstructor
public class AccountTenant {
	private long accountId;
	private long tenantId;
}
