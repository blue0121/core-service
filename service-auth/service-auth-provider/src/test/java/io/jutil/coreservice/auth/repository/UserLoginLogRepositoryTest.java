package io.jutil.coreservice.auth.repository;

import io.jutil.coreservice.auth.dao.UserLoginLogMapper;
import io.jutil.coreservice.auth.entity.PageTest;
import io.jutil.coreservice.auth.entity.UserLoginLog;
import io.jutil.coreservice.auth.entity.UserLoginLogSearch;
import io.jutil.coreservice.auth.entity.UserLoginLogTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

/**
 * @author Jin Zheng
 * @since 2025-09-02
 */
@ExtendWith(MockitoExtension.class)
class UserLoginLogRepositoryTest {
	@Mock
	UserLoginLogMapper mapper;

	@InjectMocks
	UserLoginLogRepository repository;

	@Test
	void testSearch() {
		var search = new UserLoginLogSearch();
		var page = PageTest.createPage();
		var entity = UserLoginLogTest.create(1L);
		Mockito.when(mapper.countPage(Mockito.any())).thenReturn(1);
		Mockito.when(mapper.listPage(Mockito.any(), Mockito.any())).thenReturn(List.of(entity));
		var view = repository.search(search, page);
		List<UserLoginLog> list = view.getContents();
		Assertions.assertEquals(1, list.size());
		Assertions.assertSame(entity, list.getFirst());
	}

	@Test
	void testSearch1() {
		var search = new UserLoginLogSearch();
		var page = PageTest.createPage();
		Mockito.when(mapper.countPage(Mockito.any())).thenReturn(0);
		var view = repository.search(search, page);
		List<UserLoginLog> list = view.getContents();
		Assertions.assertNull(list);
		Mockito.verify(mapper, Mockito.never()).listPage(Mockito.any(), Mockito.any());
	}
}
