package io.jutil.coreservice.admin.dao;

import io.jutil.coreservice.admin.entity.AuditLog;
import io.jutil.coreservice.admin.entity.AuditLogSearch;
import io.jutil.coreservice.core.dao.PageableMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author Jin Zheng
 * @since 2025-09-10
 */
@Mapper
public interface AuditLogMapper extends PageableMapper<AuditLogSearch, AuditLog> {

	AuditLog selectOne(@Param("business") String business,
	                   @Param("tenantId") long tenantId,
	                   @Param("id") long id);

	int insertOne(@Param("entity") AuditLog entity);

}
