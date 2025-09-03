package io.jutil.coreservice.auth.provider;

import io.jutil.coreservice.auth.entity.PageTest;
import io.jutil.coreservice.auth.entity.SearchRequestTest;
import io.jutil.coreservice.auth.entity.User;
import io.jutil.coreservice.auth.entity.UserTest;
import io.jutil.coreservice.auth.model.UserResponse;
import io.jutil.coreservice.auth.model.UserSearchRequest;
import io.jutil.coreservice.auth.service.UserService;
import io.jutil.coreservice.core.dict.Realm;
import io.jutil.springeasy.core.collection.Sort;
import io.jutil.springeasy.core.util.DateUtil;
import io.jutil.springeasy.core.util.StringUtil;
import io.jutil.springeasy.spring.exception.ErrorCodeException;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

/**
 * @author Jin Zheng
 * @since 2025-08-20
 */
@ExtendWith(MockitoExtension.class)
class UserProviderTest {
	@Mock
	UserService service;

	@InjectMocks
	UserProvider provider;

	@Test
	void testGetOne() {
		var now = DateUtil.now();
		var user = UserTest.create(now);
		Mockito.when(service.getOne(Mockito.any(), Mockito.anyLong())).thenReturn(user);
		var response = provider.getOne(Realm.ADMIN, 1L);
		UserTest.verify(response, user.getId(),1, "code", "name", 0,
				"remarks", "extension", "ip", now, now, now);
	}

	@Test
	void testGetOne1() {
		Assertions.assertThrows(ValidationException.class, () -> provider.getOne(Realm.ADMIN, 0L));
		Assertions.assertThrows(ValidationException.class, () -> provider.getOne(Realm.ADMIN, -1L));
	}

	@Test
	void testGetList() {
		var now = DateUtil.now();
		var user = UserTest.create(now);
		Mockito.when(service.getList(Mockito.any(), Mockito.anyList()))
				.thenReturn(Map.of(user.getId(), user));
		var response = provider.getList(Realm.ADMIN, List.of(1L));
		var view = response.get(user.getId());
		UserTest.verify(view, user.getId(),1, "code", "name", 0,
				"remarks", "extension", "ip", now, now, now);
	}

	@Test
	void testGetList1() {
		Assertions.assertThrows(ValidationException.class,
				() -> provider.getList(null, List.of(1L)));
		Assertions.assertThrows(ValidationException.class,
				() -> provider.getList(Realm.ADMIN, null));
		Assertions.assertThrows(ValidationException.class,
				() -> provider.getList(Realm.ADMIN, List.of()));
		Assertions.assertThrows(ValidationException.class,
				() -> provider.getList(Realm.ADMIN, List.of(0L)));
		Assertions.assertThrows(ValidationException.class,
				() -> provider.getList(Realm.ADMIN, List.of(-1L)));
		Assertions.assertThrows(ValidationException.class,
				() -> provider.getList(Realm.ADMIN, List.of(1L, -1L)));
	}

	@Test
	void testDeleteOne() {
		Mockito.when(service.deleteOne(Mockito.any(), Mockito.anyLong())).thenReturn(1);
		var count = provider.deleteOne(Realm.ADMIN, 1L);
		Assertions.assertEquals(1, count);
	}

	@Test
	void testDeleteOne1() {
		Assertions.assertThrows(ValidationException.class, () -> provider.deleteOne(Realm.ADMIN, 0L));
		Assertions.assertThrows(ValidationException.class, () -> provider.deleteOne(Realm.ADMIN, -1L));
	}

	@Test
	void testDeleteList() {
		Mockito.when(service.deleteList(Mockito.any(), Mockito.anyList())).thenReturn(1);
		var count = provider.deleteList(Realm.ADMIN, List.of(1L));
		Assertions.assertEquals(1, count);
	}

	@Test
	void testDeleteList1() {
		Assertions.assertThrows(ValidationException.class,
				() -> provider.deleteList(null, List.of(1L)));
		Assertions.assertThrows(ValidationException.class,
				() -> provider.deleteList(Realm.ADMIN, null));
		Assertions.assertThrows(ValidationException.class,
				() -> provider.deleteList(Realm.ADMIN, List.of()));
		Assertions.assertThrows(ValidationException.class,
				() -> provider.deleteList(Realm.ADMIN, List.of(0L)));
		Assertions.assertThrows(ValidationException.class,
				() -> provider.deleteList(Realm.ADMIN, List.of(-1L)));
		Assertions.assertThrows(ValidationException.class,
				() -> provider.deleteList(Realm.ADMIN, List.of(1L, -1L)));
	}

	@Test
	void testAddOne() {
		var now = DateUtil.now();
		var request = UserTest.createRequest();
		var user = UserTest.create(now);
		Mockito.when(service.addOne(Mockito.any())).thenReturn(user);
		var response = provider.addOne(request);
		UserTest.verify(response, user.getId(),1, "code", "name", 0,
				"remarks", "extension", "ip", now, now, now);

		var captor = ArgumentCaptor.forClass(User.class);
		Mockito.verify(service).addOne(captor.capture());
		var view = captor.getValue();
		UserTest.verify(view, 1, "code", "name", "password",0,
				"remarks", "extension",
				null, null);
	}

	@Test
	void testAddOne1() {
		var request = UserTest.createRequest();
		Assertions.assertThrows(ErrorCodeException.class, () -> provider.addOne(request));
	}

	@Test
	void testAddOne2() {
		var r1 = UserTest.createRequest();
		r1.setRealm(null);
		Assertions.assertThrows(ValidationException.class, () -> provider.addOne(r1));
	}

	@Test
	void testAddOne3() {
		var r1 = UserTest.createRequest();
		r1.setCode(null);
		Assertions.assertThrows(ValidationException.class, () -> provider.addOne(r1));

		var r2 = UserTest.createRequest();
		r2.setCode("");
		Assertions.assertThrows(ValidationException.class, () -> provider.addOne(r2));

		var r3 = UserTest.createRequest();
		r3.setCode(StringUtil.repeat("1", 51, ""));
		Assertions.assertThrows(ValidationException.class, () -> provider.addOne(r3));
	}

	@Test
	void testAddOne4() {
		var r1 = UserTest.createRequest();
		r1.setName(null);
		Assertions.assertThrows(ValidationException.class, () -> provider.addOne(r1));

		var r2 = UserTest.createRequest();
		r2.setName("");
		Assertions.assertThrows(ValidationException.class, () -> provider.addOne(r2));

		var r3 = UserTest.createRequest();
		r3.setName(StringUtil.repeat("1", 51, ""));
		Assertions.assertThrows(ValidationException.class, () -> provider.addOne(r3));
	}

	@Test
	void testAddOne5() {
		var r1 = UserTest.createRequest();
		r1.setPassword(null);
		Assertions.assertThrows(ValidationException.class, () -> provider.addOne(r1));

		var r2 = UserTest.createRequest();
		r2.setPassword("");
		Assertions.assertThrows(ValidationException.class, () -> provider.addOne(r2));
	}

	@Test
	void testAddOne6() {
		var r1 = UserTest.createRequest();
		r1.setStatus(null);
		Assertions.assertThrows(ValidationException.class, () -> provider.addOne(r1));
	}

	@Test
	void testAddOne7() {
		var r1 = UserTest.createRequest();
		r1.setRemarks(StringUtil.repeat("1", 501, ""));
		Assertions.assertThrows(ValidationException.class, () -> provider.addOne(r1));
	}

	@Test
	void testAddOne8() {
		var r1 = UserTest.createRequest();
		r1.setExtension(StringUtil.repeat("1", 501, "").getBytes());
		Assertions.assertThrows(ValidationException.class, () -> provider.addOne(r1));
	}

	@Test
	void testUpdateOne() {
		var now = DateUtil.now();
		var user = UserTest.create(now);
		Mockito.when(service.updateOne(Mockito.any())).thenReturn(user);

		var request = UserTest.createRequest();
		request.setOldPassword("oldPassword");
		var response = provider.updateOne(request);
		UserTest.verify(response, user.getId(),1, "code", "name", 0,
				"remarks", "extension", "ip", now, now, now);

		var captor = ArgumentCaptor.forClass(User.class);
		Mockito.verify(service).updateOne(captor.capture());
		var view = captor.getValue();
		UserTest.verify(view, 1, "code", "name", "password",0,
				"remarks", "extension",
				null, null);
	}

	@Test
	void testUpdateOne1() {
		var now = DateUtil.now();
		var user = UserTest.create(now);
		Mockito.when(service.updateOne(Mockito.any())).thenReturn(user);

		var request = UserTest.createRequest();
		request.setPassword(null);
		var response = provider.updateOne(request);
		UserTest.verify(response, user.getId(),1, "code", "name", 0,
				"remarks", "extension", "ip", now, now, now);

		var captor = ArgumentCaptor.forClass(User.class);
		Mockito.verify(service).updateOne(captor.capture());
		var view = captor.getValue();
		UserTest.verify(view, 1, "code", "name", null,0,
				"remarks", "extension",
				null, null);
	}

	@Test
	void testUpdateOne2() {
		var now = DateUtil.now();
		var user = UserTest.create(now);
		Mockito.when(service.updateOne(Mockito.any())).thenReturn(user);

		var request = UserTest.createRequest();
		request.setPassword("");
		var response = provider.updateOne(request);
		UserTest.verify(response, user.getId(),1, "code", "name", 0,
				"remarks", "extension", "ip", now, now, now);

		var captor = ArgumentCaptor.forClass(User.class);
		Mockito.verify(service).updateOne(captor.capture());
		var view = captor.getValue();
		UserTest.verify(view, 1, "code", "name", "",0,
				"remarks", "extension",
				null, null);
	}

	@Test
	void testUpdateOne3() {
		var r1 = UserTest.createRequest();
		r1.setPassword(null);
		r1.setId(0L);
		Assertions.assertThrows(ValidationException.class, () -> provider.updateOne(r1));

		var r2 = UserTest.createRequest();
		r2.setPassword(null);
		r2.setId(-1L);
		Assertions.assertThrows(ValidationException.class, () -> provider.updateOne(r2));
	}

	@Test
	void testUpdateOne4() {
		var r1 = UserTest.createRequest();
		r1.setPassword(null);
		r1.setCode(StringUtil.repeat("1", 51, ""));
		Assertions.assertThrows(ValidationException.class, () -> provider.updateOne(r1));
	}

	@Test
	void testUpdateOne5() {
		var r1 = UserTest.createRequest();
		r1.setPassword(null);
		r1.setName(StringUtil.repeat("1", 51, ""));
		Assertions.assertThrows(ValidationException.class, () -> provider.updateOne(r1));
	}

	@Test
	void testUpdateOne6() {
		var r1 = UserTest.createRequest();
		r1.setPassword(null);
		r1.setRemarks(StringUtil.repeat("1", 501, ""));
		Assertions.assertThrows(ValidationException.class, () -> provider.updateOne(r1));
	}

	@Test
	void testUpdateOne7() {
		var r1 = UserTest.createRequest();
		r1.setPassword(null);
		r1.setExtension(StringUtil.repeat("1", 501, "").getBytes());
		Assertions.assertThrows(ValidationException.class, () -> provider.updateOne(r1));
	}

	@Test
	void testSearch() {
		var request = new UserSearchRequest();
		request.setFilter(new UserSearchRequest.SearchRequest());
		request.getFilter().setRealm(Realm.ADMIN);
		SearchRequestTest.setPage(request, "id", Sort.Direction.DESC);

		var page = PageTest.createPage();
		page.setTotal(1);
		var now = DateUtil.now();
		var entity = UserTest.create(now);
		page.setContents(List.of(entity));
		Mockito.when(service.search(Mockito.any(), Mockito.any())).thenReturn(page);
		var response = provider.search(request);
		PageTest.verify(response, 1, 10, 1, 1);
		List<UserResponse> view = response.getContents();
		Assertions.assertEquals(1, view.size());
		UserTest.verify(view.getFirst(), entity.getId(),1, "code", "name", 0,
				"remarks", "extension", "ip", now, now, now);
	}

	@Test
	void testSearch1() {
		var request = new UserSearchRequest();
		SearchRequestTest.setPage(request, "id", Sort.Direction.DESC);
		Assertions.assertThrows(ValidationException.class, () -> provider.search(request));
	}

	@Test
	void testSearch2() {
		var request = new UserSearchRequest();
		request.setFilter(new UserSearchRequest.SearchRequest());
		request.getFilter().setRealm(Realm.ADMIN);
		SearchRequestTest.setPage(request, "abc", Sort.Direction.DESC);
		Assertions.assertThrows(ValidationException.class, () -> provider.search(request));
	}
}
