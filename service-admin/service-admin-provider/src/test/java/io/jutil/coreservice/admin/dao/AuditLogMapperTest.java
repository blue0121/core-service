package io.jutil.coreservice.admin.dao;

import io.jutil.coreservice.admin.entity.AuditLog;
import io.jutil.coreservice.admin.entity.AuditLogSearch;
import io.jutil.coreservice.admin.entity.AuditLogTest;
import io.jutil.coreservice.admin.entity.PageTest;
import io.jutil.coreservice.admin.util.BusinessType;
import io.jutil.coreservice.core.dict.Operation;
import io.jutil.springeasy.core.collection.Sort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.util.Map;

/**
 * @author Jin Zheng
 * @since 2025-09-11
 */
public abstract class AuditLogMapperTest {
	@Autowired
	AuditLogMapper mapper;

	@Autowired
	JdbcTemplate jdbcTemplate;

	@BeforeEach
	public void beforeEach() {
		jdbcTemplate.update("TRUNCATE TABLE adm_tenant_audit_log");
	}

	@Test
	public void testInsertOne() {
		var entity = AuditLogTest.create();
		Assertions.assertEquals(1, mapper.insertOne(entity));

		var view = mapper.selectOne(BusinessType.TENANT, entity.getTenantId(), entity.getId());
		AuditLogTest.verify(view, 1L, 1L, 2L, 3);
	}

	@CsvSource({"operatorId", "operation", "startDate", "endDate"})
	@ParameterizedTest
	public void testCountPage(String type) {
		var entity = AuditLogTest.create();
		Assertions.assertEquals(1, mapper.insertOne(entity));

		var search = this.getSearch(type, entity);
		var count = mapper.countPage(search);
		Assertions.assertEquals(1, count);
	}

	private AuditLogSearch getSearch(String type, AuditLog entity) {
		var search = new AuditLogSearch();
		search.setTenantId(entity.getTenantId());
		search.setBusinessId(entity.getBusinessId());
		search.setBusiness(BusinessType.TENANT);
		switch (type) {
			case "operatorId":
				search.setOperatorId(entity.getOperatorId());
				break;
			case "operation":
				search.setOperation(Operation.DELETE);
				break;
			case "startDate":
				search.setStartDate(LocalDate.now());
				break;
			case "endDate":
				search.setEndDate(LocalDate.now().plusDays(1));
				break;
		}
		return search;
	}

	@CsvSource({"operatorId", "operation", "startDate", "endDate"})
	@ParameterizedTest
	public void testListPage(String type) {
		var entity = AuditLogTest.create();
		Assertions.assertEquals(1, mapper.insertOne(entity));

		var search = this.getSearch(type, entity);
		var pageable = PageTest.createPageable(0, 10, new Sort("id"),
				Map.of("id", "e.id"));
		var list = mapper.listPage(search, pageable);
		Assertions.assertEquals(1, list.size());
		AuditLogTest.verify(list.getFirst(), 1L, 1L, 2L, 3);
	}
}
