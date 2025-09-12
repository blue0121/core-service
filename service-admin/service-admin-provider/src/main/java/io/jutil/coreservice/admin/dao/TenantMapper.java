package io.jutil.coreservice.admin.dao;

import io.jutil.coreservice.admin.entity.Tenant;
import io.jutil.coreservice.admin.entity.TenantSearch;
import io.jutil.coreservice.core.dao.PageableMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * @author Jin Zheng
 * @since 2025-09-10
 */
@Mapper
public interface TenantMapper extends PageableMapper<TenantSearch, Tenant> {

	Tenant selectOne(@Param("id") long id);

	List<Tenant> selectList(@Param("idList") Collection<Long> idList);

	int insertOne(@Param("entity") Tenant entity);

	int updateOne(@Param("entity") Tenant entity);

	int deleteOne(@Param("id") long id);

	int deleteList(@Param("idList") Collection<Long> idList);

}
