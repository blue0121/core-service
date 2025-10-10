package io.jutil.coreservice.admin.dao;

import io.jutil.coreservice.admin.entity.Config;
import io.jutil.coreservice.core.dao.PageableMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * @author Jin Zheng
 * @since 2025-10-07
 */
@Mapper
public interface ConfigMapper extends PageableMapper<Config.Search, Config> {

	Config selectOne(@Param("tenantId") long tenantId, @Param("id") long id);

	List<Config> selectList(@Param("tenantId") long tenantId,
	                        @Param("idList") Collection<Long> idList);

	int insertOne(@Param("entity") Config entity);

	int updateOne(@Param("entity") Config entity);

	int deleteOne(@Param("tenantId") long tenantId, @Param("id") long id);

	int deleteList(@Param("tenantId") long tenantId, @Param("idList") Collection<Long> idList);

}
