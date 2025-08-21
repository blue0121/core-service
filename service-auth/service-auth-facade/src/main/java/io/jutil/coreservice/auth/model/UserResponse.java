package io.jutil.coreservice.auth.model;

import io.jutil.coreservice.auth.dict.Realm;
import io.jutil.coreservice.core.dict.Status;
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
public class UserResponse {
	private long id;
	private Realm realm;
	private String code;
	private String name;
	private Status status;
	private byte[] extension;
	private String remarks;
	private String ip;
	private LocalDateTime loginTime;
	private LocalDateTime createTime;
	private LocalDateTime updateTime;

}
