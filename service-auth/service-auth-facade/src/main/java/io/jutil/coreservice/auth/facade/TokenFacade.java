package io.jutil.coreservice.auth.facade;

import io.jutil.coreservice.auth.model.TokenRequest;
import io.jutil.coreservice.auth.model.TokenResponse;

/**
 * @author Jin Zheng
 * @since 2025-08-15
 */
public interface TokenFacade {

	TokenResponse create(TokenRequest request);

	TokenResponse refresh(String token);

}
