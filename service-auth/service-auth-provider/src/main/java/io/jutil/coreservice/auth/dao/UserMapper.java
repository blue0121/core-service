package io.jutil.coreservice.auth.dao;

import io.jutil.coreservice.auth.entity.User;
import io.jutil.coreservice.auth.entity.UserSearch;
import io.jutil.coreservice.core.dict.Realm;
import io.jutil.coreservice.core.entity.Pageable;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * @author Jin Zheng
 * @since 2025-08-15
 */
@Mapper
public interface UserMapper {

	User selectOne(@Param("realm") Realm realm,
	               @Param("id") long id);

	List<User> selectList(@Param("realm") Realm realm,
	                      @Param("idList") Collection<Long> idList);

	int insertOne(@Param("entity") User entity);

	int updateOne(@Param("entity") User entity);

	int deleteOne(@Param("realm") Realm realm,
	              @Param("id") long id);

	int deleteList(@Param("realm") Realm realm,
	               @Param("idList") Collection<Long> idList);

	User login(@Param("realm") Realm realm,
	           @Param("code") String code,
	           @Param("password") String password);

	int countPage(@Param("search") UserSearch search);

	List<User> listPage(@Param("search") UserSearch search,
	                    @Param("pageable") Pageable pageable);

}
