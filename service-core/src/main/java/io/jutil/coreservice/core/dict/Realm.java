package io.jutil.coreservice.core.dict;

import io.jutil.springeasy.core.codec.json.Dict;
import lombok.Getter;

/**
 * @author Jin Zheng
 * @since 2025-08-15
 */
@Getter
public enum Realm implements Dict {
	ADMIN(1, "后台管理账号"),
	USER(2, "用户"),
	;

	private final int code;
	private final String label;

	Realm(int code, String label) {
		this.code = code;
		this.label = label;
	}

	public static Realm from(int code) {
		for (Realm realm : Realm.values()) {
			if (realm.getCode() == code) {
				return realm;
			}
		}
		return null;
	}
}
