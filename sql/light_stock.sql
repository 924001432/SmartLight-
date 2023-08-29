-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: localhost    Database: light
-- ------------------------------------------------------
-- Server version	8.0.32

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `stock`
--

DROP TABLE IF EXISTS `stock`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `stock` (
  `stock_id` int NOT NULL AUTO_INCREMENT COMMENT '库存编号',
  `device_serial` varchar(45) DEFAULT NULL COMMENT '设备编号',
  `device_type` int DEFAULT NULL COMMENT '设备类型',
  `device_name` varchar(45) DEFAULT NULL COMMENT '设备名称',
  `device_model` varchar(45) DEFAULT NULL COMMENT '设备型号',
  `device_brand` varchar(45) DEFAULT NULL COMMENT '设备品牌',
  `stock_batch` varchar(45) DEFAULT NULL COMMENT '库存批次',
  `stock_user` varchar(45) DEFAULT NULL COMMENT '领用人',
  `device_producetime` varchar(45) DEFAULT NULL COMMENT '生产时间',
  `stock_intime` varchar(45) DEFAULT NULL COMMENT '入库时间',
  `stock_outtime` varchar(45) DEFAULT NULL COMMENT '出库时间',
  `stock_updatetime` varchar(45) DEFAULT NULL,
  `stock_status` int DEFAULT NULL COMMENT '记录',
  `device_repairnum` int DEFAULT NULL COMMENT '维修次数',
  PRIMARY KEY (`stock_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stock`
--

LOCK TABLES `stock` WRITE;
/*!40000 ALTER TABLE `stock` DISABLE KEYS */;
INSERT INTO `stock` VALUES (1,'0010',2,'ld1','ldxg','ppxg','202308',NULL,'2023-08-25','2023-08-25 20:10:37','2023-08-26 15:13:45','1',1,NULL),(2,'0011',1,'ld2','ld','ppxg','202308',NULL,'2023-08-25','2023-08-26 10:12:23','2023-08-26 10:13:23','1',1,NULL),(3,'0011',2,'ld3','ldxg','pp','202308',NULL,'2023-08-26','2023-08-26 15:19:55',NULL,'2023-08-26 15:39:03',0,NULL),(4,'0013',1,'ldd','ld','ppxg','202308',NULL,'2023-08-26','2023-08-26 15:22:20','2023-08-26 15:39:12','2023-08-26 15:39:08',1,NULL);
/*!40000 ALTER TABLE `stock` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-08-29 22:33:19
