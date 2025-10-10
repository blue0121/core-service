package io.jutil.coreservice.admin.facade;

import io.jutil.coreservice.admin.entity.AuditLogTest;
import io.jutil.coreservice.admin.entity.ConfigItemTest;
import io.jutil.coreservice.admin.entity.ConfigTest;
import io.jutil.coreservice.admin.model.ConfigItemRequest;
import io.jutil.coreservice.admin.model.ConfigItemResponse;
import io.jutil.coreservice.admin.repository.AuditLogRepositoryTest;
import io.jutil.coreservice.core.dict.Operation;
import io.jutil.coreservice.core.facade.AuditLogFacade;
import io.jutil.coreservice.core.model.DeleteListTenantAuditRequest;
import io.jutil.coreservice.core.model.DeleteTenantAuditRequest;
import io.jutil.springeasy.mybatis.metadata.DatabaseProduct;
import io.jutil.springeasy.mybatis.metadata.MetadataUtil;
import io.jutil.springeasy.spring.exception.ErrorCodeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * @author Jin Zheng
 * @since 2025-10-10
 */
public abstract class ConfigItemFacadeTest {
	@Autowired
	ConfigItemFacade facade;

	@Autowired
	ConfigFacade configFacade;

	@Autowired
	AuditLogRepositoryTest auditLogRepositoryTest;

	@Autowired
	JdbcTemplate jdbcTemplate;

	long configId;

	@BeforeEach
	public void beforeEach() {
		var dataSource = jdbcTemplate.getDataSource();
		var database = MetadataUtil.getDatabaseInfo(dataSource);
		if (database.product() == DatabaseProduct.MYSQL) {
			jdbcTemplate.update("DELETE FROM adm_config_item");
			jdbcTemplate.update("DELETE FROM adm_config");
		} else {
			jdbcTemplate.update("TRUNCATE TABLE adm_config CASCADE");
		}
		auditLogRepositoryTest.beforeEach(AuditLogFacade.Business.CONFIG_ITEM);

		var request = ConfigTest.createRequest();
		var response = configFacade.addOne(request);
		this.configId = response.getId();
	}

	@Test
	public void testUpdateOne() {
		var response = this.createConfigAndVerify();

		var request = new ConfigItemRequest();
		request.setId(response.getId());
		request.setTenantId(response.getTenantId());
		request.setOperatorId(22L);
		request.setName("test");

		var view = facade.updateOne(request);
		ConfigItemTest.verify(view, 1L, configId, 22L,  "test",
				"value", 0, "remarks");

		var auditLogList = auditLogRepositoryTest.list(response.getTenantId(), response.getId(),
				AuditLogFacade.Business.CONFIG_ITEM, Operation.UPDATE);
		Assertions.assertEquals(1, auditLogList.size());
		AuditLogTest.verify(auditLogList.getFirst(), response.getTenantId(), response.getId(),
				22L, 2);
	}

	@Test
	public void testDeleteOne() {
		var response = this.createConfigAndVerify();

		var request = DeleteTenantAuditRequest.create(response.getTenantId(),
				response.getId(), 1L);
		Assertions.assertEquals(1, facade.deleteOne(request));

		this.searchConfigAndVerify(response.getTenantId(), 0);

		var auditLogList = auditLogRepositoryTest.list(response.getTenantId(), response.getId(),
				AuditLogFacade.Business.CONFIG_ITEM, Operation.DELETE);
		Assertions.assertEquals(1, auditLogList.size());
		AuditLogTest.verify(auditLogList.getFirst(), response.getTenantId(), response.getId(),
				1L, 3);
	}

	@Test
	public void testDeleteList() {
		var response = this.createConfigAndVerify();
		var response2 = this.createConfigAndVerify2();

		var request = DeleteListTenantAuditRequest.create(response.getTenantId(),
				List.of(response.getId(), response2.getId()), 1L);
		Assertions.assertEquals(2, facade.deleteList(request));

		this.searchConfigAndVerify(response.getTenantId(), 0);

		var auditLogList1 = auditLogRepositoryTest.list(response.getTenantId(), response.getId(),
				AuditLogFacade.Business.CONFIG_ITEM, Operation.DELETE);
		Assertions.assertEquals(1, auditLogList1.size());
		AuditLogTest.verify(auditLogList1.getFirst(), response.getTenantId(), response.getId(),
				1L, 3);

		var auditLogList2 = auditLogRepositoryTest.list(response.getTenantId(), response2.getId(),
				AuditLogFacade.Business.CONFIG_ITEM, Operation.DELETE);
		Assertions.assertEquals(1, auditLogList2.size());
		AuditLogTest.verify(auditLogList2.getFirst(), response.getTenantId(), response2.getId(),
				1L, 3);
	}

	@Test
	public void testCreateConfig() {
		this.createConfigAndVerify();
		var request = ConfigItemTest.createRequest(configId);
		Assertions.assertThrows(ErrorCodeException.class, () -> facade.addOne(request));
	}

	private ConfigItemResponse createConfigAndVerify() {
		var request = ConfigItemTest.createRequest(configId);
		var response = facade.addOne(request);

		ConfigItemTest.verify(response,1L, configId, 2L, "name",
				"value", 0, "remarks");

		var addLogList = auditLogRepositoryTest.list(request.getTenantId(), response.getId(),
				AuditLogFacade.Business.CONFIG_ITEM, Operation.ADD);
		Assertions.assertEquals(1, addLogList.size());
		AuditLogTest.verify(addLogList.getFirst(), request.getTenantId(), response.getId(),
				2L, 1);

		return response;
	}

	private ConfigItemResponse createConfigAndVerify2() {
		var request = ConfigItemTest.createRequest(configId);
		request.setValue("value2");
		request.setName("name2");
		var response = facade.addOne(request);

		ConfigItemTest.verify(response, 1L, configId, 2L, "name2",
				"value2", 0, "remarks");

		var addLogList = auditLogRepositoryTest.list(request.getTenantId(), response.getId(),
				AuditLogFacade.Business.CONFIG_ITEM, Operation.ADD);
		Assertions.assertEquals(1, addLogList.size());
		AuditLogTest.verify(addLogList.getFirst(), request.getTenantId(), response.getId(),
				2L, 1);

		return response;
	}

	private List<ConfigItemResponse> searchConfigAndVerify(long tenantId, int total) {
		var map = facade.listByConfigIdList(tenantId, List.of(configId));
		Assertions.assertEquals(total, map.size());
		if (total == 0) {
			return List.of();
		}

		var list = map.get(configId);
		Assertions.assertEquals(total, list.size());
		return list;
	}
}
