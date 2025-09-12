package io.jutil.coreservice.core.dao;

import io.jutil.coreservice.core.entity.Pageable;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Jin Zheng
 * @since 2025-09-11
 */
public interface PageableMapper<S, E> {

	int countPage(@Param("search") S search);

	List<E> listPage(@Param("search") S search, @Param("pageable") Pageable pageable);

}
