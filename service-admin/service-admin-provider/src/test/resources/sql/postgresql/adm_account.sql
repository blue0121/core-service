CREATE TABLE adm_account (
	id                   INT8                        PRIMARY KEY,
	tenant_id            INT8                        NOT NULL,
	operator_id          INT8                        NOT NULL,
	code                 VARCHAR(50)                 NOT NULL,
	name                 VARCHAR(50)                 NOT NULL,
	status               INT2             DEFAULT 0  NOT NULL,
	remarks              VARCHAR(500),
	image_url            VARCHAR(200),
	create_time          TIMESTAMP(0)                NOT NULL,
	update_time          TIMESTAMP(0)                NOT NULL
);
COMMENT ON TABLE adm_account IS '用户';
COMMENT ON COLUMN adm_account.id IS '雪花ID';
COMMENT ON COLUMN adm_account.tenant_id IS '默认租户ID';
COMMENT ON COLUMN adm_account.operator_id IS '操作人ID';
COMMENT ON COLUMN adm_account.code IS '标识符';
COMMENT ON COLUMN adm_account.name IS '名称';
COMMENT ON COLUMN adm_account.status IS '状态: 0正常, 1作废';
COMMENT ON COLUMN adm_account.remarks IS '备注';
COMMENT ON COLUMN adm_account.image_url IS '头像URL';
COMMENT ON COLUMN adm_account.create_time IS '创建时间';
COMMENT ON COLUMN adm_account.update_time IS '更新时间';

CREATE UNIQUE INDEX udx_adm_account_code ON adm_account (code);


CREATE TABLE adm_account_tenant (
	account_id           INT8                        NOT NULL,
	tenant_id            INT8                        NOT NULL,
	PRIMARY KEY (account_id, tenant_id)
);
COMMENT ON TABLE adm_account_tenant IS '账号租户';
COMMENT ON COLUMN adm_account_tenant.account_id IS '账号ID';
COMMENT ON COLUMN adm_account_tenant.tenant_id IS '租户ID';


CREATE TABLE adm_account_audit_log (
	id                   INT8                        PRIMARY KEY,
	tenant_id            INT8                        NOT NULL,
	business_id          INT8                        NOT NULL,
	operator_id          INT8                        NOT NULL,
	operation            INT2                        NOT NULL,
	content              JSONB                       NOT NULL,
	create_time          TIMESTAMP(0)                NOT NULL
);
COMMENT ON TABLE adm_account_audit_log IS '账号审计日志';
COMMENT ON COLUMN adm_account_audit_log.id IS '雪花ID';
COMMENT ON COLUMN adm_account_audit_log.tenant_id IS '租户ID';
COMMENT ON COLUMN adm_account_audit_log.business_id IS '业务ID';
COMMENT ON COLUMN adm_account_audit_log.operator_id IS '操作人ID';
COMMENT ON COLUMN adm_account_audit_log.operation IS '操作: 1增加, 2更新, 3删除';
COMMENT ON COLUMN adm_account_audit_log.content IS '操作内容';
COMMENT ON COLUMN adm_account_audit_log.create_time IS '创建时间';

CREATE INDEX idx_adm_account_audit_log_business_create_time
	ON adm_account_audit_log (business_id, create_time);