/*
 Navicat MySQL Data Transfer

 Source Server         : localhost
 Source Server Version : 50624
 Source Host           : localhost
 Source Database       : leave_db

 Target Server Version : 50624
 File Encoding         : utf-8

 Date: 10/12/2015 23:37:32 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `tb_class`
-- ----------------------------
DROP TABLE IF EXISTS `tb_class`;
CREATE TABLE `tb_class` (
  `class_id` int(20) NOT NULL AUTO_INCREMENT,
  `class_name` varchar(20) DEFAULT NULL,
  `colleage_id` int(20) DEFAULT NULL,
  `professional_id` int(11) DEFAULT NULL,
  `create_time` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`class_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `tb_class`
-- ----------------------------
BEGIN;
INSERT INTO `tb_class` VALUES ('1', '90916P', '1', null, '2015-10-12 23:20:53');
COMMIT;

-- ----------------------------
--  Table structure for `tb_colleage`
-- ----------------------------
DROP TABLE IF EXISTS `tb_colleage`;
CREATE TABLE `tb_colleage` (
  `colleage_id` int(20) NOT NULL AUTO_INCREMENT,
  `colleage_name` varchar(50) DEFAULT NULL,
  `prefix` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`colleage_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `tb_colleage`
-- ----------------------------
BEGIN;
INSERT INTO `tb_colleage` VALUES ('1', '计算机与软件学院', '9');
COMMIT;

-- ----------------------------
--  Table structure for `tb_leave`
-- ----------------------------
DROP TABLE IF EXISTS `tb_leave`;
CREATE TABLE `tb_leave` (
  `leave_id` int(20) NOT NULL AUTO_INCREMENT,
  `student_id` varchar(50) DEFAULT NULL,
  `student_name` varchar(20) DEFAULT NULL,
  `class_id` varchar(20) DEFAULT NULL,
  `user_no` varchar(20) DEFAULT NULL,
  `professional_id` int(20) DEFAULT NULL,
  `colleage_id` int(20) DEFAULT NULL,
  `approved` int(11) DEFAULT '-1' COMMENT '-1 未审批0 未通过 1通过 2辅导员已审批等待学管处审批',
  `need_second_approve` int(3) DEFAULT NULL,
  `instructor_id` int(20) DEFAULT NULL,
  `instructor_approved` int(3) DEFAULT NULL COMMENT '教师审批结果',
  `student_pipe_id` int(30) DEFAULT NULL,
  `instructor_approved_date` varchar(30) DEFAULT NULL,
  `student_pipe_approved` int(3) DEFAULT NULL COMMENT '学管处审批结果',
  `student_pipe_approved_date` varchar(30) DEFAULT NULL,
  `instructor_note` varchar(1024) DEFAULT NULL,
  `student_pipe_note` varchar(1024) DEFAULT NULL,
  `leave_type` int(3) DEFAULT NULL COMMENT '0:课程请假 1:天次请假',
  `course_index` int(3) DEFAULT NULL COMMENT '第一节大课 的序号 为1，第二节大课的序号为2， \n第三节大课的序号为4，第四节大课的序号为8 .从2的n次方开始算',
  `teacher_name` varchar(20) DEFAULT NULL,
  `student_mobile` varchar(20) DEFAULT NULL,
  `leave_sicked` int(3) NOT NULL,
  `leave_sick_date` varchar(30) NOT NULL,
  `course_name` varchar(20) DEFAULT NULL,
  `leave_days` int(3) DEFAULT NULL,
  `student_note` varchar(1024) DEFAULT NULL,
  `leave_start_date` varchar(30) DEFAULT NULL,
  `leave_end_date` varchar(30) DEFAULT NULL,
  `leave_date` varchar(255) DEFAULT NULL,
  `class_name` varchar(100) DEFAULT NULL,
  `create_time` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`leave_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `tb_student`
-- ----------------------------
DROP TABLE IF EXISTS `tb_student`;
CREATE TABLE `tb_student` (
  `student_id` varchar(50) NOT NULL,
  `student_num` varchar(20) NOT NULL,
  `student_name` varchar(20) DEFAULT NULL,
  `password` varchar(50) NOT NULL,
  `create_time` varchar(30) DEFAULT NULL,
  `colleage_id` int(20) DEFAULT NULL,
  `colleage_name` varchar(40) DEFAULT NULL,
  `class_id` int(20) DEFAULT NULL,
  `class_name` varchar(20) DEFAULT NULL,
  `professional_id` int(20) DEFAULT NULL,
  `professional_name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`student_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `tb_student`
-- ----------------------------
BEGIN;
INSERT INTO `tb_student` VALUES ('1', '90916p39', '晓晓', 'e10adc3949ba59abbe56e057f20f883e', null, '1', '计算机与软件学院', '1', '90916p', '0', ''), ('2015101223315500005', '25', '嗒嗒', 'e10adc3949ba59abbe56e057f20f883e', '2015-10-12 23:31:55', '1', '计算机与软件学院', '1', '90916P', null, null);
COMMIT;

-- ----------------------------
--  Table structure for `tb_teacher`
-- ----------------------------
DROP TABLE IF EXISTS `tb_teacher`;
CREATE TABLE `tb_teacher` (
  `teacher_id` int(20) NOT NULL AUTO_INCREMENT,
  `teacher_work_num` varchar(20) DEFAULT NULL,
  `teacher_name` varchar(20) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `colleage_id` int(20) DEFAULT NULL,
  `role` int(5) DEFAULT NULL,
  `create_time` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`teacher_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `tb_teacher`
-- ----------------------------
BEGIN;
INSERT INTO `tb_teacher` VALUES ('1', '1', '1', 'c4ca4238a0b923820dcc509a6f75849b', '1', '1', null), ('2', '2', '2', 'c81e728d9d4c2f636f067f89cc14862c', '1', '2', null), ('3', '3', '3', 'eccbc87e4b5ce2fe28308fd9f2a7baf3', '1', '3', null);
COMMIT;

-- ----------------------------
--  Table structure for `tb_teacher_managed_class`
-- ----------------------------
DROP TABLE IF EXISTS `tb_teacher_managed_class`;
CREATE TABLE `tb_teacher_managed_class` (
  `teacher_id` int(20) DEFAULT NULL,
  `class_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
