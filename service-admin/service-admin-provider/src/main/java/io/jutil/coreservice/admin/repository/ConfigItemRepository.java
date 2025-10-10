package io.jutil.coreservice.admin.repository;

import io.jutil.coreservice.admin.dao.ConfigItemMapper;
import io.jutil.coreservice.admin.entity.ConfigItem;
import io.jutil.coreservice.core.entity.DeleteListTenantAuditEntity;
import io.jutil.coreservice.core.entity.DeleteTenantAuditEntity;
import io.jutil.coreservice.core.facade.AuditLogFacade;
import io.jutil.coreservice.core.repository.AuditLogRepository;
import io.jutil.springeasy.mybatis.id.LongIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * @author Jin Zheng
 * @since 2025-10-09
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ConfigItemRepository {
	@Autowired
	ConfigItemMapper configItemMapper;

	@Autowired
	AuditLogRepository auditLogRepository;

	public ConfigItem addOne(ConfigItem entity) {
		entity.setId(LongIdGenerator.nextId());
		configItemMapper.insertOne(entity);
		auditLogRepository.addOperation(AuditLogFacade.Business.CONFIG_ITEM, entity.getTenantId(),
				entity.getId(), entity.getOperatorId(), entity);
		return this.getOne(entity.getTenantId(), entity.getId());
	}

	public ConfigItem updateOne(ConfigItem entity) {
		configItemMapper.updateOne(entity);
		auditLogRepository.updateOperation(AuditLogFacade.Business.CONFIG_ITEM, entity.getTenantId(),
				entity.getId(), entity.getOperatorId(), entity);
		return this.getOne(entity.getTenantId(), entity.getId());
	}

	public ConfigItem getOne(long tenantId, long id) {
		return configItemMapper.selectOne(tenantId, id);
	}

	public int deleteOne(DeleteTenantAuditEntity entity) {
		var count = configItemMapper.deleteOne(entity.getTenantId(), entity.getId());
		if (count == 0) {
			return 0;
		}
		auditLogRepository.deleteOperation(AuditLogFacade.Business.CONFIG_ITEM,
				entity.getTenantId(), entity.getId(), entity.getOperatorId());
		return count;
	}

	public int deleteList(DeleteListTenantAuditEntity entity) {
		var count = configItemMapper.deleteList(entity.getTenantId(), entity.getIdList());
		if (count == 0) {
			return 0;
		}
		auditLogRepository.deleteListOperation(AuditLogFacade.Business.CONFIG_ITEM,
				entity.getTenantId(), entity.getIdList(), entity.getOperatorId());
		return count;
	}

	public List<ConfigItem> listByConfigId(long tenantId, long configId) {
		return configItemMapper.listByConfigId(tenantId, configId);
	}

	public List<ConfigItem> listByConfigIdList(long tenantId, Collection<Long> configIdList) {
		return configItemMapper.listByConfigIdList(tenantId, configIdList);
	}
}
