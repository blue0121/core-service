package io.jutil.coreservice.core.util;

import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author Jin Zheng
 * @since 2025-08-20
 */
class AssertUtilTest {

	@Test
	void testValidId() {
		AssertUtil.validId(1L, "ID");
		Assertions.assertThrows(ValidationException.class, () -> AssertUtil.validId(0L, "ID"));
		Assertions.assertThrows(ValidationException.class, () -> AssertUtil.validId(-1L, "ID"));
	}

	@Test
	void testValidIdList() {
		AssertUtil.validIdList(List.of(1L), "ID");
		Assertions.assertThrows(ValidationException.class,
				() -> AssertUtil.validIdList(null, "ID"));
		Assertions.assertThrows(ValidationException.class,
				() -> AssertUtil.validIdList(List.of(), "ID"));
		Assertions.assertThrows(ValidationException.class,
				() -> AssertUtil.validIdList(List.of(0L), "ID"));
		Assertions.assertThrows(ValidationException.class,
				() -> AssertUtil.validIdList(List.of(-1L), "ID"));
		Assertions.assertThrows(ValidationException.class,
				() -> AssertUtil.validIdList(List.of(1L, -1L), "ID"));
	}

	@Test
	void testValidNotEmptyString() {
		AssertUtil.validNotEmpty("a", "name");
		Assertions.assertThrows(ValidationException.class,
				() -> AssertUtil.validNotEmpty(null, "name"));
		Assertions.assertThrows(ValidationException.class,
				() -> AssertUtil.validNotEmpty("", "name"));
	}

}
