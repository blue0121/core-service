package io.jutil.coreservice.core.model;

import io.jutil.coreservice.core.dict.Realm;
import io.jutil.springeasy.core.security.KeyPair;
import io.jutil.springeasy.core.security.TokenUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

/**
 * @author Jin Zheng
 * @since 2025-08-15
 */
class UserSessionTest {
	KeyPair keyPair1;
	KeyPair keyPair2;

	final long id = 100L;
	final LocalDateTime expireTime = LocalDateTime.now().plusHours(1);
	final String text = "test_content";

	@BeforeEach
	void beforeEach() {
		keyPair1 = TokenUtil.generate();
		keyPair2 = TokenUtil.load("classpath:/key/ed25519.pub",
				"classpath:/key/ed25519.key");
	}

	@CsvSource({"true", "false"})
	@ParameterizedTest
	void testCreate1(boolean generated) {
		var keyPair = generated ? keyPair1 : keyPair2;

		var session = new UserSession();
		session.setRealm(Realm.ADMIN);
		session.setId(id);
		session.setExpireTime(expireTime);
		var token = session.createToken(keyPair.getPrivate());
		Assertions.assertFalse(token.isEmpty());
		System.out.println(token);

		var view = UserSession.createAndVerify(keyPair.getPublic(), token);
		Assertions.assertNotNull(view);
		Assertions.assertEquals(Realm.ADMIN, view.getRealm());
		Assertions.assertEquals(id, view.getId());
		Assertions.assertNull(view.getExtension());
	}

	@CsvSource({"true", "false"})
	@ParameterizedTest
	void testCreate2(boolean generated) {
		var keyPair = generated ? keyPair1 : keyPair2;

		var session = new UserSession();
		session.setRealm(Realm.ADMIN);
		session.setId(id);
		session.setExpireTime(expireTime);
		session.setExtension(text.getBytes(StandardCharsets.UTF_8));
		var token = session.createToken(keyPair.getPrivate());
		Assertions.assertFalse(token.isEmpty());
		System.out.println(token);

		var view = UserSession.createAndVerify(keyPair.getPublic(), token);
		Assertions.assertNotNull(view);
		Assertions.assertEquals(Realm.ADMIN, view.getRealm());
		Assertions.assertEquals(id, view.getId());
		var extension = view.getExtension();
		Assertions.assertNotNull(extension);
		Assertions.assertEquals(text, new String(extension));
	}
}
