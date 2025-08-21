package io.jutil.coreservice.auth.entity;

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
		entity.setUserId(userId);
		entity.setIp("ip");
		return entity;
	}

	public static void verify(UserLoginLog entity, long userId, String ip,
	                          LocalDate loginDate, int loginCount,
	                          String userCode, String userName) {
		Assertions.assertNotNull(entity);
		Assertions.assertEquals(userId, entity.getUserId());
		Assertions.assertEquals(ip, entity.getIp());
		Assertions.assertEquals(loginDate, entity.getLoginDate());
		Assertions.assertEquals(loginCount, entity.getLoginCount());
		Assertions.assertEquals(userCode, entity.getUserCode());
		Assertions.assertEquals(userName, entity.getUserName());
	}
}
