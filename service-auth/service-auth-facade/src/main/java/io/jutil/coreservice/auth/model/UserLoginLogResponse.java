package io.jutil.coreservice.auth.model;

import io.jutil.coreservice.core.dict.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author Jin Zheng
 * @since 2023-11-30
 */
@Getter
@Setter
@NoArgsConstructor
public class UserLoginLogResponse {
	private Long id;
	private Long userId;
	private String userCode;
	private String userName;
	private Status userStatus;
	private String ip;
	private LocalDate loginDate;
	private int loginCount;
	private LocalDateTime firstLoginTime;
	private LocalDateTime lastLoginTime;
}
