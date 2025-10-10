package io.jutil.coreservice.admin.facade;

import io.jutil.coreservice.admin.model.ConfigItemRequest;
import io.jutil.coreservice.admin.model.ConfigItemResponse;
import io.jutil.coreservice.core.model.DeleteListTenantAuditRequest;
import io.jutil.coreservice.core.model.DeleteTenantAuditRequest;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author Jin Zheng
 * @since 2025-10-09
 */
public interface ConfigItemFacade {

	ConfigItemResponse addOne(ConfigItemRequest request);

	ConfigItemResponse updateOne(ConfigItemRequest request);

	int deleteOne(DeleteTenantAuditRequest request);

	int deleteList(DeleteListTenantAuditRequest request);

	List<ConfigItemResponse> listByConfigId(long tenantId, long configId);

	Map<Long, List<ConfigItemResponse>> listByConfigIdList(long tenantId, Collection<Long> configIdList);
}
