package io.jutil.coreservice.admin.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * @author Jin Zheng
 * @since 2025-10-02
 */
@Mapper
public interface AccountTenantMapper {

	int insert(@Param("accountId") long accountId, @Param("tenantIdList") Collection<Long> tenantIdList);

	int deleteByAccountId(@Param("accountId") long accountId);

	List<Long> listByAccountId(@Param("accountId") long accountId);

	int deleteByAccountIdList(@Param("accountIdList") Collection<Long> accountIdList);

}
