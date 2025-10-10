package io.jutil.coreservice.admin.facade;

import io.jutil.coreservice.admin.model.ConfigRequest;
import io.jutil.coreservice.admin.model.ConfigResponse;
import io.jutil.coreservice.admin.model.ConfigSearchRequest;
import io.jutil.coreservice.core.facade.BaseTenantAuditFacade;

/**
 * @author Jin Zheng
 * @since 2025-10-09
 */
public interface ConfigFacade extends BaseTenantAuditFacade<ConfigRequest, ConfigResponse, ConfigSearchRequest> {

}
