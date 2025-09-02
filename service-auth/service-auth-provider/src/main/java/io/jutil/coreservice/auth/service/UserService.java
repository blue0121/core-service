package io.jutil.coreservice.auth.service;

import io.jutil.coreservice.auth.entity.User;
import io.jutil.coreservice.auth.entity.UserSearch;
import io.jutil.coreservice.auth.repository.UserRepository;
import io.jutil.springeasy.core.collection.Page;
import io.jutil.springeasy.core.security.PasswordUtil;
import io.jutil.springeasy.spring.exception.BaseErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Jin Zheng
 * @since 2025-08-19
 */
@Component
public class UserService {
	@Autowired
	UserRepository userRepository;

	public User getOne(long id) {
		return userRepository.getOne(id);
	}

	public Map<Long, User> getList(Collection<Long> idList) {
		var list = userRepository.getList(idList);
		Map<Long, User> map = new HashMap<>();
		for (var entity : list) {
			map.put(entity.getId(), entity);
		}
		return map;
	}

	public User addOne(User entity) {
		entity.setPassword(PasswordUtil.encrypt(entity.getPassword()));
		return userRepository.addOne(entity);
	}

	public User updateOne(User entity) {
		var view = userRepository.getOne(entity.getId());
		if (view == null) {
			throw BaseErrorCode.NOT_EXISTS.newException("用户不存在");
		}
		if (entity.getPassword() != null && !entity.getPassword().isEmpty()) {
			var oldPassword = PasswordUtil.encrypt(entity.getOldPassword());
			if (!view.getPassword().equals(oldPassword)) {
				throw BaseErrorCode.INVALID_PARAM.newException("旧密码错误");
			}
			entity.setPassword(PasswordUtil.encrypt(entity.getPassword()));
		}
		return userRepository.updateOne(entity);
	}

	public int deleteOne(long id) {
		return userRepository.deleteOne(id);
	}

	public int deleteList(Collection<Long> idList) {
		return userRepository.deleteList(idList);
	}

	public Page search(UserSearch search, Page page) {
		return userRepository.search(search, page);
	}
}
