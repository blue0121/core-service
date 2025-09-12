package io.jutil.coreservice.core.convertor;

import io.jutil.coreservice.core.entity.BaseAuditEntity;
import io.jutil.coreservice.core.entity.BaseEntity;
import io.jutil.coreservice.core.entity.BaseTenantAuditEntity;
import io.jutil.coreservice.core.entity.BaseTenantEntity;
import io.jutil.coreservice.core.entity.DeleteListTenantAuditEntity;
import io.jutil.coreservice.core.entity.DeleteTenantAuditEntity;
import io.jutil.coreservice.core.model.BaseAuditRequest;
import io.jutil.coreservice.core.model.BaseRequest;
import io.jutil.coreservice.core.model.BaseTenantAuditRequest;
import io.jutil.coreservice.core.model.BaseTenantRequest;
import io.jutil.coreservice.core.model.DeleteListTenantAuditRequest;
import io.jutil.coreservice.core.model.DeleteTenantAuditRequest;

import java.util.function.Supplier;

/**
 * @author Jin Zheng
 * @since 2025-09-11
 */
public class BaseRequestConvertor {
	private BaseRequestConvertor() {}

	public static <T extends BaseRequest, E extends BaseEntity> E toBaseEntity(T request, Supplier<E> f) {
		var entity = f.get();
		entity.setId(request.getId());
		return entity;
	}

	public static <T extends BaseAuditRequest, E extends BaseAuditEntity> E toAuditEntity(T request, Supplier<E> f) {
		var entity = toBaseEntity(request, f);
		entity.setOperatorId(request.getOperatorId());
		return entity;
	}

	public static <T extends BaseTenantRequest, E extends BaseTenantEntity> E toTenantEntity(T request, Supplier<E> f) {
		var entity = toBaseEntity(request, f);
		entity.setTenantId(request.getTenantId());
		return entity;
	}

	public static <T extends BaseTenantAuditRequest, E extends BaseTenantAuditEntity> E toTenantAuditEntity(T request, Supplier<E> f) {
		var entity = toAuditEntity(request, f);
		entity.setTenantId(request.getTenantId());
		return entity;
	}


	public static DeleteTenantAuditEntity toDeleteTenantAuditEntity(DeleteTenantAuditRequest request) {
		var entity = new DeleteTenantAuditEntity();
		entity.setId(request.getId());
		entity.setTenantId(request.getTenantId());
		entity.setOperatorId(request.getOperatorId());
		return entity;
	}

	public static DeleteListTenantAuditEntity toDeleteListTenantAuditEntity(DeleteListTenantAuditRequest request) {
		var entity = new DeleteListTenantAuditEntity();
		entity.setIdList(request.getIdList());
		entity.setTenantId(request.getTenantId());
		entity.setOperatorId(request.getOperatorId());
		return entity;
	}
}
