package io.jutil.coreservice.admin.entity;

import io.jutil.coreservice.core.dict.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

/**
 * @author Jin Zheng
 * @since 2025-09-10
 */
@Getter
@Setter
@NoArgsConstructor
public class AccountSearch {
	private long tenantId;
	private Status status;
	private String code;
	private String name;
	private Collection<Long> idList;
}
