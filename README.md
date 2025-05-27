# tlias-recource
黑马程序员web-ai课程源代码
https://www.bilibili.com/video/BV1yGydYEE3H/?spm_id_from=333.788.videopod.episodes&vd_source=c19d42f97f923d67c19457250a51962b
代码不规范，请多多包涵，希望大家多提出建议，up主会改正的（^_^）

记得更改其中的配置文件：mysql数据库的连接，阿里云oss的bucket等等

数据库其中包含六张表（若是黑马程序员那边来的就不需要看了）：
create table clazz
(
    id          int unsigned auto_increment comment 'ID,主键'
        primary key,
    name        varchar(30)      not null comment '班级名称',
    room        varchar(20)      null comment '班级教室',
    begin_date  date             not null comment '开课时间',
    end_date    date             not null comment '结课时间',
    master_id   int unsigned     null comment '班主任ID, 关联员工表ID',
    subject     tinyint unsigned not null comment '学科, 1:java, 2:前端, 3:大数据, 4:Python, 5:Go, 6: 嵌入式',
    create_time datetime         null comment '创建时间',
    update_time datetime         null comment '修改时间',
    constraint name
        unique (name)
)
    comment '班级表';

create table dept
(
    id          int unsigned auto_increment comment 'ID, 主键'
        primary key,
    name        varchar(10) not null comment '部门名称',
    create_time datetime    null comment '创建时间',
    update_time datetime    null comment '修改时间',
    constraint name
        unique (name)
)
    comment '部门表';

create table emp
(
    id          int unsigned auto_increment comment 'ID,主键'
        primary key,
    username    varchar(20)                  not null comment '用户名',
    password    varchar(32) default '123456' null comment '密码',
    name        varchar(10)                  not null comment '姓名',
    gender      tinyint unsigned             not null comment '性别, 1:男, 2:女',
    phone       char(11)                     not null comment '手机号',
    job         tinyint unsigned             null comment '职位, 1 班主任, 2 讲师 , 3 学工主管, 4 教研主管, 5 咨询师',
    salary      int unsigned                 null comment '薪资',
    image       varchar(255)                 null comment '头像',
    entry_date  date                         null comment '入职日期',
    dept_id     int unsigned                 null comment '部门ID',
    create_time datetime                     null comment '创建时间',
    update_time datetime                     null comment '修改时间',
    constraint phone
        unique (phone),
    constraint username
        unique (username)
)
    comment '员工表';
    
create table emp_expr
(
    id      int unsigned auto_increment comment 'ID, 主键'
        primary key,
    emp_id  int unsigned null comment '员工ID',
    begin   date         null comment '开始时间',
    end     date         null comment '结束时间',
    company varchar(50)  null comment '公司名称',
    job     varchar(50)  null comment '职位'
)
    comment '工作经历';

create table emp_log
(
    id           int unsigned auto_increment comment 'ID, 主键'
        primary key,
    operate_time datetime      null comment '操作时间',
    info         varchar(2000) null comment '日志信息'
)
    comment '员工日志表';

create table student
(
    id              int unsigned auto_increment comment 'ID,主键'
        primary key,
    name            varchar(10)                  not null comment '姓名',
    no              char(10)                     not null comment '学号',
    gender          tinyint unsigned             not null comment '性别, 1: 男, 2: 女',
    phone           varchar(11)                  not null comment '手机号',
    id_card         char(18)                     not null comment '身份证号',
    is_college      tinyint unsigned             not null comment '是否来自于院校, 1:是, 0:否',
    address         varchar(100)                 null comment '联系地址',
    degree          tinyint unsigned             null comment '最高学历, 1:初中, 2:高中, 3:大专, 4:本科, 5:硕士, 6:博士',
    graduation_date date                         null comment '毕业时间',
    clazz_id        int unsigned                 not null comment '班级ID, 关联班级表ID',
    violation_count tinyint unsigned default '0' not null comment '违纪次数',
    violation_score tinyint unsigned default '0' not null comment '违纪扣分',
    create_time     datetime                     null comment '创建时间',
    update_time     datetime                     null comment '修改时间',
    constraint id_card
        unique (id_card),
    constraint no
        unique (no),
    constraint phone
        unique (phone)
)
    comment '学员表';

应用技术：html、css、js、vue、Ajax、Axios、maven、单元测试类@Test、SpringBootWeb、Http协议、分层解耦-三层架构、IOC与DI、MYSQL、JDBC、Mybatis、aliyunOSS对象等等

目前项目进度已完成132、Interceptor拦截器的令牌认证
预计暑假学习完全部并开始下一个项目redis分布式的学习
