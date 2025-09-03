package io.jutil.coreservice.auth.repository;

import io.jutil.coreservice.auth.dao.UserLoginLogMapper;
import io.jutil.coreservice.auth.dao.UserMapper;
import io.jutil.coreservice.auth.entity.User;
import io.jutil.coreservice.auth.entity.UserLoginLog;
import io.jutil.coreservice.auth.entity.UserSearch;
import io.jutil.coreservice.core.dict.Realm;
import io.jutil.coreservice.core.entity.Pageable;
import io.jutil.springeasy.core.collection.Page;
import io.jutil.springeasy.core.collection.Sort;
import io.jutil.springeasy.core.util.DateUtil;
import io.jutil.springeasy.mybatis.id.LongIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author Jin Zheng
 * @since 2025-08-19
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserRepository {
	public static final Map<String, String> SORT_FILED_MAP =
			Map.of("id", "e.id",
					"createTime", "e.create_time",
					"updateTime", "e.update_time");

	@Autowired
	UserMapper userMapper;

	@Autowired
	UserLoginLogMapper userLoginLogMapper;

	public User login(User entity) {
		var loginUser = userMapper.login(entity.getRealm(), entity.getCode(), entity.getPassword());
		if (loginUser == null) {
			return null;
		}
		loginUser.setIp(entity.getIp());
		loginUser.setLoginTime(DateUtil.now());

		var updateUser = new User();
		updateUser.setId(loginUser.getId());
		updateUser.setRealm(entity.getRealm());
		updateUser.setIp(loginUser.getIp());
		updateUser.setLoginTime(loginUser.getLoginTime());
		userMapper.updateOne(updateUser);

		var userLoginLog = new UserLoginLog();
		userLoginLog.setId(LongIdGenerator.nextId());
		userLoginLog.setRealm(entity.getRealm());
		userLoginLog.setUserId(loginUser.getId());
		userLoginLog.setIp(loginUser.getIp());
		userLoginLogMapper.insertOrUpdate(userLoginLog);

		return loginUser;
	}

	public User addOne(User entity) {
		entity.setId(LongIdGenerator.nextId());
		var count = userMapper.insertOne(entity);
		if (count == 0) {
			return null;
		}
		return this.getOne(entity.getRealm(), entity.getId());
	}

	public User updateOne(User entity) {
		userMapper.updateOne(entity);
		return this.getOne(entity.getRealm(), entity.getId());
	}

	public User getOne(Realm realm, long id) {
		return userMapper.selectOne(realm, id);
	}

	public List<User> getList(Realm realm, Collection<Long> idList) {
		return userMapper.selectList(realm, idList);
	}

	public int deleteOne(Realm realm, long id) {
		return userMapper.deleteOne(realm, id);
	}

	public int deleteList(Realm realm, Collection<Long> idList) {
		return userMapper.deleteList(realm, idList);
	}

	public Page search(UserSearch search, Page page) {
		var count = userMapper.countPage(search);
		if (count == 0) {
			return page;
		}
		page.setTotal(count);
		page.setSortIfAbsent(() -> new Sort("id"));
		var pageable = Pageable.from(page);
		pageable.generateOrderBy(SORT_FILED_MAP);

		var list = userMapper.listPage(search, pageable);
		page.setContents(list);
		return page;
	}
}
