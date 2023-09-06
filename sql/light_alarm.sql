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
-- Table structure for table `alarm`
--

DROP TABLE IF EXISTS `alarm`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `alarm` (
  `alarm_id` int NOT NULL AUTO_INCREMENT COMMENT '报警编号',
  `device_serial` varchar(45) DEFAULT NULL COMMENT '设备编号',
  `alarm_type` int DEFAULT NULL COMMENT '报警类型',
  `alarm_time` varchar(45) DEFAULT NULL COMMENT '报警时间',
  `alarm_status` int DEFAULT NULL COMMENT '报警状态，0：未处理，1：正在处理，2：已处理',
  `alarm_handletime` varchar(45) DEFAULT NULL COMMENT '处理时间',
  `alarm_handlecomment` varchar(100) DEFAULT NULL COMMENT '处理备注',
  `alarm_handleway` varchar(100) DEFAULT NULL COMMENT '处理建议',
  PRIMARY KEY (`alarm_id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alarm`
--

LOCK TABLES `alarm` WRITE;
/*!40000 ALTER TABLE `alarm` DISABLE KEYS */;
INSERT INTO `alarm` VALUES (1,'0001',0,'2022-05-21 14:16:10',0,'2023-08-25 15:39:50','报修中',NULL),(2,'0001',1,'2022-05-21 14:16:10',0,'2023-07-21 22:17:00','手动消除',NULL),(3,'0001',2,'2022-05-21 14:16:10',1,'2023-07-21 22:16:31','手动消除',NULL),(4,'0001',1,'2022-05-21 14:16:10',1,'2023-07-21 22:16:37','手动消除',NULL),(5,'0001',2,'2022-05-21 14:16:10',2,'2023-07-21 22:16:38','手动消除',NULL),(6,'0001',3,'2022-05-21 14:16:10',2,'2023-07-21 22:17:10','手动消除',NULL),(7,'0001',4,'2022-05-21 14:16:10',2,'2023-07-21 22:17:11','手动消除',NULL),(8,'0001',5,'2022-05-21 14:16:10',2,'2023-07-21 22:17:13','手动消除',NULL),(9,'0001',0,'2022-05-21 14:16:10',2,'2023-07-21 22:17:14','手动消除',NULL),(10,'0001',0,'2022-05-21 14:16:10',2,'2023-07-21 22:17:16','手动消除',NULL),(11,'0001',0,'2022-05-21 14:16:10',2,'2023-07-21 22:17:17','手动消除',NULL),(12,'0001',0,'2022-05-21 14:16:10',1,'2023-07-21 22:17:19','手动消除',NULL),(13,'0001',3,'2022-05-21 14:16:10',1,'2023-07-20 19:49:29','Manual Alarm Removal',NULL),(14,'0001',1,'2022-05-21 14:16:10',1,'2023-07-21 10:50:49','Manual Alarm Removal',NULL),(15,'0001',5,'2022-05-21 14:16:10',1,'2023-07-21 12:53:13','Manual Alarm Removal',NULL),(16,'0000',1,'2023-07-15 17:17:25',1,'2023-07-21 10:50:58','Manual Alarm Removal',NULL),(17,'0002',4,'2023-07-15 17:17:25',1,'2023-07-21 10:51:01','Manual Alarm Removal',NULL),(18,'0003',5,'2023-07-15 17:17:25',1,'2023-07-21 12:53:21','Manual Alarm Removal',NULL),(19,'0000',0,'2023-07-25 14:59:32',1,'2023-08-23 15:48:35','手动消除',NULL),(20,'0000',1,'2023-07-25 14:59:32',2,'2023-08-25 15:31:10','手动消除',NULL),(21,'0000',2,'2023-07-25 14:59:32',2,'2023-08-26 22:01:27','手动消除',NULL),(22,'0000',3,'2023-07-25 14:59:32',0,NULL,NULL,NULL),(23,'0000',4,'2023-07-25 14:59:32',0,NULL,NULL,NULL),(24,'0000',5,'2023-07-25 14:59:32',0,NULL,NULL,NULL),(25,'0001',0,'2023-07-25 15:00:00',1,'2023-08-25 16:10:41','手动消除',NULL),(26,'0001',1,'2023-07-25 15:00:00',0,NULL,NULL,NULL),(27,'0001',2,'2023-07-25 15:00:00',0,NULL,NULL,NULL),(28,'0001',3,'2023-07-25 15:00:00',0,NULL,NULL,NULL),(29,'0001',4,'2023-07-25 15:00:00',0,NULL,NULL,NULL),(30,'0001',5,'2023-07-25 15:00:00',2,'2023-08-26 22:00:51','手动消除',NULL);
/*!40000 ALTER TABLE `alarm` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-09-06 10:57:23
