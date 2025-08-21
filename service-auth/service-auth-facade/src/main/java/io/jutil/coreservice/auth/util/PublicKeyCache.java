package io.jutil.coreservice.auth.util;

import io.jutil.coreservice.auth.facade.SecurityFacade;
import io.jutil.coreservice.auth.model.PublicKeyResponse;
import io.jutil.coreservice.core.model.UserSession;
import io.jutil.springeasy.core.util.DateUtil;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Jin Zheng
 * @since 2023-12-16
 */
public class PublicKeyCache {
	private final SecurityFacade securityFacade;
	private final Lock lock;

	private PublicKey publicKey;
	private LocalDateTime expireTime;


	public PublicKeyCache(SecurityFacade securityFacade) {
		this.securityFacade = securityFacade;
		this.lock = new ReentrantLock();
	}

	public UserSession createAndVerify(String token) {

		return UserSession.createAndVerify(this.getPublicKey(), token);
	}

	public PublicKey getPublicKey() {
		this.load();
		return this.publicKey;
	}

	private void load() {
		var now = DateUtil.now();
		if (expireTime != null && expireTime.isAfter(now)) {
			return;
		}

		lock.lock();
		try {
			if (expireTime != null && expireTime.isAfter(now)) {
				return;
			}
			var response = securityFacade.getPublicKey();
			this.publicKey = this.readPublicKey(response);
			this.expireTime = response.getExpireTime();
		} finally {
			lock.unlock();
		}
	}

	private PublicKey readPublicKey(PublicKeyResponse response) {
		var bytes = Base64.getUrlDecoder().decode(response.getPublicKey());;
		var keySpec = new X509EncodedKeySpec(bytes);
		try {
			var factory = KeyFactory.getInstance(response.getMode().getKey());
			return factory.generatePublic(keySpec);
		} catch (Exception e) {
			throw new SecurityException(e);
		}
	}
}
