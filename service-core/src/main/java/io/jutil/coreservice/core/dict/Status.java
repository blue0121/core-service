package io.jutil.coreservice.core.dict;

import io.jutil.springeasy.core.codec.json.Dict;
import lombok.Getter;

/**
 * @author Jin Zheng
 * @since 2025-08-15
 */
@Getter
public enum Status implements Dict {
	ACTIVE(0, "正常"),
	DISABLED(1, "作废"),
	;

	private final int code;
	private final String label;

	Status(int code, String label) {
		this.code = code;
		this.label = label;
	}
}
