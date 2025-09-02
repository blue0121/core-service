package io.jutil.coreservice.auth.dao;

import io.jutil.coreservice.auth.entity.UserLoginLog;
import io.jutil.coreservice.auth.entity.UserLoginLogSearch;
import io.jutil.coreservice.core.entity.Pageable;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Jin Zheng
 * @since 2025-08-19
 */
@Mapper
public interface UserLoginLogMapper {

	UserLoginLog selectOne(@Param("id") long id);

	int insertOrUpdate(@Param("entity") UserLoginLog entity);

	int countPage(@Param("search")UserLoginLogSearch search);

	List<UserLoginLog> listPage(@Param("search") UserLoginLogSearch search,
	                            @Param("pageable") Pageable pageable);

}
