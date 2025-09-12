package io.jutil.coreservice.core.dict;

import io.jutil.springeasy.core.codec.json.Dict;
import lombok.Getter;

/**
 * @author Jin Zheng
 * @since 2025-09-08
 */
@Getter
public enum YesOrNo implements Dict {
	NO(0, "否"),
	YES(1, "是"),
	;

	private final int code;
	private final String label;

	YesOrNo(int code, String label) {
		this.code = code;
		this.label = label;
	}
}
