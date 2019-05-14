/*
Navicat MySQL Data Transfer

Source Server         : Hello
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : data

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2019-05-14 09:55:46
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for bj
-- ----------------------------
DROP TABLE IF EXISTS `bj`;
CREATE TABLE `bj` (
                      `id` int(11) NOT NULL AUTO_INCREMENT,
                      `content` varchar(255) DEFAULT NULL,
                      `date` varchar(255) DEFAULT NULL,
                      `title` varchar(255) DEFAULT NULL,
                      `userId` varchar(255) DEFAULT NULL,
                      PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bj
-- ----------------------------
INSERT INTO `bj` VALUES ('1', 'dfdgd', '2019-3-23-12:40:29', 'fffff', '1');

-- ----------------------------
-- Table structure for coursemes
-- ----------------------------
DROP TABLE IF EXISTS `coursemes`;
CREATE TABLE `coursemes` (
                             `stu_id` int(11) DEFAULT NULL,
                             `stu_name` varchar(255) DEFAULT NULL,
                             `course_name` varchar(255) DEFAULT NULL,
                             `stu_class` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of coursemes
-- ----------------------------
INSERT INTO `coursemes` VALUES ('1', 'LiMing', 'C 语言', '计算机03班');
INSERT INTO `coursemes` VALUES ('2', 'LiNing', 'C 语言', '计算机02班');
INSERT INTO `coursemes` VALUES ('3', 'LiHua', 'C语言', '计算机01班');

-- ----------------------------
-- Table structure for kc
-- ----------------------------
DROP TABLE IF EXISTS `kc`;
CREATE TABLE `kc` (
                      `id` int(11) NOT NULL AUTO_INCREMENT,
                      `stime` varchar(255) DEFAULT NULL,
                      `addr` varchar(255) DEFAULT NULL,
                      `lsId` varchar(255) DEFAULT NULL,
                      `week` int(11) NOT NULL DEFAULT '0',
                      `ytime` int(11) NOT NULL DEFAULT '0',
                      `openKq` tinyint(1) NOT NULL,
                      `name` varchar(255) DEFAULT NULL,
                      PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of kc
-- ----------------------------
INSERT INTO `kc` VALUES ('1', null, 'X1207', '2', '1', '1', '0', '大学语文');
INSERT INTO `kc` VALUES ('2', null, 'X2232', '2', '3', '3', '1', '高等数学1');
INSERT INTO `kc` VALUES ('3', null, 'X2312', '2', '5', '4', '0', '概率论');
INSERT INTO `kc` VALUES ('4', null, 'X4213', '2', '2', '5', '0', '线性代数B');
INSERT INTO `kc` VALUES ('5', null, 'X9123', '6', '4', '6', '0', '线性代数A');
INSERT INTO `kc` VALUES ('6', null, 'X7412', '6', '5', '6', '0', '经济学概论');
INSERT INTO `kc` VALUES ('7', null, 'X5412', '2', '1', '3', '0', 'C语言');
INSERT INTO `kc` VALUES ('8', null, 'X9213', '2', '2', '2', '0', '算法');

-- ----------------------------
-- Table structure for kq
-- ----------------------------
DROP TABLE IF EXISTS `kq`;
CREATE TABLE `kq` (
                      `id` int(11) NOT NULL AUTO_INCREMENT,
                      `userName` varchar(255) DEFAULT NULL,
                      `userId` varchar(255) DEFAULT NULL,
                      `time` varchar(255) DEFAULT NULL,
                      `status` varchar(255) DEFAULT NULL,
                      `kcname` varchar(255) DEFAULT NULL,
                      `kcId` varchar(255) DEFAULT NULL,
                      `addr` varchar(255) DEFAULT NULL,
                      `cj` varchar(255) DEFAULT '',
                      PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of kq
-- ----------------------------
INSERT INTO `kq` VALUES ('5', '李小明', '1', '1555128003569', '正常', '线性代数B', '4', '中国四川省成都市郫县精勤路', '60');
INSERT INTO `kq` VALUES ('7', '李小明', '1', '1555259824213', '正常', 'C语言', '7', '中国四川省成都市郫县精勤路', '100');
INSERT INTO `kq` VALUES ('8', '王小红', '1', '1555260421978', '迟到', '大学语文', '1', '中国四川省成都市郫县精勤路', '60');
INSERT INTO `kq` VALUES ('9', '李小明', '1', '1555554842031', '正常', '大学语文', '1', '中国四川省成都市郫县精勤路', '100');
INSERT INTO `kq` VALUES ('10', '王小红', '7', '1556352123060', '正常', '大学语文', '1', '中国四川省成都市郫县精勤路', '80');
INSERT INTO `kq` VALUES ('11', '王小红', '1', '1556352436195', '正常', '高等数学1', '2', '中国四川省成都市郫县菁华路', '100');
INSERT INTO `kq` VALUES ('12', '李小明', '7', '1556463081260', '正常', 'C语言', '7', '中国四川省成都市郫县犀安路', '100');
INSERT INTO `kq` VALUES ('13', '李小明', '7', '1556465009178', '迟到', '经济学概论', '6', '中国四川省成都市郫县犀安路', ' 80');
INSERT INTO `kq` VALUES ('14', '李小明', '7', '1556465358232', '旷课', '概率论', '3', '中国四川省成都市郫县犀安路', null);
INSERT INTO `kq` VALUES ('15', '小何', '11', '1557233035571', '旷课', '大学语文', '1', '中国四川省成都市郫县菁华路', null);
INSERT INTO `kq` VALUES ('16', '王小德', '10', '1557233710099', '正常', 'C语言', '7', '中国四川省成都市郫县菁华路', '100');
INSERT INTO `kq` VALUES ('17', '李小明', '7', '1557408640755', '旷课', '算法', '8', '中国四川省成都市郫县精勤路', null);
INSERT INTO `kq` VALUES ('18', '李小明', '7', '1557449782332', '正常', 'C语言', '7', '中国四川省成都市郫县蜀源大道', null);

-- ----------------------------
-- Table structure for qj
-- ----------------------------
DROP TABLE IF EXISTS `qj`;
CREATE TABLE `qj` (
                      `id` int(11) NOT NULL AUTO_INCREMENT,
                      `cotent` varchar(255) DEFAULT NULL,
                      `kcid` varchar(255) DEFAULT NULL,
                      `time` varchar(255) DEFAULT NULL,
                      `userId` varchar(255) DEFAULT NULL,
                      `userName` varchar(255) DEFAULT NULL,
                      `kcName` varchar(255) DEFAULT NULL,
                      `status` varchar(255) DEFAULT NULL,
                      PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qj
-- ----------------------------
INSERT INTO `qj` VALUES ('1', '肚子不舒服', '1', '2019-04-13 12:00', '1', '王小红', '大学语文', '审核通过');
INSERT INTO `qj` VALUES ('2', '有电话', '7', '2019-04-15 00:37', '1', '王小红', 'C语言', '审核通过');
INSERT INTO `qj` VALUES ('3', '回去拿个作业', '4', '2019-04-15 00:47', '1', '王小红', '线性代数B', '审核通过');
INSERT INTO `qj` VALUES ('4', '出去解个手', '7', '2019-04-18 10:34', '1', '王小红', 'C语言', '审核通过');
INSERT INTO `qj` VALUES ('5', '待会得参加答辩会', '7', '2019-04-19 15:43', '1', '王小红', 'C语言', '审核通过');
INSERT INTO `qj` VALUES ('6', 'bbbb', '1', '2019-04-22 16:40', '1', '王小红', '大学语文', '审核通过');
INSERT INTO `qj` VALUES ('7', '嗯', '7', '2019-04-27 16:00', '7', '李小明', 'C语言', '待审核');
INSERT INTO `qj` VALUES ('8', '', '3', '2019-05-07 20:44', '10', '王小德', '概率论', '待审核');
INSERT INTO `qj` VALUES ('9', '', '1', '2019-05-07 20:45', '11', '小何', '大学语文', '待审核');
INSERT INTO `qj` VALUES ('10', '请假', '2', '2019-05-07 20:46', '11', '小何', '高等数学1', '审核通过');
INSERT INTO `qj` VALUES ('11', '', '3', '2019-05-07 20:47', '10', '王小德', '概率论', '待审核');
INSERT INTO `qj` VALUES ('12', 'demo', '2', '2019-05-07 20:53', '10', '王小德', '高等数学1', '审核通过');
INSERT INTO `qj` VALUES ('13', 'demo', '4', '2019-05-09 21:31', '7', '李小明', '线性代数B', '待审核');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
                        `id` int(11) NOT NULL AUTO_INCREMENT,
                        `account` varchar(255) NOT NULL,
                        `password` varchar(255) NOT NULL,
                        `name` varchar(255) DEFAULT NULL,
                        `age` int(11) NOT NULL DEFAULT '0',
                        `logo` varchar(255) DEFAULT NULL,
                        `type` int(11) NOT NULL DEFAULT '0',
                        `job` varchar(255) DEFAULT NULL,
                        `status` int(11) NOT NULL DEFAULT '0',
                        `sex` varchar(255) DEFAULT NULL,
                        `weight` int(11) NOT NULL DEFAULT '0',
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '小红', '123', '王小红', '1', 'http://bmob-cdn-24194.b0.upaiyun.com/2019/04/27/8f27f1ea16d0412ba5c7bdb1d493f659.jpg', '0', '大三', '0', '女', '11');
INSERT INTO `user` VALUES ('2', '大红', '123', '王红老师', '0', 'http://bmob-cdn-24194.b0.upaiyun.com/2019/04/27/5d339055b06d4057a8a8056ece61a913.jpg', '1', '', '0', '男', '0');
INSERT INTO `user` VALUES ('4', '大明', '123', '王大明', '0', 'http://bmob-cdn-24194.b0.upaiyun.com/2019/04/28/427ac11eafeb466ab127c58ac84516c8.jpg', '0', null, '0', '男', '0');
INSERT INTO `user` VALUES ('5', 'admin', '123', 'Admin', '0', null, '3', null, '0', null, '0');
INSERT INTO `user` VALUES ('6', '大亮', '123', '张亮老师', '0', 'http://bmob-cdn-24194.b0.upaiyun.com/2019/04/28/197cd89f1d0444348c78cae45fbcdce4.jpg', '1', null, '0', null, '0');
INSERT INTO `user` VALUES ('7', '小明', '123456', '李小明', '0', 'http://bmob-cdn-24194.b0.upaiyun.com/2019/04/28/db595b07ee944a5aaf161e690362fe4b.jpeg', '0', '大四', '0', '男', '0');
INSERT INTO `user` VALUES ('8', '王菲林', '123456', null, '0', null, '0', null, '0', null, '0');
INSERT INTO `user` VALUES ('9', '小张', '123456', '钟爱', '0', 'http://bmob-cdn-24194.b0.upaiyun.com/2019/05/05/dea3030bb3e84d7cac7a9b52c339a527.jpg', '0', '大三', '0', '男', '0');
INSERT INTO `user` VALUES ('10', '小德', '123456', '王小德', '0', 'http://bmob-cdn-24194.b0.upaiyun.com/2019/05/07/0c4718073d5c4d679288e939d5bb94bd.jpeg', '0', '大一', '0', '男', '0');
INSERT INTO `user` VALUES ('11', '小何', '11111', '小何', '0', 'http://bmob-cdn-24194.b0.upaiyun.com/2019/05/07/fa6eb7333f844b39b422ee29ae6f73d4.jpg', '0', '大一', '0', '男', '0');
