package io.jutil.coreservice.core.facade;

import io.jutil.coreservice.core.model.AuditLogSearchRequest;
import io.jutil.coreservice.core.model.PageResponse;
import lombok.Getter;

/**
 * @author Jin Zheng
 * @since 2025-09-18
 */
public interface AuditLogFacade {

	PageResponse search(AuditLogSearchRequest searchRequest, Business business);

	@Getter
	enum Business {
		TENANT("adm_tenant"),
		ACCOUNT("adm_account"),
		CONFIG("adm_config"),
		CONFIG_ITEM("adm_config_item"),
		;
		private final String label;

		Business(String label) {
			this.label = label;
		}
	}


}
