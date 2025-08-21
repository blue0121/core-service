package io.jutil.coreservice.auth.convertor;

import io.jutil.coreservice.auth.entity.Token;
import io.jutil.coreservice.auth.entity.User;
import io.jutil.coreservice.auth.model.TokenRequest;
import io.jutil.coreservice.auth.model.TokenResponse;

/**
 * @author Jin Zheng
 * @since 2025-08-15
 */
public class TokenConvertor {
	private TokenConvertor() {
	}

	public static User toEntity(TokenRequest request) {
		var entity = new User();
		entity.setCode(request.getCode());
		entity.setPassword(request.getPassword());
		entity.setRealm(request.getRealm());
		entity.setIp(request.getIp());
		return entity;
	}

	public static TokenResponse toResponse(Token entity) {
		if (entity == null) {
			return null;
		}
		var response = new TokenResponse();
		response.setToken(entity.getToken());
		response.setName(entity.getName());
		response.setExpireTime(entity.getExpireTime());
		return response;
	}
}
