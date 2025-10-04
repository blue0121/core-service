package io.jutil.coreservice.admin.entity;

import io.jutil.coreservice.admin.model.TenantRequest;
import io.jutil.coreservice.admin.model.TenantResponse;
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

	public static TenantRequest createRequest() {
		var request = new TenantRequest();
		request.setId(LongIdGenerator.nextId());
		request.setOperatorId(1L);
		request.setCode("code");
		request.setName("name");
		request.setStatus(Status.ACTIVE);
		request.setRemarks("remarks");
		return request;
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

	public static void verify(TenantResponse response, long operatorId, String code, String name,
	                          int status, String remarks) {
		Assertions.assertNotNull(response);
		Assertions.assertEquals(operatorId, response.getOperatorId());
		Assertions.assertEquals(code, response.getCode());
		Assertions.assertEquals(name, response.getName());
		Assertions.assertEquals(status, response.getStatus().getCode());
		Assertions.assertEquals(remarks, response.getRemarks());
	}
}
