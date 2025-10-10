package io.jutil.coreservice.admin.service;

import io.jutil.coreservice.admin.entity.Config;
import io.jutil.coreservice.admin.repository.ConfigRepository;
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
 * @since 2025-10-09
 */
@Component
public class ConfigService {
	@Autowired
	ConfigRepository configRepository;

	public Config addOne(Config entity) {
		return configRepository.addOne(entity);
	}

	public Config updateOne(Config entity) {
		return configRepository.updateOne(entity);
	}

	public Config getOne(long tenantId, long id) {
		return configRepository.getOne(tenantId, id);
	}

	public Map<Long, Config> getList(long tenantId, Collection<Long> idList) {
		var list = configRepository.getList(tenantId, idList);
		return ListUtil.toMap(list);
	}

	public int deleteOne(DeleteTenantAuditEntity entity) {
		return configRepository.deleteOne(entity);
	}

	public int deleteList(DeleteListTenantAuditEntity entity) {
		return configRepository.deleteList(entity);
	}

	public Page search(Config.Search search, Page page) {
		return configRepository.search(search, page);
	}
}
