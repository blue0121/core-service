package io.jutil.coreservice.admin.entity;

import io.jutil.coreservice.core.dict.Status;
import io.jutil.springeasy.mybatis.id.LongIdGenerator;
import org.junit.jupiter.api.Assertions;

/**
 * @author Jin Zheng
 * @since 2025-09-11
 */
public class TenantTest {

	public static Tenant create() {
		var tenant = new Tenant();
		tenant.setId(LongIdGenerator.nextId());
		tenant.setOperatorId(1L);
		tenant.setCode("code");
		tenant.setName("name");
		tenant.setStatus(Status.ACTIVE);
		tenant.setRemarks("remarks");
		return tenant;
	}

	public static void verify(Tenant tenant, long operatorId, String code, String name,
	                          int status, String remarks) {
		Assertions.assertNotNull(tenant);
		Assertions.assertEquals(operatorId, tenant.getOperatorId());
		Assertions.assertEquals(code, tenant.getCode());
		Assertions.assertEquals(name, tenant.getName());
		Assertions.assertEquals(status, tenant.getStatus().getCode());
		Assertions.assertEquals(remarks, tenant.getRemarks());
	}
}
