package io.jutil.coreservice.core.entity;

import com.alibaba.fastjson2.JSONObject;
import io.jutil.coreservice.core.dict.Operation;
import io.jutil.coreservice.core.facade.AuditLogFacade;
import io.jutil.coreservice.core.model.AuditLogResponse;
import io.jutil.springeasy.mybatis.id.LongIdGenerator;
import org.junit.jupiter.api.Assertions;

/**
 * @author Jin Zheng
 * @since 2025-09-11
 */
public class AuditLogTest {

	public static AuditLog create() {
		var entity = new AuditLog();
		entity.setId(LongIdGenerator.nextId());
		entity.setTenantId(1L);
		entity.setBusinessId(1L);
		entity.setOperatorId(2L);
		entity.setOperation(Operation.DELETE);
		entity.setContent(JSONObject.of("id", 1L));
		entity.setBusiness(AuditLogFacade.Business.TENANT.getLabel());
		return entity;
	}

	public static void verify(AuditLog entity, long tenantId, long businessId, long operatorId,
	                          int operation) {
		Assertions.assertNotNull(entity);
		Assertions.assertEquals(tenantId, entity.getTenantId());
		Assertions.assertEquals(businessId, entity.getBusinessId());
		Assertions.assertEquals(operatorId, entity.getOperatorId());
		Assertions.assertEquals(operation, entity.getOperation().getCode());
	}

	public static void verify(AuditLogResponse response, long tenantId, long businessId, long operatorId,
	                          int operation) {
		Assertions.assertNotNull(response);
		Assertions.assertEquals(tenantId, response.getTenantId());
		Assertions.assertEquals(businessId, response.getBusinessId());
		Assertions.assertEquals(operatorId, response.getOperatorId());
		Assertions.assertEquals(operation, response.getOperation().getCode());
	}
}
