package io.jutil.coreservice.admin.model;

import io.jutil.coreservice.admin.fetcher.OperatorFetcher;
import io.jutil.coreservice.core.dict.Status;
import io.jutil.coreservice.core.model.BaseTenantAuditResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Jin Zheng
 * @since 2025-09-07
 */
@Getter
@Setter
@NoArgsConstructor
public class AccountResponse extends BaseTenantAuditResponse implements OperatorFetcher.AccountFetcherResponse {
	private String code;
	private String name;
	private Status status;
	private String imageUrl;
	private String remarks;
}
