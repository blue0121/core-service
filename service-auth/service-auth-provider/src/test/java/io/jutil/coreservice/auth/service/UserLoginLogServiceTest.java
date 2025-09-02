package io.jutil.coreservice.auth.service;

import io.jutil.coreservice.auth.entity.PageTest;
import io.jutil.coreservice.auth.entity.UserLoginLogSearch;
import io.jutil.coreservice.auth.repository.UserLoginLogRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * @author Jin Zheng
 * @since 2025-08-20
 */
@ExtendWith(MockitoExtension.class)
class UserLoginLogServiceTest {
	@Mock
	UserLoginLogRepository repository;

	@InjectMocks
	UserLoginLogService service;


	@Test
	void testSearch() {
		var search = new UserLoginLogSearch();
		var page = PageTest.createPage();
		Mockito.when(repository.search(Mockito.any(), Mockito.any())).thenReturn(page);
		var view = service.search(search, page);
		Assertions.assertSame(page, view);
	}
}
