package io.jutil.coreservice.admin.service;

import io.jutil.coreservice.admin.entity.Tenant;
import io.jutil.coreservice.admin.entity.TenantSearch;
import io.jutil.coreservice.admin.repository.TenantRepository;
import io.jutil.springeasy.core.collection.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Jin Zheng
 * @since 2025-09-12
 */
@Component
public class TenantService {
	@Autowired
	TenantRepository tenantRepository;

	public Tenant getOne(long id) {
		return tenantRepository.getOne(id);
	}

	public Map<Long, Tenant> getList(Collection<Long> idList) {
		var list = tenantRepository.getList(idList);
		Map<Long, Tenant> map = new HashMap<>();
		for (var entity : list) {
			map.put(entity.getId(), entity);
		}
		return map;
	}

	public Tenant addOne(Tenant entity) {
		return tenantRepository.addOne(entity);
	}

	public Tenant updateOne(Tenant entity) {
		return tenantRepository.updateOne(entity);
	}

	public int deleteOne(long id, long operatorId) {
		return tenantRepository.deleteOne(id, operatorId);
	}

	public int deleteList(Collection<Long> idList, long operatorId) {
		return tenantRepository.deleteList(idList, operatorId);
	}

	public Page search(TenantSearch search, Page page) {
		return tenantRepository.search(search, page);
	}
}
