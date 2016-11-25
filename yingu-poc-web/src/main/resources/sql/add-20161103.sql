-- 新增渠道和流水号标示
alter table deduct_water add plantform_type int(1) COMMENT '支付渠道',add request_id varchar(50) COMMENT '流水号';
alter table monthly_repayment add request_id varchar(50) COMMENT '流水号';


-- 新增定时任务备份表
CREATE TABLE `qrtz_triggers_backup` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `SCHED_NAME` varchar(120) NOT NULL COMMENT '调度程序名称',
  `TRIGGER_NAME` varchar(200) NOT NULL COMMENT '触发器名称',
  `TRIGGER_GROUP` varchar(200) NOT NULL COMMENT '触发器组',
  `JOB_NAME` varchar(200) NOT NULL COMMENT '任务名称',
  `JOB_GROUP` varchar(200) NOT NULL COMMENT '任务组',
  `DESCRIPTION` varchar(250) DEFAULT NULL COMMENT '任务描述',
  `FIRE_TIME` bigint(13) DEFAULT NULL COMMENT '执行时间',
  PRIMARY KEY (`ID`,`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `SCHED_NAME` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`) USING BTREE,
  CONSTRAINT `qrtz_triggers_backup_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `qrtz_job_details` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='定时任务备份';

-- 重新创建日志表
DROP TABLE om_log;
CREATE TABLE `om_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `data` longblob NOT NULL COMMENT '接口数据',
  `interface_desc` varchar(50) NOT NULL COMMENT '接口描述',
  `oper_status` int(1) NOT NULL COMMENT '接口状态（0：失败，1：成功）',
  `oper_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='月还同步数据日志表';

-- 11.15 渠道字段修改
ALTER TABLE monthly_repayment CHANGE COLUMN global_id application_id VARCHAR(10);
ALTER TABLE monthly_repayment CHANGE COLUMN correlation_id global_id VARCHAR(50);
ALTER TABLE monthly_repayment CHANGE COLUMN plantform_type pay_channel VARCHAR(20);

ALTER TABLE deduct_water CHANGE COLUMN global_id application_id VARCHAR(10);
ALTER TABLE deduct_water CHANGE COLUMN correlation_id global_id VARCHAR(50);
ALTER TABLE deduct_water CHANGE COLUMN plantform_type pay_channel VARCHAR(20);
