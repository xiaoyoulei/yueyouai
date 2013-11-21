set names utf8;

drop table if exists TblUser;

create table TblUser
(
    `uid`       int unsigned NOT NULL   AUTO_INCREMENT COMMENT 'userid',
    `nickName`  varchar(64) NOT NULL COMMENT 'user nick name',
    `email`     varchar(64) DEFAULT NULL COMMENT 'email name may use login in',
    `phoneNum`  varchar(64) DEFAULT NULL COMMENT 'phone num may use login in',
    `passWord`  varchar(128) NOT NULL COMMENT 'pass word,encoder',
    `sex`   tinyint(1) DEFAULT NULL COMMENT '1 male, 0 famaile',
    `single` tinyint(1) DEFAULT NULL COMMENT '0 yes, 1 no',
    `birthTime` TIMESTAMP DEFAULT '0000-00-00 00:00:00' COMMENT 'birth day time',
    `togetherTime` TIMESTAMP DEFAULT '0000-00-00 00:00:00' COMMENT 'has a girl/boy friend',
    `marryTime` TIMESTAMP DEFAULT '0000-00-00 00:00:00' COMMENT "marry time",
	`auth`	tinyint(8) NOT NULL DEFAULT 1 COMMENT "1 only look,2 write some thing",
    `registerTime`  TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'register time',
    `lastLoginTime` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'last login time' ,
    PRIMARY KEY (uid),
    UNIQUE KEY `UK_email`(email),
    UNIQUE KEY `UK_phoneNum`(phoneNum)
)
ENGINE=InnoDB DEFAULT CHARSET=UTF8 COMMENT 'user info';
