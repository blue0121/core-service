package io.jutil.coreservice.core.entity;

import com.alibaba.fastjson2.JSONObject;
import io.jutil.coreservice.core.dict.Operation;
import io.jutil.coreservice.core.fetcher.OperatorFetcher;
import io.jutil.springeasy.mybatis.entity.LongIdEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author Jin Zheng
 * @since 2025-09-07
 */
@Getter
@Setter
@NoArgsConstructor
public class AuditLog extends LongIdEntity implements OperatorFetcher.OperatorFetcherEntity {
	private long tenantId;
	private long businessId;
	private long operatorId;
	private Operation operation;
	private JSONObject content;
	private LocalDateTime createTime;

	private String business;
	private String operatorCode;
	private String operatorName;
}
