package io.jutil.coreservice.admin.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * @author Jin Zheng
 * @since 2025-10-02
 */
public abstract class AccountTenantMapperTest {
	@Autowired
	AccountTenantMapper mapper;

	@Autowired
	JdbcTemplate jdbcTemplate;

	final long accountId = 1L;

	@BeforeEach
	public void beforeEach() {
		jdbcTemplate.update("TRUNCATE TABLE adm_account_tenant");
	}

	@Test
	void testInsert() {
		var tenantIdList = List.of(11L, 12L, 13L);
		Assertions.assertEquals(3, mapper.insert(accountId, tenantIdList));

		var viewList = mapper.listByAccountId(accountId);
		Assertions.assertEquals(3, viewList.size());
		Assertions.assertEquals(tenantIdList, viewList);
	}

	@Test
	void testDeleteByAccountId() {
		var tenantIdList = List.of(11L, 12L, 13L);
		Assertions.assertEquals(3, mapper.insert(accountId, tenantIdList));

		Assertions.assertEquals(3, mapper.deleteByAccountId(accountId));
		Assertions.assertTrue(mapper.listByAccountId(accountId).isEmpty());
	}

	@Test
	void testDeleteByAccountIdList() {
		var tenantIdList = List.of(11L, 12L, 13L);
		Assertions.assertEquals(3, mapper.insert(accountId, tenantIdList));
		Assertions.assertEquals(3, mapper.insert(2L, tenantIdList));

		Assertions.assertEquals(6, mapper.deleteByAccountIdList(List.of(accountId, 2L)));
		Assertions.assertTrue(mapper.listByAccountId(accountId).isEmpty());
		Assertions.assertTrue(mapper.listByAccountId(2L).isEmpty());
	}
}
