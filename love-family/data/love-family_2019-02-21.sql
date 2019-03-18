# Host: localhost  (Version 5.7.24-log)
# Date: 2019-02-21 13:14:07
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
# Structure for table "sys_user"
#

DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `login_name` varchar(100) NOT NULL DEFAULT '' COMMENT '登陆账号',
  `password` varchar(100) NOT NULL DEFAULT '' COMMENT '登陆密码',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='用户登陆信息';

#
# Data for table "sys_user"
#

INSERT INTO `sys_user` VALUES (1,'zhangsan','zhangsan'),(2,'lisi','lisi'),(3,'wangwu','wangwu'),(4,'test01','test01');

#
# Structure for table "tbl_user"
#

DROP TABLE IF EXISTS `tbl_user`;
CREATE TABLE `tbl_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sys_user_id` int(11) DEFAULT '0' COMMENT '系统登陆用户ID',
  `head_pic` varchar(255) DEFAULT NULL COMMENT '头像',
  `name` varchar(255) DEFAULT NULL COMMENT '姓名',
  `sex` varchar(255) DEFAULT NULL COMMENT '性别',
  `birthday` varchar(255) DEFAULT NULL COMMENT '生日',
  `height` varchar(255) DEFAULT NULL COMMENT '身高',
  `native_place` varchar(255) DEFAULT NULL COMMENT '籍贯',
  `education` varchar(255) DEFAULT NULL COMMENT '学历',
  `work_address` varchar(255) DEFAULT NULL COMMENT '工作地址',
  `current_living_area` varchar(255) DEFAULT NULL COMMENT '目前生活地区',
  `health_status` varchar(255) DEFAULT NULL COMMENT '身体是否健康',
  `disease_status` varchar(255) DEFAULT NULL COMMENT '是否有疾病史',
  `married_status` varchar(255) DEFAULT NULL COMMENT '是否有婚姻史',
  `family_members_id` varchar(255) DEFAULT NULL COMMENT '家庭成员',
  `hobbies_id` varchar(255) DEFAULT NULL COMMENT '兴趣爱好',
  `spouse_criterion_id` varchar(255) DEFAULT NULL COMMENT '择偶标准',
  `married_plan_year` varchar(255) DEFAULT NULL COMMENT '计划几年后结婚',
  PRIMARY KEY (`id`),
  KEY `sys_user_id` (`sys_user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='用户个人信息表';

#
# Data for table "tbl_user"
#

INSERT INTO `tbl_user` VALUES (1,1,'张三',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(2,2,'李四',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

#
# Structure for table "tbl_user_family_members"
#

DROP TABLE IF EXISTS `tbl_user_family_members`;
CREATE TABLE `tbl_user_family_members` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `tbl_user_id` varchar(255) DEFAULT NULL,
  `family_members_type` varchar(255) DEFAULT NULL COMMENT '家庭成员类型',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户家庭成员';

#
# Data for table "tbl_user_family_members"
#


#
# Structure for table "tbl_user_hobbies"
#

DROP TABLE IF EXISTS `tbl_user_hobbies`;
CREATE TABLE `tbl_user_hobbies` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `tbl_user_id` varchar(255) DEFAULT NULL,
  `hobbies_type` varchar(255) DEFAULT NULL COMMENT '兴趣爱好类型',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户兴趣爱好';

#
# Data for table "tbl_user_hobbies"
#


#
# Structure for table "tbl_user_spouse_criterion"
#

DROP TABLE IF EXISTS `tbl_user_spouse_criterion`;
CREATE TABLE `tbl_user_spouse_criterion` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `tbl_user_id` varchar(255) DEFAULT NULL,
  `spouse_criterion_type` varchar(255) DEFAULT NULL COMMENT '择偶标准类型',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户择偶标准';

#
# Data for table "tbl_user_spouse_criterion"
#

