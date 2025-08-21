package io.jutil.coreservice.auth.config;

import io.jutil.springeasy.core.util.AssertUtil;
import io.jutil.springeasy.spring.config.PropertiesChecker;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

/**
 * @author Jin Zheng
 * @since 2025-08-15
 */
@Getter
@Setter
@NoArgsConstructor
@ConfigurationProperties("app.token")
public class TokenProperties implements PropertiesChecker, InitializingBean {
	private Duration expireIn;
	private Duration publicKeyExpireIn;
	private String publicKeyPath;
	private String privateKeyPath;

	@Override
	public void check() {
		AssertUtil.notNull(expireIn, "ExpireIn");
		AssertUtil.notNull(publicKeyExpireIn, "publicKeyExpireIn");
		AssertUtil.notEmpty(publicKeyPath, "publicKeyPath");
		AssertUtil.notEmpty(privateKeyPath, "privateKeyPath");
	}

	@Override
	public void afterPropertiesSet() {
		this.check();
	}
}
