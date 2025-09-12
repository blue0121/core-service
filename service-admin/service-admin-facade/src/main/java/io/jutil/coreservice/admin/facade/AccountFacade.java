package io.jutil.coreservice.admin.facade;

import io.jutil.coreservice.admin.model.AccountRequest;
import io.jutil.coreservice.admin.model.AccountResponse;
import io.jutil.coreservice.admin.model.AccountSearchRequest;
import io.jutil.coreservice.core.facade.BaseTenantAuditFacade;

/**
 * @author Jin Zheng
 * @since 2025-09-07
 */
public interface AccountFacade extends BaseTenantAuditFacade<AccountRequest, AccountResponse, AccountSearchRequest> {

}
