package io.jutil.coreservice.core.facade;

import io.jutil.coreservice.core.model.PageResponse;

import java.util.Collection;
import java.util.Map;

/**
 * @author Jin Zheng
 * @since 2025-08-15
 */
public interface BaseTenantFacade<T, R, S> {

	R getOne(long tenantId, long id);

	Map<Long, R> getList(long tenantId, Collection<Long> idList);

	R addOne(T request);

	R updateOne(T request);

	int deleteOne(long tenantId, long id);

	int deleteList(long tenantId, Collection<Long> idList);

	PageResponse search(S searchRequest);

}
