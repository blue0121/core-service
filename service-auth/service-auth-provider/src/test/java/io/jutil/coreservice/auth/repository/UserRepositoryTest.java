package io.jutil.coreservice.auth.repository;

import io.jutil.coreservice.auth.dao.UserLoginLogMapper;
import io.jutil.coreservice.auth.dao.UserMapper;
import io.jutil.coreservice.auth.dict.Realm;
import io.jutil.coreservice.auth.entity.User;
import io.jutil.coreservice.auth.entity.UserLoginLog;
import io.jutil.coreservice.auth.entity.UserTest;
import io.jutil.springeasy.core.util.DateUtil;
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
class UserRepositoryTest {
	@Mock
	UserMapper mapper;
	@Mock
	UserLoginLogMapper logMapper;

	@InjectMocks
	UserRepository repository;

	@Test
	void testLogin() {
		var now = DateUtil.now();
		var loginUser = UserTest.create(now);
		loginUser.setIp(null);
		loginUser.setLoginTime(null);
		Mockito.when(mapper.login(Mockito.eq(Realm.ADMIN), Mockito.eq("code"),
				Mockito.eq("password"))).thenReturn(loginUser);

		var entity = UserTest.create(now);
		var view = repository.login(entity);
		Assertions.assertEquals("ip", view.getIp());
		Assertions.assertEquals(now, view.getLoginTime());

		var userCaptor = ArgumentCaptor.forClass(User.class);
		Mockito.verify(mapper).updateOne(userCaptor.capture());
		var updateUser = userCaptor.getValue();
		Assertions.assertEquals(loginUser.getId(), updateUser.getId());
		Assertions.assertEquals("ip", updateUser.getIp());
		Assertions.assertEquals(now, updateUser.getLoginTime());

		var logCaptor = ArgumentCaptor.forClass(UserLoginLog.class);
		Mockito.verify(logMapper).insertOrUpdate(logCaptor.capture());
		var log = logCaptor.getValue();
		Assertions.assertEquals(loginUser.getId(), log.getUserId());
		Assertions.assertEquals("ip", log.getIp());
	}

	@Test
	void testLogin1() {
		var now = DateUtil.now();
		var entity = UserTest.create(now);
		var view = repository.login(entity);
		Assertions.assertNull(view);

		Mockito.verify(mapper, Mockito.never()).updateOne(Mockito.any());
		Mockito.verify(logMapper, Mockito.never()).insertOrUpdate(Mockito.any());
	}

	@Test
	void testAddOne() {
		var user = UserTest.create(DateUtil.now());
		Mockito.when(mapper.insertOne(Mockito.any())).thenReturn(1);
		Mockito.when(mapper.selectOne(Mockito.anyLong())).thenReturn(user);
		var view = repository.addOne(user);
		Assertions.assertSame(user, view);
	}

	@Test
	void testAddOne1() {
		var user = UserTest.create(DateUtil.now());
		Mockito.when(mapper.insertOne(Mockito.any())).thenReturn(0);
		var view = repository.addOne(user);
		Assertions.assertNull(view);
		Mockito.verify(mapper, Mockito.never()).selectOne(Mockito.anyLong());
	}

	@Test
	void testUpdateOne() {
		var user = UserTest.create(DateUtil.now());
		Mockito.when(mapper.selectOne(Mockito.anyLong())).thenReturn(user);
		var view = repository.updateOne(user);
		Assertions.assertSame(user, view);
		Mockito.verify(mapper).updateOne(Mockito.same(user));
	}

	@Test
	void testGetOne() {
		var user = UserTest.create(DateUtil.now());
		Mockito.when(mapper.selectOne(Mockito.anyLong())).thenReturn(user);
		var view = repository.getOne(1L);
		Assertions.assertSame(user, view);
	}

	@Test
	void testGetList() {
		var user = UserTest.create(DateUtil.now());
		Mockito.when(mapper.selectList(Mockito.anyList())).thenReturn(List.of(user));
		var viewList = repository.getList(List.of(1L));
		Assertions.assertEquals(1, viewList.size());
		Assertions.assertSame(user, viewList.getFirst());
	}

	@Test
	void testDeleteOne() {
		Mockito.when(mapper.deleteOne(Mockito.anyLong())).thenReturn(1);
		var view = repository.deleteOne(1L);
		Assertions.assertSame(1, view);
	}

	@Test
	void testDeleteList() {
		Mockito.when(mapper.deleteList(Mockito.anyList())).thenReturn(1);
		var view = repository.deleteList(List.of(1L));
		Assertions.assertSame(1, view);
	}
}
