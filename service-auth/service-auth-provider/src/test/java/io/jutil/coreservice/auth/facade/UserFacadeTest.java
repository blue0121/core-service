package io.jutil.coreservice.auth.facade;

import io.jutil.coreservice.auth.entity.PageTest;
import io.jutil.coreservice.auth.entity.SearchRequestTest;
import io.jutil.coreservice.auth.entity.TokenTest;
import io.jutil.coreservice.auth.entity.UserLoginLogTest;
import io.jutil.coreservice.auth.entity.UserTest;
import io.jutil.coreservice.auth.model.UserLoginLogResponse;
import io.jutil.coreservice.auth.model.UserLoginLogSearchRequest;
import io.jutil.coreservice.auth.model.UserRequest;
import io.jutil.coreservice.auth.model.UserResponse;
import io.jutil.coreservice.auth.model.UserSearchRequest;
import io.jutil.coreservice.auth.util.PublicKeyCache;
import io.jutil.coreservice.core.dict.Realm;
import io.jutil.springeasy.core.collection.Sort;
import io.jutil.springeasy.core.security.TokenException;
import io.jutil.springeasy.spring.exception.ErrorCodeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Jin Zheng
 * @since 2025-09-02
 */
public abstract class UserFacadeTest {
	@Autowired
	UserFacade userFacade;

	@Autowired
	UserLoginLogFacade userLoginLogFacade;

	@Autowired
	TokenFacade tokenFacade;

	@Autowired
	SecurityFacade securityFacade;

	@Autowired
	JdbcTemplate jdbcTemplate;

	PublicKeyCache publicKeyCache;

	@BeforeEach
	public void beforeEach() {
		jdbcTemplate.update("TRUNCATE TABLE auth_user");
		jdbcTemplate.update("TRUNCATE TABLE auth_user_login_log");
		this.publicKeyCache = new PublicKeyCache(securityFacade);
	}

	@Test
	public void testUpdateUser() {
		var response = this.createUserAndVerify();

		var request = new UserRequest();
		request.setId(response.getId());
		request.setRealm(response.getRealm());
		request.setName("test");

		var view = userFacade.updateOne(request);
		UserTest.verify(view, response.getId(), 1, "code",
				"test", 0, "remarks", "extension");
	}

	@Test
	void testGetUserList() {
		var response = this.createUserAndVerify();
		var response2 = this.createUserAndVerify2();

		this.searchUserAndVerify(2, 1);

		var responseMap = userFacade.getList(Realm.ADMIN, List.of(response.getId(), response2.getId()));
		var view1 = responseMap.get(response.getId());
		UserTest.verify(view1, response.getId(), 1, "code",
				"name", 0, "remarks", "extension");

		var view2 = responseMap.get(response2.getId());
		UserTest.verify(view2, response2.getId(), 1, "code2",
				"name2", 0, "remarks", "extension");
	}

	@Test
	public void testDeleteUser() {
		var response = this.createUserAndVerify();

		Assertions.assertEquals(1, userFacade.deleteOne(Realm.ADMIN, response.getId()));

		this.searchUserAndVerify(0, 0);
	}

	@Test
	public void testDeleteUserList() {
		var response = this.createUserAndVerify();
		var response2 = this.createUserAndVerify2();

		Assertions.assertEquals(2, userFacade.deleteList(Realm.ADMIN,
				List.of(response.getId(), response2.getId())));

		this.searchUserAndVerify(0, 0);
	}

	@Test
	public void testCreateUser() {
		this.createUserAndVerify();
		var request = UserTest.createRequest();
		Assertions.assertThrows(ErrorCodeException.class, () -> userFacade.addOne(request));
	}

	@Test
	void testLogin() {
		this.createUserAndVerify();
		this.searchUserAndVerify(1, 1);

		var request = TokenTest.createRequest();

		var token = tokenFacade.create(request);
		Assertions.assertNotNull(token);
		Assertions.assertEquals("name", token.getName());
		var session = publicKeyCache.createAndVerify(token.getToken());
		Assertions.assertNotNull(session);
		Assertions.assertEquals("extension", new String(session.getExtension()));

		var userList = this.searchUserAndVerify(1, 1);
		Assertions.assertEquals("ip", userList.getFirst().getIp());
		Assertions.assertNotNull(userList.getFirst().getLoginTime());

		var logList = this.searchUserLoginLogAndVerify(1, 1);
		Assertions.assertEquals(1, logList.size());
		UserLoginLogTest.verify(logList.getFirst(), 1, session.getId(), "ip",
				LocalDate.now(), 1, "code", "name", 0);

		tokenFacade.create(request);

		logList = this.searchUserLoginLogAndVerify(1, 1);
		Assertions.assertEquals(1, logList.size());
		UserLoginLogTest.verify(logList.getFirst(), 1, session.getId(), "ip",
				LocalDate.now(), 2, "code", "name", 0);
	}

	@Test
	void testLogin_failed() {
		this.createUserAndVerify();
		this.searchUserAndVerify(1, 1);

		var request = TokenTest.createRequest();
		request.setPassword("failed");
		Assertions.assertThrows(ErrorCodeException.class, () -> tokenFacade.create(request));
	}

	@Test
	void testTokenRefresh() {
		this.createUserAndVerify();
		this.searchUserAndVerify(1, 1);

		var request = TokenTest.createRequest();

		var token = tokenFacade.create(request);

		var token2 = tokenFacade.refresh(token.getToken());
		Assertions.assertNotNull(token2);
		Assertions.assertEquals(token.getName(), token2.getName());
	}

	@Test
	void testTokenRefresh_failed() {
		this.createUserAndVerify();
		this.searchUserAndVerify(1, 1);

		var request = TokenTest.createRequest();

		var token = tokenFacade.create(request);

		Assertions.assertThrows(TokenException.class, () -> tokenFacade.refresh("abc"));
	}

	private UserResponse createUserAndVerify() {
		var request = UserTest.createRequest();
		var response = userFacade.addOne(request);
		var view = userFacade.getOne(Realm.ADMIN, response.getId());

		UserTest.verify(view, response.getId(), 1, "code",
				"name", 0, "remarks", "extension");
		return response;
	}

	private UserResponse createUserAndVerify2() {
		var request = UserTest.createRequest();
		request.setCode("code2");
		request.setName("name2");
		request.setPassword("password2");
		var response = userFacade.addOne(request);
		var view = userFacade.getOne(Realm.ADMIN, response.getId());

		UserTest.verify(view, response.getId(), 1, "code2",
				"name2", 0, "remarks", "extension");
		return response;
	}

	private List<UserResponse> searchUserAndVerify(int total, int totalPage) {
		var request = new UserSearchRequest();
		request.getFilter().setRealm(Realm.ADMIN);
		SearchRequestTest.setPage(request, "id", Sort.Direction.DESC);
		var response = userFacade.search(request);
		PageTest.verify(response, 1, 10, total, totalPage);
		List<UserResponse> list = response.getContents();
		Assertions.assertEquals(total, list.size());
		return list;
	}

	private List<UserLoginLogResponse> searchUserLoginLogAndVerify(int total, int totalPage) {
		var request = new UserLoginLogSearchRequest();
		request.getFilter().setRealm(Realm.ADMIN);
		SearchRequestTest.setPage(request, "id", Sort.Direction.DESC);
		var response = userLoginLogFacade.search(request);
		PageTest.verify(response, 1, 10, total, totalPage);
		List<UserLoginLogResponse> list = response.getContents();
		Assertions.assertEquals(totalPage, list.size());
		return list;
	}
}
