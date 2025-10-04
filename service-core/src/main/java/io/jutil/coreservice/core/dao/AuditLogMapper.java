package io.jutil.coreservice.core.dao;

import io.jutil.coreservice.core.entity.AuditLog;
import io.jutil.coreservice.core.entity.AuditLogSearch;
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
