CREATE TABLE adm_config (
	id                   INT8                        PRIMARY KEY,
	tenant_id            INT8                        NOT NULL,
	operator_id          INT8                        NOT NULL,
	code                 VARCHAR(50)                 NOT NULL,
	name                 VARCHAR(50)                 NOT NULL,
	value                VARCHAR(200),
	status               INT2             DEFAULT 0  NOT NULL,
	multi_value          INT2             DEFAULT 0  NOT NULL,
	remarks              VARCHAR(500),
	create_time          TIMESTAMP(0)                NOT NULL,
	update_time          TIMESTAMP(0)                NOT NULL
);
COMMENT ON TABLE adm_config IS '配置';
COMMENT ON COLUMN adm_config.id IS '雪花ID';
COMMENT ON COLUMN adm_config.tenant_id IS '租户ID';
COMMENT ON COLUMN adm_config.operator_id IS '操作人ID';
COMMENT ON COLUMN adm_config.code IS '标识符';
COMMENT ON COLUMN adm_config.name IS '名称';
COMMENT ON COLUMN adm_config.value IS '值';
COMMENT ON COLUMN adm_config.status IS '状态: 0正常, 1作废';
COMMENT ON COLUMN adm_config.multi_value IS '是否多值: 0否, 1是';
COMMENT ON COLUMN adm_config.remarks IS '备注';
COMMENT ON COLUMN adm_config.create_time IS '创建时间';
COMMENT ON COLUMN adm_config.update_time IS '更新时间';

CREATE UNIQUE INDEX udx_adm_config_tenant_code ON adm_config (tenant_id, code);

CREATE TABLE adm_config_item (
	id                   INT8                        PRIMARY KEY,
	tenant_id            INT8                        NOT NULL,
	config_id            INT8                        NOT NULL,
	operator_id          INT8                        NOT NULL,
	value                VARCHAR(200)                NOT NULL,
	name                 VARCHAR(50)                 NOT NULL,
	status               INT2             DEFAULT 0  NOT NULL,
	remarks              VARCHAR(500),
	create_time          TIMESTAMP(0)                NOT NULL,
	update_time          TIMESTAMP(0)                NOT NULL
);
COMMENT ON TABLE adm_config_item IS '配置项';
COMMENT ON COLUMN adm_config_item.id IS '雪花ID';
COMMENT ON COLUMN adm_config_item.tenant_id IS '租户ID';
COMMENT ON COLUMN adm_config_item.config_id IS '配置ID';
COMMENT ON COLUMN adm_config_item.operator_id IS '操作人ID';
COMMENT ON COLUMN adm_config_item.value IS '值';
COMMENT ON COLUMN adm_config_item.name IS '名称';
COMMENT ON COLUMN adm_config_item.status IS '状态: 0正常, 1作废';
COMMENT ON COLUMN adm_config_item.remarks IS '备注';
COMMENT ON COLUMN adm_config_item.create_time IS '创建时间';
COMMENT ON COLUMN adm_config_item.update_time IS '更新时间';

CREATE UNIQUE INDEX udx_adm_config_item_config_value ON adm_config_item (config_id, value);

ALTER TABLE adm_config_item ADD CONSTRAINT fk_adm_config_item_ref_adm_config
	FOREIGN KEY (config_id) REFERENCES adm_config (id);

CREATE TABLE adm_config_audit_log (
	id                   INT8                        PRIMARY KEY,
	tenant_id            INT8                        NOT NULL,
	business_id          INT8                        NOT NULL,
	operator_id          INT8                        NOT NULL,
	operation            INT2                        NOT NULL,
	content              JSONB                       NOT NULL,
	create_time          TIMESTAMP(0)                NOT NULL
);
COMMENT ON TABLE adm_account_audit_log IS '配置审计日志';
COMMENT ON COLUMN adm_account_audit_log.id IS '雪花ID';
COMMENT ON COLUMN adm_account_audit_log.tenant_id IS '租户ID';
COMMENT ON COLUMN adm_account_audit_log.business_id IS '业务ID';
COMMENT ON COLUMN adm_account_audit_log.operator_id IS '操作人ID';
COMMENT ON COLUMN adm_account_audit_log.operation IS '操作: 1增加, 2更新, 3删除';
COMMENT ON COLUMN adm_account_audit_log.content IS '操作内容';
COMMENT ON COLUMN adm_account_audit_log.create_time IS '创建时间';

CREATE INDEX idx_adm_config_audit_log_business_create_time
	ON adm_config_audit_log (business_id, create_time);

CREATE TABLE adm_config_item_audit_log (
	id                   INT8                        PRIMARY KEY,
	tenant_id            INT8                        NOT NULL,
	business_id          INT8                        NOT NULL,
	operator_id          INT8                        NOT NULL,
	operation            INT2                        NOT NULL,
	content              JSONB                       NOT NULL,
	create_time          TIMESTAMP(0)                NOT NULL
);
COMMENT ON TABLE adm_config_item_audit_log IS '配置项审计日志';
COMMENT ON COLUMN adm_config_item_audit_log.id IS '雪花ID';
COMMENT ON COLUMN adm_config_item_audit_log.tenant_id IS '租户ID';
COMMENT ON COLUMN adm_config_item_audit_log.business_id IS '业务ID';
COMMENT ON COLUMN adm_config_item_audit_log.operator_id IS '操作人ID';
COMMENT ON COLUMN adm_config_item_audit_log.operation IS '操作: 1增加, 2更新, 3删除';
COMMENT ON COLUMN adm_config_item_audit_log.content IS '操作内容';
COMMENT ON COLUMN adm_config_item_audit_log.create_time IS '创建时间';

CREATE INDEX idx_adm_config_item_audit_log_business_create_time
	ON adm_config_item_audit_log (business_id, create_time);