package io.jutil.coreservice.auth.provider;

import io.jutil.coreservice.auth.BaseTest;
import io.jutil.coreservice.auth.config.TokenProperties;
import io.jutil.springeasy.core.security.KeyPairMode;
import io.jutil.springeasy.core.util.DateUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.KeyFactory;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @author Jin Zheng
 * @since 2025-08-20
 */
class SecurityProviderTest implements BaseTest {
	@Autowired
	SecurityProvider provider;

	@Autowired
	TokenProperties prop;

	@Test
	void testGetPublicKey() throws Exception {
		var now = DateUtil.now();
		var expireTime = now.plus(prop.getPublicKeyExpireIn());
		var response = provider.getPublicKey();
		Assertions.assertNotNull(response);
		Assertions.assertEquals(KeyPairMode.ED_25519, response.getMode());
		Assertions.assertNotNull(response.getPublicKey());
		Assertions.assertTrue(DateUtil.equal(expireTime, response.getExpireTime()));


		var pubKey = Base64.getUrlDecoder().decode(response.getPublicKey());
		var keySpec = new X509EncodedKeySpec(pubKey);
		var factory = KeyFactory.getInstance(response.getMode().getKey());
		var key = factory.generatePublic(keySpec);
		Assertions.assertNotNull(key);
	}
}
