package io.jutil.coreservice.auth.provider;

import io.jutil.coreservice.auth.entity.PageTest;
import io.jutil.coreservice.auth.entity.SearchRequestTest;
import io.jutil.coreservice.auth.entity.UserLoginLogTest;
import io.jutil.coreservice.auth.model.UserLoginLogResponse;
import io.jutil.coreservice.auth.model.UserLoginLogSearchRequest;
import io.jutil.coreservice.auth.service.UserLoginLogService;
import io.jutil.springeasy.core.collection.Sort;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Jin Zheng
 * @since 2025-08-20
 */
@ExtendWith(MockitoExtension.class)
class UserLoginLogProviderTest {
	@Mock
	UserLoginLogService service;

	@InjectMocks
	UserLoginLogProvider provider;


	@Test
	void testSearch() {
		var request = new UserLoginLogSearchRequest();
		SearchRequestTest.setPage(request, "id", Sort.Direction.DESC);

		var page = PageTest.createPage();
		page.setTotal(1);
		var loginDate = LocalDate.now();
		var entity = UserLoginLogTest.create(1L, loginDate);
		page.setContents(List.of(entity));
		Mockito.when(service.search(Mockito.any(), Mockito.any())).thenReturn(page);
		var response = provider.search(request);
		PageTest.verify(response, 1, 10, 1, 1);
		List<UserLoginLogResponse> view = response.getContents();
		Assertions.assertEquals(1, view.size());
		UserLoginLogTest.verify(view.getFirst(), 1L, "ip", loginDate, 1,
				"code", "name", 0);
	}

	@Test
	void testSearch1() {
		var request = new UserLoginLogSearchRequest();
		SearchRequestTest.setPage(request, "abc", Sort.Direction.DESC);
		Assertions.assertThrows(ValidationException.class, () -> provider.search(request));
	}
}
