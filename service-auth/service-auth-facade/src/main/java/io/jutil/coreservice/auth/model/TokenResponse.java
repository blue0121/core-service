package io.jutil.coreservice.auth.model;

import io.jutil.coreservice.core.dict.Realm;
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
public class TokenResponse {
	private String token;
	private Realm realm;
	private String name;
	private LocalDateTime expireTime;
}
