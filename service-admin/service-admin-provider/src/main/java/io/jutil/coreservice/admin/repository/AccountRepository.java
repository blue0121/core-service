package io.jutil.coreservice.admin.repository;

import io.jutil.coreservice.admin.dao.AccountMapper;
import io.jutil.coreservice.admin.entity.Account;
import io.jutil.coreservice.admin.entity.AccountSearch;
import io.jutil.coreservice.admin.util.BusinessType;
import io.jutil.coreservice.core.repository.PageableRepository;
import io.jutil.springeasy.core.collection.Page;
import io.jutil.springeasy.mybatis.id.LongIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author Jin Zheng
 * @since 2025-09-12
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AccountRepository {
	public static final Map<String, String> SORT_FILED_MAP =
			Map.of("id", "e.id",
					"createTime", "e.create_time",
					"updateTime", "e.update_time");

	@Autowired
	AccountMapper accountMapper;

	@Autowired
	AuditLogRepository auditLogRepository;

	public Account addOne(Account entity) {
		entity.setId(LongIdGenerator.nextId());
		var count = accountMapper.insertOne(entity);
		if (count == 0) {
			return null;
		}
		auditLogRepository.addOperation(BusinessType.ACCOUNT, entity.getTenantId(), entity.getId(),
				entity.getOperatorId(), entity);
		return this.getOne(entity.getTenantId(), entity.getId());
	}

	public Account updateOne(Account entity) {
		var count = accountMapper.updateOne(entity);
		if (count == 0) {
			return null;
		}
		auditLogRepository.updateOperation(BusinessType.ACCOUNT, entity.getTenantId(), entity.getId(),
				entity.getOperatorId(), entity);
		return this.getOne(entity.getTenantId(), entity.getId());
	}

	public Account getOne(long tenantId, long id) {
		return accountMapper.selectOne(tenantId, id);
	}

	public List<Account> getList(long tenantId, Collection<Long> idList) {
		return accountMapper.selectList(tenantId, idList);
	}

	public int deleteOne(long tenantId, long id, long operatorId) {
		var count = accountMapper.deleteOne(tenantId, id);
		if (count == 0) {
			return 0;
		}
		auditLogRepository.deleteOperation(BusinessType.ACCOUNT, tenantId, id, operatorId);
		return count;
	}

	public int deleteList(long tenantId, Collection<Long> idList, long operatorId) {
		var count = accountMapper.deleteList(tenantId, idList);
		if (count == 0) {
			return 0;
		}
		auditLogRepository.deleteListOperation(BusinessType.ACCOUNT, tenantId, idList, operatorId);
		return count;
	}

	public Page search(AccountSearch search, Page page) {
		return PageableRepository.search(accountMapper, SORT_FILED_MAP, search, page);
	}
}
