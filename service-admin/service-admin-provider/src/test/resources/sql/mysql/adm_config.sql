CREATE TABLE adm_config (
	id                   BIGINT                      PRIMARY KEY      COMMENT '雪花ID',
	tenant_id            BIGINT                      NOT NULL         COMMENT '租户ID',
	operator_id          BIGINT                      NOT NULL         COMMENT '操作人ID',
	code                 VARCHAR(100)                NOT NULL         COMMENT '标识符',
	name                 VARCHAR(50)                 NOT NULL         COMMENT '名称',
	value                VARCHAR(200)                                 COMMENT '值',
	status               TINYINT          DEFAULT 0  NOT NULL         COMMENT '状态: 0正常, 1作废',
	multi_value          TINYINT          DEFAULT 0  NOT NULL         COMMENT '是否多值: 0否, 1是',
	remarks              VARCHAR(500)                                 COMMENT '备注',
	create_time          DATETIME                    NOT NULL         COMMENT '创建时间',
	update_time          DATETIME                    NOT NULL         COMMENT '更新时间'
) COMMENT = '配置';
CREATE UNIQUE INDEX udx_tenant_code ON adm_config (tenant_id, code);

CREATE TABLE adm_config_item (
	id                   BIGINT                      PRIMARY KEY      COMMENT '雪花ID',
	tenant_id            BIGINT                      NOT NULL         COMMENT '租户ID',
	config_id            BIGINT                      NOT NULL         COMMENT '配置ID',
	operator_id          BIGINT                      NOT NULL         COMMENT '操作人ID',
	value                VARCHAR(200)                NOT NULL         COMMENT '值',
	name                 VARCHAR(50)                 NOT NULL         COMMENT '名称',
	status               TINYINT          DEFAULT 0  NOT NULL         COMMENT '状态: 0正常, 1作废',
	remarks              VARCHAR(500)                                 COMMENT '备注',
	create_time          DATETIME                    NOT NULL         COMMENT '创建时间',
	update_time          DATETIME                    NOT NULL         COMMENT '更新时间'
) COMMENT = '配置项';
CREATE UNIQUE INDEX udx_config_value ON adm_config_item (config_id, value);

ALTER TABLE adm_config_item ADD CONSTRAINT fk_adm_config_item_ref_adm_config
	FOREIGN KEY (config_id) REFERENCES adm_config (id);

CREATE TABLE adm_config_audit_log (
	id                   BIGINT                      PRIMARY KEY      COMMENT '雪花ID',
	tenant_id            BIGINT                      NOT NULL         COMMENT '租户ID',
	business_id          BIGINT                      NOT NULL         COMMENT '业务ID',
	operator_id          BIGINT                      NOT NULL         COMMENT '操作人ID',
	operation            TINYINT                     NOT NULL         COMMENT '操作: 1增加, 2更新, 3删除',
	content              JSON                        NOT NULL         COMMENT '操作内容',
	create_time          DATETIME                    NOT NULL         COMMENT '创建时间'
) COMMENT = '配置审计日志';
CREATE INDEX idx_business_create_time ON adm_config_audit_log (business_id, create_time);

CREATE TABLE adm_config_item_audit_log (
	id                   BIGINT                      PRIMARY KEY      COMMENT '雪花ID',
	tenant_id            BIGINT                      NOT NULL         COMMENT '租户ID',
	business_id          BIGINT                      NOT NULL         COMMENT '业务ID',
	operator_id          BIGINT                      NOT NULL         COMMENT '操作人ID',
	operation            TINYINT                     NOT NULL         COMMENT '操作: 1增加, 2更新, 3删除',
	content              JSON                        NOT NULL         COMMENT '操作内容',
	create_time          DATETIME                    NOT NULL         COMMENT '创建时间'
) COMMENT = '配置项审计日志';
CREATE INDEX idx_business_create_time ON adm_config_item_audit_log (business_id, create_time);