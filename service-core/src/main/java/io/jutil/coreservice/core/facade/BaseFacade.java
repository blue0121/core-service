package io.jutil.coreservice.core.facade;

import java.util.Collection;
import java.util.Map;

/**
 * @author Jin Zheng
 * @since 2025-08-15
 */
public interface BaseFacade<T, R> {

	R getOne(long id);

	Map<Long, R> getList(Collection<Long> idList);

	R addOne(T request);

	R updateOne(T request);

	int deleteOne(long id);

	int deleteList(Collection<Long> idList);

}
