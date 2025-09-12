package io.jutil.coreservice.admin.repository;

import io.jutil.coreservice.admin.dao.TenantMapper;
import io.jutil.coreservice.admin.entity.Tenant;
import io.jutil.coreservice.admin.entity.TenantSearch;
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
 * @since 2025-08-19
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TenantRepository {
	public static final Map<String, String> SORT_FILED_MAP =
			Map.of("id", "e.id",
					"createTime", "e.create_time",
					"updateTime", "e.update_time");

	@Autowired
	TenantMapper tenantMapper;

	@Autowired
	AuditLogRepository auditLogRepository;


	public Tenant addOne(Tenant entity) {
		entity.setId(LongIdGenerator.nextId());
		var count = tenantMapper.insertOne(entity);
		if (count == 0) {
			return null;
		}
		auditLogRepository.addOperation(BusinessType.TENANT, entity.getId(), entity.getId(),
				entity.getOperatorId(), entity);
		return this.getOne(entity.getId());
	}

	public Tenant updateOne(Tenant entity) {
		var count = tenantMapper.updateOne(entity);
		if (count == 0) {
			return null;
		}
		auditLogRepository.updateOperation(BusinessType.TENANT, entity.getId(), entity.getId(),
				entity.getOperatorId(), entity);
		return this.getOne(entity.getId());
	}

	public Tenant getOne(long id) {
		return tenantMapper.selectOne(id);
	}

	public List<Tenant> getList(Collection<Long> idList) {
		return tenantMapper.selectList(idList);
	}

	public int deleteOne(long id, long operatorId) {
		var count = tenantMapper.deleteOne(id);
		if (count == 0) {
			return 0;
		}
		auditLogRepository.deleteOperation(BusinessType.TENANT, id, id, operatorId);
		return count;
	}

	public int deleteList(Collection<Long> idList, long operatorId) {
		var count = tenantMapper.deleteList(idList);
		if (count == 0) {
			return 0;
		}
		var tenantId = idList.iterator().next();
		auditLogRepository.deleteListOperation(BusinessType.TENANT, tenantId, idList, operatorId);
		return count;
	}

	public Page search(TenantSearch search, Page page) {
		return PageableRepository.search(tenantMapper, SORT_FILED_MAP, search, page);
	}
}
