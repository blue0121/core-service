package io.jutil.coreservice.auth.provider;

import io.jutil.coreservice.auth.convertor.TokenConvertor;
import io.jutil.coreservice.auth.facade.TokenFacade;
import io.jutil.coreservice.auth.model.TokenRequest;
import io.jutil.coreservice.auth.model.TokenResponse;
import io.jutil.coreservice.auth.service.TokenService;
import io.jutil.coreservice.core.util.AssertUtil;
import io.jutil.springeasy.core.validation.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Jin Zheng
 * @since 2025-08-15
 */
@Component
public class TokenProvider implements TokenFacade {
	@Autowired
	TokenService tokenService;

	@Override
	public TokenResponse create(TokenRequest request) {
		ValidationUtil.valid(request);
		var entity = TokenConvertor.toEntity(request);
		var token = tokenService.login(entity);
		return TokenConvertor.toResponse(token);
	}

	@Override
	public TokenResponse refresh(String token) {
		AssertUtil.validNotEmpty(token, "令牌");
		var t = tokenService.refresh(token);
		return TokenConvertor.toResponse(t);
	}
}
