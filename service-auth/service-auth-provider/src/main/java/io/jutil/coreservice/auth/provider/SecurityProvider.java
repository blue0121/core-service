package io.jutil.coreservice.auth.provider;

import io.jutil.coreservice.auth.config.TokenProperties;
import io.jutil.coreservice.auth.facade.SecurityFacade;
import io.jutil.coreservice.auth.model.PublicKeyResponse;
import io.jutil.coreservice.auth.service.TokenService;
import io.jutil.springeasy.core.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Base64;

/**
 * @author Jin Zheng
 * @since 2025-08-15
 */
@Component
public class SecurityProvider implements SecurityFacade {
	@Autowired
	TokenService tokenService;

	@Autowired
	TokenProperties tokenProperties;

	@Override
	public PublicKeyResponse getPublicKey() {
		var response = new PublicKeyResponse();
		var expireTime = DateUtil.now().plus(tokenProperties.getPublicKeyExpireIn());
		response.setExpireTime(expireTime);
		var pubKey = tokenService.getPublicKey().getEncoded();
		response.setPublicKey(Base64.getUrlEncoder().encodeToString(pubKey));
		return response;
	}
}
