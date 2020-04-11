--基于默认数据库模型的认证与授权
create table users(
    username varchar(50) not null primary key,
    password varchar(500) not null,
    enabled boolean not null
);
create table authorities (
    username varchar(50) not null,
    authority varchar(50) not null,
    constraint fk_authorities_users foreign key(username) references users(username)
);
create unique index ix_auth_username on authorities (username,authority);

insert  into users values('test','123',true) ;
insert into authorities values ('test',"ROLE_USER");

---自定义数据库模型的认证与授权
CREATE TABLE users(
    id         BIGINT (20)   NOT NULL AUTO_INCREMENT,
    username   VARCHAR (50)  NOT NULL ,
    PASSWORD   VARCHAR (60)  ,
    ENABLE     TINYINT(4)    NOT NULL DEFAULT '1' COMMENT '用户是否可用',
    roles      TEXT          CHARACTER SET utf8 COMMENT '用户角色，多个角色之前用逗号隔开',
    PRIMARY KEY (id),
    KEY username (username)
) ;

INSERT INTO users(username,PASSWORD,roles) VALUES('admin','123','ROLE_USER,ROLE_ADMIN') ;
INSERT INTO users(username,PASSWORD,roles) VALUES('user','123','ROLE_USER') ;