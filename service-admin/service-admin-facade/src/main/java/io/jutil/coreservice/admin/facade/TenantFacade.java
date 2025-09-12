package io.jutil.coreservice.admin.facade;

import io.jutil.coreservice.admin.model.TenantRequest;
import io.jutil.coreservice.admin.model.TenantResponse;
import io.jutil.coreservice.admin.model.TenantSearchRequest;
import io.jutil.coreservice.core.facade.BaseAuditFacade;

/**
 * @author Jin Zheng
 * @since 2025-09-07
 */
public interface TenantFacade extends BaseAuditFacade<TenantRequest, TenantResponse, TenantSearchRequest> {

}
