package io.jutil.coreservice.admin.facade;

import io.jutil.coreservice.admin.entity.AuditLogTest;
import io.jutil.coreservice.admin.entity.PageTest;
import io.jutil.coreservice.admin.entity.SearchRequestTest;
import io.jutil.coreservice.admin.entity.TenantTest;
import io.jutil.coreservice.admin.model.TenantRequest;
import io.jutil.coreservice.admin.model.TenantResponse;
import io.jutil.coreservice.admin.model.TenantSearchRequest;
import io.jutil.coreservice.admin.provider.AuditLogProviderTest;
import io.jutil.coreservice.core.dict.Operation;
import io.jutil.coreservice.core.dict.Status;
import io.jutil.coreservice.core.facade.AuditLogFacade;
import io.jutil.coreservice.core.util.Const;
import io.jutil.springeasy.core.collection.Sort;
import io.jutil.springeasy.spring.exception.ErrorCodeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * @author Jin Zheng
 * @since 2025-09-15
 */
public abstract class TenantFacadeTest {
	@Autowired
	TenantFacade facade;

	@Autowired
	AuditLogProviderTest auditLogProviderTest;
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	@BeforeEach
	public void beforeEach() {
		jdbcTemplate.update("TRUNCATE TABLE adm_tenant");
		auditLogProviderTest.beforeEach();
	}

	@Test
	public void testUpdateOne() {
		var response = this.createTenantAndVerify();

		var request = new TenantRequest();
		request.setId(response.getId());
		request.setOperatorId(2L);
		request.setName("test");

		var view = facade.updateOne(request);
		TenantTest.verify(view, 2L, "code", "test",
				0, "remarks");

		var auditLogList = auditLogProviderTest.list(Const.DEFAULT_TENANT_ID, response.getId(),
				AuditLogFacade.Business.TENANT, Operation.UPDATE);
		Assertions.assertEquals(1, auditLogList.size());
		AuditLogTest.verify(auditLogList.getFirst(), Const.DEFAULT_TENANT_ID, response.getId(),
				2L, 2);
	}

	@Test
	void testGetList() {
		var response = this.createTenantAndVerify();
		var response2 = this.createTenantAndVerify2();

		this.searchTenantAndVerify(2, 1);

		var responseMap = facade.getList(List.of(response.getId(), response2.getId()));
		var view1 = responseMap.get(response.getId());
		TenantTest.verify(view1, 1, "code","name",
				0, "remarks");

		var view2 = responseMap.get(response2.getId());
		TenantTest.verify(view2, 1, "code2", "name2",
				0, "remarks");
	}

	@Test
	public void testDeleteOne() {
		var response = this.createTenantAndVerify();

		Assertions.assertEquals(1, facade.deleteOne(response.getId(), 1L));

		this.searchTenantAndVerify(0, 0);

		var auditLogList = auditLogProviderTest.list(Const.DEFAULT_TENANT_ID, response.getId(),
				AuditLogFacade.Business.TENANT, Operation.DELETE);
		Assertions.assertEquals(1, auditLogList.size());
		AuditLogTest.verify(auditLogList.getFirst(), Const.DEFAULT_TENANT_ID, response.getId(),
				1L, 3);
	}

	@Test
	public void testDeleteList() {
		var response = this.createTenantAndVerify();
		var response2 = this.createTenantAndVerify2();

		Assertions.assertEquals(2, facade.deleteList(
				List.of(response.getId(), response2.getId()), 1L));

		this.searchTenantAndVerify(0, 0);

		var auditLogList1 = auditLogProviderTest.list(Const.DEFAULT_TENANT_ID, response.getId(),
				AuditLogFacade.Business.TENANT, Operation.DELETE);
		Assertions.assertEquals(1, auditLogList1.size());
		AuditLogTest.verify(auditLogList1.getFirst(), Const.DEFAULT_TENANT_ID, response.getId(),
				1L, 3);

		var auditLogList2 = auditLogProviderTest.list(Const.DEFAULT_TENANT_ID, response2.getId(),
				AuditLogFacade.Business.TENANT, Operation.DELETE);
		Assertions.assertEquals(1, auditLogList2.size());
		AuditLogTest.verify(auditLogList2.getFirst(), Const.DEFAULT_TENANT_ID, response2.getId(),
				1L, 3);
	}

	@Test
	public void testCreateTenant() {
		this.createTenantAndVerify();
		var request = TenantTest.createRequest();
		Assertions.assertThrows(ErrorCodeException.class, () -> facade.addOne(request));
	}

	private TenantResponse createTenantAndVerify() {
		var request = TenantTest.createRequest();
		var response = facade.addOne(request);
		var view = facade.getOne(response.getId());

		TenantTest.verify(view, 1L, "code", "name",
				0, "remarks");

		var addLogList = auditLogProviderTest.list(Const.DEFAULT_TENANT_ID, response.getId(),
				AuditLogFacade.Business.TENANT, Operation.ADD);
		Assertions.assertEquals(1, addLogList.size());
		AuditLogTest.verify(addLogList.getFirst(), Const.DEFAULT_TENANT_ID, response.getId(),
				1L, 1);

		return response;
	}

	private TenantResponse createTenantAndVerify2() {
		var request = TenantTest.createRequest();
		request.setCode("code2");
		request.setName("name2");
		var response = facade.addOne(request);
		var view = facade.getOne(response.getId());

		TenantTest.verify(view, 1, "code2", "name2",
				0, "remarks");

		var addLogList = auditLogProviderTest.list(Const.DEFAULT_TENANT_ID, response.getId(),
				AuditLogFacade.Business.TENANT, Operation.ADD);
		Assertions.assertEquals(1, addLogList.size());
		AuditLogTest.verify(addLogList.getFirst(), Const.DEFAULT_TENANT_ID, response.getId(),
				1L, 1);

		return response;
	}

	private List<TenantResponse> searchTenantAndVerify(int total, int totalPage) {
		var request = new TenantSearchRequest();
		request.getFilter().setStatus(Status.ACTIVE);
		SearchRequestTest.setPage(request, "id", Sort.Direction.DESC);
		var response = facade.search(request);
		PageTest.verify(response, 1, 10, total, totalPage);
		List<TenantResponse> list = response.getContents();
		Assertions.assertEquals(total, list.size());
		return list;
	}

}
