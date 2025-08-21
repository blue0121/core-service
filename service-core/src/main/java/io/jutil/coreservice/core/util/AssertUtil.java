package io.jutil.coreservice.core.util;

import jakarta.validation.ValidationException;

import java.util.Collection;

/**
 * @author Jin Zheng
 * @since 2025-08-20
 */
public class AssertUtil {
	private AssertUtil() {}

	public static void validId(long id, String name) {
		if (id <= 0) {
			throw new ValidationException(name + "不能为空");
		}
	}

	public static void validIdList(Collection<Long> idList, String name) {
		if (idList == null || idList.isEmpty()) {
			throw new ValidationException(name + "不能为空");
		}
		for (var id : idList) {
			validId(id, name);
		}
	}

	public static void validNotEmpty(String str, String name) {
		if (str == null || str.isEmpty()) {
			throw new ValidationException(name + "不能为空");
		}
	}
}
