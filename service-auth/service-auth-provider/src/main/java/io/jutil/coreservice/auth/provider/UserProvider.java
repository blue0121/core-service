package io.jutil.coreservice.auth.provider;

import io.jutil.coreservice.auth.convertor.UserConvertor;
import io.jutil.coreservice.auth.facade.UserFacade;
import io.jutil.coreservice.auth.model.UserRequest;
import io.jutil.coreservice.auth.model.UserResponse;
import io.jutil.coreservice.auth.service.UserService;
import io.jutil.coreservice.core.util.AssertUtil;
import io.jutil.springeasy.core.validation.ValidationUtil;
import io.jutil.springeasy.core.validation.group.AddOperation;
import io.jutil.springeasy.core.validation.group.UpdateOperation;
import io.jutil.springeasy.spring.exception.BaseErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;

/**
 * @author Jin Zheng
 * @since 2025-08-15
 */
@Component
public class UserProvider implements UserFacade {
	@Autowired
	UserService userService;

	@Override
	public UserResponse getOne(long id) {
		AssertUtil.validId(id, "ID");
		var entity = userService.getOne(id);
		return UserConvertor.toResponse(entity);
	}

	@Override
	public Map<Long, UserResponse> getList(Collection<Long> idList) {
		AssertUtil.validIdList(idList, "ID列表");
		var map = userService.getList(idList);
		return UserConvertor.toMapResponse(map);
	}

	@Override
	public UserResponse addOne(UserRequest request) {
		ValidationUtil.valid(request, AddOperation.class);
		var entity = UserConvertor.toEntity(request);
		entity = userService.addOne(entity);
		if (entity == null) {
			throw BaseErrorCode.EXISTS.newException(request.getCode());
		}
		return UserConvertor.toResponse(entity);
	}

	@Override
	public UserResponse updateOne(UserRequest request) {
		ValidationUtil.valid(request, UpdateOperation.class);
		if (request.getPassword() != null && !request.getPassword().isEmpty()) {
			if (request.getOldPassword() == null || request.getOldPassword().isEmpty()) {
				throw BaseErrorCode.INVALID_PARAM.newException("旧密码不能为空");
			}
		}
		var entity = UserConvertor.toEntity(request);
		entity = userService.updateOne(entity);
		return UserConvertor.toResponse(entity);
	}

	@Override
	public int deleteOne(long id) {
		AssertUtil.validId(id, "ID");
		return userService.deleteOne(id);
	}

	@Override
	public int deleteList(Collection<Long> idList) {
		AssertUtil.validIdList(idList, "ID列表");
		return userService.deleteList(idList);
	}
}
