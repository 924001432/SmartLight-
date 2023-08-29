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
-- Table structure for table `logs`
--

DROP TABLE IF EXISTS `logs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `logs` (
  `logs_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `user_name` varchar(45) DEFAULT NULL,
  `logs_module` varchar(45) DEFAULT NULL,
  `logs_flag` int DEFAULT NULL,
  `logs_remark` text,
  `logs_createtime` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`logs_id`)
) ENGINE=InnoDB AUTO_INCREMENT=904 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `logs`
--

LOCK TABLES `logs` WRITE;
/*!40000 ALTER TABLE `logs` DISABLE KEYS */;
INSERT INTO `logs` VALUES (1,NULL,'11','用户登录',1,NULL,'2023-08-24 15:21:51'),(801,NULL,'11','用户登录',1,NULL,'2023-08-24 15:22:36'),(802,NULL,'11','用户登录',1,NULL,'2023-08-24 16:45:11'),(803,NULL,'11','用户登录',1,NULL,'2023-08-24 18:58:01'),(804,NULL,'11','用户登录',1,NULL,'2023-08-24 21:10:12'),(805,NULL,'11','用户登录',1,NULL,'2023-08-24 22:10:52'),(806,NULL,'11','用户登录',1,NULL,'2023-08-25 10:37:19'),(807,NULL,'11','用户登录',1,NULL,'2023-08-25 11:05:46'),(808,NULL,'11','用户登录',1,NULL,'2023-08-25 12:10:57'),(809,NULL,'11','用户登录',1,NULL,'2023-08-25 14:06:49'),(810,NULL,'11','用户登录',1,NULL,'2023-08-25 15:44:57'),(811,NULL,'11','消除报警',1,NULL,'2023-08-25 16:10:41'),(812,NULL,'11','用户登录',1,NULL,'2023-08-25 16:44:14'),(813,NULL,'11','用户登录',1,NULL,'2023-08-25 18:08:04'),(814,NULL,'huangbin','用户登录',1,NULL,'2023-08-25 19:02:19'),(815,NULL,'11','用户登录',1,NULL,'2023-08-26 11:15:47'),(816,NULL,'11','用户登录',1,NULL,'2023-08-26 12:09:13'),(817,NULL,'11','用户登录',1,NULL,'2023-08-26 12:26:09'),(818,NULL,'11','用户登录',1,NULL,'2023-08-26 14:46:39'),(819,NULL,'11','用户登录',1,NULL,'2023-08-26 16:51:09'),(820,NULL,'11','用户登录',1,NULL,'2023-08-26 21:29:35'),(821,NULL,'11','用户登录',1,NULL,'2023-08-26 21:41:50'),(822,NULL,'11','消除报警',1,NULL,'2023-08-26 22:00:51'),(823,NULL,'11','消除报警',1,NULL,'2023-08-26 22:01:27'),(824,NULL,'11','用户登录',1,NULL,'2023-08-26 22:26:28'),(825,NULL,'11','用户登录',1,NULL,'2023-08-26 22:30:11'),(826,NULL,NULL,'用户登录',0,'Authentication failed for token submission [org.apache.shiro.authc.UsernamePasswordToken - sunchen, rememberMe=false].  Possible unexpected error? (Typical or expected login exceptions should extend from AuthenticationException).','2023-08-26 22:31:38'),(827,NULL,'user','用户登录',1,NULL,'2023-08-26 22:31:54'),(828,NULL,'11','用户登录',1,NULL,'2023-08-26 23:36:03'),(829,NULL,'11','用户登录',1,NULL,'2023-08-27 10:12:21'),(830,NULL,'11','用户登录',1,NULL,'2023-08-27 11:36:32'),(831,NULL,'11','用户登录',1,NULL,'2023-08-27 11:39:07'),(832,NULL,NULL,'用户登录',1,NULL,'2023-08-27 11:43:55'),(833,NULL,NULL,'用户登录',1,NULL,'2023-08-27 11:44:45'),(834,NULL,NULL,'用户登录',1,NULL,'2023-08-27 11:55:17'),(835,NULL,NULL,'用户登录',0,NULL,'2023-08-27 11:56:41'),(836,NULL,NULL,'用户登录',0,NULL,'2023-08-27 11:56:43'),(837,NULL,NULL,'用户登录',0,NULL,'2023-08-27 11:57:24'),(838,NULL,NULL,'用户登录',1,NULL,'2023-08-27 11:57:40'),(839,NULL,NULL,'用户登录',1,NULL,'2023-08-27 11:58:42'),(840,NULL,NULL,'用户登录',1,NULL,'2023-08-27 11:58:53'),(841,NULL,NULL,'用户登录',1,NULL,'2023-08-27 11:58:54'),(842,NULL,'11','用户登录',1,NULL,'2023-08-27 12:54:12'),(843,NULL,'11','用户登录',1,NULL,'2023-08-27 13:05:31'),(844,NULL,'11','用户登录',1,NULL,'2023-08-27 13:11:18'),(845,NULL,'11','用户登录',1,NULL,'2023-08-27 15:32:27'),(846,NULL,'11','校准时间',1,NULL,'2023-08-27 15:33:57'),(847,NULL,'user','用户登录',1,NULL,'2023-08-27 15:34:33'),(848,NULL,'11','用户登录',1,NULL,'2023-08-27 15:44:06'),(849,NULL,'11','校准时间',1,NULL,'2023-08-27 15:44:10'),(850,NULL,'11','校准时间',1,NULL,'2023-08-27 15:44:30'),(851,NULL,'11','校准时间',1,NULL,'2023-08-27 15:44:58'),(852,NULL,'user','用户登录',1,NULL,'2023-08-27 15:45:59'),(853,NULL,'11','用户登录',1,NULL,'2023-08-27 15:54:49'),(854,NULL,'11','校准时间',1,NULL,'2023-08-27 15:54:52'),(855,NULL,'11','校准时间',1,NULL,'2023-08-27 15:57:00'),(856,NULL,'11','校准时间',1,NULL,'2023-08-27 15:57:06'),(857,NULL,'11','校准时间',1,NULL,'2023-08-27 15:57:15'),(858,NULL,'11','校准时间',1,NULL,'2023-08-27 15:58:31'),(859,NULL,'11','校准时间',1,NULL,'2023-08-27 16:00:39'),(860,NULL,'user','用户登录',1,NULL,'2023-08-27 16:00:48'),(861,NULL,'11','用户登录',1,NULL,'2023-08-27 16:01:39'),(862,NULL,'11','校准时间',1,NULL,'2023-08-27 16:01:43'),(863,NULL,'11','校准时间',1,NULL,'2023-08-27 16:03:50'),(864,NULL,'user','用户登录',1,NULL,'2023-08-27 16:03:57'),(865,NULL,'user','用户登录',1,NULL,'2023-08-27 19:22:02'),(866,NULL,'11','用户登录',1,NULL,'2023-08-28 09:16:18'),(867,NULL,'sunchen','用户登录',1,NULL,'2023-08-28 09:24:19'),(868,NULL,'sunchen','用户登录',1,NULL,'2023-08-28 10:02:47'),(869,NULL,'sunchen','用户登录',1,NULL,'2023-08-28 10:24:02'),(870,NULL,NULL,'用户登录',0,'Authentication failed for token submission [org.apache.shiro.authc.UsernamePasswordToken - sunchen, rememberMe=false].  Possible unexpected error? (Typical or expected login exceptions should extend from AuthenticationException).','2023-08-28 11:00:51'),(871,NULL,'11','用户登录',1,NULL,'2023-08-28 11:07:19'),(872,NULL,'sunchen','用户登录',1,NULL,'2023-08-28 11:12:09'),(873,NULL,NULL,'用户登录',1,NULL,'2023-08-28 11:13:32'),(874,NULL,'sunchen','用户登录',1,NULL,'2023-08-28 11:13:37'),(875,NULL,'11','用户登录',1,NULL,'2023-08-28 11:13:41'),(876,NULL,'11','用户登录',1,NULL,'2023-08-28 11:14:17'),(877,NULL,NULL,'用户登录',0,'Authentication failed for token submission [org.apache.shiro.authc.UsernamePasswordToken - 11, rememberMe=false].  Possible unexpected error? (Typical or expected login exceptions should extend from AuthenticationException).','2023-08-28 11:24:35'),(878,NULL,NULL,'用户登录',0,'Authentication failed for token submission [org.apache.shiro.authc.UsernamePasswordToken - 11, rememberMe=false].  Possible unexpected error? (Typical or expected login exceptions should extend from AuthenticationException).','2023-08-28 12:33:02'),(879,NULL,NULL,'用户登录',0,'Authentication failed for token submission [org.apache.shiro.authc.UsernamePasswordToken - 11, rememberMe=false].  Possible unexpected error? (Typical or expected login exceptions should extend from AuthenticationException).','2023-08-28 12:36:40'),(880,NULL,'11','用户登录',1,NULL,'2023-08-28 12:45:11'),(881,NULL,'11','用户登录',1,NULL,'2023-08-28 12:47:47'),(882,NULL,'11','用户登录',1,NULL,'2023-08-28 12:51:08'),(883,NULL,'11','用户登录',1,NULL,'2023-08-28 12:58:19'),(884,NULL,'sunchen','用户登录',1,NULL,'2023-08-28 13:01:39'),(885,NULL,'11','用户登录',1,NULL,'2023-08-28 14:13:10'),(886,NULL,'11','校准时间',1,NULL,'2023-08-28 14:16:09'),(887,NULL,'user','用户登录',1,NULL,'2023-08-28 14:34:20'),(888,NULL,'11','用户登录',1,NULL,'2023-08-28 14:35:23'),(889,NULL,'user','用户登录',1,NULL,'2023-08-28 15:08:29'),(890,NULL,'11','用户登录',1,NULL,'2023-08-28 16:15:38'),(891,NULL,'11','用户登录',1,NULL,'2023-08-28 17:24:52'),(892,NULL,'11','用户登录',1,NULL,'2023-08-28 18:33:00'),(893,NULL,'11','用户登录',1,NULL,'2023-08-28 20:46:33'),(894,NULL,'11','用户登录',1,NULL,'2023-08-29 10:22:09'),(895,NULL,'11','开启全部路灯',1,NULL,'2023-08-29 10:49:43'),(896,NULL,NULL,'用户登录',1,NULL,'2023-08-29 13:45:54'),(897,NULL,'pp','用户登录',1,NULL,'2023-08-29 13:55:13'),(898,NULL,'pp','用户登录',1,NULL,'2023-08-29 13:57:54'),(899,NULL,'11','用户登录',1,NULL,'2023-08-29 14:01:56'),(900,NULL,'11','用户登录',1,NULL,'2023-08-29 16:26:48'),(901,NULL,'11','用户登录',1,NULL,'2023-08-29 20:00:04'),(902,NULL,'11','开启全部路灯',1,NULL,'2023-08-29 20:12:38'),(903,NULL,'11','开启全部路灯',1,NULL,'2023-08-29 20:12:54');
/*!40000 ALTER TABLE `logs` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-08-29 22:33:18
