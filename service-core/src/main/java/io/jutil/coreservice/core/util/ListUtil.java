package io.jutil.coreservice.core.util;

import io.jutil.springeasy.mybatis.entity.LongIdEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @author Jin Zheng
 * @since 2025-09-17
 */
public class ListUtil {
	private ListUtil() {}

	public static <T extends LongIdEntity> Map<Long, T> toMap(List<T> entityList) {
		Map<Long, T> map = new HashMap<>();
		if (entityList == null || entityList.isEmpty()) {
			return map;
		}
		for (T entity : entityList) {
			if (entity != null) {
				 map.put(entity.getId(), entity);
			}
		}
		return map;
	}

	public static <T extends LongIdEntity> Map<Long, List<T>> groupMap(List<T> entityList,
	                                                                   Function<T, Long> f) {
		Map<Long, List<T>> map = new HashMap<>();
		if (entityList == null || entityList.isEmpty()) {
			return map;
		}
		for (T entity : entityList) {
			if (entity == null) {
				continue;
			}
			var key = f.apply(entity);
			var list = map.computeIfAbsent(key, k -> new ArrayList<>());
			list.add(entity);
		}
		return map;
	}
}
