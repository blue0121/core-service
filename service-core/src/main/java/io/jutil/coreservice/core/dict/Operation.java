package io.jutil.coreservice.core.dict;

import io.jutil.springeasy.core.codec.json.Dict;
import lombok.Getter;

/**
 * @author Jin Zheng
 * @since 2025-08-15
 */
@Getter
public enum Operation implements Dict {
	ADD(1, "增加"),
	UPDATE(2, "更新"),
	DELETE(3, "删除"),
	;

	private final int code;
	private final String label;

	Operation(int code, String label) {
		this.code = code;
		this.label = label;
	}
}
