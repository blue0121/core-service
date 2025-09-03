CREATE TABLE auth_user (
	id                   INT8                        PRIMARY KEY,
	realm                INT2                        NOT NULL,
	code                 VARCHAR(50)                 NOT NULL,
	name                 VARCHAR(50)                 NOT NULL,
	password             VARCHAR(100)                NOT NULL,
	status               INT2             DEFAULT 0  NOT NULL,
	remarks              VARCHAR(500),
	extension            BYTEA,
	ip                   VARCHAR(50),
	login_time           TIMESTAMP(0),
	create_time          TIMESTAMP(0)                NOT NULL,
	update_time          TIMESTAMP(0)                NOT NULL
);
COMMENT ON TABLE auth_user IS '用户';
COMMENT ON COLUMN auth_user.id IS '雪花ID';
COMMENT ON COLUMN auth_user.realm IS '域';
COMMENT ON COLUMN auth_user.code IS '标识符';
COMMENT ON COLUMN auth_user.name IS '名称';
COMMENT ON COLUMN auth_user.password IS '密码';
COMMENT ON COLUMN auth_user.status IS '状态: 0正常, 1作废';
COMMENT ON COLUMN auth_user.remarks IS '备注';
COMMENT ON COLUMN auth_user.extension IS '扩展';
COMMENT ON COLUMN auth_user.ip IS 'IP地址';
COMMENT ON COLUMN auth_user.login_time IS '最近登录时间';
COMMENT ON COLUMN auth_user.create_time IS '创建时间';
COMMENT ON COLUMN auth_user.update_time IS '更新时间';

CREATE UNIQUE INDEX udx_auth_user_code_realm ON auth_user (code, realm);


CREATE TABLE auth_user_login_log (
	id                   INT8                        PRIMARY KEY,
	realm                INT2                        NOT NULL,
	user_id              INT8                        NOT NULL,
	ip                   VARCHAR(50),
	login_date           DATE                        NOT NULL,
	login_count          INT              DEFAULT 0  NOT NULL,
	first_login_time     TIMESTAMP(0)                NOT NULL,
	last_login_time      TIMESTAMP(0)                NOT NULL
);
COMMENT ON TABLE auth_user_login_log IS '用户';
COMMENT ON COLUMN auth_user_login_log.id IS '雪花ID';
COMMENT ON COLUMN auth_user_login_log.realm IS '域';
COMMENT ON COLUMN auth_user_login_log.user_id IS '用户ID';
COMMENT ON COLUMN auth_user_login_log.ip IS 'IP地址';
COMMENT ON COLUMN auth_user_login_log.login_date IS '登录日期';
COMMENT ON COLUMN auth_user_login_log.login_count IS '登录次数';
COMMENT ON COLUMN auth_user_login_log.first_login_time IS '当天第一次登录时间';
COMMENT ON COLUMN auth_user_login_log.last_login_time IS '当天最一次登录时间';

CREATE UNIQUE INDEX udx_auth_user_login_log_user_login_date on auth_user_login_log (user_id, login_date);