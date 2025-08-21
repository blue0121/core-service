package io.jutil.coreservice.core.model;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

/**
 * @author Jin Zheng
 * @since 2025-08-13
 */
@Getter
@Setter
@NoArgsConstructor
public class PageRequest {
	@Min(value = 1, message = "分页索引不能小于1")
	private int index = 1;

	@Range(min = 1, max = 100, message = "分页记录数值[1-100]")
	private int size = 10;


}
