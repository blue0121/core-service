package io.jutil.coreservice.admin.repository;

import io.jutil.coreservice.admin.dao.ConfigMapper;
import io.jutil.coreservice.admin.entity.Config;
import io.jutil.coreservice.core.entity.DeleteListTenantAuditEntity;
import io.jutil.coreservice.core.entity.DeleteTenantAuditEntity;
import io.jutil.coreservice.core.facade.AuditLogFacade;
import io.jutil.coreservice.core.repository.AuditLogRepository;
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
public class ConfigRepository {
	public static final Map<String, String> SORT_FILED_MAP =
			Map.of("id", "e.id",
					"createTime", "e.create_time",
					"updateTime", "e.update_time");

	@Autowired
	ConfigMapper configMapper;

	@Autowired
	AuditLogRepository auditLogRepository;

	public Config addOne(Config entity) {
		entity.setId(LongIdGenerator.nextId());
		var count = configMapper.insertOne(entity);
		if (count == 0) {
			return null;
		}
		auditLogRepository.addOperation(AuditLogFacade.Business.CONFIG, entity.getTenantId(),
				entity.getId(), entity.getOperatorId(), entity);
		return this.getOne(entity.getTenantId(), entity.getId());
	}

	public Config updateOne(Config entity) {
		var count = configMapper.updateOne(entity);
		if (count == 0) {
			return null;
		}
		auditLogRepository.updateOperation(AuditLogFacade.Business.CONFIG, entity.getTenantId(),
				entity.getId(), entity.getOperatorId(), entity);
		return this.getOne(entity.getTenantId(), entity.getId());
	}

	public Config getOne(long tenantId, long id) {
		return configMapper.selectOne(tenantId, id);
	}

	public List<Config> getList(long tenantId, Collection<Long> idList) {
		return configMapper.selectList(tenantId, idList);
	}

	public int deleteOne(DeleteTenantAuditEntity entity) {
		var count = configMapper.deleteOne(entity.getTenantId(), entity.getId());
		if (count == 0) {
			return 0;
		}
		auditLogRepository.deleteOperation(AuditLogFacade.Business.CONFIG,
				entity.getTenantId(), entity.getId(), entity.getOperatorId());
		return count;
	}

	public int deleteList(DeleteListTenantAuditEntity entity) {
		var count = configMapper.deleteList(entity.getTenantId(), entity.getIdList());
		if (count == 0) {
			return 0;
		}
		auditLogRepository.deleteListOperation(AuditLogFacade.Business.CONFIG,
				entity.getTenantId(), entity.getIdList(), entity.getOperatorId());
		return count;
	}

	public Page search(Config.Search search, Page page) {
		return PageableRepository.search(configMapper, SORT_FILED_MAP, search, page);
	}
}
