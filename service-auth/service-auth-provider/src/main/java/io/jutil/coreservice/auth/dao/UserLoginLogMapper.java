package io.jutil.coreservice.auth.dao;

import io.jutil.coreservice.auth.entity.UserLoginLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author Jin Zheng
 * @since 2025-08-19
 */
@Mapper
public interface UserLoginLogMapper {

	UserLoginLog selectOne(@Param("id") long id);

	int insertOrUpdate(@Param("entity") UserLoginLog entity);

}
