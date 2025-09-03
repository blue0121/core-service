package io.jutil.coreservice.auth.entity;

import io.jutil.coreservice.auth.model.UserLoginLogResponse;
import io.jutil.coreservice.core.dict.Realm;
import io.jutil.coreservice.core.dict.Status;
import io.jutil.springeasy.mybatis.id.LongIdGenerator;
import org.junit.jupiter.api.Assertions;

import java.time.LocalDate;

/**
 * @author Jin Zheng
 * @since 2025-08-19
 */
public class UserLoginLogTest {

	public static UserLoginLog create(long userId) {
		var entity = new UserLoginLog();
		entity.setId(LongIdGenerator.nextId());
		entity.setRealm(Realm.ADMIN);
		entity.setUserId(userId);
		entity.setIp("ip");
		return entity;
	}

	public static UserLoginLog create(long userId, LocalDate loginDate) {
		var entity = new UserLoginLog();
		entity.setId(LongIdGenerator.nextId());
		entity.setRealm(Realm.ADMIN);
		entity.setUserId(userId);
		entity.setIp("ip");
		entity.setLoginDate(loginDate);
		entity.setLoginCount(1);
		entity.setUserCode("code");
		entity.setUserName("name");
		entity.setUserStatus(Status.ACTIVE);
		return entity;
	}

	public static void verify(UserLoginLog entity, int realm, long userId,
	                          String ip, LocalDate loginDate, int loginCount,
	                          String userCode, String userName, int userStatus) {
		Assertions.assertNotNull(entity);
		Assertions.assertEquals(realm, entity.getRealm().getCode());
		Assertions.assertEquals(userId, entity.getUserId());
		Assertions.assertEquals(ip, entity.getIp());
		Assertions.assertEquals(loginDate, entity.getLoginDate());
		Assertions.assertEquals(loginCount, entity.getLoginCount());
		Assertions.assertEquals(userCode, entity.getUserCode());
		Assertions.assertEquals(userName, entity.getUserName());
		Assertions.assertEquals(userStatus, entity.getUserStatus().getCode());
	}

	public static void verify(UserLoginLogResponse entity, int realm, long userId,
	                          String ip, LocalDate loginDate, int loginCount,
	                          String userCode, String userName, int userStatus) {
		Assertions.assertNotNull(entity);
		Assertions.assertEquals(realm, entity.getRealm().getCode());
		Assertions.assertEquals(userId, entity.getUserId());
		Assertions.assertEquals(ip, entity.getIp());
		Assertions.assertEquals(loginDate, entity.getLoginDate());
		Assertions.assertEquals(loginCount, entity.getLoginCount());
		Assertions.assertEquals(userCode, entity.getUserCode());
		Assertions.assertEquals(userName, entity.getUserName());
		Assertions.assertEquals(userStatus, entity.getUserStatus().getCode());
	}
}
