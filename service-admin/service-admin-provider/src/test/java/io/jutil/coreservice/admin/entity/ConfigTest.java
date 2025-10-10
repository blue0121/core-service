package io.jutil.coreservice.admin.entity;

import io.jutil.coreservice.admin.model.ConfigRequest;
import io.jutil.coreservice.admin.model.ConfigResponse;
import io.jutil.coreservice.core.dict.Status;
import io.jutil.coreservice.core.dict.YesOrNo;
import io.jutil.springeasy.mybatis.id.LongIdGenerator;
import org.junit.jupiter.api.Assertions;

import java.time.LocalDateTime;

/**
 * @author Jin Zheng
 * @since 2025-10-08
 */
public class ConfigTest {

	public static Config create() {
		var entity = new Config();
		entity.setId(LongIdGenerator.nextId());
		entity.setTenantId(1L);
		entity.setOperatorId(2L);
		entity.setCode("code");
		entity.setName("name");
		entity.setValue("value");
		entity.setStatus(Status.ACTIVE);
		entity.setMultiValue(YesOrNo.NO);
		entity.setRemarks("remarks");
		return entity;
	}

	public static ConfigRequest createRequest() {
		var request = new ConfigRequest();
		request.setId(1L);
		request.setTenantId(1L);
		request.setOperatorId(2L);
		request.setCode("code");
		request.setName("name");
		request.setValue("value");
		request.setStatus(Status.ACTIVE);
		request.setMultiValue(YesOrNo.NO);
		request.setRemarks("remarks");
		return request;
	}

	public static void verify(Config entity, long tenantId, long operatorId, String code, String name,
	                          String value, int status, int multiValue, String remarks) {
		Assertions.assertNotNull(entity);
		Assertions.assertEquals(tenantId, entity.getTenantId());
		Assertions.assertEquals(operatorId, entity.getOperatorId());
		Assertions.assertEquals(code, entity.getCode());
		Assertions.assertEquals(name, entity.getName());
		Assertions.assertEquals(value, entity.getValue());
		Assertions.assertEquals(status, entity.getStatus().getCode());
		Assertions.assertEquals(multiValue, entity.getMultiValue().getCode());
		Assertions.assertEquals(remarks, entity.getRemarks());
	}

	public static void verify(ConfigResponse response, long tenantId, long operatorId, String code,
	                          String name, String value, int status, int multiValue, String remarks) {
		Assertions.assertNotNull(response);
		Assertions.assertEquals(tenantId, response.getTenantId());
		Assertions.assertEquals(operatorId, response.getOperatorId());
		Assertions.assertEquals(code, response.getCode());
		Assertions.assertEquals(name, response.getName());
		Assertions.assertEquals(value, response.getValue());
		Assertions.assertEquals(status, response.getStatus().getCode());
		Assertions.assertEquals(multiValue, response.getMultiValue().getCode());
		Assertions.assertEquals(remarks, response.getRemarks());
	}

	public static void verify(ConfigResponse response, long id, long tenantId, long operatorId, String code,
	                          String name, String value, int status, int multiValue, String remarks,
	                          LocalDateTime createTime, LocalDateTime updateTime) {
		Assertions.assertNotNull(response);
		Assertions.assertEquals(id, response.getId());
		Assertions.assertEquals(tenantId, response.getTenantId());
		Assertions.assertEquals(operatorId, response.getOperatorId());
		Assertions.assertEquals(code, response.getCode());
		Assertions.assertEquals(name, response.getName());
		Assertions.assertEquals(value, response.getValue());
		Assertions.assertEquals(status, response.getStatus().getCode());
		Assertions.assertEquals(multiValue, response.getMultiValue().getCode());
		Assertions.assertEquals(remarks, response.getRemarks());
		Assertions.assertEquals(createTime, response.getCreateTime());
		Assertions.assertEquals(updateTime, response.getUpdateTime());
	}
}
