package io.jutil.coreservice.auth.entity;

import io.jutil.coreservice.auth.dict.Realm;
import io.jutil.coreservice.core.dict.Status;
import io.jutil.coreservice.core.entity.BaseEntity;
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
public class User extends BaseEntity {
	private Realm realm;
	private String code;
	private String name;
	private String password;
	private String oldPassword;
	private Status status;
	private String remarks;
	private byte[] extension;
	private String ip;
	private LocalDateTime loginTime;
}
