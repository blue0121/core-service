package io.jutil.coreservice.admin.entity;

import io.jutil.coreservice.admin.model.AccountRequest;
import io.jutil.coreservice.admin.model.AccountResponse;
import io.jutil.coreservice.core.dict.Status;
import io.jutil.springeasy.mybatis.id.LongIdGenerator;
import org.junit.jupiter.api.Assertions;

import java.time.LocalDateTime;

/**
 * @author Jin Zheng
 * @since 2025-09-12
 */
public class AccountTest {

	public static Account create(LocalDateTime loginTime) {
		var entity = new Account();
		entity.setId(LongIdGenerator.nextId());
		entity.setTenantId(1L);
		entity.setOperatorId(2L);
		entity.setCode("code");
		entity.setName("name");
		entity.setStatus(Status.ACTIVE);
		entity.setRemarks("remarks");
		entity.setCreateTime(loginTime);
		entity.setUpdateTime(loginTime);
		return entity;
	}

	public static AccountRequest createRequest() {
		var request = new AccountRequest();
		request.setId(1L);
		request.setTenantId(1L);
		request.setOperatorId(2L);
		request.setCode("code");
		request.setName("name");
		request.setStatus(Status.ACTIVE);
		request.setRemarks("remarks");
		return request;
	}

	public static void verify(Account entity, long tenantId, long operatorId, String code,
	                          String name, int status, String remarks) {
		Assertions.assertNotNull(entity);
		Assertions.assertEquals(tenantId, entity.getTenantId());
		Assertions.assertEquals(operatorId, entity.getOperatorId());
		Assertions.assertEquals(code, entity.getCode());
		Assertions.assertEquals(name, entity.getName());
		Assertions.assertEquals(status, entity.getStatus().getCode());
		Assertions.assertEquals(remarks, entity.getRemarks());
	}

	public static void verify(AccountResponse response, long id, int tenantId, long operatorId,
	                          String code, String name, int status, String remarks,
	                          LocalDateTime createTime, LocalDateTime updateTime) {
		Assertions.assertNotNull(response);
		Assertions.assertEquals(id, response.getId());
		Assertions.assertEquals(tenantId, response.getTenantId());
		Assertions.assertEquals(operatorId, response.getOperatorId());
		Assertions.assertEquals(code, response.getCode());
		Assertions.assertEquals(name, response.getName());
		Assertions.assertEquals(status, response.getStatus().getCode());
		Assertions.assertEquals(remarks, response.getRemarks());
		Assertions.assertEquals(createTime, response.getCreateTime());
		Assertions.assertEquals(updateTime, response.getUpdateTime());
	}

	public static void verify(AccountResponse response, long id, int tenantId, long operatorId,
	                          String code,  String name, int status, String remarks) {
		Assertions.assertNotNull(response);
		Assertions.assertEquals(id, response.getId());
		Assertions.assertEquals(tenantId, response.getTenantId());
		Assertions.assertEquals(operatorId, response.getOperatorId());
		Assertions.assertEquals(code, response.getCode());
		Assertions.assertEquals(name, response.getName());
		Assertions.assertEquals(status, response.getStatus().getCode());
		Assertions.assertEquals(remarks, response.getRemarks());
	}
}
