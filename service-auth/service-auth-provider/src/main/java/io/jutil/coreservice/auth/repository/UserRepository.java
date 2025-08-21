package io.jutil.coreservice.auth.repository;

import io.jutil.coreservice.auth.dao.UserLoginLogMapper;
import io.jutil.coreservice.auth.dao.UserMapper;
import io.jutil.coreservice.auth.entity.User;
import io.jutil.coreservice.auth.entity.UserLoginLog;
import io.jutil.springeasy.core.util.DateUtil;
import io.jutil.springeasy.mybatis.id.LongIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * @author Jin Zheng
 * @since 2025-08-19
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserRepository {
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
		updateUser.setIp(loginUser.getIp());
		updateUser.setLoginTime(loginUser.getLoginTime());
		userMapper.updateOne(updateUser);

		var userLoginLog = new UserLoginLog();
		userLoginLog.setId(LongIdGenerator.nextId());
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
		return this.getOne(entity.getId());
	}

	public User updateOne(User entity) {
		userMapper.updateOne(entity);
		return this.getOne(entity.getId());
	}

	public User getOne(long id) {
		return userMapper.selectOne(id);
	}

	public List<User> getList(Collection<Long> idList) {
		return userMapper.selectList(idList);
	}

	public int deleteOne(long id) {
		return userMapper.deleteOne(id);
	}

	public int deleteList(Collection<Long> idList) {
		return userMapper.deleteList(idList);
	}
}
