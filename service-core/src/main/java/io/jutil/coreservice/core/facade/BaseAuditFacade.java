package io.jutil.coreservice.core.facade;

import io.jutil.coreservice.core.model.AuditLogSearchRequest;
import io.jutil.coreservice.core.model.PageResponse;

import java.util.Collection;
import java.util.Map;

/**
 * @author Jin Zheng
 * @since 2025-09-11
 */
public interface BaseAuditFacade<T, R, S> {

	R getOne(long id);

	Map<Long, R> getList(Collection<Long> idList);

	R addOne(T request);

	R updateOne(T request);

	int deleteOne(long id, long operatorId);

	int deleteList(Collection<Long> idList, long operatorId);

	PageResponse search(S searchRequest);

	PageResponse searchAuditLog(AuditLogSearchRequest searchRequest);

}
