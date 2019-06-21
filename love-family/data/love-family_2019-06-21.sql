# Host: localhost  (Version 5.7.24-log)
# Date: 2019-06-21 20:03:50
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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='系统功能表';

#
# Data for table "sys_function"
#

INSERT INTO `sys_function` VALUES (1,'1QXGL','权限管理','1','MENU','#QXGL'),(2,'1GNGL','功能管理','1','MENU','/templates/system/function.html'),(3,'2CDGL','菜单管理','1','MENU','/templates/system/menu.html');

#
# Structure for table "sys_menu"
#

DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `Id` bigint(1) NOT NULL AUTO_INCREMENT,
  `code` varchar(20) DEFAULT NULL COMMENT '功能代码',
  `icon` varchar(50) DEFAULT NULL COMMENT '图标代码',
  `label` varchar(100) DEFAULT NULL COMMENT '菜单名称',
  `parent_id` bigint(1) DEFAULT NULL COMMENT '父级菜单ID',
  `order_num` bigint(1) DEFAULT NULL COMMENT '排序',
  `is_channel` varchar(2) DEFAULT NULL COMMENT '是否有效（0 有效，1无效）',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='菜单设置表';

#
# Data for table "sys_menu"
#

INSERT INTO `sys_menu` VALUES (1,'1QXGL','fa-unlock-alt','权限管理',NULL,NULL,NULL),(2,'1GNGL',NULL,'功能管理',1,NULL,NULL),(3,'2CDGL',NULL,'菜单管理',1,NULL,NULL);

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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='系统角色功能表';

#
# Data for table "sys_role_function"
#

INSERT INTO `sys_role_function` VALUES (1,3,1),(2,3,2),(3,3,3);

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

#
# Function "getMenuChildrenList"
#

DROP FUNCTION IF EXISTS `getMenuChildrenList`;
CREATE FUNCTION `getMenuChildrenList`(rootId INT) RETURNS varchar(4000) CHARSET utf8
BEGIN
DECLARE oTemp VARCHAR(4000);
DECLARE oTempChild VARCHAR(4000);
 
SET oTemp = '';
SET oTempChild = CAST(rootId AS CHAR);
 
WHILE oTempChild IS NOT NULL
DO
SET oTemp = CONCAT(oTemp,',',oTempChild);
SELECT GROUP_CONCAT(id) INTO oTempChild FROM sys_menu WHERE FIND_IN_SET(parent_id,oTempChild) > 0;
END WHILE;
RETURN oTemp;
END;

#
# Function "getMenuParentList"
#

DROP FUNCTION IF EXISTS `getMenuParentList`;
CREATE FUNCTION `getMenuParentList`(rootId varchar(100)) RETURNS varchar(1000) CHARSET utf8
BEGIN   
DECLARE fid varchar(100) default '';   
DECLARE str varchar(1000) default rootId;   
  
WHILE rootId is not null  do   
    SET fid =(SELECT parent_id FROM sys_menu WHERE id = rootId);   
    IF fid is not null THEN   
        SET str = concat(str, ',', fid);   
        SET rootId = fid;   
    ELSE   
        SET rootId = fid;   
    END IF;   
END WHILE;   
return str;  
END;

#
# Function "getMenuLevel"
#

DROP FUNCTION IF EXISTS `getMenuLevel`;
CREATE FUNCTION `getMenuLevel`(rootId INT) RETURNS int(11)
BEGIN
	 		RETURN length(getMenuParentList(rootId)) - length(REPLACE (getMenuParentList(rootId), ',', ''));
END;

#
# Function "isHasMenuChildrenList"
#

DROP FUNCTION IF EXISTS `isHasMenuChildrenList`;
CREATE FUNCTION `isHasMenuChildrenList`(rootId INT) RETURNS varchar(2) CHARSET utf8
BEGIN
		DECLARE childrenNum INT;
		SET childrenNum = length(getMenuChildrenList(rootId)) - length(REPLACE (getMenuChildrenList(rootId), ',', ''))-1;
		IF childrenNum>0 THEN
				RETURN 'Y';
		ELSE
				RETURN	'N';
    END IF;
END;
