package io.jutil.coreservice.admin.provider;

import io.jutil.coreservice.admin.entity.PageTest;
import io.jutil.coreservice.admin.entity.TenantTest;
import io.jutil.coreservice.admin.model.TenantResponse;
import io.jutil.coreservice.admin.model.TenantSearchRequest;
import io.jutil.coreservice.admin.service.TenantService;
import io.jutil.coreservice.core.dict.Status;
import io.jutil.springeasy.core.util.StringUtil;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

/**
 * @author Jin Zheng
 * @since 2025-09-15
 */
@ExtendWith(MockitoExtension.class)
class TenantProviderTest {
	@Mock
	TenantService service;
	@InjectMocks
	TenantProvider provider;

	@Test
	void testGetOne() {
		var entity = TenantTest.create();
		Mockito.when(service.getOne(Mockito.anyLong())).thenReturn(entity);
		var response = provider.getOne(entity.getId());
		TenantTest.verify(response, 1L, "code", "name",
				0, "remarks");
	}

	@Test
	void testGetOne_fail() {
		Assertions.assertThrows(ValidationException.class, () -> provider.getOne(0L));
	}

	@Test
	void testGetList() {
		var entity = TenantTest.create();
		Mockito.when(service.getList(Mockito.anyList())).thenReturn(Map.of(entity.getId(), entity));
		var response = provider.getList(List.of(entity.getId()));
		TenantTest.verify(response.get(entity.getId()), 1L, "code", "name",
				0, "remarks");
	}

	@Test
	void testGetList_fail() {
		Assertions.assertThrows(ValidationException.class, () -> provider.getList(null));
		Assertions.assertThrows(ValidationException.class, () -> provider.getList(List.of()));
		Assertions.assertThrows(ValidationException.class, () -> provider.getList(List.of(0L)));
	}

	@Test
	void testAddOne() {
		var request = TenantTest.createRequest();
		var entity = TenantTest.create();
		Mockito.when(service.addOne(Mockito.any())).thenReturn(entity);
		var response = provider.addOne(request);
		TenantTest.verify(response, 1L, "code", "name",
				0, "remarks");
	}

	@Test
	void testAddOne_fail() {
		var r1 = TenantTest.createRequest();
		r1.setOperatorId(0L);
		Assertions.assertThrows(ValidationException.class, () -> provider.addOne(r1));

		var r2 = TenantTest.createRequest();
		r2.setCode(null);
		Assertions.assertThrows(ValidationException.class, () -> provider.addOne(r2));
		r2.setCode("");
		Assertions.assertThrows(ValidationException.class, () -> provider.addOne(r2));

		var r3 = TenantTest.createRequest();
		r3.setName(null);
		Assertions.assertThrows(ValidationException.class, () -> provider.addOne(r3));
		r3.setName("");
		Assertions.assertThrows(ValidationException.class, () -> provider.addOne(r3));

		var r4 = TenantTest.createRequest();
		r4.setStatus(null);
		Assertions.assertThrows(ValidationException.class, () -> provider.addOne(r4));

		var r5 = TenantTest.createRequest();
		r5.setRemarks(StringUtil.repeat("a", 501, ""));
		Assertions.assertThrows(ValidationException.class, () -> provider.addOne(r5));
	}

	@Test
	void testUpdateOne() {
		var request = TenantTest.createRequest();
		var entity = TenantTest.create();
		Mockito.when(service.updateOne(Mockito.any())).thenReturn(entity);
		var response = provider.updateOne(request);
		TenantTest.verify(response, 1L, "code", "name",
				0, "remarks");
	}

	@Test
	void testUpdateOne_fail() {
		var r1 = TenantTest.createRequest();
		r1.setId(0L);
		Assertions.assertThrows(ValidationException.class, () -> provider.updateOne(r1));

		var r2 = TenantTest.createRequest();
		r2.setOperatorId(0L);
		Assertions.assertThrows(ValidationException.class, () -> provider.updateOne(r2));

		var r3 = TenantTest.createRequest();
		r3.setRemarks(StringUtil.repeat("a", 501, ""));
		Assertions.assertThrows(ValidationException.class, () -> provider.updateOne(r3));
	}

	@Test
	void testDeleteOne() {
		Mockito.when(service.deleteOne(Mockito.anyLong(), Mockito.anyLong())).thenReturn(1);
		var response = provider.deleteOne(1L, 1L);
		Assertions.assertEquals(1, response);
	}

	@Test
	void testDeleteOne_fail() {
		Assertions.assertThrows(ValidationException.class, () -> provider.deleteOne(0L, 1L));
		Assertions.assertThrows(ValidationException.class, () -> provider.deleteOne(1L, 0L));
	}

	@Test
	void testDeleteList() {
		Mockito.when(service.deleteList(Mockito.anyList(), Mockito.anyLong())).thenReturn(2);
		var response = provider.deleteList(List.of(1L, 2L), 10L);
		Assertions.assertEquals(2, response);
	}

	@Test
	void testDeleteList_fail() {
		Assertions.assertThrows(ValidationException.class, () -> provider.deleteList(null, 1L));
		Assertions.assertThrows(ValidationException.class, () -> provider.deleteList(List.of(), 1L));
		Assertions.assertThrows(ValidationException.class, () -> provider.deleteList(List.of(0L), 1L));
		Assertions.assertThrows(ValidationException.class, () -> provider.deleteList(List.of(1L), 0L));
	}

	@Test
	void testSearch() {
		var request = new TenantSearchRequest();
		request.getFilter().setStatus(Status.ACTIVE);
		var page = PageTest.createPage();
		page.setTotal(1);
		var entity = TenantTest.create();
		page.setContents(List.of(entity));
		Mockito.when(service.search(Mockito.any(), Mockito.any())).thenReturn(page);
		var view = provider.search(request);
		PageTest.verify(view, 1, 10, 1, 1);
		List<TenantResponse> list = view.getContents();
		TenantTest.verify(list.getFirst(), 1, "code", "name",
				0, "remarks");
	}
}
