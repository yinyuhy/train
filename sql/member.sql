drop table if exists `member`;
create table `member`
(
    `id`     bigint not null comment 'id',
    `mobile` varchar(11) comment '手机号',
    primary key (`id`),
    unique key `mobile_unique` (`mobile`)
) engine = innodb
  default charset = utf8mb4 comment = '会员';

drop table if exists `passenger`;
create table `passenger`
(
    `id`          bigint      not null comment 'id',
    `member_id`   bigint      not null comment '會員id',
    `name`        varchar(20) not null comment '姓名',
    `id_card`     varchar(18) not null comment '身份證',
    `type`        char(1)     not null comment '旅客類型|枚舉【PassengerTypeEnum】',
    `create_time` datetime(3) comment '新增時間',
    `update_time` datetime(3) comment '修改時間',
    primary key (`id`),
    index `member_id_index` (`member_id`)
) engine = innodb
  default charset = utf8mb4 comment = '乘車人'