package io.jutil.coreservice.admin.entity;

import io.jutil.coreservice.admin.model.ConfigItemRequest;
import io.jutil.coreservice.admin.model.ConfigItemResponse;
import io.jutil.coreservice.core.dict.Status;
import io.jutil.springeasy.mybatis.id.LongIdGenerator;
import org.junit.jupiter.api.Assertions;

/**
 * @author Jin Zheng
 * @since 2025-10-08
 */
public class ConfigItemTest {

	public static ConfigItem create(long configId) {
		var entity = new ConfigItem();
		entity.setId(LongIdGenerator.nextId());
		entity.setTenantId(1L);
		entity.setConfigId(configId);
		entity.setOperatorId(2L);
		entity.setName("name");
		entity.setValue("value");
		entity.setStatus(Status.ACTIVE);
		entity.setRemarks("remarks");
		return entity;
	}

	public static ConfigItemRequest createRequest(long configId) {
		var request = new ConfigItemRequest();
		request.setId(1L);
		request.setTenantId(1L);
		request.setConfigId(configId);
		request.setOperatorId(2L);
		request.setName("name");
		request.setValue("value");
		request.setStatus(Status.ACTIVE);
		request.setRemarks("remarks");
		return request;
	}

	public static void verify(ConfigItem entity, long tenantId, long configId, long operatorId, String name,
	                          String value, int status, String remarks) {
		Assertions.assertNotNull(entity);
		Assertions.assertEquals(tenantId, entity.getTenantId());
		Assertions.assertEquals(operatorId, entity.getOperatorId());
		Assertions.assertEquals(configId, entity.getConfigId());
		Assertions.assertEquals(name, entity.getName());
		Assertions.assertEquals(value, entity.getValue());
		Assertions.assertEquals(status, entity.getStatus().getCode());
		Assertions.assertEquals(remarks, entity.getRemarks());
	}

	public static void verify(ConfigItemResponse response, long tenantId, long configId, long operatorId,
	                          String name, String value, int status, String remarks) {
		Assertions.assertNotNull(response);
		Assertions.assertEquals(tenantId, response.getTenantId());
		Assertions.assertEquals(operatorId, response.getOperatorId());
		Assertions.assertEquals(configId, response.getConfigId());
		Assertions.assertEquals(name, response.getName());
		Assertions.assertEquals(value, response.getValue());
		Assertions.assertEquals(status, response.getStatus().getCode());
		Assertions.assertEquals(remarks, response.getRemarks());
	}
}
