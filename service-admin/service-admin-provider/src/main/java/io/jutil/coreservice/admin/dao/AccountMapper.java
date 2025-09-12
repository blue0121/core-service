package io.jutil.coreservice.admin.dao;

import io.jutil.coreservice.admin.entity.Account;
import io.jutil.coreservice.admin.entity.AccountSearch;
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
public interface AccountMapper extends PageableMapper<AccountSearch, Account> {

	Account selectOne(@Param("tenantId") long tenantId,
	                  @Param("id") long id);

	List<Account> selectList(@Param("tenantId") long tenantId,
	                         @Param("idList") Collection<Long> idList);

	int insertOne(@Param("entity") Account entity);

	int updateOne(@Param("entity") Account entity);

	int deleteOne(@Param("tenantId") long tenantId,
	              @Param("id") long id);

	int deleteList(@Param("tenantId") long tenantId,
	               @Param("idList") Collection<Long> idList);

}
