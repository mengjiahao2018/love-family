# Host: localhost  (Version 5.7.24-log)
# Date: 2019-06-03 17:52:30
# Generator: MySQL-Front 6.1  (Build 1.26)


#
# Structure for table "s_category"
#

DROP TABLE IF EXISTS `s_category`;
CREATE TABLE `s_category` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `cvalue` varchar(255) DEFAULT NULL COMMENT '大类代码',
  `cname` varchar(255) DEFAULT NULL COMMENT '大类名称',
  `describe` varchar(255) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='大类表';

#
# Data for table "s_category"
#


#
# Structure for table "s_codes"
#

DROP TABLE IF EXISTS `s_codes`;
CREATE TABLE `s_codes` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `code_value` varchar(255) DEFAULT NULL COMMENT '小类代码',
  `code_name` varchar(255) DEFAULT NULL COMMENT '小类名称',
  `ava` varchar(255) DEFAULT NULL COMMENT '是否有效',
  `category_id` varchar(255) DEFAULT NULL COMMENT '大类表ID',
  `help` varchar(255) DEFAULT NULL COMMENT '备注',
  `seq` varchar(255) DEFAULT NULL COMMENT '序号',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='小类表';

#
# Data for table "s_codes"
#


#
# Structure for table "sys_function"
#

DROP TABLE IF EXISTS `sys_function`;
CREATE TABLE `sys_function` (
  `Id` bigint(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL COMMENT '功能代码',
  `name` varchar(255) DEFAULT NULL COMMENT '功能名称',
  `status` varchar(255) DEFAULT NULL COMMENT '是否有效（0：无效，1：有效）',
  `type` varchar(255) DEFAULT NULL COMMENT '类型（URL类型，菜单类型）',
  `url` varchar(254) DEFAULT NULL COMMENT '页面地址',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统功能表';

#
# Data for table "sys_function"
#


#
# Structure for table "sys_role"
#

DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `Id` bigint(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL COMMENT '角色代码',
  `name` varchar(255) DEFAULT NULL COMMENT '角色名称',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='角色名';

#
# Data for table "sys_role"
#

INSERT INTO `sys_role` VALUES (1,'ROLE_SUPER_ADMIN','超级管理员'),(2,'ROLE_ADMIN','管理员'),(3,'ROLE_USER','普通用户');

#
# Structure for table "sys_role_function"
#

DROP TABLE IF EXISTS `sys_role_function`;
CREATE TABLE `sys_role_function` (
  `Id` bigint(11) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(11) DEFAULT NULL COMMENT '角色ID',
  `function_id` bigint(11) DEFAULT NULL COMMENT '功能ID',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统角色功能表';

#
# Data for table "sys_role_function"
#


#
# Structure for table "sys_user"
#

DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `Id` bigint(11) NOT NULL AUTO_INCREMENT,
  `login_name` varchar(100) NOT NULL DEFAULT '' COMMENT '登陆账号',
  `password` varchar(100) NOT NULL DEFAULT '' COMMENT '登陆密码',
  `last_modify_password` varchar(255) DEFAULT NULL COMMENT '最后一次修改的密码',
  `user_name` varchar(255) DEFAULT NULL COMMENT '姓名',
  `status` varchar(255) DEFAULT '' COMMENT '是否启用(0:启用，1：停用)',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='用户登陆信息';

#
# Data for table "sys_user"
#

INSERT INTO `sys_user` VALUES (1,'super_admin','$2a$10$Yw73QrfUu0rVbrjVMoy9OOspHpFJ3wi7hl7TIsVQ8l0/vGRWKwSqi',NULL,'超级管理员','0'),(2,'admin','$2a$10$fQdUda72mE/JbZ//oJDT3.RJLRKK2.v9k07XuvKoGDI76QVjwm24O',NULL,'管理员','0'),(3,'mengjiahao','$2a$10$x1sXmalzkxGZvmWYSQaGLeB/M389KAxpaYjP4nFRtPtb2FY2QlF9G',NULL,'孟钾濠','0');

#
# Structure for table "sys_user_role"
#

DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `Id` bigint(11) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(11) NOT NULL DEFAULT '0' COMMENT '角色ID',
  `user_id` bigint(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='用户角色表';

#
# Data for table "sys_user_role"
#

INSERT INTO `sys_user_role` VALUES (1,1,1),(2,2,2),(3,3,3),(4,2,3),(5,1,3);
