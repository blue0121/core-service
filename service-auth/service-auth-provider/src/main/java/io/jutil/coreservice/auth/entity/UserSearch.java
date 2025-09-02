package io.jutil.coreservice.auth.entity;

import io.jutil.coreservice.auth.dict.Realm;
import io.jutil.coreservice.core.dict.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

/**
 * @author Jin Zheng
 * @since 2025-09-02
 */
@Getter
@Setter
@NoArgsConstructor
public class UserSearch {
	private Realm realm;
	private Status status;
	private String code;
	private String name;
	private Collection<Long> idList;
}
