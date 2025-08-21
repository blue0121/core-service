package io.jutil.coreservice.auth.entity;

import io.jutil.coreservice.auth.dict.Realm;
import io.jutil.coreservice.auth.model.TokenRequest;
import io.jutil.coreservice.auth.model.TokenResponse;
import io.jutil.springeasy.core.util.DateUtil;
import org.junit.jupiter.api.Assertions;

/**
 * @author Jin Zheng
 * @since 2025-08-20
 */
public class TokenTest {

	public static Token create() {
		var model = new Token();
		model.setToken("token");
		model.setName("name");
		model.setExpireTime(DateUtil.now());
		return model;
	}

	public static TokenRequest createRequest() {
		var request = new TokenRequest();
		request.setCode("code");
		request.setPassword("password");
		request.setRealm(Realm.ADMIN);
		request.setIp("ip");
		return request;
	}

	public static void verify(TokenResponse response, Token model) {
		Assertions.assertNotNull(response);
		Assertions.assertEquals(model.getToken(), response.getToken());
		Assertions.assertEquals(model.getName(), response.getName());
		Assertions.assertEquals(model.getExpireTime(), response.getExpireTime());
	}
}
