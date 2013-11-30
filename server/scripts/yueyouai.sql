set names utf8;

drop table if exists TblUser;
drop table if exists TblIdea;
drop table if exists TblRecord;
drop table if exists TblQuestion;
drop table if exists TblComment;
drop table if exists TblTag;
drop table if exists TblEdge;

create table TblUser
(
    `uid`       int unsigned NOT NULL   AUTO_INCREMENT COMMENT 'userid',
    `nickName`  varchar(64) NOT NULL COMMENT 'user nick name',
    `email`     varchar(64) DEFAULT NULL COMMENT 'email name may use login in',
    `phoneNum`  varchar(64) DEFAULT NULL COMMENT 'phone num may use login in',
    `passWord`  varchar(128) NOT NULL COMMENT 'pass word,encoder',
	`status`	tinyint(3) NOT NULL DEFAULT 0 COMMENT "0 ok,1 delete",
    `sex`		tinyint(2) NOT NULL DEFAULT 2 COMMENT '1 male, 0 famaile, 2 unkown',
    `single`	tinyint(2) NOT NULL DEFAULT 2 COMMENT '0 yes, 1 no, 2 unkown',
    `birthTime` TIMESTAMP DEFAULT '0000-00-00 00:00:00' COMMENT 'birth day time',
    `togetherTime` TIMESTAMP DEFAULT '0000-00-00 00:00:00' COMMENT 'has a girl/boy friend',
    `marryTime` TIMESTAMP DEFAULT '0000-00-00 00:00:00' COMMENT "marry time",
	`auth`		tinyint(8) NOT NULL DEFAULT 1 COMMENT "1 only look,2 write some thing",
    `registerTime`  TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'register time',
    `lastLoginTime` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'last login time' ,
    PRIMARY KEY (uid),
    UNIQUE KEY `UK_nickName`(nickName),
    UNIQUE KEY `UK_email`(email),
    UNIQUE KEY `UK_phoneNum`(phoneNum)
)
ENGINE=InnoDB DEFAULT CHARSET=UTF8 COMMENT 'user info';
/*
	add admin user
*/
INSERT INTO TblUser(nickName,email,phoneNum,passWord) VALUES('admin','admin@yueyouai.com','+86YUEYOUAILOVE','M<!$ADMINPxw1024>Love');

create table TblIdea
(
	`iid`		int unsigned NOT NULL   AUTO_INCREMENT COMMENT 'idea id',
	`title`		varchar(256) NOT NULL COMMENT 'ieda title',
	`abstract`	varchar(256) NOT NULL COMMENT 'ieda abstract',
	`content`	TEXT	NOT NULL COMMENT 'idead content ,max size 65535',
	`userLove`	int unsigned NOT NULL DEFAULT 0 COMMENT 'the user love',
	`userHate`	int unsigned NOT NULL DEFAULT 0 COMMENT 'the user hate',
	`adminWeight`	int  NOT NULL DEFAULT 0 COMMENT 'the admin weight,love > 0 and hate < 0 ',
	`rank`		int unsigned NOT NULL DEFAULT 0 COMMENT 'depend on userLove,userHate,adminWeight',
	`status`	tinyint(3) NOT NULL DEFAULT 1 COMMENT '0 public, 1 to review, 2 draft ,3 delete',
	`uid`		int unsigned NOT NULL DEFAULT 1 COMMENT 'user provide id',
	`releaseTime` TIMESTAMP	NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'release time',
	PRIMARY KEY (iid),
	KEY `K_S_U`(status,uid),
	KEY `K_Title`(title)
)
ENGINE=InnoDB DEFAULT CHARSET=UTF8 COMMENT 'idea info';

create table TblRecord
(
	`rid`		int unsigned NOT NULL   AUTO_INCREMENT COMMENT 'rid',
	`tile`		varchar(256) NOT NULL COMMENT 'record title',
	`site`		varchar(256) NOT NULL COMMENT 'record site',
	`time`		TIMESTAMP	NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'record time',
	`content`	TEXT	NOT NULL COMMENT 'record content ,max size 65535',
	`userLove`	int unsigned NOT NULL DEFAULT 0 COMMENT 'the user love',
	`userHate`	int unsigned NOT NULL DEFAULT 0 COMMENT 'the user hate',
	`adminWeight`	int  NOT NULL DEFAULT 0 COMMENT 'the admin weight,love > 0 and hate < 0 ',
	`rank`      int unsigned NOT NULL DEFAULT 0 COMMENT 'depend on userLove,userHate,adminWeight',
	`status`	tinyint(3) NOT NULL DEFAULT 1 COMMENT '0 public, 1 prvate , 2 draft ,3 delete',
	`uid`		int unsigned NOT NULL DEFAULT 1 COMMENT 'user provide id',
	`releaseTime` TIMESTAMP	NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'release time',
	PRIMARY KEY (rid),
	KEY `K_S_U`(status,uid)
)
ENGINE=InnoDB DEFAULT CHARSET=UTF8 COMMENT 'record info';

create table TblQuestion
(
	`qid`		int unsigned NOT NULL   AUTO_INCREMENT COMMENT 'rid',
	`content`	TEXT	NOT NULL COMMENT 'record content ,max size 65535',
	`hitNum`	int unsigned NOT NULL DEFAULT 0 COMMENT 'the user hit num',
	`adminWeight`   int  NOT NULL DEFAULT 0 COMMENT 'the admin weight,love > 0 and hate < 0 ',
	`rank`      int unsigned NOT NULL DEFAULT 0 COMMENT 'depend on hitNum adminWeight',
	`status`	tinyint(3) NOT NULL DEFAULT 1 COMMENT '0 public , 2 draft ,3 delete',
	`uid`		int unsigned NOT NULL DEFAULT 1 COMMENT 'user provide id',
	`releaseTime` TIMESTAMP	NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'release time',
	PRIMARY KEY (qid),
	KEY `K_S_U`(status,uid)
)
ENGINE=InnoDB DEFAULT CHARSET=UTF8 COMMENT 'question info';

create table TblComment
(
	`cid`		int unsigned NOT NULL   AUTO_INCREMENT COMMENT 'rid',
	`refType`	tinyint(3) NOT NULL	COMMENT '1 TblIdea, 2 TblRecord,3 TblQuestion', 
	`refId`		int unsigned NOT NULL COMMENT 'ref id',
	`content`	varchar(1024) NOT NULL COMMENT 'comment content ,must < 1024',
	`uid`		int unsigned NOT NULL DEFAULT 1 COMMENT 'user provide id',
	PRIMARY KEY (cid),
	KEY `K_UID`(uid),
	KEY	`K_T_I`(refType,refId)
)
ENGINE=InnoDB DEFAULT CHARSET=UTF8 COMMENT 'comment info';

create table TblTag
(
	`tid`		int unsigned NOT NULL   AUTO_INCREMENT COMMENT 'rid',
	`name`		varchar(64)	NOT NULL COMMENT 'tag name',
	`heat`		int unsigned NOT NULL DEFAULT 0 COMMENT 'the user heat value',
	`adminWeight`   int NOT NULL DEFAULT 0 COMMENT 'the admin weight,love > 0 and hate < 0 ',
	`rank`      int unsigned NOT NULL DEFAULT 0 COMMENT 'depend on heat adminWeight',
	PRIMARY KEY (tid),
	KEY `K_NAME`(name)
)
ENGINE=InnoDB DEFAULT CHARSET=UTF8 COMMENT 'tag info';

/*
 * ****  left - right  ***
 * TblIdea - TblRecord , TblRecord - TblQuestion , TblQuestion - TblIdea
 * TblTag - TblIdea    , TblTag    - TblRecord,    TblTag - TblQuestion
*/
create table TblEdge
(
	`eid`		int unsigned NOT NULL   AUTO_INCREMENT COMMENT 'rid',
	`typel`		tinyint(3) NOT NULL COMMENT '1 TblIdea, 2 TblRecord,3 TblQuestion,4,TblTag',
	`lid`		int unsigned NOT NULL  COMMENT 'edge left id',
	`typer`		tinyint(3) NOT NULL COMMENT '1 TblIdea, 2 TblRecord,3 TblQuestion,4,TblTag',
	`rid`		int unsigned NOT NULL  COMMENT 'edge right id',
	`userLove`	int unsigned NOT NULL DEFAULT 0 COMMENT 'the user love',
	`userHate`	int unsigned NOT NULL DEFAULT 0 COMMENT 'the user hate',
	`adminWeight`	int  NOT NULL DEFAULT 0 COMMENT 'the admin weight,love > 0 and hate < 0 ',
	`rank`      int unsigned NOT NULL DEFAULT 0 COMMENT 'depend on userLove,userHate,adminWeight',
	`status`	 tinyint(3) NOT NULL DEFAULT 1 COMMENT '0 public , 1 to review ,3 delete',
	`releaseTime` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'release time',
	PRIMARY KEY (eid),
	KEY `K_L_I`(typel,lid),
	KEY	`K_R_I`(typer,rid)
)
ENGINE=InnoDB DEFAULT CHARSET=UTF8 COMMENT 'related info';
