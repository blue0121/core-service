package io.jutil.coreservice.auth.model;

import io.jutil.springeasy.core.security.KeyPairMode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author Jin Zheng
 * @since 2025-08-15
 */
@Getter
@Setter
@NoArgsConstructor
public class PublicKeyResponse {
	private final KeyPairMode mode = KeyPairMode.ED_25519;
	private String publicKey;
	private LocalDateTime expireTime;
}
