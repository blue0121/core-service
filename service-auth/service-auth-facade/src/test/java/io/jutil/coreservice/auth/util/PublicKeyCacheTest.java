package io.jutil.coreservice.auth.util;

import io.jutil.coreservice.auth.facade.SecurityFacade;
import io.jutil.coreservice.auth.model.PublicKeyResponse;
import io.jutil.springeasy.core.security.TokenUtil;
import io.jutil.springeasy.core.util.DateUtil;
import io.jutil.springeasy.core.util.WaitUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Base64;

/**
 * @author Jin Zheng
 * @since 2025-08-17
 */
@ExtendWith(MockitoExtension.class)
class PublicKeyCacheTest {
	@Mock
	SecurityFacade securityFacade;

	PublicKeyCache cache;

	@BeforeEach
	void beforeEach() {
		var keyPair = TokenUtil.generate();
		var response = new PublicKeyResponse();
		var pubKey = keyPair.getPublic().getEncoded();
		response.setPublicKey(Base64.getUrlEncoder().encodeToString(pubKey));
		response.setExpireTime(DateUtil.now().plusSeconds(1));
		Mockito.when(securityFacade.getPublicKey()).thenReturn(response);
		this.cache = new PublicKeyCache(securityFacade);
	}

	@Test
	void testDecodePublicKey() {
		var key = cache.getPublicKey();
		Assertions.assertNotNull(key);
		Mockito.verify(securityFacade).getPublicKey();
	}

	@Test
	void testPublicKeyCache1() {
		var key = cache.getPublicKey();
		Assertions.assertNotNull(key);
		key = cache.getPublicKey();
		Assertions.assertNotNull(key);
		Mockito.verify(securityFacade).getPublicKey();
	}

	@Test
	void testPublicKeyCache2() {
		var key = cache.getPublicKey();
		Assertions.assertNotNull(key);
		WaitUtil.sleep(1500);
		key = cache.getPublicKey();
		Assertions.assertNotNull(key);
		Mockito.verify(securityFacade, Mockito.times(2)).getPublicKey();
	}
}
