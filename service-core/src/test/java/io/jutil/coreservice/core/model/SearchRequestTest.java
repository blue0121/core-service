package io.jutil.coreservice.core.model;

import com.alibaba.fastjson2.JSONPath;
import io.jutil.coreservice.core.dict.Realm;
import io.jutil.springeasy.core.codec.json.Json;
import io.jutil.springeasy.core.validation.ValidationUtil;
import jakarta.validation.ValidationException;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Map;

/**
 * @author Jin Zheng
 * @since 2025-08-28
 */
class SearchRequestTest {

	@Test
	void testSearchRequest() {
		var json = """
				{
					"filter": {
						"body": "test"
					},
					"page": {
						"index": 2,
						"size": 20
					},
					"sorts": [{
						"field": "a",
						"direction": "ASC"
					}, {
						"field": "b",
						"direction": "DESC"
					}, {
						"field": "c"
					}]
				}""";
		var request = Json.fromString(json, TestSearchRequest.class);
		ValidationUtil.valid(request);
		Assertions.assertNotNull(request);

		var body = request.getFilter();
		Assertions.assertEquals("test", body.body());

		var pageRequest = request.getPage();
		Assertions.assertEquals(2, pageRequest.getIndex());
		Assertions.assertEquals(20, pageRequest.getSize());

		var sorts = request.getSorts();
		Assertions.assertEquals(3, sorts.size());
		Assertions.assertEquals("a", sorts.getFirst().getField());
		Assertions.assertEquals("ASC", sorts.getFirst().getDirection());
		Assertions.assertEquals("b", sorts.get(1).getField());
		Assertions.assertEquals("DESC", sorts.get(1).getDirection());
		Assertions.assertEquals("c", sorts.get(2).getField());
		Assertions.assertEquals("DESC", sorts.get(2).getDirection());

		var page = request.toPage();
		Assertions.assertEquals(2, page.getPageIndex());
		Assertions.assertEquals(20, page.getPageSize());

		var sort = page.getSort();
		Assertions.assertEquals("ORDER BY a ASC,b DESC,c DESC",
				sort.toOrderByString(Map.of("a", "a", "b", "b", "c", "c")));
	}

	@CsvSource({"$.filter.body,", "$.page.index,0", "$.page.size,0",
			"$.page.size,101", "$.sorts[0].direction,abc"})
	@ParameterizedTest
	void testSearchRequest1(String path, String value) {
		var json = """
				{
					"filter": {
						"body": "test"
					},
					"page": {
						"index": 1,
						"size": 20
					},
					"sorts": [{
						"field": "a",
						"direction": "ASC"
					}, {
						"field": "b",
						"direction": "DESC"
					}, {
						"field": "c"
					}]
				}""";
		json = JSONPath.set(json, path, value);
		System.out.println(json);
		var request = Json.fromString(json, TestSearchRequest.class);
		Assertions.assertThrows(ValidationException.class, () -> ValidationUtil.valid(request));
	}

	static class TestSearchRequest extends SearchRequest<TestSearchRequest.Body> {

		public record Body(@NotEmpty(message = "内容不能为空") String body) {
		}
	}

	@Test
	void testRealmSearchRequest() {
		var json = """
				{
					"filter": {
					},
					"page": {
						"index": 1,
						"size": 20
					},
					"sorts": [{
						"field": "a",
						"direction": "ASC"
					}, {
						"field": "b",
						"direction": "DESC"
					}, {
						"field": "c"
					}]
				}""";
		System.out.println(json);
		var request = Json.fromString(json, RealmSearchRequest.class);
		Assertions.assertThrows(ValidationException.class, () -> ValidationUtil.valid(request));
	}

	static class RealmSearchRequest extends SearchRequest<RealmSearchRequest.Body> {

		@Getter
		@Setter
		@NoArgsConstructor
		public static class Body {
			@NotNull(message = "域不能为空")
			private Realm realm;
		}
	}
}
