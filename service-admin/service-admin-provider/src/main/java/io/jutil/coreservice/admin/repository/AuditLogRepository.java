package io.jutil.coreservice.admin.repository;

import com.alibaba.fastjson2.JSONObject;
import io.jutil.coreservice.admin.dao.AuditLogMapper;
import io.jutil.coreservice.admin.entity.AuditLog;
import io.jutil.coreservice.admin.entity.AuditLogSearch;
import io.jutil.coreservice.core.dict.Operation;
import io.jutil.coreservice.core.repository.PageableRepository;
import io.jutil.springeasy.core.collection.Page;
import io.jutil.springeasy.mybatis.id.LongIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Map;

/**
 * @author Jin Zheng
 * @since 2025-09-10
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AuditLogRepository {
	public static final Map<String, String> SORT_FILED_MAP =
			Map.of("id", "e.id");

	@Autowired
	AuditLogMapper auditLogMapper;

	public int add(String business, long tenantId, long businessId, long operatorId,
	               Operation operation, Object content) {
		var entity = new AuditLog();
		entity.setId(LongIdGenerator.nextId());
		entity.setBusiness(business);
		entity.setTenantId(tenantId);
		entity.setBusinessId(businessId);
		entity.setOperatorId(operatorId);
		entity.setOperation(operation);
		switch (content) {
			case JSONObject json -> entity.setContent(json);
			default -> entity.setContent(JSONObject.from(content));
		}
		return auditLogMapper.insertOne(entity);
	}

	public int addOperation(String business, long tenantId, long businessId,
	                        long operatorId, Object content) {
		return this.add(business, tenantId, businessId, operatorId, Operation.ADD, content);
	}

	public int updateOperation(String business, long tenantId, long businessId,
	                           long operatorId, Object content) {
		return this.add(business, tenantId, businessId, operatorId, Operation.UPDATE, content);
	}

	public int deleteOperation(String business, long tenantId, long businessId,
	                           long operatorId) {
		return this.add(business, tenantId, businessId, operatorId, Operation.DELETE,
				this.getIdContent(businessId));
	}

	public int deleteListOperation(String business, long tenantId, Collection<Long> businessIdList,
	                               long operatorId) {
		int count = 0;
		for (var businessId : businessIdList) {
			count += this.add(business, tenantId, businessId, operatorId, Operation.DELETE,
				this.getIdContent(businessId));
		}
		return count;
	}

	private JSONObject getIdContent(long id) {
		return JSONObject.of("id", id);
	}

	public AuditLog getOne(String business, long tenantId, long id) {
		return auditLogMapper.selectOne(business, tenantId, id);
	}

	public Page search(AuditLogSearch search, Page page) {
		return PageableRepository.search(auditLogMapper, SORT_FILED_MAP, search, page);
	}
}
