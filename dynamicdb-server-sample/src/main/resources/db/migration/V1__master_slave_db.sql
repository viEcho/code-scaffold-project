-- 主库表
-- master_db.`user` definition
CREATE TABLE master_db.`user` (
	id BIGINT(12) auto_increment NOT NULL,
	name varchar(40) NULL,
	sex int(2) DEFAULT 1 NOT NULL COMMENT '1男，2女',
	address varchar(100) NULL COMMENT '地址',
	created_by varchar(40) NULL,
	created_date DATETIME NULL,
	updated_by varchar(40) NULL,
	updated_date DATETIME NULL,
	CONSTRAINT user_pk PRIMARY KEY (id)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8
COLLATE=utf8_general_ci;

INSERT INTO master_db.`user` (name,sex,address,created_by,created_date,updated_by,updated_date) VALUES
('echo1',1,'深圳市',NULL,NULL,NULL,NULL),
('echo2',1,'武汉市',NULL,NULL,NULL,NULL),
('echo3',1,'北京市',NULL,NULL,NULL,NULL),
('echo4',1,'重庆市',NULL,NULL,NULL,NULL);


-- 从库表
-- slave_db.`user` definition
CREATE TABLE slave_db.`user` (
     id BIGINT(12) auto_increment NOT NULL,
     name varchar(40) NULL,
     sex int(2) DEFAULT 1 NOT NULL COMMENT '1男，2女',
     address varchar(100) NULL COMMENT '地址',
     created_by varchar(40) NULL,
     created_date DATETIME NULL,
     updated_by varchar(40) NULL,
     updated_date DATETIME NULL,
     CONSTRAINT user_pk PRIMARY KEY (id)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8
COLLATE=utf8_general_ci;

INSERT INTO slave_db.`user` (name,sex,address,created_by,created_date,updated_by,updated_date) VALUES
('echo1',1,'深圳市',NULL,NULL,NULL,NULL),
('echo2',1,'武汉市',NULL,NULL,NULL,NULL),
('echo3',1,'北京市',NULL,NULL,NULL,NULL),
('echo4',1,'重庆市',NULL,NULL,NULL,NULL),
('echo5',1,'青岛市',NULL,NULL,NULL,NULL),
('echo6',1,'长沙市',NULL,NULL,NULL,NULL);