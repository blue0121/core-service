package io.jutil.coreservice.auth.service;

import io.jutil.coreservice.auth.config.TokenProperties;
import io.jutil.coreservice.auth.entity.Token;
import io.jutil.coreservice.auth.entity.User;
import io.jutil.coreservice.auth.repository.UserRepository;
import io.jutil.coreservice.core.model.UserSession;
import io.jutil.springeasy.core.security.PasswordUtil;
import io.jutil.springeasy.core.security.TokenUtil;
import io.jutil.springeasy.core.util.DateUtil;
import io.jutil.springeasy.spring.exception.BaseErrorCode;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @author Jin Zheng
 * @since 2025-08-15
 */
@Component
public class TokenService {
	@Autowired
	TokenProperties tokenProperties;

	@Autowired
	UserRepository userRepository;

	PublicKey publicKey;
	PrivateKey privateKey;

	@PostConstruct
	public void init() {
		var keyPair = TokenUtil.load(tokenProperties.getPublicKeyPath(),
				tokenProperties.getPrivateKeyPath());
		this.publicKey = keyPair.getPublic();
		this.privateKey = keyPair.getPrivate();
	}

	public Token login(User entity) {
		entity.setPassword(PasswordUtil.encrypt(entity.getPassword()));
		var loginUser = userRepository.login(entity);
		if (loginUser == null) {
			throw BaseErrorCode.LOGIN_FAILURE.newException();
		}
		var session = new UserSession();
		session.setRealm(loginUser.getRealm());
		session.setId(loginUser.getId());
		session.setExtension(loginUser.getExtension());
		var token = this.sign(session);
		token.setName(loginUser.getName());
		return token;
	}

	public Token refresh(String token) {
		var session = UserSession.createAndVerify(publicKey, token);
		var t = this.sign(session);
		var user = userRepository.getOne(session.getRealm(), session.getId());
		if (user != null) {
			t.setName(user.getName());
		}
		return t;
	}

	private Token sign(UserSession session) {
		var expireTime = DateUtil.now().plus(tokenProperties.getExpireIn());
		session.setExpireTime(expireTime);
		var data = session.createToken(privateKey);

		var token = new Token();
		token.setToken(data);
		token.setExpireTime(expireTime);
		return token;
	}

	public PublicKey getPublicKey() {
		return publicKey;
	}
}
