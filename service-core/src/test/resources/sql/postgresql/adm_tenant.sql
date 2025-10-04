CREATE TABLE adm_tenant (
	id                   INT8                        PRIMARY KEY,
	operator_id          INT8                        NOT NULL,
	code                 VARCHAR(50)                 NOT NULL,
	name                 VARCHAR(50)                 NOT NULL,
	status               INT2             DEFAULT 0  NOT NULL,
	remarks              VARCHAR(500),
	create_time          TIMESTAMP(0)                NOT NULL,
	update_time          TIMESTAMP(0)                NOT NULL
);
COMMENT ON TABLE adm_tenant IS '租户';
COMMENT ON COLUMN adm_tenant.id IS '雪花ID';
COMMENT ON COLUMN adm_tenant.operator_id IS '操作人ID';
COMMENT ON COLUMN adm_tenant.code IS '标识符';
COMMENT ON COLUMN adm_tenant.name IS '名称';
COMMENT ON COLUMN adm_tenant.status IS '状态: 0正常, 1作废';
COMMENT ON COLUMN adm_tenant.remarks IS '备注';
COMMENT ON COLUMN adm_tenant.create_time IS '创建时间';
COMMENT ON COLUMN adm_tenant.update_time IS '更新时间';

CREATE UNIQUE INDEX udx_adm_tenant_code ON adm_tenant (code);


CREATE TABLE adm_tenant_audit_log (
	id                   INT8                        PRIMARY KEY,
	tenant_id            INT8                        NOT NULL,
	business_id          INT8                        NOT NULL,
	operator_id          INT8                        NOT NULL,
	operation            INT2                        NOT NULL,
	content              JSONB                       NOT NULL,
	create_time          TIMESTAMP(0)                NOT NULL
);
COMMENT ON TABLE adm_tenant_audit_log IS '租户审计日志';
COMMENT ON COLUMN adm_tenant_audit_log.id IS '雪花ID';
COMMENT ON COLUMN adm_tenant_audit_log.tenant_id IS '租户ID';
COMMENT ON COLUMN adm_tenant_audit_log.business_id IS '业务ID';
COMMENT ON COLUMN adm_tenant_audit_log.operator_id IS '操作人ID';
COMMENT ON COLUMN adm_tenant_audit_log.operation IS '操作: 1增加, 2更新, 3删除';
COMMENT ON COLUMN adm_tenant_audit_log.content IS '操作内容';
COMMENT ON COLUMN adm_tenant_audit_log.create_time IS '创建时间';

CREATE INDEX idx_adm_tenant_audit_log_business_create_time
	ON adm_tenant_audit_log (business_id, create_time);