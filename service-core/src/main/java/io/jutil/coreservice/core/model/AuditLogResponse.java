package io.jutil.coreservice.core.model;

import com.alibaba.fastjson2.JSONObject;
import io.jutil.coreservice.core.dict.Operation;
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
public class AuditLogResponse {
	private long id;
	private long tenantId;
	private long businessId;
	private long operatorId;
	private Operation operation;
	private JSONObject content;
	private LocalDateTime createTime;

	private String operatorCode;
	private String operatorName;
}
