package io.jutil.coreservice.core.facade;

import io.jutil.coreservice.core.model.AuditLogSearchRequest;
import io.jutil.coreservice.core.model.DeleteListTenantAuditRequest;
import io.jutil.coreservice.core.model.DeleteTenantAuditRequest;
import io.jutil.coreservice.core.model.PageResponse;

import java.util.Collection;
import java.util.Map;

/**
 * @author Jin Zheng
 * @since 2025-09-11
 */
public interface BaseTenantAuditFacade<T, R, S> {

	R getOne(long tenantId, long id);

	Map<Long, R> getList(long tenantId, Collection<Long> idList);

	R addOne(T request);

	R updateOne(T request);

	int deleteOne(DeleteTenantAuditRequest request);

	int deleteList(DeleteListTenantAuditRequest request);

	PageResponse search(S searchRequest);

	PageResponse searchAuditLog(AuditLogSearchRequest searchRequest);

}
