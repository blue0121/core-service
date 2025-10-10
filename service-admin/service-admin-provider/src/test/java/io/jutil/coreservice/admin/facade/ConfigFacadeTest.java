package io.jutil.coreservice.admin.facade;

import io.jutil.coreservice.admin.entity.AuditLogTest;
import io.jutil.coreservice.admin.entity.ConfigTest;
import io.jutil.coreservice.admin.entity.PageTest;
import io.jutil.coreservice.admin.entity.SearchRequestTest;
import io.jutil.coreservice.admin.model.ConfigRequest;
import io.jutil.coreservice.admin.model.ConfigResponse;
import io.jutil.coreservice.admin.model.ConfigSearchRequest;
import io.jutil.coreservice.admin.repository.AuditLogRepositoryTest;
import io.jutil.coreservice.core.dict.Operation;
import io.jutil.coreservice.core.dict.Status;
import io.jutil.coreservice.core.facade.AuditLogFacade;
import io.jutil.coreservice.core.model.DeleteListTenantAuditRequest;
import io.jutil.coreservice.core.model.DeleteTenantAuditRequest;
import io.jutil.springeasy.core.collection.Sort;
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
public abstract class ConfigFacadeTest {
	@Autowired
	ConfigFacade facade;

	@Autowired
	AuditLogRepositoryTest auditLogRepositoryTest;

	@Autowired
	JdbcTemplate jdbcTemplate;

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
		auditLogRepositoryTest.beforeEach(AuditLogFacade.Business.CONFIG);
	}

	@Test
	public void testUpdateOne() {
		var response = this.createConfigAndVerify();

		var request = new ConfigRequest();
		request.setId(response.getId());
		request.setTenantId(response.getTenantId());
		request.setOperatorId(22L);
		request.setName("test");

		var view = facade.updateOne(request);
		ConfigTest.verify(view, 1L, 22L,  "code", "test",
				null, 0, 0,"remarks");

		var auditLogList = auditLogRepositoryTest.list(response.getTenantId(), response.getId(),
				AuditLogFacade.Business.CONFIG, Operation.UPDATE);
		Assertions.assertEquals(1, auditLogList.size());
		AuditLogTest.verify(auditLogList.getFirst(), response.getTenantId(), response.getId(),
				22L, 2);
	}

	@Test
	void testGetList() {
		var response = this.createConfigAndVerify();
		var response2 = this.createConfigAndVerify2();

		this.searchConfigAndVerify(response.getTenantId(), 2, 1);

		var responseMap = facade.getList(response.getTenantId(),
				List.of(response.getId(), response2.getId()));
		var view1 = responseMap.get(response.getId());
		ConfigTest.verify(view1, 1L, 2L,  "code", "name",
				"value", 0, 0,"remarks");

		var view2 = responseMap.get(response2.getId());
		ConfigTest.verify(view2, 1L, 2L,  "code2", "name2",
				"value", 0, 0,"remarks");
	}

	@Test
	public void testDeleteOne() {
		var response = this.createConfigAndVerify();

		var request = DeleteTenantAuditRequest.create(response.getTenantId(),
				response.getId(), 1L);
		Assertions.assertEquals(1, facade.deleteOne(request));

		this.searchConfigAndVerify(response.getTenantId(), 0, 0);

		var auditLogList = auditLogRepositoryTest.list(response.getTenantId(), response.getId(),
				AuditLogFacade.Business.CONFIG, Operation.DELETE);
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

		this.searchConfigAndVerify(response.getTenantId(), 0, 0);

		var auditLogList1 = auditLogRepositoryTest.list(response.getTenantId(), response.getId(),
				AuditLogFacade.Business.CONFIG, Operation.DELETE);
		Assertions.assertEquals(1, auditLogList1.size());
		AuditLogTest.verify(auditLogList1.getFirst(), response.getTenantId(), response.getId(),
				1L, 3);

		var auditLogList2 = auditLogRepositoryTest.list(response.getTenantId(), response2.getId(),
				AuditLogFacade.Business.CONFIG, Operation.DELETE);
		Assertions.assertEquals(1, auditLogList2.size());
		AuditLogTest.verify(auditLogList2.getFirst(), response.getTenantId(), response2.getId(),
				1L, 3);
	}

	@Test
	public void testCreateConfig() {
		this.createConfigAndVerify();
		var request = ConfigTest.createRequest();
		Assertions.assertThrows(ErrorCodeException.class, () -> facade.addOne(request));
	}

	private ConfigResponse createConfigAndVerify() {
		var request = ConfigTest.createRequest();
		var response = facade.addOne(request);
		var view = facade.getOne(request.getTenantId(), response.getId());

		ConfigTest.verify(view, 1L, 2L,  "code", "name",
				"value", 0, 0,"remarks");

		var addLogList = auditLogRepositoryTest.list(request.getTenantId(), response.getId(),
				AuditLogFacade.Business.CONFIG, Operation.ADD);
		Assertions.assertEquals(1, addLogList.size());
		AuditLogTest.verify(addLogList.getFirst(), request.getTenantId(), response.getId(),
				2L, 1);

		return response;
	}

	private ConfigResponse createConfigAndVerify2() {
		var request = ConfigTest.createRequest();
		request.setCode("code2");
		request.setName("name2");
		var response = facade.addOne(request);
		var view = facade.getOne(request.getTenantId(), response.getId());

		ConfigTest.verify(view, 1L, 2L,  "code2", "name2",
				"value", 0, 0,"remarks");

		var addLogList = auditLogRepositoryTest.list(request.getTenantId(), response.getId(),
				AuditLogFacade.Business.CONFIG, Operation.ADD);
		Assertions.assertEquals(1, addLogList.size());
		AuditLogTest.verify(addLogList.getFirst(), request.getTenantId(), response.getId(),
				2L, 1);

		return response;
	}

	private List<ConfigResponse> searchConfigAndVerify(long tenantId, int total, int totalPage) {
		var request = new ConfigSearchRequest();
		var filter = request.getFilter();
		filter.setTenantId(tenantId);
		filter.setStatus(Status.ACTIVE);
		SearchRequestTest.setPage(request, "id", Sort.Direction.DESC);
		var response = facade.search(request);
		PageTest.verify(response, 1, 10, total, totalPage);
		List<ConfigResponse> list = response.getContents();
		Assertions.assertEquals(total, list.size());
		return list;
	}
}
