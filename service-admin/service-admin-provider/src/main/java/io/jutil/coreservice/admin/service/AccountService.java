package io.jutil.coreservice.admin.service;

import io.jutil.coreservice.admin.entity.Account;
import io.jutil.coreservice.admin.entity.AccountSearch;
import io.jutil.coreservice.admin.repository.AccountRepository;
import io.jutil.coreservice.core.entity.DeleteListTenantAuditEntity;
import io.jutil.coreservice.core.entity.DeleteTenantAuditEntity;
import io.jutil.coreservice.core.util.ListUtil;
import io.jutil.springeasy.core.collection.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;

/**
 * @author Jin Zheng
 * @since 2025-09-17
 */
@Component
public class AccountService {
	@Autowired
	AccountRepository accountRepository;

	public Account addOne(Account entity) {
		return accountRepository.addOne(entity);
	}

	public Account updateOne(Account entity) {
		return accountRepository.updateOne(entity);
	}

	public Account getOne(long tenantId, long id) {
		return accountRepository.getOne(tenantId, id);
	}

	public Map<Long, Account> getList(long tenantId, Collection<Long> idList) {
		var list = accountRepository.getList(tenantId, idList);
		return ListUtil.toMap(list);
	}

	public int deleteOne(DeleteTenantAuditEntity entity) {
		return accountRepository.deleteOne(entity.getTenantId(), entity.getId(), entity.getOperatorId());
	}

	public int deleteList(DeleteListTenantAuditEntity entity) {
		return accountRepository.deleteList(entity.getTenantId(), entity.getIdList(), entity.getOperatorId());
	}

	public Page search(AccountSearch search, Page page) {
		return accountRepository.search(search, page);
	}
}
