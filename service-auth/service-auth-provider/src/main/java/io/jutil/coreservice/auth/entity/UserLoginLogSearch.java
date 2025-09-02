package io.jutil.coreservice.auth.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * @author Jin Zheng
 * @since 2025-08-28
 */
@Getter
@Setter
@NoArgsConstructor
public class UserLoginLogSearch {
	private long userId;
	private LocalDate startDate;
	private LocalDate endDate;
}
