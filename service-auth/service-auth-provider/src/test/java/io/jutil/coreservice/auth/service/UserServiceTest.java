package io.jutil.coreservice.auth.service;

import io.jutil.coreservice.auth.entity.PageTest;
import io.jutil.coreservice.auth.entity.User;
import io.jutil.coreservice.auth.entity.UserSearch;
import io.jutil.coreservice.auth.entity.UserTest;
import io.jutil.coreservice.auth.repository.UserRepository;
import io.jutil.coreservice.core.dict.Realm;
import io.jutil.springeasy.core.security.PasswordUtil;
import io.jutil.springeasy.core.util.DateUtil;
import io.jutil.springeasy.spring.exception.ErrorCodeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

/**
 * @author Jin Zheng
 * @since 2025-08-20
 */
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
	@Mock
	UserRepository repository;

	@InjectMocks
	UserService service;

	@Test
	void testGetOne() {
		var user = UserTest.create(DateUtil.now());
		Mockito.when(repository.getOne(Mockito.any(), Mockito.anyLong())).thenReturn(user);
		var view = service.getOne(Realm.ADMIN, 1L);
		Assertions.assertSame(user, view);
	}

	@Test
	void testGetList() {
		var user = UserTest.create(DateUtil.now());
		Mockito.when(repository.getList(Mockito.any(), Mockito.anyList())).thenReturn(List.of(user));
		var viewMap = service.getList(Realm.ADMIN, List.of(1L));
		Assertions.assertEquals(1, viewMap.size());
		Assertions.assertSame(user, viewMap.get(user.getId()));
	}

	@Test
	void testAddOne() {
		var user = UserTest.create(DateUtil.now());
		Mockito.when(repository.addOne(Mockito.any())).thenReturn(user);
		Assertions.assertSame(user, service.addOne(user));

		var captor = ArgumentCaptor.forClass(User.class);
		Mockito.verify(repository).addOne(captor.capture());
		var view = captor.getValue();
		Assertions.assertEquals(64, view.getPassword().length());
	}

	@Test
	void testUpdateOne() {
		var now = DateUtil.now();
		var user = UserTest.create(now);
		user.setPassword(PasswordUtil.encrypt(user.getPassword()));
		Mockito.when(repository.getOne(Mockito.any(), Mockito.anyLong())).thenReturn(user);

		var entity = UserTest.create(now);
		entity.setOldPassword(entity.getPassword());
		Mockito.when(repository.updateOne(Mockito.any())).thenReturn(user);
		Assertions.assertSame(user, service.updateOne(entity));

		var captor = ArgumentCaptor.forClass(User.class);
		Mockito.verify(repository).updateOne(captor.capture());
		var view = captor.getValue();
		Assertions.assertEquals(64, view.getPassword().length());
	}

	@Test
	void testUpdateOne1() {
		var now = DateUtil.now();
		var user = UserTest.create(now);
		Assertions.assertThrows(ErrorCodeException.class, () -> service.updateOne(user));
	}

	@Test
	void testUpdateOne2() {
		var now = DateUtil.now();
		var user = UserTest.create(now);
		user.setPassword(PasswordUtil.encrypt(user.getPassword()));
		Mockito.when(repository.getOne(Mockito.any(), Mockito.anyLong())).thenReturn(user);

		var entity = UserTest.create(now);
		entity.setOldPassword("password1");
		Assertions.assertThrows(ErrorCodeException.class, () -> service.updateOne(entity));
	}

	@Test
	void testDeleteOne() {
		Mockito.when(repository.deleteOne(Mockito.any(), Mockito.anyLong())).thenReturn(1);
		var view = service.deleteOne(Realm.ADMIN, 1L);
		Assertions.assertSame(1, view);
	}

	@Test
	void testDeleteList() {
		Mockito.when(repository.deleteList(Mockito.any(), Mockito.anyList())).thenReturn(1);
		var view = service.deleteList(Realm.ADMIN, List.of(1L));
		Assertions.assertSame(1, view);
	}

	@Test
	void testSearch() {
		var search = new UserSearch();
		var page = PageTest.createPage();
		Mockito.when(repository.search(Mockito.any(), Mockito.any())).thenReturn(page);
		var view = service.search(search, page);
		Assertions.assertSame(page, view);
	}
}
