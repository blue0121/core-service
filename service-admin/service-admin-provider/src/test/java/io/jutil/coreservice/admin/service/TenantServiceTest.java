package io.jutil.coreservice.admin.service;

import io.jutil.coreservice.admin.entity.TenantTest;
import io.jutil.coreservice.admin.repository.TenantRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

/**
 * @author Jin Zheng
 * @since 2025-09-15
 */
@ExtendWith(MockitoExtension.class)
class TenantServiceTest {
	@Mock
	TenantRepository repository;

	@InjectMocks
	TenantService service;

	@Test
	void testGetList() {
		var tenant1 = TenantTest.create();
		var tenant2 = TenantTest.create();
		tenant2.setCode("code2");
		Mockito.when(repository.getList(Mockito.anyList())).thenReturn(List.of(tenant1, tenant2));
		var map = service.getList(List.of(tenant1.getId(), tenant2.getId()));
		Assertions.assertEquals(2, map.size());
		TenantTest.verify(map.get(tenant1.getId()), 1L, "code", "name",
				0, "remarks");
		TenantTest.verify(map.get(tenant2.getId()), 1L, "code2", "name",
				0, "remarks");
	}
}
