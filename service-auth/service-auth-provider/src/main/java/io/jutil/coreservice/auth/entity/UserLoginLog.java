package io.jutil.coreservice.auth.entity;

import io.jutil.coreservice.core.dict.Realm;
import io.jutil.coreservice.core.dict.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author Jin Zheng
 * @since 2025-08-19
 */
@Getter
@Setter
@NoArgsConstructor
public class UserLoginLog {
	private Long id;
	private Realm realm;
	private Long userId;
	private String ip;
	private LocalDate loginDate;
	private int loginCount;
	private LocalDateTime firstLoginTime;
	private LocalDateTime lastLoginTime;

	private String userCode;
	private String userName;
	private Status userStatus;
}
