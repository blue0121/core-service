CREATE TABLE auth_user (
	id                   BIGINT                      PRIMARY KEY      COMMENT '雪花ID',
	realm                TINYINT                     NOT NULL         COMMENT '域',
	code                 VARCHAR(50)                 NOT NULL         COMMENT '标识符',
	name                 VARCHAR(50)                 NOT NULL         COMMENT '名称',
	password             VARCHAR(100)                NOT NULL         COMMENT '密码',
	status               TINYINT          DEFAULT 0  NOT NULL         COMMENT '状态: 0正常, 1作废',
	remarks              VARCHAR(500)                                 COMMENT '备注',
	extension            VARBINARY(500)                               COMMENT '扩展',
	ip                   VARCHAR(50)                                  COMMENT 'IP地址',
	login_time           DATETIME                                     COMMENT '最近登录时间',
	create_time          DATETIME                    NOT NULL         COMMENT '创建时间',
	update_time          DATETIME                    NOT NULL         COMMENT '更新时间'
) COMMENT = '用户';
CREATE UNIQUE INDEX udx_code_realm ON auth_user (code, realm);


CREATE TABLE auth_user_login_log (
	id                   BIGINT                      PRIMARY KEY      COMMENT '雪花ID',
	realm                TINYINT                     NOT NULL         COMMENT '域',
	user_id              BIGINT                      NOT NULL         COMMENT '用户ID',
	ip                   VARCHAR(50)                                  COMMENT 'IP地址',
	login_date           DATE                        NOT NULL         COMMENT '登录日期',
	login_count          INT              DEFAULT 0  NOT NULL         COMMENT '登录次数',
	first_login_time     DATETIME                    NOT NULL         COMMENT '当天第一次登录时间',
	last_login_time      DATETIME                    NOT NULL         COMMENT '当天最一次登录时间'
) COMMENT = '用户登录日志';
CREATE UNIQUE INDEX idx_user_login_date on auth_user_login_log (user_id, login_date);