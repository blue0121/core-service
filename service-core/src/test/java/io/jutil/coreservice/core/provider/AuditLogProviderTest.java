package io.jutil.coreservice.core.provider;

import io.jutil.coreservice.core.entity.AuditLogTest;
import io.jutil.coreservice.core.entity.PageTest;
import io.jutil.coreservice.core.facade.AuditLogFacade;
import io.jutil.coreservice.core.model.AuditLogResponse;
import io.jutil.coreservice.core.model.AuditLogSearchRequest;
import io.jutil.coreservice.core.repository.AuditLogRepository;
import jakarta.validation.ValidationException;
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
 * @since 2025-09-18
 */
@ExtendWith(MockitoExtension.class)
class AuditLogProviderTest {
	@Mock
	AuditLogRepository repository;

	@InjectMocks
	AuditLogProvider provider;

	@Test
	void testSearch() {
		var entity = AuditLogTest.create();
		var request = new AuditLogSearchRequest();
		var filter = request.getFilter();
		filter.setTenantId(1L);
		filter.setBusinessId(1L);

		var page = PageTest.createPage();
		page.setTotal(1);
		page.setContents(List.of(entity));
		Mockito.when(repository.search(Mockito.any(), Mockito.any())).thenReturn(page);

		var response = provider.search(request, AuditLogFacade.Business.TENANT);
		PageTest.verify(response, 1, 10, 1, 1);
		List<AuditLogResponse> viewList = response.getContents();
		AuditLogTest.verify(viewList.getFirst(), 1, 1, 2L, 3);
	}

	@Test
	void testSearch_failed() {
		var request = new AuditLogSearchRequest();
		var filter = request.getFilter();
		filter.setTenantId(1L);
		filter.setBusinessId(1L);
		Assertions.assertThrows(ValidationException.class, () -> provider.search(request, null));

		filter.setBusinessId(0L);
		Assertions.assertThrows(ValidationException.class, () -> provider.search(request, AuditLogFacade.Business.TENANT));

		filter.setBusinessId(1L);
		filter.setTenantId(0L);
		Assertions.assertThrows(ValidationException.class, () -> provider.search(request, AuditLogFacade.Business.TENANT));
	}
}
