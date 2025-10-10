package io.jutil.coreservice.admin.dao;

import io.jutil.coreservice.admin.entity.ConfigItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * @author Jin Zheng
 * @since 2025-10-07
 */
@Mapper
public interface ConfigItemMapper {

	ConfigItem selectOne(@Param("tenantId") long tenantId, @Param("id") long id);

	int insertOne(@Param("entity") ConfigItem entity);

	int updateOne(@Param("entity") ConfigItem entity);

	int deleteOne(@Param("tenantId") long tenantId, @Param("id") long id);

	int deleteList(@Param("tenantId") long tenantId, @Param("idList") Collection<Long> idList);

	int deleteByConfigId(@Param("tenantId") long tenantId, @Param("configId") long configId);

	int deleteByConfigIdList(@Param("tenantId") long tenantId,
	                         @Param("configIdList") Collection<Long> configIdList);

	List<ConfigItem> listByConfigId(@Param("tenantId") long tenantId, @Param("configId") long configId);

	List<ConfigItem> listByConfigIdList(@Param("tenantId") long tenantId,
	                                    @Param("configIdList") Collection<Long> configIdList);

}
