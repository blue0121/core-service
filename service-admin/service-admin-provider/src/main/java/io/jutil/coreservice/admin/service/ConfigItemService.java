package io.jutil.coreservice.admin.service;

import io.jutil.coreservice.admin.entity.ConfigItem;
import io.jutil.coreservice.admin.repository.ConfigItemRepository;
import io.jutil.coreservice.core.entity.DeleteListTenantAuditEntity;
import io.jutil.coreservice.core.entity.DeleteTenantAuditEntity;
import io.jutil.coreservice.core.util.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author Jin Zheng
 * @since 2025-10-09
 */
@Component
public class ConfigItemService {
	@Autowired
	ConfigItemRepository configItemRepository;

	public ConfigItem addOne(ConfigItem entity) {
		return configItemRepository.addOne(entity);
	}

	public ConfigItem updateOne(ConfigItem entity) {
		return configItemRepository.updateOne(entity);
	}

	public int deleteOne(DeleteTenantAuditEntity entity) {
		return configItemRepository.deleteOne(entity);
	}

	public int deleteList(DeleteListTenantAuditEntity entity) {
		return configItemRepository.deleteList(entity);
	}

	public List<ConfigItem> listByConfigId(long tenantId, long configId) {
		return configItemRepository.listByConfigId(tenantId, configId);
	}

	public Map<Long, List<ConfigItem>> listByConfigIdList(long tenantId, Collection<Long> configIdList) {
		var itemList = configItemRepository.listByConfigIdList(tenantId, configIdList);
		return ListUtil.groupMap(itemList, ConfigItem::getConfigId);
	}
}
