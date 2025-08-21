package io.jutil.coreservice.auth.model;

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
	private String name;
	private LocalDateTime expireTime;
}
