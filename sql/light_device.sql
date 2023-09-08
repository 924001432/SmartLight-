/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50724
 Source Host           : localhost:3306
 Source Schema         : roadlight

 Target Server Type    : MySQL
 Target Server Version : 50724
 File Encoding         : 65001

 Date: 08/09/2023 20:37:22
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for device
-- ----------------------------
DROP TABLE IF EXISTS `device`;
CREATE TABLE `device`  (
  `device_id` int(11) NOT NULL AUTO_INCREMENT,
  `device_mac` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `device_short` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `device_serial` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `device_type` int(11) NULL DEFAULT 0,
  `device_coord` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0',
  `device_light` int(11) NULL DEFAULT 2 COMMENT '设备开关灯状态',
  `device_model` int(11) NULL DEFAULT NULL COMMENT '模式',
  `device_status` int(11) NULL DEFAULT 0 COMMENT '设备在线状态',
  `device_updatetime` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `device_hearttime` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `device_lon` double(7, 4) NULL DEFAULT NULL COMMENT '设备经度',
  `device_lat` double(7, 4) NULL DEFAULT NULL COMMENT '设备纬度',
  PRIMARY KEY (`device_id`) USING BTREE,
  UNIQUE INDEX `device_serial_UNIQUE`(`device_serial`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of device
-- ----------------------------
INSERT INTO `device` VALUES (1, '3030303035113030', '9243', '1000', 2, '0001', 1, 1, 0, '123', '2023-06-20 15:23:51', 120.3115, 36.3188);
INSERT INTO `device` VALUES (2, '0300505F0902004B', 'D4ED', '1001', 0, '1101', 2, 0, 0, NULL, '2023-06-20 15:23:51', NULL, NULL);
INSERT INTO `device` VALUES (7, '0300505F0902004B', 'D4ED', '1002', 0, '0001', 2, 0, 0, NULL, '2023-06-20 15:23:51', 120.3153, 36.3188);
INSERT INTO `device` VALUES (8, '0300505F0902004B', 'EDD4', '1003', 0, '0001', 2, 0, 0, NULL, '2023-06-20 15:23:51', 120.3195, 36.3188);
INSERT INTO `device` VALUES (9, '505F0902004B1200', 'FAD5', '0001', 0, '0001', 1, 1, 0, NULL, '2023-08-29 10:34:34', 120.3230, 36.3188);
INSERT INTO `device` VALUES (10, 'F0EA0525004B1200', 'AF15', '0002', 0, '0001', 1, 0, 0, '2023-08-29 10:58:12', '2023-08-29 10:58:12', NULL, NULL);
INSERT INTO `device` VALUES (11, '84916702004B1200', '7466', '0003', 0, '0001', 1, 0, 0, NULL, '2023-06-20 15:23:51', NULL, NULL);
INSERT INTO `device` VALUES (12, '505F0902004B1200', 'EDD4', '0004', 0, '1101', 2, 0, 0, NULL, '2023-06-20 15:38:00', NULL, NULL);
INSERT INTO `device` VALUES (13, '505F0902004B1200', 'EDD4', '0005', 0, '0002', 1, 2, 0, NULL, '2023-06-20 15:39:30', NULL, NULL);
INSERT INTO `device` VALUES (14, '505F0902004B1200', 'EDD4', '0006', 0, '0002', 2, 0, 0, NULL, '2023-06-20 15:39:30', NULL, NULL);
INSERT INTO `device` VALUES (15, '505F0902004B1200', 'EDD4', '0007', 0, '0002', 2, 0, 0, NULL, '2023-08-29 10:30:34', NULL, NULL);
INSERT INTO `device` VALUES (16, '0000000000000000', '0000', '0000', 0, '1101', 0, 0, 0, '2023-07-18 14:16:10', '2023-08-29 10:22:30', NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
