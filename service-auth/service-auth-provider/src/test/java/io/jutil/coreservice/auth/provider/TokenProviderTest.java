package io.jutil.coreservice.auth.provider;

import io.jutil.coreservice.auth.entity.TokenTest;
import io.jutil.coreservice.auth.entity.User;
import io.jutil.coreservice.auth.service.TokenService;
import io.jutil.springeasy.core.util.StringUtil;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * @author Jin Zheng
 * @since 2025-08-20
 */
@ExtendWith(MockitoExtension.class)
class TokenProviderTest {
	@Mock
	TokenService service;

	@InjectMocks
	TokenProvider provider;

	@Test
	void testCreate() {
		var request = TokenTest.createRequest();
		var token = TokenTest.create();
		Mockito.when(service.login(Mockito.any(User.class))).thenReturn(token);
		var response = provider.create(request);
		TokenTest.verify(response, token);
	}

	@Test
	void testCreate1() {
		var request = TokenTest.createRequest();
		request.setCode(null);
		Assertions.assertThrows(ValidationException.class, () -> provider.create(request));

		request.setCode("");
		Assertions.assertThrows(ValidationException.class, () -> provider.create(request));
	}

	@Test
	void testCreate2() {
		var request = TokenTest.createRequest();
		request.setPassword(null);
		Assertions.assertThrows(ValidationException.class, () -> provider.create(request));

		request.setPassword("");
		Assertions.assertThrows(ValidationException.class, () -> provider.create(request));
	}

	@Test
	void testCreate3() {
		var request = TokenTest.createRequest();
		request.setRealm(null);
		Assertions.assertThrows(ValidationException.class, () -> provider.create(request));
	}

	@Test
	void testCreate4() {
		var request = TokenTest.createRequest();
		request.setIp(null);
		Assertions.assertThrows(ValidationException.class, () -> provider.create(request));

		request.setIp("");
		Assertions.assertThrows(ValidationException.class, () -> provider.create(request));

		request.setIp(StringUtil.repeat("a", 51, ""));
		Assertions.assertThrows(ValidationException.class, () -> provider.create(request));
	}

	@Test
	void testRefresh() {
		var token = TokenTest.create();
		Mockito.when(service.refresh(Mockito.anyString())).thenReturn(token);
		var response = provider.refresh(token.getToken());
		TokenTest.verify(response, token);
	}

	@Test
	void testRefresh1() {
		Assertions.assertThrows(ValidationException.class, () -> provider.refresh(null));
		Assertions.assertThrows(ValidationException.class, () -> provider.refresh(""));
	}
}
