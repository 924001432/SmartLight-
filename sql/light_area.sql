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

 Date: 08/09/2023 20:36:09
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for area
-- ----------------------------
DROP TABLE IF EXISTS `area`;
CREATE TABLE `area`  (
  `area_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '区域编号',
  `area_name` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '区域名称',
  `parent_id` int(11) NULL DEFAULT NULL COMMENT '上级编号',
  `area_level` int(11) NULL DEFAULT NULL COMMENT '区域级别',
  `area_rank` int(11) NULL DEFAULT NULL COMMENT '所在级别位次',
  `area_serial` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '区域序列号',
  `area_net` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `area_lon` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `area_lat` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`area_id`) USING BTREE,
  UNIQUE INDEX `area_net_UNIQUE`(`area_net`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 107 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of area
-- ----------------------------
INSERT INTO `area` VALUES (0, '所有区域', -1, 0, NULL, '0000000000', NULL, NULL, NULL);
INSERT INTO `area` VALUES (1, '山东省', 0, 1, 5, '05000000', NULL, '117.0274', '36.6749');
INSERT INTO `area` VALUES (2, '青岛市', 1, 2, 4, '01040000', NULL, '120.3895', '36.0722');
INSERT INTO `area` VALUES (3, '城阳区', 2, 3, 4, '01010400', NULL, '120.3962', '36.3076');
INSERT INTO `area` VALUES (4, '春阳路', 3, 4, 6, '01010406', '0001', '120.3130', '36.3188');
INSERT INTO `area` VALUES (5, '瑞阳路', 3, 4, 7, '01010407', '0002', '120.4432', '36.3179');
INSERT INTO `area` VALUES (6, '即墨区', 2, 3, 5, '05040500', NULL, '120.4535', '36.3947');
INSERT INTO `area` VALUES (7, '墨城路', 6, 4, 3, '05040503', '0003', '120.4750', '36.4732');
INSERT INTO `area` VALUES (66, '正阳路', 3, 4, 16, '05040416', '0005', '120.4028', '36.3118');
INSERT INTO `area` VALUES (67, '荟城路', 3, 4, 9, '01010409', '000B', '120.4399', '36.3248');
INSERT INTO `area` VALUES (68, '陕西省', 0, 1, 6, '06000000', NULL, '108.9604', '34.2758');
INSERT INTO `area` VALUES (69, '西安市', 68, 2, 3, '06030000', NULL, '108.9465', '34.3473');
INSERT INTO `area` VALUES (70, '雁塔区', 69, 3, 5, '06030500', NULL, '108.9514', '34.2206');
INSERT INTO `area` VALUES (71, '太白南路', 70, 4, 3, '06030503', '1101', '108.9141', '34.2172');
INSERT INTO `area` VALUES (72, '良城路', 3, 4, 38, '05040438', '0007', '120.4520', '36.3235');
INSERT INTO `area` VALUES (87, '山西省', 0, 1, 7, '07000000', NULL, '112.5694', '37.8798');
INSERT INTO `area` VALUES (88, '甘肃省', 0, 1, 4, NULL, NULL, NULL, NULL);
INSERT INTO `area` VALUES (89, '天水市', 88, 2, 1, NULL, NULL, NULL, NULL);
INSERT INTO `area` VALUES (90, '济南市', 1, 2, 5, '05050000', NULL, '117.1264', '36.6566');
INSERT INTO `area` VALUES (91, '甘谷县', 89, 3, 1, NULL, NULL, NULL, NULL);
INSERT INTO `area` VALUES (92, '大像山镇', 91, 4, 1, '0401010100', NULL, NULL, NULL);
INSERT INTO `area` VALUES (93, '长治市', 87, 2, 2, '07020000', NULL, '113.1226', '36.2013');
INSERT INTO `area` VALUES (94, '武乡县', 93, 3, 2, '07020200', NULL, '112.8706', '36.8432');
INSERT INTO `area` VALUES (95, '六峰镇', 91, 4, 2, '0401010200', NULL, NULL, NULL);
INSERT INTO `area` VALUES (96, '磐安镇', 91, 4, 3, '0401010300', NULL, NULL, NULL);
INSERT INTO `area` VALUES (97, '古坡镇', 91, 4, 4, '0401010400', NULL, NULL, NULL);
INSERT INTO `area` VALUES (98, '历城区', 90, 3, 3, '05050300', NULL, '117.0716', '36.6857');
INSERT INTO `area` VALUES (99, '历下区', 90, 3, 4, '05050400', NULL, '117.0826', '36.6722');
INSERT INTO `area` VALUES (100, '莲湖区', 69, 3, 6, '06030600', NULL, '108.9504', '34.2710');
INSERT INTO `area` VALUES (101, '碑林区', 69, 3, 7, '06030700', NULL, '108.9470', '34.2630');
INSERT INTO `area` VALUES (102, '长安区', 69, 3, 8, '06030800', NULL, '108.9136', '34.1633');
INSERT INTO `area` VALUES (103, '烟台市', 1, 2, 6, '05060000', NULL, '121.4544', '37.4700');
INSERT INTO `area` VALUES (104, '汉中市', 68, 2, 2, '0202000000', NULL, NULL, NULL);
INSERT INTO `area` VALUES (105, '贾豁乡', 94, 4, 1, '0301010100', NULL, NULL, NULL);
INSERT INTO `area` VALUES (106, '李沧区', 2, 3, 6, '05040600', NULL, '120.4396', '36.1510');

SET FOREIGN_KEY_CHECKS = 1;
