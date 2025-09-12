package io.jutil.coreservice.core.convertor;

import io.jutil.coreservice.core.entity.BaseAuditEntity;
import io.jutil.coreservice.core.entity.BaseEntity;
import io.jutil.coreservice.core.entity.BaseTenantAuditEntity;
import io.jutil.coreservice.core.entity.BaseTenantEntity;
import io.jutil.coreservice.core.model.BaseAuditResponse;
import io.jutil.coreservice.core.model.BaseResponse;
import io.jutil.coreservice.core.model.BaseTenantAuditResponse;
import io.jutil.coreservice.core.model.BaseTenantResponse;

import java.util.function.Supplier;

/**
 * @author Jin Zheng
 * @since 2025-09-11
 */
public class BaseResponseConvertor {
	private BaseResponseConvertor() {}

	public static <E extends BaseEntity, R extends BaseResponse> R toBaseResponse(E entity, Supplier<R> f) {
		var response = f.get();
		response.setId(entity.getId());
		response.setCreateTime(entity.getCreateTime());
		response.setUpdateTime(entity.getUpdateTime());
		return response;
	}

	public static <E extends BaseAuditEntity, R extends BaseAuditResponse> R toAuditResponse(E entity, Supplier<R> f) {
		var response = toBaseResponse(entity, f);
		response.setOperatorId(entity.getOperatorId());
		response.setOperatorCode(entity.getOperatorCode());
		response.setOperatorName(entity.getOperatorName());
		return response;
	}

	public static <E extends BaseTenantEntity, R extends BaseTenantResponse> R toTenantResponse(E entity, Supplier<R> f) {
		var response = toBaseResponse(entity, f);
		response.setTenantId(entity.getTenantId());
		return response;
	}

	public static <E extends BaseTenantAuditEntity, R extends BaseTenantAuditResponse> R toTenantAuditResponse(E entity, Supplier<R> f) {
		var response = toAuditResponse(entity, f);
		response.setTenantId(entity.getTenantId());
		return response;
	}

}
