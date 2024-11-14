/*
 Navicat Premium Dump SQL

 Source Server         : 注册
 Source Server Type    : MySQL
 Source Server Version : 50718 (5.7.18-cynos-log)
 Source Host           : gz-cynosdbmysql-grp-r47yt4ij.sql.tencentcdb.com:23394
 Source Schema         : studio_manager

 Target Server Type    : MySQL
 Target Server Version : 50718 (5.7.18-cynos-log)
 File Encoding         : 65001

 Date: 14/11/2024 15:59:06
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for activitiy
-- ----------------------------
DROP TABLE IF EXISTS `activitiy`;
CREATE TABLE `activitiy`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `date` date NOT NULL,
  `location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for competition_student
-- ----------------------------
DROP TABLE IF EXISTS `competition_student`;
CREATE TABLE `competition_student`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `competition_id` bigint(20) NULL DEFAULT NULL COMMENT '竞赛ID（指向竞赛表）',
  `student_id` bigint(20) NULL DEFAULT NULL COMMENT '学生ID（指向学生表）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 54 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '学生竞赛关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for competition_teacher
-- ----------------------------
DROP TABLE IF EXISTS `competition_teacher`;
CREATE TABLE `competition_teacher`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `competition_id` bigint(20) NULL DEFAULT NULL COMMENT '竞赛ID（指向竞赛表）',
  `teacher_id` bigint(20) NULL DEFAULT NULL COMMENT '教师ID（指向教师表）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 130 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '竞赛教师关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for departments
-- ----------------------------
DROP TABLE IF EXISTS `departments`;
CREATE TABLE `departments`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '部门的唯一标识',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '部门名称',
  `responsibilities` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '部门职责',
  `manager` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '部门经理姓名',
  `manager_phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '部门经理电话',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 109 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '部门信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for individual_competition
-- ----------------------------
DROP TABLE IF EXISTS `individual_competition`;
CREATE TABLE `individual_competition`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '竞赛ID',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '竞赛名称',
  `year` int(11) NULL DEFAULT NULL COMMENT '竞赛年份',
  `type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '竞赛类型',
  `status` tinyint(4) NULL DEFAULT 1 COMMENT '竞赛状态 (0: inactive - 已经结束, 1: 进行中 )，默认进行中',
  `registration_deadline` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '报名截止日期',
  `end_deadline` timestamp NULL DEFAULT NULL COMMENT '竞赛结束日期',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1856900512100298754 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '个人赛信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for integral
-- ----------------------------
DROP TABLE IF EXISTS `integral`;
CREATE TABLE `integral`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `memberId` bigint(20) NULL DEFAULT NULL COMMENT '成员id',
  `score` int(11) NULL DEFAULT NULL COMMENT '成员积分',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `memberId`(`memberId`) USING BTREE,
  CONSTRAINT `integral_ibfk_1` FOREIGN KEY (`memberId`) REFERENCES `member` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 114 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for member
-- ----------------------------
DROP TABLE IF EXISTS `member`;
CREATE TABLE `member`  (
  `id` bigint(20) NOT NULL,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `gender` tinyint(4) NOT NULL,
  `studentId` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `contact` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `joinDate` date NULL DEFAULT NULL,
  `department` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `major` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `grade` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `studentId`(`studentId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for notifications
-- ----------------------------
DROP TABLE IF EXISTS `notifications`;
CREATE TABLE `notifications`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `STATUS` int(11) NULL DEFAULT 1,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for project
-- ----------------------------
DROP TABLE IF EXISTS `project`;
CREATE TABLE `project`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '项目ID',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '项目名称',
  `source_code_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '源代码路径',
  `status` tinyint(4) NULL DEFAULT 1 COMMENT '项目状态 (0: not_started - 未启动, 1: in_progress - 进行中, 2: completed - 已完成)，默认进行中',
  `start_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '项目启动日期',
  `leader_id` bigint(20) NULL DEFAULT NULL COMMENT '项目组长学生id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1856903692817182722 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '项目管理表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for project_student
-- ----------------------------
DROP TABLE IF EXISTS `project_student`;
CREATE TABLE `project_student`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '关联ID',
  `project_id` bigint(20) NULL DEFAULT NULL COMMENT '项目ID',
  `student_id` bigint(20) NULL DEFAULT NULL COMMENT '学生ID',
  `role` tinyint(4) NULL DEFAULT NULL COMMENT '学生在项目中的角色，如组员(0)、组长(1)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 84 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '项目-学生关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for project_teacher
-- ----------------------------
DROP TABLE IF EXISTS `project_teacher`;
CREATE TABLE `project_teacher`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '关联ID',
  `project_id` bigint(20) NULL DEFAULT NULL COMMENT '项目ID',
  `teacher_id` bigint(20) NULL DEFAULT NULL COMMENT '教师ID',
  `role` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '教师在项目中的角色，如主指导老师、助理指导老师',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 81 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '项目-教师关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `student_id` bigint(11) NULL DEFAULT NULL COMMENT '学生学号',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '学生姓名',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系方式',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电子邮箱',
  `college` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '二级学院',
  `major` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '专业名称',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `status` tinyint(1) NULL DEFAULT 1 COMMENT '学生状态 (0: inactive - 毕业或离校, 1: active - 在校)，默认在校',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `student_id`(`student_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1856619644907999234 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '竞赛学生表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for teacher
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `teacher_id` int(11) NULL DEFAULT NULL COMMENT '教师工号',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '教师姓名',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系方式',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电子邮箱',
  `college` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '二级学院',
  `department` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所在系',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `status` tinyint(1) NULL DEFAULT 1 COMMENT '教师状态 (0: inactive - 离职, 1: active - 在职)，默认在职',
  `teacherType` tinyint(1) NULL DEFAULT NULL COMMENT '教师类型(0:校内指导老师 1:企业指导老师)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1856691197964230658 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '教师信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for team_competition
-- ----------------------------
DROP TABLE IF EXISTS `team_competition`;
CREATE TABLE `team_competition`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '竞赛ID',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '竞赛名称',
  `year` int(11) NULL DEFAULT NULL COMMENT '竞赛年份',
  `type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '竞赛类型',
  `status` tinyint(4) NULL DEFAULT 1 COMMENT '竞赛状态 (0: inactive - 已经结束, 1: ongoing - 进行中)，默认进行中',
  `registration_deadline` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '报名截止日期',
  `project_id` bigint(100) NULL DEFAULT NULL COMMENT '竞赛项目id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1856939352173228034 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '团体赛信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `userAccount` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '账号',
  `userPassword` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码',
  `unionId` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '微信开放平台id',
  `mpOpenId` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '公众号openId',
  `userName` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `userAvatar` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户头像',
  `userProfile` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户简介',
  `userRole` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'user' COMMENT '用户角色：user/admin/ban',
  `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `isDelete` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_unionId`(`unionId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1846120596387880962 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Triggers structure for table member
-- ----------------------------
DROP TRIGGER IF EXISTS `after_member_insert`;
delimiter ;;
CREATE TRIGGER `after_member_insert` AFTER INSERT ON `member` FOR EACH ROW BEGIN
    INSERT INTO integral (memberId, score) VALUES (NEW.id, 0);
END
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
