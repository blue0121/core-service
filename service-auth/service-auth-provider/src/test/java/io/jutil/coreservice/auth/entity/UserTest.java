package io.jutil.coreservice.auth.entity;

import io.jutil.coreservice.auth.model.UserRequest;
import io.jutil.coreservice.auth.model.UserResponse;
import io.jutil.coreservice.core.dict.Realm;
import io.jutil.coreservice.core.dict.Status;
import io.jutil.springeasy.mybatis.id.LongIdGenerator;
import org.junit.jupiter.api.Assertions;

import java.time.LocalDateTime;

/**
 * @author Jin Zheng
 * @since 2025-08-18
 */
public class UserTest {

	public static User create(LocalDateTime loginTime) {
		var entity = new User();
		entity.setId(LongIdGenerator.nextId());
		entity.setRealm(Realm.ADMIN);
		entity.setCode("code");
		entity.setName("name");
		entity.setPassword("password");
		entity.setStatus(Status.ACTIVE);
		entity.setRemarks("remarks");
		entity.setExtension("extension".getBytes());
		entity.setIp("ip");
		entity.setLoginTime(loginTime);
		entity.setCreateTime(loginTime);
		entity.setUpdateTime(loginTime);
		return entity;
	}

	public static UserRequest createRequest() {
		var request = new UserRequest();
		request.setId(1L);
		request.setRealm(Realm.ADMIN);
		request.setCode("code");
		request.setName("name");
		request.setPassword("password");
		request.setStatus(Status.ACTIVE);
		request.setRemarks("remarks");
		request.setExtension("extension".getBytes());
		return request;
	}

	public static void verify(User entity, int realm, String code, String name, String password,
	                          int status, String remarks, String extension, String ip,
	                          LocalDateTime loginTime) {
		Assertions.assertNotNull(entity);
		Assertions.assertEquals(realm, entity.getRealm().getCode());
		Assertions.assertEquals(code, entity.getCode());
		Assertions.assertEquals(name, entity.getName());
		Assertions.assertEquals(password, entity.getPassword());
		Assertions.assertEquals(status, entity.getStatus().getCode());
		Assertions.assertEquals(remarks, entity.getRemarks());
		Assertions.assertEquals(extension, new String(entity.getExtension()));
		Assertions.assertEquals(ip, entity.getIp());
		Assertions.assertEquals(loginTime, entity.getLoginTime());
	}

	public static void verify(UserResponse response, long id, int realm, String code,
	                          String name, int status, String remarks, String extension,
	                          String ip, LocalDateTime loginTime, LocalDateTime createTime,
	                          LocalDateTime updateTime) {
		Assertions.assertNotNull(response);
		Assertions.assertEquals(id, response.getId());
		Assertions.assertEquals(realm, response.getRealm().getCode());
		Assertions.assertEquals(code, response.getCode());
		Assertions.assertEquals(name, response.getName());
		Assertions.assertEquals(status, response.getStatus().getCode());
		Assertions.assertEquals(remarks, response.getRemarks());
		Assertions.assertEquals(extension, new String(response.getExtension()));
		Assertions.assertEquals(ip, response.getIp());
		Assertions.assertEquals(loginTime, response.getLoginTime());
		Assertions.assertEquals(createTime, response.getCreateTime());
		Assertions.assertEquals(updateTime, response.getUpdateTime());
	}

	public static void verify(UserResponse response, long id, int realm, String code,
	                          String name, int status, String remarks, String extension) {
		Assertions.assertNotNull(response);
		Assertions.assertEquals(id, response.getId());
		Assertions.assertEquals(realm, response.getRealm().getCode());
		Assertions.assertEquals(code, response.getCode());
		Assertions.assertEquals(name, response.getName());
		Assertions.assertEquals(status, response.getStatus().getCode());
		Assertions.assertEquals(remarks, response.getRemarks());
		Assertions.assertEquals(extension, new String(response.getExtension()));
	}
}
