package io.jutil.coreservice.admin.entity;

import org.junit.jupiter.api.Assertions;

/**
 * @author Jin Zheng
 * @since 2025-10-02
 */
public class AccountTenantTest {

	public static void verify(AccountTenant entity, long accountId, long tenantId) {
		Assertions.assertNotNull(entity);
		Assertions.assertEquals(accountId, entity.getAccountId());
		Assertions.assertEquals(tenantId, entity.getTenantId());
	}

}
