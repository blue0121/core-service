CREATE TABLE adm_account (
	id                   BIGINT                      PRIMARY KEY      COMMENT '雪花ID',
	tenant_id            BIGINT                      NOT NULL         COMMENT '默认租户ID',
	operator_id          BIGINT                      NOT NULL         COMMENT '操作人ID',
	code                 VARCHAR(50)                 NOT NULL         COMMENT '标识符',
	name                 VARCHAR(50)                 NOT NULL         COMMENT '名称',
	status               TINYINT          DEFAULT 0  NOT NULL         COMMENT '状态: 0正常, 1作废',
	remarks              VARCHAR(500)                                 COMMENT '备注',
	image_url            VARCHAR(200)                                 COMMENT '头像URL',
	create_time          DATETIME                    NOT NULL         COMMENT '创建时间',
	update_time          DATETIME                    NOT NULL         COMMENT '更新时间'
) COMMENT = '账号';
CREATE UNIQUE INDEX udx_code ON adm_account (code);


CREATE TABLE adm_account_tenant (
	account_id           BIGINT                      NOT NULL         COMMENT '账号ID',
	tenant_id            BIGINT                      NOT NULL         COMMENT '租户ID',
	PRIMARY KEY (account_id, tenant_id)
) COMMENT = '账号租户';


CREATE TABLE adm_account_audit_log (
	id                   BIGINT                      PRIMARY KEY      COMMENT '雪花ID',
	tenant_id            BIGINT                      NOT NULL         COMMENT '租户ID',
	business_id          BIGINT                      NOT NULL         COMMENT '业务ID',
	operator_id          BIGINT                      NOT NULL         COMMENT '操作人ID',
	operation            TINYINT                     NOT NULL         COMMENT '操作: 1增加, 2更新, 3删除',
	content              JSON                        NOT NULL         COMMENT '操作内容',
	create_time          DATETIME                    NOT NULL         COMMENT '创建时间'
) COMMENT = '账号审计日志';
CREATE INDEX idx_business_create_time ON adm_account_audit_log (business_id, create_time);