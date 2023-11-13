-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: sep
-- ------------------------------------------------------
-- Server version	8.0.35

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
-- Table structure for table `attendance`
--

DROP TABLE IF EXISTS `attendance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `attendance` (
                              `attendance_id` bigint NOT NULL,
                              `created_by` varchar(255) DEFAULT NULL,
    `create_date` datetime(6) DEFAULT NULL,
    `updated_by` varchar(255) DEFAULT NULL,
    `updated_date` datetime(6) DEFAULT NULL,
    `date_log` date DEFAULT NULL,
    `leave_status` varchar(255) DEFAULT NULL,
    `over_time` time DEFAULT NULL,
    `regular_hour` time DEFAULT NULL,
    `request_active` tinyint(1) DEFAULT '0',
    `time_in` time DEFAULT NULL,
    `time_out` time DEFAULT NULL,
    `total_work` time DEFAULT NULL,
    `signs_id` bigint DEFAULT NULL,
    `user_id` bigint DEFAULT NULL,
    PRIMARY KEY (`attendance_id`),
    KEY `FKmrksvah1c131dhkb8g4wyx4hi` (`signs_id`),
    KEY `FKjcaqd29v2qy723owsdah2t8vx` (`user_id`),
    CONSTRAINT `FKjcaqd29v2qy723owsdah2t8vx` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
    CONSTRAINT `FKmrksvah1c131dhkb8g4wyx4hi` FOREIGN KEY (`signs_id`) REFERENCES `sign` (`sign_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attendance`
--

LOCK TABLES `attendance` WRITE;
/*!40000 ALTER TABLE `attendance` DISABLE KEYS */;
INSERT INTO `attendance` VALUES (269,'anonymousUser','2023-11-01 05:51:49.727000','anonymousUser','2023-11-01 05:51:49.727000','2023-10-30',NULL,NULL,'01:39:34',0,'09:20:26','11:00:00','01:39:34',NULL,3),(272,'anonymousUser','2023-11-01 05:51:49.766000','anonymousUser','2023-11-01 05:51:49.766000','2023-10-30',NULL,NULL,'01:39:27',0,'09:20:33','11:00:00','01:39:27',NULL,52),(275,'anonymousUser','2023-11-01 05:51:49.787000','anonymousUser','2023-11-01 05:51:49.787000','2023-10-30',NULL,NULL,'01:39:32',0,'09:20:28','11:00:00','01:39:32',NULL,53),(278,'anonymousUser','2023-11-01 05:51:49.806000','anonymousUser','2023-11-01 05:51:49.806000','2023-10-30',NULL,NULL,'01:39:30',0,'09:20:30','11:00:00','01:39:30',NULL,55),(281,'anonymousUser','2023-11-01 05:51:49.824000','anonymousUser','2023-11-01 05:51:49.824000','2023-10-30',NULL,NULL,'01:39:39',0,'09:20:21','11:00:00','01:39:39',NULL,56),(284,'anonymousUser','2023-11-01 05:51:49.847000','anonymousUser','2023-11-01 05:51:49.847000','2023-10-30',NULL,NULL,'01:39:24',0,'09:20:36','11:00:00','01:39:24',NULL,57),(287,'anonymousUser','2023-11-01 05:52:34.970000','anonymousUser','2023-11-01 05:52:34.970000','2023-10-27',NULL,NULL,NULL,0,'17:00:00','17:00:00',NULL,NULL,3),(289,'anonymousUser','2023-11-01 05:52:35.072000','anonymousUser','2023-11-01 05:52:35.072000','2023-10-27',NULL,NULL,NULL,0,'17:00:00','17:00:00',NULL,NULL,52),(291,'anonymousUser','2023-11-01 05:52:35.113000','anonymousUser','2023-11-01 05:52:35.113000','2023-10-27',NULL,NULL,NULL,0,'17:00:00','17:00:00',NULL,NULL,53),(293,'anonymousUser','2023-11-01 05:52:35.150000','anonymousUser','2023-11-01 05:52:35.150000','2023-10-27',NULL,NULL,NULL,0,'17:00:00','17:00:00',NULL,NULL,55),(295,'anonymousUser','2023-11-01 05:52:35.177000','anonymousUser','2023-11-01 05:52:35.177000','2023-10-27',NULL,NULL,NULL,0,'17:00:00','17:00:00',NULL,NULL,56),(297,'anonymousUser','2023-11-01 05:52:35.197000','anonymousUser','2023-11-01 05:52:35.197000','2023-10-27',NULL,NULL,NULL,0,'17:00:00','17:00:00',NULL,NULL,57),(302,'anonymousUser','2023-11-01 09:51:37.296000','anonymousUser','2023-11-01 09:51:37.296000','2023-10-26',NULL,NULL,'00:00:18',0,'07:53:23','07:53:41','00:00:18',303,3),(306,'anonymousUser','2023-11-01 09:51:37.436000','anonymousUser','2023-11-01 09:51:37.436000','2023-10-26',NULL,NULL,'00:00:00',0,'07:53:30','07:53:30','00:00:00',307,52),(310,'anonymousUser','2023-11-01 09:51:37.486000','anonymousUser','2023-11-01 09:51:37.486000','2023-10-26',NULL,NULL,'00:00:18',0,'07:53:25','07:53:43','00:00:18',311,53),(314,'anonymousUser','2023-11-01 09:51:37.546000','anonymousUser','2023-11-01 09:51:37.546000','2023-10-26',NULL,NULL,'00:00:17',0,'07:53:28','07:53:45','00:00:17',315,55),(318,'anonymousUser','2023-11-01 09:51:37.624000','anonymousUser','2023-11-01 09:51:37.624000','2023-10-26',NULL,NULL,'00:00:18',0,'07:53:21','07:53:39','00:00:18',319,56),(322,'anonymousUser','2023-11-01 09:51:37.669000','anonymousUser','2023-11-01 09:51:37.669000','2023-10-26',NULL,NULL,'00:00:00',0,'07:53:35','07:53:35','00:00:00',323,57),(326,'dong','2023-11-01 17:51:14.725000','dong','2023-11-01 17:51:14.725000','2023-10-26',NULL,NULL,'00:00:18',0,'07:53:23','07:53:41','00:00:18',327,3),(330,'dong','2023-11-01 17:51:14.892000','dong','2023-11-01 17:51:14.892000','2023-10-26',NULL,NULL,'00:00:00',0,'07:53:30','07:53:30','00:00:00',331,52),(334,'dong','2023-11-01 17:51:14.966000','dong','2023-11-01 17:51:14.966000','2023-10-26',NULL,NULL,'00:00:18',0,'07:53:25','07:53:43','00:00:18',335,53),(338,'dong','2023-11-01 17:51:15.047000','dong','2023-11-01 17:51:15.047000','2023-10-26',NULL,NULL,'00:00:17',0,'07:53:28','07:53:45','00:00:17',339,55),(342,'dong','2023-11-01 17:51:15.138000','dong','2023-11-01 17:51:15.138000','2023-10-26',NULL,NULL,'00:00:18',0,'07:53:21','07:53:39','00:00:18',343,56),(346,'dong','2023-11-01 17:51:15.220000','dong','2023-11-01 17:51:15.220000','2023-10-26',NULL,NULL,'00:00:00',0,'07:53:35','07:53:35','00:00:00',347,57),(350,'dong','2023-11-01 17:52:26.682000','dong','2023-11-01 17:52:26.682000','2023-10-26',NULL,NULL,'00:00:18',0,'07:53:23','07:53:41','00:00:18',351,3),(354,'dong','2023-11-01 17:52:26.747000','dong','2023-11-01 17:52:26.747000','2023-10-26',NULL,NULL,'00:00:00',0,'07:53:30','07:53:30','00:00:00',355,52),(358,'dong','2023-11-01 17:52:26.802000','dong','2023-11-01 17:52:26.802000','2023-10-26',NULL,NULL,'00:00:18',0,'07:53:25','07:53:43','00:00:18',359,53),(362,'dong','2023-11-01 17:52:26.862000','dong','2023-11-01 17:52:26.862000','2023-10-26',NULL,NULL,'00:00:17',0,'07:53:28','07:53:45','00:00:17',363,55),(366,'dong','2023-11-01 17:52:26.937000','dong','2023-11-01 17:52:26.937000','2023-10-26',NULL,NULL,'00:00:18',0,'07:53:21','07:53:39','00:00:18',367,56),(370,'dong','2023-11-01 17:52:27.009000','dong','2023-11-01 17:52:27.009000','2023-10-26',NULL,NULL,'00:00:00',0,'07:53:35','07:53:35','00:00:00',371,57),(374,'anonymousUser','2023-11-01 17:59:43.958000','anonymousUser','2023-11-01 17:59:43.958000','2023-10-26',NULL,NULL,'00:00:18',0,'07:53:23','07:53:41','00:00:18',375,3),(378,'anonymousUser','2023-11-01 17:59:44.008000','anonymousUser','2023-11-01 17:59:44.008000','2023-10-26',NULL,NULL,'00:00:00',0,'07:53:30','07:53:30','00:00:00',379,52),(382,'anonymousUser','2023-11-01 17:59:44.055000','anonymousUser','2023-11-01 17:59:44.055000','2023-10-26',NULL,NULL,'00:00:18',0,'07:53:25','07:53:43','00:00:18',383,53),(386,'anonymousUser','2023-11-01 17:59:44.110000','anonymousUser','2023-11-01 17:59:44.110000','2023-10-26',NULL,NULL,'00:00:17',0,'07:53:28','07:53:45','00:00:17',387,55),(390,'anonymousUser','2023-11-01 17:59:44.170000','anonymousUser','2023-11-01 17:59:44.170000','2023-10-26',NULL,NULL,'00:00:18',0,'07:53:21','07:53:39','00:00:18',391,56),(394,'anonymousUser','2023-11-01 17:59:44.246000','anonymousUser','2023-11-01 17:59:44.246000','2023-10-26',NULL,NULL,'00:00:00',0,'07:53:35','07:53:35','00:00:00',395,57),(398,'dong','2023-11-01 18:00:48.544000','dong','2023-11-01 18:00:48.544000','2023-10-26',NULL,NULL,'00:00:18',0,'07:53:23','07:53:41','00:00:18',399,3),(402,'dong','2023-11-01 18:00:48.600000','dong','2023-11-01 18:00:48.600000','2023-10-26',NULL,NULL,'00:00:00',0,'07:53:30','07:53:30','00:00:00',403,52),(406,'dong','2023-11-01 18:00:48.641000','dong','2023-11-01 18:00:48.641000','2023-10-26',NULL,NULL,'00:00:18',0,'07:53:25','07:53:43','00:00:18',407,53),(410,'dong','2023-11-01 18:00:48.679000','dong','2023-11-01 18:00:48.679000','2023-10-26',NULL,NULL,'00:00:17',0,'07:53:28','07:53:45','00:00:17',411,55),(414,'dong','2023-11-01 18:00:48.711000','dong','2023-11-01 18:00:48.711000','2023-10-26',NULL,NULL,'00:00:18',0,'07:53:21','07:53:39','00:00:18',415,56),(418,'dong','2023-11-01 18:00:48.749000','dong','2023-11-01 18:00:48.749000','2023-10-26',NULL,NULL,'00:00:00',0,'07:53:35','07:53:35','00:00:00',419,57),(422,'dong','2023-11-01 18:00:51.026000','dong','2023-11-01 18:00:51.026000','2023-10-26',NULL,NULL,'00:00:18',0,'07:53:23','07:53:41','00:00:18',423,3),(426,'dong','2023-11-01 18:00:51.056000','dong','2023-11-01 18:00:51.056000','2023-10-26',NULL,NULL,'00:00:00',0,'07:53:30','07:53:30','00:00:00',427,52),(430,'dong','2023-11-01 18:00:51.083000','dong','2023-11-01 18:00:51.083000','2023-10-26',NULL,NULL,'00:00:18',0,'07:53:25','07:53:43','00:00:18',431,53),(434,'dong','2023-11-01 18:00:51.113000','dong','2023-11-01 18:00:51.113000','2023-10-26',NULL,NULL,'00:00:17',0,'07:53:28','07:53:45','00:00:17',435,55),(438,'dong','2023-11-01 18:00:51.163000','dong','2023-11-01 18:00:51.163000','2023-10-26',NULL,NULL,'00:00:18',0,'07:53:21','07:53:39','00:00:18',439,56),(442,'dong','2023-11-01 18:00:51.219000','dong','2023-11-01 18:00:51.219000','2023-10-26',NULL,NULL,'00:00:00',0,'07:53:35','07:53:35','00:00:00',443,57),(446,'hue','2023-11-01 19:50:48.544000','hue','2023-11-01 19:50:48.544000','2023-10-26',NULL,NULL,'00:00:18',0,'07:53:23','07:53:41','00:00:18',447,3),(450,'hue','2023-11-01 19:50:48.665000','hue','2023-11-01 19:50:48.665000','2023-10-26',NULL,NULL,'00:00:00',0,'07:53:30','07:53:30','00:00:00',451,52),(454,'hue','2023-11-01 19:50:48.749000','hue','2023-11-01 19:50:48.749000','2023-10-26',NULL,NULL,'00:00:18',0,'07:53:25','07:53:43','00:00:18',455,53),(458,'hue','2023-11-01 19:50:48.807000','hue','2023-11-01 19:50:48.807000','2023-10-26',NULL,NULL,'00:00:17',0,'07:53:28','07:53:45','00:00:17',459,55),(462,'hue','2023-11-01 19:50:48.867000','hue','2023-11-01 19:50:48.867000','2023-10-26',NULL,NULL,'00:00:18',0,'07:53:21','07:53:39','00:00:18',463,56),(466,'hue','2023-11-01 19:50:48.959000','hue','2023-11-01 19:50:48.959000','2023-10-26',NULL,NULL,'00:00:00',0,'07:53:35','07:53:35','00:00:00',467,57),(470,'anonymousUser','2023-11-02 00:16:31.412000','anonymousUser','2023-11-02 00:16:31.412000','2023-10-26',NULL,NULL,'00:00:18',0,'07:53:23','07:53:41','00:00:18',471,3),(474,'anonymousUser','2023-11-02 00:16:31.718000','anonymousUser','2023-11-02 00:16:31.718000','2023-10-26',NULL,NULL,'00:00:00',0,'07:53:30','07:53:30','00:00:00',475,52),(478,'anonymousUser','2023-11-02 00:16:31.817000','anonymousUser','2023-11-02 00:16:31.817000','2023-10-26',NULL,NULL,'00:00:18',0,'07:53:25','07:53:43','00:00:18',479,53),(482,'anonymousUser','2023-11-02 00:16:31.919000','anonymousUser','2023-11-02 00:16:31.919000','2023-10-26',NULL,NULL,'00:00:17',0,'07:53:28','07:53:45','00:00:17',483,55),(486,'anonymousUser','2023-11-02 00:16:32.004000','anonymousUser','2023-11-02 00:16:32.004000','2023-10-26',NULL,NULL,'00:00:18',0,'07:53:21','07:53:39','00:00:18',487,56),(490,'anonymousUser','2023-11-02 00:16:32.100000','dong','2023-11-02 13:02:38.883000','2023-10-26',NULL,NULL,'00:00:00',0,'07:53:35','07:53:35','00:00:00',499,57);
/*!40000 ALTER TABLE `attendance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contract`
--

DROP TABLE IF EXISTS `contract`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `contract` (
                            `id` bigint NOT NULL AUTO_INCREMENT,
                            `created_by` varchar(255) DEFAULT NULL,
    `create_date` datetime(6) DEFAULT NULL,
    `updated_by` varchar(255) DEFAULT NULL,
    `updated_date` datetime(6) DEFAULT NULL,
    `contract_name` varchar(200) NOT NULL,
    `file_name` varchar(255) NOT NULL,
    `user_id` bigint DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FKi6rphdb5rpnqnrp5twyk83jao` (`user_id`),
    CONSTRAINT `FKi6rphdb5rpnqnrp5twyk83jao` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contract`
--

LOCK TABLES `contract` WRITE;
/*!40000 ALTER TABLE `contract` DISABLE KEYS */;
/*!40000 ALTER TABLE `contract` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `department`
--

DROP TABLE IF EXISTS `department`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `department` (
                              `department_id` bigint NOT NULL,
                              `created_by` varchar(255) DEFAULT NULL,
    `create_date` datetime(6) DEFAULT NULL,
    `updated_by` varchar(255) DEFAULT NULL,
    `updated_date` datetime(6) DEFAULT NULL,
    `department_name` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`department_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `department`
--

LOCK TABLES `department` WRITE;
/*!40000 ALTER TABLE `department` DISABLE KEYS */;
INSERT INTO `department` VALUES (1,NULL,NULL,NULL,NULL,'Phong phat trien phan mem');
/*!40000 ALTER TABLE `department` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employees`
--

DROP TABLE IF EXISTS `employees`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employees` (
                             `id` bigint NOT NULL AUTO_INCREMENT,
                             `email` varchar(255) DEFAULT NULL,
    `first_name` varchar(255) DEFAULT NULL,
    `last_name` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employees`
--

LOCK TABLES `employees` WRITE;
/*!40000 ALTER TABLE `employees` DISABLE KEYS */;
INSERT INTO `employees` VALUES (1,'dong@gmail.com','Dong','Ho');
/*!40000 ALTER TABLE `employees` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hibernate_sequence` (
    `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (500);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `holiday`
--

DROP TABLE IF EXISTS `holiday`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `holiday` (
                           `holiday_id` int NOT NULL AUTO_INCREMENT,
                           `end_date` date DEFAULT NULL,
                           `holiday_name` varchar(100) NOT NULL,
    `start_date` date DEFAULT NULL,
    PRIMARY KEY (`holiday_id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `holiday`
--

LOCK TABLES `holiday` WRITE;
/*!40000 ALTER TABLE `holiday` DISABLE KEYS */;
INSERT INTO `holiday` VALUES (1,'2023-10-10','Ngay phu nu Viet Nam','2023-10-20');
/*!40000 ALTER TABLE `holiday` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `log_attendance_history`
--

DROP TABLE IF EXISTS `log_attendance_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `log_attendance_history` (
                                          `attendance_history_id` bigint NOT NULL,
                                          `updated_by` varchar(255) DEFAULT NULL,
    `updated_date` datetime(6) DEFAULT NULL,
    `attendance_id` bigint DEFAULT NULL,
    `new_signs_id` bigint DEFAULT NULL,
    `old_signs_id` bigint DEFAULT NULL,
    `reason` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`attendance_history_id`),
    KEY `FK8aecqs13iob6i6ky6matw7jef` (`attendance_id`),
    KEY `FKtk3pxf2a97fqkticupoxu2ay3` (`new_signs_id`),
    KEY `FKjk3rw5hr0h5ggpn881u3gugh9` (`old_signs_id`),
    CONSTRAINT `FK8aecqs13iob6i6ky6matw7jef` FOREIGN KEY (`attendance_id`) REFERENCES `attendance` (`attendance_id`),
    CONSTRAINT `FKjk3rw5hr0h5ggpn881u3gugh9` FOREIGN KEY (`old_signs_id`) REFERENCES `sign` (`sign_id`),
    CONSTRAINT `FKtk3pxf2a97fqkticupoxu2ay3` FOREIGN KEY (`new_signs_id`) REFERENCES `sign` (`sign_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `log_attendance_history`
--

LOCK TABLES `log_attendance_history` WRITE;
/*!40000 ALTER TABLE `log_attendance_history` DISABLE KEYS */;
INSERT INTO `log_attendance_history` VALUES (494,NULL,NULL,490,495,487,'abc'),(497,'Ho Phuong Dong-Truong phong','2023-11-02 13:02:38.000000',490,498,496,'di lam day du');
/*!40000 ALTER TABLE `log_attendance_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `log_check_in_out`
--

DROP TABLE IF EXISTS `log_check_in_out`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `log_check_in_out` (
                                    `log_checkInOut_id` bigint NOT NULL,
                                    `created_by` varchar(255) DEFAULT NULL,
    `create_date` datetime(6) DEFAULT NULL,
    `updated_by` varchar(255) DEFAULT NULL,
    `updated_date` datetime(6) DEFAULT NULL,
    `badge_number` varchar(255) DEFAULT NULL,
    `date_check` date DEFAULT NULL,
    `time_check` time DEFAULT NULL,
    `user_id` bigint DEFAULT NULL,
    PRIMARY KEY (`log_checkInOut_id`),
    KEY `FK3348gbb1ck4uksbe3hj0cnf5d` (`user_id`),
    CONSTRAINT `FK3348gbb1ck4uksbe3hj0cnf5d` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `log_check_in_out`
--

LOCK TABLES `log_check_in_out` WRITE;
/*!40000 ALTER TABLE `log_check_in_out` DISABLE KEYS */;
INSERT INTO `log_check_in_out` VALUES (36,'anonymousUser','2023-11-01 05:33:27.317000','anonymousUser','2023-11-01 05:33:27.317000','15','2023-10-30','11:47:33',57),(37,'anonymousUser','2023-11-01 05:33:27.368000','anonymousUser','2023-11-01 05:33:27.368000','10','2023-10-30','11:47:32',52),(38,'anonymousUser','2023-11-01 05:33:27.379000','anonymousUser','2023-11-01 05:33:27.379000','13','2023-10-30','11:47:29',55),(39,'anonymousUser','2023-11-01 05:33:27.388000','anonymousUser','2023-11-01 05:33:27.388000','11','2023-10-30','11:47:26',53),(40,'anonymousUser','2023-11-01 05:33:27.399000','anonymousUser','2023-11-01 05:33:27.399000','9','2023-10-30','11:47:23',3),(41,'anonymousUser','2023-11-01 05:33:27.409000','anonymousUser','2023-11-01 05:33:27.409000','14','2023-10-30','11:47:21',56),(42,'anonymousUser','2023-11-01 05:33:27.420000','anonymousUser','2023-11-01 05:33:27.420000','15','2023-10-30','09:20:36',57),(43,'anonymousUser','2023-11-01 05:33:27.430000','anonymousUser','2023-11-01 05:33:27.430000','10','2023-10-30','09:20:33',52),(44,'anonymousUser','2023-11-01 05:33:27.442000','anonymousUser','2023-11-01 05:33:27.442000','13','2023-10-30','09:20:30',55),(45,'anonymousUser','2023-11-01 05:33:27.457000','anonymousUser','2023-11-01 05:33:27.457000','11','2023-10-30','09:20:28',53),(46,'anonymousUser','2023-11-01 05:33:27.470000','anonymousUser','2023-11-01 05:33:27.470000','9','2023-10-30','09:20:26',3),(47,'anonymousUser','2023-11-01 05:33:27.480000','anonymousUser','2023-11-01 05:33:27.480000','14','2023-10-30','09:20:21',56),(48,'anonymousUser','2023-11-01 05:42:53.620000','anonymousUser','2023-11-01 05:42:53.620000','10','2023-10-27','20:10:03',52),(49,'anonymousUser','2023-11-01 05:42:53.632000','anonymousUser','2023-11-01 05:42:53.632000','15','2023-10-27','20:10:01',57),(50,'anonymousUser','2023-11-01 05:42:53.643000','anonymousUser','2023-11-01 05:42:53.643000','13','2023-10-27','20:09:58',55),(51,'anonymousUser','2023-11-01 05:42:53.658000','anonymousUser','2023-11-01 05:42:53.658000','11','2023-10-27','20:09:50',53),(52,'anonymousUser','2023-11-01 05:42:53.670000','anonymousUser','2023-11-01 05:42:53.670000','9','2023-10-27','20:09:48',3),(53,'anonymousUser','2023-11-01 05:42:53.678000','anonymousUser','2023-11-01 05:42:53.678000','14','2023-10-27','20:09:44',56),(54,'anonymousUser','2023-11-01 05:43:13.371000','anonymousUser','2023-11-01 05:43:13.371000','13','2023-10-26','07:53:45',55),(55,'anonymousUser','2023-11-01 05:43:13.386000','anonymousUser','2023-11-01 05:43:13.386000','11','2023-10-26','07:53:43',53),(56,'anonymousUser','2023-11-01 05:43:13.397000','anonymousUser','2023-11-01 05:43:13.397000','9','2023-10-26','07:53:41',3),(57,'anonymousUser','2023-11-01 05:43:13.409000','anonymousUser','2023-11-01 05:43:13.409000','14','2023-10-26','07:53:39',56),(58,'anonymousUser','2023-11-01 05:43:13.420000','anonymousUser','2023-11-01 05:43:13.420000','15','2023-10-26','07:53:35',57),(59,'anonymousUser','2023-11-01 05:43:13.431000','anonymousUser','2023-11-01 05:43:13.431000','10','2023-10-26','07:53:30',52),(60,'anonymousUser','2023-11-01 05:43:13.444000','anonymousUser','2023-11-01 05:43:13.444000','13','2023-10-26','07:53:28',55),(61,'anonymousUser','2023-11-01 05:43:13.456000','anonymousUser','2023-11-01 05:43:13.456000','11','2023-10-26','07:53:25',53),(62,'anonymousUser','2023-11-01 05:43:13.466000','anonymousUser','2023-11-01 05:43:13.466000','9','2023-10-26','07:53:23',3),(63,'anonymousUser','2023-11-01 05:43:13.477000','anonymousUser','2023-11-01 05:43:13.477000','14','2023-10-26','07:53:21',56),(64,'anonymousUser','2023-11-01 05:43:42.802000','anonymousUser','2023-11-01 05:43:42.802000','13','2023-10-25','20:09:09',55),(65,'anonymousUser','2023-11-01 05:43:42.817000','anonymousUser','2023-11-01 05:43:42.817000','9','2023-10-25','20:09:05',3),(66,'anonymousUser','2023-11-01 05:43:42.831000','anonymousUser','2023-11-01 05:43:42.831000','15','2023-10-25','20:09:02',57),(67,'anonymousUser','2023-11-01 05:43:42.843000','anonymousUser','2023-11-01 05:43:42.843000','10','2023-10-25','20:08:54',52),(68,'anonymousUser','2023-11-01 05:43:42.860000','anonymousUser','2023-11-01 05:43:42.860000','11','2023-10-25','20:08:43',53),(69,'anonymousUser','2023-11-01 05:43:42.871000','anonymousUser','2023-11-01 05:43:42.871000','14','2023-10-25','20:05:32',56),(70,'anonymousUser','2023-11-01 05:43:42.883000','anonymousUser','2023-11-01 05:43:42.883000','9','2023-10-25','02:17:00',3),(71,'anonymousUser','2023-11-01 05:43:42.894000','anonymousUser','2023-11-01 05:43:42.894000','14','2023-10-25','02:16:56',56),(72,'anonymousUser','2023-11-01 05:43:58.201000','anonymousUser','2023-11-01 05:43:58.201000','10','2023-10-24','12:47:49',52),(73,'anonymousUser','2023-11-01 05:43:58.210000','anonymousUser','2023-11-01 05:43:58.210000','15','2023-10-24','12:47:47',57),(74,'anonymousUser','2023-11-01 05:43:58.219000','anonymousUser','2023-11-01 05:43:58.219000','13','2023-10-24','12:47:44',55),(75,'anonymousUser','2023-11-01 05:43:58.230000','anonymousUser','2023-11-01 05:43:58.230000','11','2023-10-24','12:47:42',53),(76,'anonymousUser','2023-11-01 05:43:58.241000','anonymousUser','2023-11-01 05:43:58.241000','9','2023-10-24','12:47:39',3),(77,'anonymousUser','2023-11-01 05:43:58.253000','anonymousUser','2023-11-01 05:43:58.253000','14','2023-10-24','12:47:37',56),(78,'anonymousUser','2023-11-01 05:44:17.917000','anonymousUser','2023-11-01 05:44:17.917000','15','2023-10-23','01:47:52',57),(79,'anonymousUser','2023-11-01 05:44:17.930000','anonymousUser','2023-11-01 05:44:17.930000','10','2023-10-23','01:47:50',52),(80,'anonymousUser','2023-11-01 05:44:17.942000','anonymousUser','2023-11-01 05:44:17.942000','13','2023-10-23','01:47:47',55),(81,'anonymousUser','2023-11-01 05:44:17.954000','anonymousUser','2023-11-01 05:44:17.954000','11','2023-10-23','01:47:45',53),(82,'anonymousUser','2023-11-01 05:44:17.968000','anonymousUser','2023-11-01 05:44:17.968000','9','2023-10-23','01:47:43',3),(83,'anonymousUser','2023-11-01 05:44:17.981000','anonymousUser','2023-11-01 05:44:17.981000','14','2023-10-23','01:47:40',56),(84,'anonymousUser','2023-11-01 05:45:40.740000','anonymousUser','2023-11-01 05:45:40.740000','9','2023-10-21','19:44:21',3),(85,'anonymousUser','2023-11-01 05:45:40.750000','anonymousUser','2023-11-01 05:45:40.750000','15','2023-10-21','02:19:12',57),(86,'anonymousUser','2023-11-01 05:45:40.759000','anonymousUser','2023-11-01 05:45:40.759000','10','2023-10-21','02:19:10',52),(87,'anonymousUser','2023-11-01 05:45:40.770000','anonymousUser','2023-11-01 05:45:40.770000','14','2023-10-21','02:19:04',56),(88,'anonymousUser','2023-11-01 05:45:40.778000','anonymousUser','2023-11-01 05:45:40.778000','11','2023-10-21','02:18:57',53),(89,'anonymousUser','2023-11-01 05:45:40.787000','anonymousUser','2023-11-01 05:45:40.787000','9','2023-10-21','02:18:55',3),(90,'anonymousUser','2023-11-01 05:46:07.043000','anonymousUser','2023-11-01 05:46:07.043000','15','2023-10-20','13:37:16',57),(91,'anonymousUser','2023-11-01 05:46:07.055000','anonymousUser','2023-11-01 05:46:07.055000','10','2023-10-20','13:37:14',52),(92,'anonymousUser','2023-11-01 05:46:07.063000','anonymousUser','2023-11-01 05:46:07.063000','13','2023-10-20','13:37:11',55),(93,'anonymousUser','2023-11-01 05:46:07.073000','anonymousUser','2023-11-01 05:46:07.073000','11','2023-10-20','13:37:09',53),(94,'anonymousUser','2023-11-01 05:46:07.085000','anonymousUser','2023-11-01 05:46:07.085000','9','2023-10-20','13:37:06',3),(95,'anonymousUser','2023-11-01 05:46:07.096000','anonymousUser','2023-11-01 05:46:07.096000','14','2023-10-20','13:37:04',56),(96,'anonymousUser','2023-11-01 05:46:07.107000','anonymousUser','2023-11-01 05:46:07.107000','15','2023-10-20','02:05:54',57),(97,'anonymousUser','2023-11-01 05:46:07.119000','anonymousUser','2023-11-01 05:46:07.119000','10','2023-10-20','02:05:48',52),(98,'anonymousUser','2023-11-01 05:46:07.131000','anonymousUser','2023-11-01 05:46:07.131000','14','2023-10-20','02:05:45',56),(99,'anonymousUser','2023-11-01 05:46:07.146000','anonymousUser','2023-11-01 05:46:07.146000','9','2023-10-20','02:05:41',3),(100,'anonymousUser','2023-11-01 05:46:07.158000','anonymousUser','2023-11-01 05:46:07.158000','11','2023-10-20','02:05:39',53),(101,'anonymousUser','2023-11-01 05:46:07.169000','anonymousUser','2023-11-01 05:46:07.169000','11','2023-10-20','02:05:38',53),(102,'anonymousUser','2023-11-01 05:46:07.179000','anonymousUser','2023-11-01 05:46:07.179000','13','2023-10-20','02:05:35',55),(103,'anonymousUser','2023-11-01 05:46:29.860000','anonymousUser','2023-11-01 05:46:29.860000','17','2023-10-19','21:02:34',59),(104,'anonymousUser','2023-11-01 05:46:29.870000','anonymousUser','2023-11-01 05:46:29.870000','16','2023-10-19','21:02:31',58),(105,'anonymousUser','2023-11-01 05:46:29.880000','anonymousUser','2023-11-01 05:46:29.880000','17','2023-10-19','21:02:28',59),(106,'anonymousUser','2023-11-01 05:46:29.889000','anonymousUser','2023-11-01 05:46:29.889000','16','2023-10-19','21:02:26',58),(107,'anonymousUser','2023-11-01 05:46:29.900000','anonymousUser','2023-11-01 05:46:29.900000','15','2023-10-19','21:02:11',57),(108,'anonymousUser','2023-11-01 05:46:29.911000','anonymousUser','2023-11-01 05:46:29.911000','10','2023-10-19','21:02:06',52),(109,'anonymousUser','2023-11-01 05:46:29.922000','anonymousUser','2023-11-01 05:46:29.922000','14','2023-10-19','20:53:38',56),(110,'anonymousUser','2023-11-01 05:46:29.930000','anonymousUser','2023-11-01 05:46:29.930000','14','2023-10-19','20:53:36',56),(111,'anonymousUser','2023-11-01 05:46:29.939000','anonymousUser','2023-11-01 05:46:29.939000','9','2023-10-19','20:53:33',3),(112,'anonymousUser','2023-11-01 05:46:29.948000','anonymousUser','2023-11-01 05:46:29.948000','13','2023-10-19','20:53:31',55),(113,'anonymousUser','2023-11-01 05:46:29.957000','anonymousUser','2023-11-01 05:46:29.957000','11','2023-10-19','20:53:26',53),(114,'anonymousUser','2023-11-01 05:46:29.967000','anonymousUser','2023-11-01 05:46:29.967000','13','2023-10-19','12:55:35',55),(115,'anonymousUser','2023-11-01 05:46:29.975000','anonymousUser','2023-11-01 05:46:29.975000','11','2023-10-19','12:55:32',53),(116,'anonymousUser','2023-11-01 05:46:29.982000','anonymousUser','2023-11-01 05:46:29.982000','9','2023-10-19','12:55:26',3),(117,'anonymousUser','2023-11-01 05:46:29.990000','anonymousUser','2023-11-01 05:46:29.990000','15','2023-10-19','12:55:17',57),(118,'anonymousUser','2023-11-01 05:46:29.997000','anonymousUser','2023-11-01 05:46:29.997000','10','2023-10-19','12:55:14',52),(119,'anonymousUser','2023-11-01 05:46:30.007000','anonymousUser','2023-11-01 05:46:30.007000','14','2023-10-19','12:55:10',56),(120,'anonymousUser','2023-11-01 05:46:30.015000','anonymousUser','2023-11-01 05:46:30.015000','11','2023-10-19','12:55:07',53),(121,'anonymousUser','2023-11-01 05:46:30.023000','anonymousUser','2023-11-01 05:46:30.023000','9','2023-10-19','12:54:54',3),(122,'anonymousUser','2023-11-01 05:46:30.031000','anonymousUser','2023-11-01 05:46:30.031000','9','2023-10-19','01:05:23',3),(123,'anonymousUser','2023-11-01 05:46:30.040000','anonymousUser','2023-11-01 05:46:30.040000','9','2023-10-19','01:05:21',3),(124,'anonymousUser','2023-11-01 05:46:55.286000','anonymousUser','2023-11-01 05:46:55.286000','15','2023-10-18','20:18:27',57),(125,'anonymousUser','2023-11-01 05:46:55.302000','anonymousUser','2023-11-01 05:46:55.302000','10','2023-10-18','20:18:20',52),(126,'anonymousUser','2023-11-01 05:46:55.313000','anonymousUser','2023-11-01 05:46:55.313000','14','2023-10-18','20:18:18',56),(127,'anonymousUser','2023-11-01 05:46:55.323000','anonymousUser','2023-11-01 05:46:55.323000','13','2023-10-18','20:18:15',55),(128,'anonymousUser','2023-11-01 05:46:55.333000','anonymousUser','2023-11-01 05:46:55.333000','11','2023-10-18','20:18:09',53),(129,'anonymousUser','2023-11-01 05:46:55.345000','anonymousUser','2023-11-01 05:46:55.345000','9','2023-10-18','20:18:07',3),(130,'anonymousUser','2023-11-01 05:46:55.352000','anonymousUser','2023-11-01 05:46:55.352000','15','2023-10-18','12:50:30',57),(131,'anonymousUser','2023-11-01 05:46:55.361000','anonymousUser','2023-11-01 05:46:55.361000','10','2023-10-18','12:50:28',52),(132,'anonymousUser','2023-11-01 05:46:55.372000','anonymousUser','2023-11-01 05:46:55.372000','14','2023-10-18','12:50:25',56),(133,'anonymousUser','2023-11-01 05:46:55.381000','anonymousUser','2023-11-01 05:46:55.381000','13','2023-10-18','12:50:23',55),(134,'anonymousUser','2023-11-01 05:46:55.390000','anonymousUser','2023-11-01 05:46:55.390000','11','2023-10-18','12:50:20',53),(135,'anonymousUser','2023-11-01 05:46:55.398000','anonymousUser','2023-11-01 05:46:55.398000','9','2023-10-18','12:50:15',3),(136,'anonymousUser','2023-11-01 05:47:13.141000','anonymousUser','2023-11-01 05:47:13.141000','16','2023-10-17','22:04:32',58),(137,'anonymousUser','2023-11-01 05:47:13.150000','anonymousUser','2023-11-01 05:47:13.150000','17','2023-10-17','22:04:22',59),(138,'anonymousUser','2023-11-01 05:47:13.158000','anonymousUser','2023-11-01 05:47:13.158000','10','2023-10-17','19:18:48',52),(139,'anonymousUser','2023-11-01 05:47:13.167000','anonymousUser','2023-11-01 05:47:13.167000','15','2023-10-17','19:18:46',57),(140,'anonymousUser','2023-11-01 05:47:13.177000','anonymousUser','2023-11-01 05:47:13.177000','13','2023-10-17','19:18:44',55),(141,'anonymousUser','2023-11-01 05:47:13.188000','anonymousUser','2023-11-01 05:47:13.188000','11','2023-10-17','19:18:41',53),(142,'anonymousUser','2023-11-01 05:47:13.196000','anonymousUser','2023-11-01 05:47:13.196000','9','2023-10-17','19:18:39',3),(143,'anonymousUser','2023-11-01 05:47:13.206000','anonymousUser','2023-11-01 05:47:13.206000','14','2023-10-17','19:18:36',56),(144,'anonymousUser','2023-11-01 05:47:13.213000','anonymousUser','2023-11-01 05:47:13.213000','16','2023-10-17','18:09:37',58),(145,'anonymousUser','2023-11-01 05:47:13.222000','anonymousUser','2023-11-01 05:47:13.222000','8','2023-10-17','18:09:12',2),(146,'anonymousUser','2023-11-01 05:47:13.232000','anonymousUser','2023-11-01 05:47:13.232000','8','2023-10-17','17:58:03',2),(147,'anonymousUser','2023-11-01 05:47:13.241000','anonymousUser','2023-11-01 05:47:13.241000','16','2023-10-17','17:57:59',58),(148,'anonymousUser','2023-11-01 05:47:13.255000','anonymousUser','2023-11-01 05:47:13.255000','17','2023-10-17','15:58:32',59),(149,'anonymousUser','2023-11-01 05:47:13.266000','anonymousUser','2023-11-01 05:47:13.266000','16','2023-10-17','15:58:26',58),(150,'anonymousUser','2023-11-01 05:47:30.465000','anonymousUser','2023-11-01 05:47:30.465000','16','2023-10-16','00:54:22',58),(151,'anonymousUser','2023-11-01 05:47:30.473000','anonymousUser','2023-11-01 05:47:30.473000','17','2023-10-16','00:54:19',59),(152,'anonymousUser','2023-11-01 05:47:30.484000','anonymousUser','2023-11-01 05:47:30.484000','16','2023-10-16','00:54:00',58),(153,'anonymousUser','2023-11-01 05:47:30.495000','anonymousUser','2023-11-01 05:47:30.495000','16','2023-10-16','00:21:16',58),(154,'anonymousUser','2023-11-01 05:47:30.504000','anonymousUser','2023-11-01 05:47:30.504000','17','2023-10-16','00:21:13',59),(155,'anonymousUser','2023-11-01 05:47:46.955000','anonymousUser','2023-11-01 05:47:46.955000','13','2023-10-14','23:54:15',55),(156,'anonymousUser','2023-11-01 05:47:46.966000','anonymousUser','2023-11-01 05:47:46.966000','10','2023-10-14','23:54:11',52),(157,'anonymousUser','2023-11-01 05:47:46.973000','anonymousUser','2023-11-01 05:47:46.973000','15','2023-10-14','23:54:09',57),(158,'anonymousUser','2023-11-01 05:47:46.985000','anonymousUser','2023-11-01 05:47:46.985000','9','2023-10-14','23:54:06',3),(159,'anonymousUser','2023-11-01 05:47:46.996000','anonymousUser','2023-11-01 05:47:46.996000','14','2023-10-14','23:54:04',56),(160,'anonymousUser','2023-11-01 05:47:47.006000','anonymousUser','2023-11-01 05:47:47.006000','11','2023-10-14','23:54:00',53),(161,'anonymousUser','2023-11-01 05:47:47.016000','anonymousUser','2023-11-01 05:47:47.016000','9','2023-10-14','23:53:58',3),(162,'anonymousUser','2023-11-01 05:47:47.027000','anonymousUser','2023-11-01 05:47:47.027000','15','2023-10-14','22:17:15',57),(163,'anonymousUser','2023-11-01 05:47:47.041000','anonymousUser','2023-11-01 05:47:47.041000','10','2023-10-14','22:17:13',52),(164,'anonymousUser','2023-11-01 05:47:47.055000','anonymousUser','2023-11-01 05:47:47.055000','13','2023-10-14','22:17:10',55),(165,'anonymousUser','2023-11-01 05:47:47.063000','anonymousUser','2023-11-01 05:47:47.063000','11','2023-10-14','22:17:08',53),(166,'anonymousUser','2023-11-01 05:47:47.071000','anonymousUser','2023-11-01 05:47:47.071000','9','2023-10-14','22:17:05',3),(167,'anonymousUser','2023-11-01 05:47:47.078000','anonymousUser','2023-11-01 05:47:47.078000','14','2023-10-14','22:17:03',56),(168,'anonymousUser','2023-11-01 05:48:16.483000','anonymousUser','2023-11-01 05:48:16.483000','15','2023-10-13','07:01:48',57),(169,'anonymousUser','2023-11-01 05:48:16.494000','anonymousUser','2023-11-01 05:48:16.494000','10','2023-10-13','07:01:44',52),(170,'anonymousUser','2023-11-01 05:48:16.503000','anonymousUser','2023-11-01 05:48:16.503000','13','2023-10-13','07:01:39',55),(171,'anonymousUser','2023-11-01 05:48:16.510000','anonymousUser','2023-11-01 05:48:16.510000','11','2023-10-13','07:01:33',53),(172,'anonymousUser','2023-11-01 05:48:16.518000','anonymousUser','2023-11-01 05:48:16.518000','9','2023-10-13','07:01:30',3),(173,'anonymousUser','2023-11-01 05:48:16.526000','anonymousUser','2023-11-01 05:48:16.526000','14','2023-10-13','07:01:02',56),(174,'anonymousUser','2023-11-01 05:48:16.535000','anonymousUser','2023-11-01 05:48:16.535000','14','2023-10-13','06:56:15',56),(175,'anonymousUser','2023-11-01 05:48:16.543000','anonymousUser','2023-11-01 05:48:16.543000','15','2023-10-13','06:55:56',57),(176,'anonymousUser','2023-11-01 05:48:16.551000','anonymousUser','2023-11-01 05:48:16.551000','10','2023-10-13','06:55:54',52),(177,'anonymousUser','2023-11-01 05:48:16.560000','anonymousUser','2023-11-01 05:48:16.560000','11','2023-10-13','06:55:49',53),(178,'anonymousUser','2023-11-01 05:48:16.567000','anonymousUser','2023-11-01 05:48:16.567000','9','2023-10-13','06:55:47',3),(179,'anonymousUser','2023-11-01 05:48:16.576000','anonymousUser','2023-11-01 05:48:16.576000','14','2023-10-13','06:55:44',56),(180,'anonymousUser','2023-11-01 05:48:16.586000','anonymousUser','2023-11-01 05:48:16.586000','16','2023-10-13','06:55:15',58),(181,'anonymousUser','2023-11-01 05:48:16.623000','anonymousUser','2023-11-01 05:48:16.623000','11','2023-10-13','00:51:16',53),(182,'anonymousUser','2023-11-01 05:48:16.630000','anonymousUser','2023-11-01 05:48:16.630000','13','2023-10-13','00:51:14',55),(183,'anonymousUser','2023-11-01 05:48:16.637000','anonymousUser','2023-11-01 05:48:16.637000','9','2023-10-13','00:51:09',3),(184,'anonymousUser','2023-11-01 05:48:16.644000','anonymousUser','2023-11-01 05:48:16.644000','8','2023-10-13','00:51:05',2),(185,'anonymousUser','2023-11-01 05:48:16.650000','anonymousUser','2023-11-01 05:48:16.650000','8','2023-10-13','00:50:53',2),(186,'anonymousUser','2023-11-01 05:48:16.658000','anonymousUser','2023-11-01 05:48:16.658000','16','2023-10-13','00:49:32',58),(187,'anonymousUser','2023-11-01 05:48:16.666000','anonymousUser','2023-11-01 05:48:16.666000','16','2023-10-13','00:49:23',58),(188,'anonymousUser','2023-11-01 05:48:16.673000','anonymousUser','2023-11-01 05:48:16.673000','15','2023-10-13','00:47:45',57),(189,'anonymousUser','2023-11-01 05:48:16.681000','anonymousUser','2023-11-01 05:48:16.681000','10','2023-10-13','00:47:41',52),(190,'anonymousUser','2023-11-01 05:48:16.688000','anonymousUser','2023-11-01 05:48:16.688000','14','2023-10-13','00:47:38',56),(191,'anonymousUser','2023-11-01 05:48:40.523000','anonymousUser','2023-11-01 05:48:40.523000','13','2023-10-12','21:14:11',55),(192,'anonymousUser','2023-11-01 05:48:40.531000','anonymousUser','2023-11-01 05:48:40.531000','11','2023-10-12','21:14:09',53),(193,'anonymousUser','2023-11-01 05:48:40.537000','anonymousUser','2023-11-01 05:48:40.537000','14','2023-10-12','21:14:07',56),(194,'anonymousUser','2023-11-01 05:48:40.546000','anonymousUser','2023-11-01 05:48:40.546000','9','2023-10-12','21:14:04',3),(195,'anonymousUser','2023-11-01 05:48:40.553000','anonymousUser','2023-11-01 05:48:40.553000','15','2023-10-12','21:14:00',57),(196,'anonymousUser','2023-11-01 05:48:40.563000','anonymousUser','2023-11-01 05:48:40.563000','15','2023-10-12','21:13:59',57),(197,'anonymousUser','2023-11-01 05:48:40.571000','anonymousUser','2023-11-01 05:48:40.571000','10','2023-10-12','21:13:56',52),(198,'anonymousUser','2023-11-01 05:48:40.579000','anonymousUser','2023-11-01 05:48:40.579000','10','2023-10-12','21:13:55',52),(199,'anonymousUser','2023-11-01 05:48:40.587000','anonymousUser','2023-11-01 05:48:40.587000','14','2023-10-12','20:50:48',56),(200,'anonymousUser','2023-11-01 05:48:40.597000','anonymousUser','2023-11-01 05:48:40.597000','11','2023-10-12','20:50:34',53),(201,'anonymousUser','2023-11-01 05:48:40.604000','anonymousUser','2023-11-01 05:48:40.604000','9','2023-10-12','20:50:32',3),(202,'anonymousUser','2023-11-01 05:48:40.612000','anonymousUser','2023-11-01 05:48:40.612000','15','2023-10-12','20:50:30',57),(203,'anonymousUser','2023-11-01 05:48:40.621000','anonymousUser','2023-11-01 05:48:40.621000','10','2023-10-12','20:50:28',52),(204,'anonymousUser','2023-11-01 05:48:40.635000','anonymousUser','2023-11-01 05:48:40.635000','15','2023-10-12','20:44:27',57),(205,'anonymousUser','2023-11-01 05:48:40.646000','anonymousUser','2023-11-01 05:48:40.646000','10','2023-10-12','20:44:24',52),(206,'anonymousUser','2023-11-01 05:48:40.654000','anonymousUser','2023-11-01 05:48:40.654000','13','2023-10-12','20:44:19',55),(207,'anonymousUser','2023-11-01 05:48:40.662000','anonymousUser','2023-11-01 05:48:40.662000','11','2023-10-12','20:44:14',53),(208,'anonymousUser','2023-11-01 05:48:40.669000','anonymousUser','2023-11-01 05:48:40.669000','15','2023-10-12','20:44:07',57),(209,'anonymousUser','2023-11-01 05:48:40.680000','anonymousUser','2023-11-01 05:48:40.680000','14','2023-10-12','20:44:04',56),(210,'anonymousUser','2023-11-01 05:48:40.687000','anonymousUser','2023-11-01 05:48:40.687000','13','2023-10-12','20:42:40',55),(211,'anonymousUser','2023-11-01 05:48:40.694000','anonymousUser','2023-11-01 05:48:40.694000','10','2023-10-12','20:42:37',52),(212,'anonymousUser','2023-11-01 05:48:40.702000','anonymousUser','2023-11-01 05:48:40.702000','11','2023-10-12','20:42:28',53),(213,'anonymousUser','2023-11-01 05:48:40.709000','anonymousUser','2023-11-01 05:48:40.709000','9','2023-10-12','20:42:25',3),(214,'anonymousUser','2023-11-01 05:49:01.704000','anonymousUser','2023-11-01 05:49:01.704000','8','2023-10-03','19:58:52',2),(215,'anonymousUser','2023-11-01 05:49:01.715000','anonymousUser','2023-11-01 05:49:01.715000','13','2023-10-03','19:58:40',55),(216,'anonymousUser','2023-11-01 05:49:01.725000','anonymousUser','2023-11-01 05:49:01.725000','11','2023-10-03','19:58:37',53),(217,'anonymousUser','2023-11-01 05:49:01.733000','anonymousUser','2023-11-01 05:49:01.733000','9','2023-10-03','19:58:34',3),(218,'anonymousUser','2023-11-01 05:49:01.742000','anonymousUser','2023-11-01 05:49:01.742000','10','2023-10-03','11:40:00',52),(219,'anonymousUser','2023-11-01 05:49:01.753000','anonymousUser','2023-11-01 05:49:01.753000','13','2023-10-03','11:39:56',55),(220,'anonymousUser','2023-11-01 05:49:01.761000','anonymousUser','2023-11-01 05:49:01.761000','11','2023-10-03','11:39:53',53),(221,'anonymousUser','2023-11-01 05:49:01.769000','anonymousUser','2023-11-01 05:49:01.769000','9','2023-10-03','11:39:51',3),(222,'anonymousUser','2023-11-01 05:49:01.778000','anonymousUser','2023-11-01 05:49:01.778000','9','2023-10-03','02:28:26',3),(223,'anonymousUser','2023-11-01 05:49:01.786000','anonymousUser','2023-11-01 05:49:01.786000','10','2023-10-03','02:28:13',52),(224,'anonymousUser','2023-11-01 05:49:01.795000','anonymousUser','2023-11-01 05:49:01.795000','10','2023-10-03','02:13:15',52),(225,'anonymousUser','2023-11-01 05:49:01.806000','anonymousUser','2023-11-01 05:49:01.806000','13','2023-10-03','02:13:12',55),(226,'anonymousUser','2023-11-01 05:49:01.816000','anonymousUser','2023-11-01 05:49:01.816000','11','2023-10-03','02:13:10',53),(227,'anonymousUser','2023-11-01 05:49:01.825000','anonymousUser','2023-11-01 05:49:01.825000','9','2023-10-03','02:13:07',3),(228,'anonymousUser','2023-11-01 05:49:01.835000','anonymousUser','2023-11-01 05:49:01.835000','10','2023-10-03','01:36:22',52),(229,'anonymousUser','2023-11-01 05:49:01.842000','anonymousUser','2023-11-01 05:49:01.842000','13','2023-10-03','01:36:20',55),(230,'anonymousUser','2023-11-01 05:49:01.849000','anonymousUser','2023-11-01 05:49:01.849000','11','2023-10-03','01:36:16',53),(231,'anonymousUser','2023-11-01 05:49:01.857000','anonymousUser','2023-11-01 05:49:01.857000','9','2023-10-03','01:36:15',3),(232,'anonymousUser','2023-11-01 05:49:19.572000','anonymousUser','2023-11-01 05:49:19.572000','10','2023-10-02','12:42:37',52),(233,'anonymousUser','2023-11-01 05:49:19.581000','anonymousUser','2023-11-01 05:49:19.581000','13','2023-10-02','12:42:34',55),(234,'anonymousUser','2023-11-01 05:49:19.591000','anonymousUser','2023-11-01 05:49:19.591000','11','2023-10-02','12:42:32',53),(235,'anonymousUser','2023-11-01 05:49:19.602000','anonymousUser','2023-11-01 05:49:19.602000','9','2023-10-02','12:42:30',3),(236,'anonymousUser','2023-11-01 05:49:33.117000','anonymousUser','2023-11-01 05:49:33.117000','10','2023-10-01','22:05:20',52),(237,'anonymousUser','2023-11-01 05:49:33.127000','anonymousUser','2023-11-01 05:49:33.127000','13','2023-10-01','22:05:16',55),(238,'anonymousUser','2023-11-01 05:49:33.137000','anonymousUser','2023-11-01 05:49:33.137000','11','2023-10-01','22:05:13',53),(239,'anonymousUser','2023-11-01 05:49:33.149000','anonymousUser','2023-11-01 05:49:33.149000','9','2023-10-01','22:05:11',3),(240,'anonymousUser','2023-11-01 05:49:33.158000','anonymousUser','2023-11-01 05:49:33.158000','13','2023-10-01','18:55:14',55),(241,'anonymousUser','2023-11-01 05:49:33.168000','anonymousUser','2023-11-01 05:49:33.168000','10','2023-10-01','18:48:24',52),(242,'anonymousUser','2023-11-01 05:49:33.177000','anonymousUser','2023-11-01 05:49:33.177000','11','2023-10-01','18:48:05',53),(243,'anonymousUser','2023-11-01 05:49:33.184000','anonymousUser','2023-11-01 05:49:33.184000','9','2023-10-01','18:48:03',3),(244,'anonymousUser','2023-11-01 05:49:33.191000','anonymousUser','2023-11-01 05:49:33.191000','13','2023-10-01','15:29:08',55),(245,'anonymousUser','2023-11-01 05:49:33.198000','anonymousUser','2023-11-01 05:49:33.198000','11','2023-10-01','15:29:02',53),(246,'anonymousUser','2023-11-01 05:49:33.207000','anonymousUser','2023-11-01 05:49:33.207000','9','2023-10-01','15:28:57',3),(247,'anonymousUser','2023-11-01 05:49:33.215000','anonymousUser','2023-11-01 05:49:33.215000','9','2023-10-01','15:28:55',3),(248,'anonymousUser','2023-11-01 05:49:33.222000','anonymousUser','2023-11-01 05:49:33.222000','10','2023-10-01','15:28:51',52),(249,'anonymousUser','2023-11-01 05:49:33.229000','anonymousUser','2023-11-01 05:49:33.229000','13','2023-10-01','15:28:46',55),(250,'anonymousUser','2023-11-01 05:49:33.235000','anonymousUser','2023-11-01 05:49:33.235000','11','2023-10-01','15:28:45',53),(251,'anonymousUser','2023-11-01 05:49:33.242000','anonymousUser','2023-11-01 05:49:33.242000','11','2023-10-01','15:28:44',53),(252,'anonymousUser','2023-11-01 05:49:33.249000','anonymousUser','2023-11-01 05:49:33.249000','9','2023-10-01','15:28:42',3),(253,'anonymousUser','2023-11-01 05:49:33.256000','anonymousUser','2023-11-01 05:49:33.256000','13','2023-10-01','08:28:22',55),(254,'anonymousUser','2023-11-01 05:49:33.262000','anonymousUser','2023-11-01 05:49:33.262000','13','2023-10-01','08:28:21',55),(255,'anonymousUser','2023-11-01 05:49:33.269000','anonymousUser','2023-11-01 05:49:33.269000','9','2023-10-01','08:28:17',3),(256,'anonymousUser','2023-11-01 05:49:33.275000','anonymousUser','2023-11-01 05:49:33.275000','11','2023-10-01','08:28:11',53),(257,'anonymousUser','2023-11-01 05:49:33.282000','anonymousUser','2023-11-01 05:49:33.282000','13','2023-10-01','08:25:54',55),(258,'anonymousUser','2023-11-01 05:49:33.289000','anonymousUser','2023-11-01 05:49:33.289000','11','2023-10-01','08:15:55',53),(259,'anonymousUser','2023-11-01 05:49:33.297000','anonymousUser','2023-11-01 05:49:33.297000','10','2023-10-01','08:13:15',52),(260,'anonymousUser','2023-11-01 05:49:33.304000','anonymousUser','2023-11-01 05:49:33.304000','10','2023-10-01','08:13:07',52),(261,'anonymousUser','2023-11-01 05:49:33.312000','anonymousUser','2023-11-01 05:49:33.312000','9','2023-10-01','07:41:41',3),(262,'anonymousUser','2023-11-01 05:49:33.321000','anonymousUser','2023-11-01 05:49:33.321000','9','2023-10-01','07:32:06',3),(263,'anonymousUser','2023-11-01 05:49:33.330000','anonymousUser','2023-11-01 05:49:33.330000','9','2023-10-01','07:32:05',3),(264,'anonymousUser','2023-11-01 05:49:33.339000','anonymousUser','2023-11-01 05:49:33.339000','9','2023-10-01','07:28:42',3),(265,'anonymousUser','2023-11-01 05:49:33.348000','anonymousUser','2023-11-01 05:49:33.348000','10','2023-10-01','07:24:33',52),(266,'anonymousUser','2023-11-01 05:49:33.355000','anonymousUser','2023-11-01 05:49:33.355000','9','2023-10-01','07:22:46',3),(267,'anonymousUser','2023-11-01 05:49:33.365000','anonymousUser','2023-11-01 05:49:33.365000','10','2023-10-01','07:22:27',52),(268,'anonymousUser','2023-11-01 05:49:33.372000','anonymousUser','2023-11-01 05:49:33.372000','10','2023-10-01','07:18:01',52);
/*!40000 ALTER TABLE `log_check_in_out` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `log_in_late_out_early`
--

DROP TABLE IF EXISTS `log_in_late_out_early`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `log_in_late_out_early` (
                                         `log_inLateOutEarly_id` bigint NOT NULL,
                                         `created_by` varchar(255) DEFAULT NULL,
    `create_date` datetime(6) DEFAULT NULL,
    `updated_by` varchar(255) DEFAULT NULL,
    `updated_date` datetime(6) DEFAULT NULL,
    `employee_id` varchar(255) DEFAULT NULL,
    `io_kind` varchar(255) DEFAULT NULL,
    `reason` varchar(255) DEFAULT NULL,
    `date_check` date DEFAULT NULL,
    `duration` time DEFAULT NULL,
    `time_end` time DEFAULT NULL,
    `time_start` time DEFAULT NULL,
    `user_id` bigint DEFAULT NULL,
    PRIMARY KEY (`log_inLateOutEarly_id`),
    KEY `FKpvam1mwq724d82ntch7xntmg9` (`user_id`),
    CONSTRAINT `FKpvam1mwq724d82ntch7xntmg9` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `log_in_late_out_early`
--

LOCK TABLES `log_in_late_out_early` WRITE;
/*!40000 ALTER TABLE `log_in_late_out_early` DISABLE KEYS */;
INSERT INTO `log_in_late_out_early` VALUES (270,'anonymousUser','2023-11-01 05:51:49.748000','anonymousUser','2023-11-01 05:51:49.748000',NULL,'LATE_CHECKIN',NULL,'2023-10-30','02:20:26','11:00:00','09:20:26',3),(271,'anonymousUser','2023-11-01 05:51:49.758000','anonymousUser','2023-11-01 05:51:49.758000',NULL,'EARLY_CHECKOUT',NULL,'2023-10-30','06:00:00','11:00:00','09:20:26',3),(273,'anonymousUser','2023-11-01 05:51:49.772000','anonymousUser','2023-11-01 05:51:49.772000',NULL,'LATE_CHECKIN',NULL,'2023-10-30','02:20:33','11:00:00','09:20:33',52),(274,'anonymousUser','2023-11-01 05:51:49.778000','anonymousUser','2023-11-01 05:51:49.778000',NULL,'EARLY_CHECKOUT',NULL,'2023-10-30','06:00:00','11:00:00','09:20:33',52),(276,'anonymousUser','2023-11-01 05:51:49.792000','anonymousUser','2023-11-01 05:51:49.792000',NULL,'LATE_CHECKIN',NULL,'2023-10-30','02:20:28','11:00:00','09:20:28',53),(277,'anonymousUser','2023-11-01 05:51:49.797000','anonymousUser','2023-11-01 05:51:49.797000',NULL,'EARLY_CHECKOUT',NULL,'2023-10-30','06:00:00','11:00:00','09:20:28',53),(279,'anonymousUser','2023-11-01 05:51:49.811000','anonymousUser','2023-11-01 05:51:49.811000',NULL,'LATE_CHECKIN',NULL,'2023-10-30','02:20:30','11:00:00','09:20:30',55),(280,'anonymousUser','2023-11-01 05:51:49.816000','anonymousUser','2023-11-01 05:51:49.816000',NULL,'EARLY_CHECKOUT',NULL,'2023-10-30','06:00:00','11:00:00','09:20:30',55),(282,'anonymousUser','2023-11-01 05:51:49.830000','anonymousUser','2023-11-01 05:51:49.830000',NULL,'LATE_CHECKIN',NULL,'2023-10-30','02:20:21','11:00:00','09:20:21',56),(283,'anonymousUser','2023-11-01 05:51:49.836000','anonymousUser','2023-11-01 05:51:49.836000',NULL,'EARLY_CHECKOUT',NULL,'2023-10-30','06:00:00','11:00:00','09:20:21',56),(285,'anonymousUser','2023-11-01 05:51:49.854000','anonymousUser','2023-11-01 05:51:49.854000',NULL,'LATE_CHECKIN',NULL,'2023-10-30','02:20:36','11:00:00','09:20:36',57),(286,'anonymousUser','2023-11-01 05:51:49.859000','anonymousUser','2023-11-01 05:51:49.859000',NULL,'EARLY_CHECKOUT',NULL,'2023-10-30','06:00:00','11:00:00','09:20:36',57),(288,'anonymousUser','2023-11-01 05:52:35.048000','anonymousUser','2023-11-01 05:52:35.048000',NULL,'LATE_CHECKIN',NULL,'2023-10-27','10:00:00','17:00:00','17:00:00',3),(290,'anonymousUser','2023-11-01 05:52:35.087000','anonymousUser','2023-11-01 05:52:35.087000',NULL,'LATE_CHECKIN',NULL,'2023-10-27','10:00:00','17:00:00','17:00:00',52),(292,'anonymousUser','2023-11-01 05:52:35.128000','anonymousUser','2023-11-01 05:52:35.128000',NULL,'LATE_CHECKIN',NULL,'2023-10-27','10:00:00','17:00:00','17:00:00',53),(294,'anonymousUser','2023-11-01 05:52:35.162000','anonymousUser','2023-11-01 05:52:35.162000',NULL,'LATE_CHECKIN',NULL,'2023-10-27','10:00:00','17:00:00','17:00:00',55),(296,'anonymousUser','2023-11-01 05:52:35.185000','anonymousUser','2023-11-01 05:52:35.185000',NULL,'LATE_CHECKIN',NULL,'2023-10-27','10:00:00','17:00:00','17:00:00',56),(298,'anonymousUser','2023-11-01 05:52:35.205000','anonymousUser','2023-11-01 05:52:35.205000',NULL,'LATE_CHECKIN',NULL,'2023-10-27','10:00:00','17:00:00','17:00:00',57),(304,'anonymousUser','2023-11-01 09:51:37.397000','anonymousUser','2023-11-01 09:51:37.397000',NULL,'LATE_CHECKIN',NULL,'2023-10-26','00:53:23','07:53:41','07:53:23',3),(305,'anonymousUser','2023-11-01 09:51:37.415000','anonymousUser','2023-11-01 09:51:37.415000',NULL,'EARLY_CHECKOUT',NULL,'2023-10-26','09:06:19','07:53:41','07:53:23',3),(308,'anonymousUser','2023-11-01 09:51:37.454000','anonymousUser','2023-11-01 09:51:37.454000',NULL,'LATE_CHECKIN',NULL,'2023-10-26','00:53:30','07:53:30','07:53:30',52),(309,'anonymousUser','2023-11-01 09:51:37.468000','anonymousUser','2023-11-01 09:51:37.468000',NULL,'EARLY_CHECKOUT',NULL,'2023-10-26','09:06:30','07:53:30','07:53:30',52),(312,'anonymousUser','2023-11-01 09:51:37.502000','anonymousUser','2023-11-01 09:51:37.502000',NULL,'LATE_CHECKIN',NULL,'2023-10-26','00:53:25','07:53:43','07:53:25',53),(313,'anonymousUser','2023-11-01 09:51:37.513000','anonymousUser','2023-11-01 09:51:37.513000',NULL,'EARLY_CHECKOUT',NULL,'2023-10-26','09:06:17','07:53:43','07:53:25',53),(316,'anonymousUser','2023-11-01 09:51:37.574000','anonymousUser','2023-11-01 09:51:37.574000',NULL,'LATE_CHECKIN',NULL,'2023-10-26','00:53:28','07:53:45','07:53:28',55),(317,'anonymousUser','2023-11-01 09:51:37.595000','anonymousUser','2023-11-01 09:51:37.595000',NULL,'EARLY_CHECKOUT',NULL,'2023-10-26','09:06:15','07:53:45','07:53:28',55),(320,'anonymousUser','2023-11-01 09:51:37.644000','anonymousUser','2023-11-01 09:51:37.644000',NULL,'LATE_CHECKIN',NULL,'2023-10-26','00:53:21','07:53:39','07:53:21',56),(321,'anonymousUser','2023-11-01 09:51:37.652000','anonymousUser','2023-11-01 09:51:37.652000',NULL,'EARLY_CHECKOUT',NULL,'2023-10-26','09:06:21','07:53:39','07:53:21',56),(324,'anonymousUser','2023-11-01 09:51:37.683000','anonymousUser','2023-11-01 09:51:37.683000',NULL,'LATE_CHECKIN',NULL,'2023-10-26','00:53:35','07:53:35','07:53:35',57),(325,'anonymousUser','2023-11-01 09:51:37.692000','anonymousUser','2023-11-01 09:51:37.692000',NULL,'EARLY_CHECKOUT',NULL,'2023-10-26','09:06:25','07:53:35','07:53:35',57),(328,'dong','2023-11-01 17:51:14.821000','dong','2023-11-01 17:51:14.821000',NULL,'LATE_CHECKIN',NULL,'2023-10-26','00:53:23','07:53:41','07:53:23',3),(329,'dong','2023-11-01 17:51:14.851000','dong','2023-11-01 17:51:14.851000',NULL,'EARLY_CHECKOUT',NULL,'2023-10-26','09:06:19','07:53:41','07:53:23',3),(332,'dong','2023-11-01 17:51:14.923000','dong','2023-11-01 17:51:14.923000',NULL,'LATE_CHECKIN',NULL,'2023-10-26','00:53:30','07:53:30','07:53:30',52),(333,'dong','2023-11-01 17:51:14.940000','dong','2023-11-01 17:51:14.940000',NULL,'EARLY_CHECKOUT',NULL,'2023-10-26','09:06:30','07:53:30','07:53:30',52),(336,'dong','2023-11-01 17:51:14.990000','dong','2023-11-01 17:51:14.990000',NULL,'LATE_CHECKIN',NULL,'2023-10-26','00:53:25','07:53:43','07:53:25',53),(337,'dong','2023-11-01 17:51:15.011000','dong','2023-11-01 17:51:15.011000',NULL,'EARLY_CHECKOUT',NULL,'2023-10-26','09:06:17','07:53:43','07:53:25',53),(340,'dong','2023-11-01 17:51:15.084000','dong','2023-11-01 17:51:15.084000',NULL,'LATE_CHECKIN',NULL,'2023-10-26','00:53:28','07:53:45','07:53:28',55),(341,'dong','2023-11-01 17:51:15.108000','dong','2023-11-01 17:51:15.108000',NULL,'EARLY_CHECKOUT',NULL,'2023-10-26','09:06:15','07:53:45','07:53:28',55),(344,'dong','2023-11-01 17:51:15.169000','dong','2023-11-01 17:51:15.169000',NULL,'LATE_CHECKIN',NULL,'2023-10-26','00:53:21','07:53:39','07:53:21',56),(345,'dong','2023-11-01 17:51:15.190000','dong','2023-11-01 17:51:15.190000',NULL,'EARLY_CHECKOUT',NULL,'2023-10-26','09:06:21','07:53:39','07:53:21',56),(348,'dong','2023-11-01 17:51:15.257000','dong','2023-11-01 17:51:15.257000',NULL,'LATE_CHECKIN',NULL,'2023-10-26','00:53:35','07:53:35','07:53:35',57),(349,'dong','2023-11-01 17:51:15.279000','dong','2023-11-01 17:51:15.279000',NULL,'EARLY_CHECKOUT',NULL,'2023-10-26','09:06:25','07:53:35','07:53:35',57),(352,'dong','2023-11-01 17:52:26.710000','dong','2023-11-01 17:52:26.710000',NULL,'LATE_CHECKIN',NULL,'2023-10-26','00:53:23','07:53:41','07:53:23',3),(353,'dong','2023-11-01 17:52:26.725000','dong','2023-11-01 17:52:26.725000',NULL,'EARLY_CHECKOUT',NULL,'2023-10-26','09:06:19','07:53:41','07:53:23',3),(356,'dong','2023-11-01 17:52:26.768000','dong','2023-11-01 17:52:26.768000',NULL,'LATE_CHECKIN',NULL,'2023-10-26','00:53:30','07:53:30','07:53:30',52),(357,'dong','2023-11-01 17:52:26.781000','dong','2023-11-01 17:52:26.781000',NULL,'EARLY_CHECKOUT',NULL,'2023-10-26','09:06:30','07:53:30','07:53:30',52),(360,'dong','2023-11-01 17:52:26.822000','dong','2023-11-01 17:52:26.822000',NULL,'LATE_CHECKIN',NULL,'2023-10-26','00:53:25','07:53:43','07:53:25',53),(361,'dong','2023-11-01 17:52:26.837000','dong','2023-11-01 17:52:26.837000',NULL,'EARLY_CHECKOUT',NULL,'2023-10-26','09:06:17','07:53:43','07:53:25',53),(364,'dong','2023-11-01 17:52:26.889000','dong','2023-11-01 17:52:26.889000',NULL,'LATE_CHECKIN',NULL,'2023-10-26','00:53:28','07:53:45','07:53:28',55),(365,'dong','2023-11-01 17:52:26.906000','dong','2023-11-01 17:52:26.906000',NULL,'EARLY_CHECKOUT',NULL,'2023-10-26','09:06:15','07:53:45','07:53:28',55),(368,'dong','2023-11-01 17:52:26.970000','dong','2023-11-01 17:52:26.970000',NULL,'LATE_CHECKIN',NULL,'2023-10-26','00:53:21','07:53:39','07:53:21',56),(369,'dong','2023-11-01 17:52:26.987000','dong','2023-11-01 17:52:26.987000',NULL,'EARLY_CHECKOUT',NULL,'2023-10-26','09:06:21','07:53:39','07:53:21',56),(372,'dong','2023-11-01 17:52:27.032000','dong','2023-11-01 17:52:27.032000',NULL,'LATE_CHECKIN',NULL,'2023-10-26','00:53:35','07:53:35','07:53:35',57),(373,'dong','2023-11-01 17:52:27.050000','dong','2023-11-01 17:52:27.050000',NULL,'EARLY_CHECKOUT',NULL,'2023-10-26','09:06:25','07:53:35','07:53:35',57),(376,'anonymousUser','2023-11-01 17:59:43.979000','anonymousUser','2023-11-01 17:59:43.979000',NULL,'LATE_CHECKIN',NULL,'2023-10-26','00:53:23','07:53:41','07:53:23',3),(377,'anonymousUser','2023-11-01 17:59:43.990000','anonymousUser','2023-11-01 17:59:43.990000',NULL,'EARLY_CHECKOUT',NULL,'2023-10-26','09:06:19','07:53:41','07:53:23',3),(380,'anonymousUser','2023-11-01 17:59:44.026000','anonymousUser','2023-11-01 17:59:44.026000',NULL,'LATE_CHECKIN',NULL,'2023-10-26','00:53:30','07:53:30','07:53:30',52),(381,'anonymousUser','2023-11-01 17:59:44.038000','anonymousUser','2023-11-01 17:59:44.038000',NULL,'EARLY_CHECKOUT',NULL,'2023-10-26','09:06:30','07:53:30','07:53:30',52),(384,'anonymousUser','2023-11-01 17:59:44.073000','anonymousUser','2023-11-01 17:59:44.073000',NULL,'LATE_CHECKIN',NULL,'2023-10-26','00:53:25','07:53:43','07:53:25',53),(385,'anonymousUser','2023-11-01 17:59:44.086000','anonymousUser','2023-11-01 17:59:44.086000',NULL,'EARLY_CHECKOUT',NULL,'2023-10-26','09:06:17','07:53:43','07:53:25',53),(388,'anonymousUser','2023-11-01 17:59:44.130000','anonymousUser','2023-11-01 17:59:44.130000',NULL,'LATE_CHECKIN',NULL,'2023-10-26','00:53:28','07:53:45','07:53:28',55),(389,'anonymousUser','2023-11-01 17:59:44.143000','anonymousUser','2023-11-01 17:59:44.143000',NULL,'EARLY_CHECKOUT',NULL,'2023-10-26','09:06:15','07:53:45','07:53:28',55),(392,'anonymousUser','2023-11-01 17:59:44.198000','anonymousUser','2023-11-01 17:59:44.198000',NULL,'LATE_CHECKIN',NULL,'2023-10-26','00:53:21','07:53:39','07:53:21',56),(393,'anonymousUser','2023-11-01 17:59:44.216000','anonymousUser','2023-11-01 17:59:44.216000',NULL,'EARLY_CHECKOUT',NULL,'2023-10-26','09:06:21','07:53:39','07:53:21',56),(396,'anonymousUser','2023-11-01 17:59:44.272000','anonymousUser','2023-11-01 17:59:44.272000',NULL,'LATE_CHECKIN',NULL,'2023-10-26','00:53:35','07:53:35','07:53:35',57),(397,'anonymousUser','2023-11-01 17:59:44.289000','anonymousUser','2023-11-01 17:59:44.289000',NULL,'EARLY_CHECKOUT',NULL,'2023-10-26','09:06:25','07:53:35','07:53:35',57),(400,'dong','2023-11-01 18:00:48.566000','dong','2023-11-01 18:00:48.566000',NULL,'LATE_CHECKIN',NULL,'2023-10-26','00:53:23','07:53:41','07:53:23',3),(401,'dong','2023-11-01 18:00:48.578000','dong','2023-11-01 18:00:48.578000',NULL,'EARLY_CHECKOUT',NULL,'2023-10-26','09:06:19','07:53:41','07:53:23',3),(404,'dong','2023-11-01 18:00:48.616000','dong','2023-11-01 18:00:48.616000',NULL,'LATE_CHECKIN',NULL,'2023-10-26','00:53:30','07:53:30','07:53:30',52),(405,'dong','2023-11-01 18:00:48.625000','dong','2023-11-01 18:00:48.625000',NULL,'EARLY_CHECKOUT',NULL,'2023-10-26','09:06:30','07:53:30','07:53:30',52),(408,'dong','2023-11-01 18:00:48.656000','dong','2023-11-01 18:00:48.656000',NULL,'LATE_CHECKIN',NULL,'2023-10-26','00:53:25','07:53:43','07:53:25',53),(409,'dong','2023-11-01 18:00:48.666000','dong','2023-11-01 18:00:48.666000',NULL,'EARLY_CHECKOUT',NULL,'2023-10-26','09:06:17','07:53:43','07:53:25',53),(412,'dong','2023-11-01 18:00:48.691000','dong','2023-11-01 18:00:48.691000',NULL,'LATE_CHECKIN',NULL,'2023-10-26','00:53:28','07:53:45','07:53:28',55),(413,'dong','2023-11-01 18:00:48.699000','dong','2023-11-01 18:00:48.699000',NULL,'EARLY_CHECKOUT',NULL,'2023-10-26','09:06:15','07:53:45','07:53:28',55),(416,'dong','2023-11-01 18:00:48.725000','dong','2023-11-01 18:00:48.725000',NULL,'LATE_CHECKIN',NULL,'2023-10-26','00:53:21','07:53:39','07:53:21',56),(417,'dong','2023-11-01 18:00:48.735000','dong','2023-11-01 18:00:48.735000',NULL,'EARLY_CHECKOUT',NULL,'2023-10-26','09:06:21','07:53:39','07:53:21',56),(420,'dong','2023-11-01 18:00:48.765000','dong','2023-11-01 18:00:48.765000',NULL,'LATE_CHECKIN',NULL,'2023-10-26','00:53:35','07:53:35','07:53:35',57),(421,'dong','2023-11-01 18:00:48.776000','dong','2023-11-01 18:00:48.776000',NULL,'EARLY_CHECKOUT',NULL,'2023-10-26','09:06:25','07:53:35','07:53:35',57),(424,'dong','2023-11-01 18:00:51.038000','dong','2023-11-01 18:00:51.038000',NULL,'LATE_CHECKIN',NULL,'2023-10-26','00:53:23','07:53:41','07:53:23',3),(425,'dong','2023-11-01 18:00:51.044000','dong','2023-11-01 18:00:51.044000',NULL,'EARLY_CHECKOUT',NULL,'2023-10-26','09:06:19','07:53:41','07:53:23',3),(428,'dong','2023-11-01 18:00:51.067000','dong','2023-11-01 18:00:51.067000',NULL,'LATE_CHECKIN',NULL,'2023-10-26','00:53:30','07:53:30','07:53:30',52),(429,'dong','2023-11-01 18:00:51.073000','dong','2023-11-01 18:00:51.073000',NULL,'EARLY_CHECKOUT',NULL,'2023-10-26','09:06:30','07:53:30','07:53:30',52),(432,'dong','2023-11-01 18:00:51.093000','dong','2023-11-01 18:00:51.093000',NULL,'LATE_CHECKIN',NULL,'2023-10-26','00:53:25','07:53:43','07:53:25',53),(433,'dong','2023-11-01 18:00:51.101000','dong','2023-11-01 18:00:51.101000',NULL,'EARLY_CHECKOUT',NULL,'2023-10-26','09:06:17','07:53:43','07:53:25',53),(436,'dong','2023-11-01 18:00:51.129000','dong','2023-11-01 18:00:51.129000',NULL,'LATE_CHECKIN',NULL,'2023-10-26','00:53:28','07:53:45','07:53:28',55),(437,'dong','2023-11-01 18:00:51.141000','dong','2023-11-01 18:00:51.141000',NULL,'EARLY_CHECKOUT',NULL,'2023-10-26','09:06:15','07:53:45','07:53:28',55),(440,'dong','2023-11-01 18:00:51.185000','dong','2023-11-01 18:00:51.185000',NULL,'LATE_CHECKIN',NULL,'2023-10-26','00:53:21','07:53:39','07:53:21',56),(441,'dong','2023-11-01 18:00:51.199000','dong','2023-11-01 18:00:51.199000',NULL,'EARLY_CHECKOUT',NULL,'2023-10-26','09:06:21','07:53:39','07:53:21',56),(444,'dong','2023-11-01 18:00:51.240000','dong','2023-11-01 18:00:51.240000',NULL,'LATE_CHECKIN',NULL,'2023-10-26','00:53:35','07:53:35','07:53:35',57),(445,'dong','2023-11-01 18:00:51.253000','dong','2023-11-01 18:00:51.253000',NULL,'EARLY_CHECKOUT',NULL,'2023-10-26','09:06:25','07:53:35','07:53:35',57),(448,'hue','2023-11-01 19:50:48.615000','hue','2023-11-01 19:50:48.615000',NULL,'LATE_CHECKIN',NULL,'2023-10-26','00:53:23','07:53:41','07:53:23',3),(449,'hue','2023-11-01 19:50:48.634000','hue','2023-11-01 19:50:48.634000',NULL,'EARLY_CHECKOUT',NULL,'2023-10-26','09:06:19','07:53:41','07:53:23',3),(452,'hue','2023-11-01 19:50:48.692000','hue','2023-11-01 19:50:48.692000',NULL,'LATE_CHECKIN',NULL,'2023-10-26','00:53:30','07:53:30','07:53:30',52),(453,'hue','2023-11-01 19:50:48.709000','hue','2023-11-01 19:50:48.709000',NULL,'EARLY_CHECKOUT',NULL,'2023-10-26','09:06:30','07:53:30','07:53:30',52),(456,'hue','2023-11-01 19:50:48.772000','hue','2023-11-01 19:50:48.772000',NULL,'LATE_CHECKIN',NULL,'2023-10-26','00:53:25','07:53:43','07:53:25',53),(457,'hue','2023-11-01 19:50:48.786000','hue','2023-11-01 19:50:48.786000',NULL,'EARLY_CHECKOUT',NULL,'2023-10-26','09:06:17','07:53:43','07:53:25',53),(460,'hue','2023-11-01 19:50:48.827000','hue','2023-11-01 19:50:48.827000',NULL,'LATE_CHECKIN',NULL,'2023-10-26','00:53:28','07:53:45','07:53:28',55),(461,'hue','2023-11-01 19:50:48.840000','hue','2023-11-01 19:50:48.840000',NULL,'EARLY_CHECKOUT',NULL,'2023-10-26','09:06:15','07:53:45','07:53:28',55),(464,'hue','2023-11-01 19:50:48.913000','hue','2023-11-01 19:50:48.913000',NULL,'LATE_CHECKIN',NULL,'2023-10-26','00:53:21','07:53:39','07:53:21',56),(465,'hue','2023-11-01 19:50:48.932000','hue','2023-11-01 19:50:48.932000',NULL,'EARLY_CHECKOUT',NULL,'2023-10-26','09:06:21','07:53:39','07:53:21',56),(468,'hue','2023-11-01 19:50:48.980000','hue','2023-11-01 19:50:48.980000',NULL,'LATE_CHECKIN',NULL,'2023-10-26','00:53:35','07:53:35','07:53:35',57),(469,'hue','2023-11-01 19:50:48.994000','hue','2023-11-01 19:50:48.994000',NULL,'EARLY_CHECKOUT',NULL,'2023-10-26','09:06:25','07:53:35','07:53:35',57),(472,'anonymousUser','2023-11-02 00:16:31.652000','anonymousUser','2023-11-02 00:16:31.652000',NULL,'LATE_CHECKIN',NULL,'2023-10-26','00:53:23','07:53:41','07:53:23',3),(473,'anonymousUser','2023-11-02 00:16:31.677000','anonymousUser','2023-11-02 00:16:31.677000',NULL,'EARLY_CHECKOUT',NULL,'2023-10-26','09:06:19','07:53:41','07:53:23',3),(476,'anonymousUser','2023-11-02 00:16:31.750000','anonymousUser','2023-11-02 00:16:31.750000',NULL,'LATE_CHECKIN',NULL,'2023-10-26','00:53:30','07:53:30','07:53:30',52),(477,'anonymousUser','2023-11-02 00:16:31.774000','anonymousUser','2023-11-02 00:16:31.774000',NULL,'EARLY_CHECKOUT',NULL,'2023-10-26','09:06:30','07:53:30','07:53:30',52),(480,'anonymousUser','2023-11-02 00:16:31.855000','anonymousUser','2023-11-02 00:16:31.855000',NULL,'LATE_CHECKIN',NULL,'2023-10-26','00:53:25','07:53:43','07:53:25',53),(481,'anonymousUser','2023-11-02 00:16:31.882000','anonymousUser','2023-11-02 00:16:31.882000',NULL,'EARLY_CHECKOUT',NULL,'2023-10-26','09:06:17','07:53:43','07:53:25',53),(484,'anonymousUser','2023-11-02 00:16:31.950000','anonymousUser','2023-11-02 00:16:31.950000',NULL,'LATE_CHECKIN',NULL,'2023-10-26','00:53:28','07:53:45','07:53:28',55),(485,'anonymousUser','2023-11-02 00:16:31.970000','anonymousUser','2023-11-02 00:16:31.970000',NULL,'EARLY_CHECKOUT',NULL,'2023-10-26','09:06:15','07:53:45','07:53:28',55),(488,'anonymousUser','2023-11-02 00:16:32.037000','anonymousUser','2023-11-02 00:16:32.037000',NULL,'LATE_CHECKIN',NULL,'2023-10-26','00:53:21','07:53:39','07:53:21',56),(489,'anonymousUser','2023-11-02 00:16:32.064000','anonymousUser','2023-11-02 00:16:32.064000',NULL,'EARLY_CHECKOUT',NULL,'2023-10-26','09:06:21','07:53:39','07:53:21',56),(492,'anonymousUser','2023-11-02 00:16:32.128000','anonymousUser','2023-11-02 00:16:32.128000',NULL,'LATE_CHECKIN',NULL,'2023-10-26','00:53:35','07:53:35','07:53:35',57),(493,'anonymousUser','2023-11-02 00:16:32.144000','anonymousUser','2023-11-02 00:16:32.144000',NULL,'EARLY_CHECKOUT',NULL,'2023-10-26','09:06:25','07:53:35','07:53:35',57);
/*!40000 ALTER TABLE `log_in_late_out_early` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `position`
--

DROP TABLE IF EXISTS `position`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `position` (
                            `position_id` bigint NOT NULL AUTO_INCREMENT,
                            `created_by` varchar(255) DEFAULT NULL,
    `create_date` datetime(6) DEFAULT NULL,
    `updated_by` varchar(255) DEFAULT NULL,
    `updated_date` datetime(6) DEFAULT NULL,
    `position_name` varchar(200) NOT NULL,
    PRIMARY KEY (`position_id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `position`
--

LOCK TABLES `position` WRITE;
/*!40000 ALTER TABLE `position` DISABLE KEYS */;
INSERT INTO `position` VALUES (1,NULL,NULL,NULL,NULL,'Truong phong'),(2,NULL,NULL,NULL,NULL,'Nhan vien');
/*!40000 ALTER TABLE `position` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `position_roles`
--

DROP TABLE IF EXISTS `position_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `position_roles` (
                                  `position_id` bigint NOT NULL,
                                  `role_id` bigint NOT NULL,
                                  PRIMARY KEY (`position_id`,`role_id`),
    KEY `FKns0g5lbet1viwpnyaffh8m51k` (`role_id`),
    CONSTRAINT `FKns0g5lbet1viwpnyaffh8m51k` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
    CONSTRAINT `FKpy5tejqwi1y43h4a0ei86v94c` FOREIGN KEY (`position_id`) REFERENCES `position` (`position_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `position_roles`
--

LOCK TABLES `position_roles` WRITE;
/*!40000 ALTER TABLE `position_roles` DISABLE KEYS */;
INSERT INTO `position_roles` VALUES (1,1),(2,2);
/*!40000 ALTER TABLE `position_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `request`
--

DROP TABLE IF EXISTS `request`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `request` (
                           `request_id` bigint NOT NULL AUTO_INCREMENT,
                           `created_by` varchar(255) DEFAULT NULL,
    `create_date` datetime(6) DEFAULT NULL,
    `updated_by` varchar(255) DEFAULT NULL,
    `updated_date` datetime(6) DEFAULT NULL,
    `accept_at` date DEFAULT NULL,
    `accept_by` bigint DEFAULT NULL,
    `end_date` date DEFAULT NULL,
    `end_time` time DEFAULT NULL,
    `note` varchar(255) DEFAULT NULL,
    `content` varchar(300) NOT NULL,
    `request_title` varchar(100) NOT NULL,
    `start_date` date DEFAULT NULL,
    `start_time` time DEFAULT NULL,
    `status` int NOT NULL,
    `request_type_id` int NOT NULL,
    `user_id` bigint NOT NULL,
    PRIMARY KEY (`request_id`),
    KEY `FKibmr315gqv6g75nhasnydst5w` (`request_type_id`),
    KEY `FKg03bldv86pfuboqfefx48p6u3` (`user_id`),
    CONSTRAINT `FKg03bldv86pfuboqfefx48p6u3` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
    CONSTRAINT `FKibmr315gqv6g75nhasnydst5w` FOREIGN KEY (`request_type_id`) REFERENCES `request_type` (`request_type_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `request`
--

LOCK TABLES `request` WRITE;
/*!40000 ALTER TABLE `request` DISABLE KEYS */;
/*!40000 ALTER TABLE `request` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `request_category`
--

DROP TABLE IF EXISTS `request_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `request_category` (
                                    `request_category_id` int NOT NULL AUTO_INCREMENT,
                                    `request_category_name` varchar(100) NOT NULL,
    PRIMARY KEY (`request_category_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `request_category`
--

LOCK TABLES `request_category` WRITE;
/*!40000 ALTER TABLE `request_category` DISABLE KEYS */;
/*!40000 ALTER TABLE `request_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `request_type`
--

DROP TABLE IF EXISTS `request_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `request_type` (
                                `request_type_id` int NOT NULL AUTO_INCREMENT,
                                `replacement_person` varchar(100) DEFAULT NULL,
    `replacement_work` varchar(100) DEFAULT NULL,
    `request_type_name` varchar(100) NOT NULL,
    `request_category_id` int NOT NULL,
    PRIMARY KEY (`request_type_id`),
    KEY `FK5hvutd3vg845f5iirsuk8wtc` (`request_category_id`),
    CONSTRAINT `FK5hvutd3vg845f5iirsuk8wtc` FOREIGN KEY (`request_category_id`) REFERENCES `request_category` (`request_category_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `request_type`
--

LOCK TABLES `request_type` WRITE;
/*!40000 ALTER TABLE `request_type` DISABLE KEYS */;
/*!40000 ALTER TABLE `request_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
                        `id` bigint NOT NULL AUTO_INCREMENT,
                        `role_name` varchar(200) NOT NULL,
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'ROLE_ADMIN'),(2,'ROLE_USER'),(3,'ROLE_MODERATOR');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sign`
--

DROP TABLE IF EXISTS `sign`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sign` (
                        `sign_id` bigint NOT NULL,
                        `sign_name` varchar(20) DEFAULT NULL,
    PRIMARY KEY (`sign_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sign`
--

LOCK TABLES `sign` WRITE;
/*!40000 ALTER TABLE `sign` DISABLE KEYS */;
INSERT INTO `sign` VALUES (1,'H'),(2,'H_P'),(303,'H'),(307,'H'),(311,'H'),(315,'H'),(319,'H'),(323,'H'),(327,'H_P'),(331,'H_P'),(335,'H_P'),(339,'H_P'),(343,'H_P'),(347,'H_P'),(351,'H_P'),(355,'H_P'),(359,'H_P'),(363,'H_P'),(367,'H_P'),(371,'H_P'),(375,'H_P'),(379,'H_P'),(383,'H_P'),(387,'H_P'),(391,'H_P'),(395,'H_P'),(399,'H_P'),(403,'H_P'),(407,'H_P'),(411,'H_P'),(415,'H_P'),(419,'H_P'),(423,'H_P'),(427,'H_P'),(431,'H_P'),(435,'H_P'),(439,'H_P'),(443,'H_P'),(447,'H_P'),(451,'H_P'),(455,'H_P'),(459,'H_P'),(463,'H_P'),(467,'H_P'),(471,'H_P'),(475,'H_P'),(479,'H_P'),(483,'H_P'),(487,'H_P'),(491,'H_P'),(495,'H'),(496,'H'),(498,'H'),(499,'H');
/*!40000 ALTER TABLE `sign` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
                         `id` bigint NOT NULL AUTO_INCREMENT,
                         `created_by` varchar(255) DEFAULT NULL,
    `create_date` datetime(6) DEFAULT NULL,
    `updated_by` varchar(255) DEFAULT NULL,
    `updated_date` datetime(6) DEFAULT NULL,
    `address` varchar(255) DEFAULT NULL,
    `birth_day` date DEFAULT NULL,
    `email` varchar(200) NOT NULL,
    `end_work` date DEFAULT NULL,
    `full_name` varchar(200) NOT NULL,
    `gender` int DEFAULT NULL,
    `password` varchar(255) NOT NULL,
    `phone` varchar(255) DEFAULT NULL,
    `start_work` date DEFAULT NULL,
    `status` int DEFAULT NULL,
    `user_code` varchar(255) DEFAULT NULL,
    `user_image` varchar(255) DEFAULT NULL,
    `user_name` varchar(255) DEFAULT NULL,
    `department_id` bigint NOT NULL,
    `position_id` bigint DEFAULT NULL,
    `day_off` int DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_6dotkott2kjsp8vw4d0m25fb7` (`email`),
    KEY `FK7vgyxapb64uaodvvpvpubeert` (`department_id`),
    KEY `FKay81wkdumu7athhw45e1l1lln` (`position_id`),
    CONSTRAINT `FK7vgyxapb64uaodvvpvpubeert` FOREIGN KEY (`department_id`) REFERENCES `department` (`department_id`),
    CONSTRAINT `FKay81wkdumu7athhw45e1l1lln` FOREIGN KEY (`position_id`) REFERENCES `position` (`position_id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,NULL,NULL,NULL,NULL,NULL,NULL,'hoan@gmail.com',NULL,'Nguyen Ngoc Hoan',NULL,'$2a$12$1IwF1HUkYzV4X8ZOhNUdVuJ7hXYZVLSLphHAfWhhVBMHirKlceS1K',NULL,NULL,NULL,'FPT_00001',NULL,'hoan',1,2,NULL),(2,NULL,NULL,NULL,NULL,NULL,NULL,'cuong@gmail.com',NULL,'cao van cuong',NULL,'$2a$12$1IwF1HUkYzV4X8ZOhNUdVuJ7hXYZVLSLphHAfWhhVBMHirKlceS1K',NULL,NULL,NULL,'FPT_00008',NULL,'cuong',1,2,NULL),(3,NULL,NULL,NULL,NULL,NULL,NULL,'giang@gmai.com',NULL,'pham minh giang',NULL,'$2a$12$1IwF1HUkYzV4X8ZOhNUdVuJ7hXYZVLSLphHAfWhhVBMHirKlceS1K',NULL,NULL,NULL,'FPT_00009',NULL,'giang',1,1,NULL),(46,NULL,NULL,NULL,NULL,NULL,NULL,'dung@gmail.com',NULL,'Chu Tuan Dung',NULL,'$2a$12$1IwF1HUkYzV4X8ZOhNUdVuJ7hXYZVLSLphHAfWhhVBMHirKlceS1K',NULL,NULL,NULL,'FPT_00002',NULL,'dung',1,2,NULL),(47,NULL,NULL,NULL,NULL,NULL,NULL,'son@gmail.con',NULL,'Do Dinh Son',NULL,'$2a$12$1IwF1HUkYzV4X8ZOhNUdVuJ7hXYZVLSLphHAfWhhVBMHirKlceS1K',NULL,NULL,NULL,'FPT_00003',NULL,'son',1,2,NULL),(48,NULL,NULL,NULL,NULL,NULL,NULL,'hanh@gmail.com',NULL,'vo thi hong hanh',NULL,'$2a$12$1IwF1HUkYzV4X8ZOhNUdVuJ7hXYZVLSLphHAfWhhVBMHirKlceS1K',NULL,NULL,NULL,'FPT_00004',NULL,'hanh',1,2,NULL),(49,NULL,NULL,NULL,NULL,NULL,NULL,'hao@gmail.com',NULL,'nguyen phong hao',NULL,'$2a$12$1IwF1HUkYzV4X8ZOhNUdVuJ7hXYZVLSLphHAfWhhVBMHirKlceS1K',NULL,NULL,NULL,'FPT_00005',NULL,'hao',1,2,NULL),(50,NULL,NULL,NULL,NULL,NULL,NULL,'hai@gmail.com',NULL,'phung duc hai',NULL,'$2a$12$1IwF1HUkYzV4X8ZOhNUdVuJ7hXYZVLSLphHAfWhhVBMHirKlceS1K',NULL,NULL,NULL,'FPT_00006',NULL,'hai',1,2,NULL),(51,NULL,NULL,NULL,NULL,NULL,NULL,'truong@gmail.com',NULL,'trinh xuan truong',NULL,'$2a$12$1IwF1HUkYzV4X8ZOhNUdVuJ7hXYZVLSLphHAfWhhVBMHirKlceS1K',NULL,NULL,NULL,'FPT_00007',NULL,'truong',1,2,NULL),(52,NULL,NULL,NULL,NULL,NULL,NULL,'hue@gmail.com',NULL,'doan thi hue',NULL,'$2a$12$1IwF1HUkYzV4X8ZOhNUdVuJ7hXYZVLSLphHAfWhhVBMHirKlceS1K',NULL,NULL,NULL,'FPT_00010',NULL,'hue',1,1,NULL),(53,NULL,NULL,NULL,NULL,NULL,NULL,'minh@gmail.com',NULL,'hoang nhat minh',NULL,'$2a$12$1IwF1HUkYzV4X8ZOhNUdVuJ7hXYZVLSLphHAfWhhVBMHirKlceS1K',NULL,NULL,NULL,'FPT_00011',NULL,'minh',1,2,NULL),(54,NULL,NULL,NULL,NULL,NULL,NULL,'khanh@gmail.com',NULL,'tran duy khanh',NULL,'$2a$12$1IwF1HUkYzV4X8ZOhNUdVuJ7hXYZVLSLphHAfWhhVBMHirKlceS1K',NULL,NULL,NULL,'FPT_00012',NULL,'khanh',1,2,NULL),(55,NULL,NULL,NULL,NULL,NULL,NULL,'dungg@gmail.com',NULL,'le trung dung',NULL,'$2a$12$1IwF1HUkYzV4X8ZOhNUdVuJ7hXYZVLSLphHAfWhhVBMHirKlceS1K',NULL,NULL,NULL,'FPT_00013',NULL,'dungg',1,2,NULL),(56,NULL,NULL,NULL,NULL,NULL,NULL,'nam@gmail.com',NULL,'le ha nam',NULL,'$2a$12$1IwF1HUkYzV4X8ZOhNUdVuJ7hXYZVLSLphHAfWhhVBMHirKlceS1K',NULL,NULL,NULL,'FPT_00014',NULL,'nam',1,2,NULL),(57,NULL,NULL,NULL,NULL,NULL,NULL,'lam@gmail.com',NULL,'dao duc lam',NULL,'$2a$12$1IwF1HUkYzV4X8ZOhNUdVuJ7hXYZVLSLphHAfWhhVBMHirKlceS1K',NULL,NULL,NULL,'FPT_00015',NULL,'lam',1,2,NULL),(58,NULL,NULL,NULL,NULL,NULL,NULL,'quynh@gmail.com',NULL,'chu thuy quyynh',NULL,'$2a$12$1IwF1HUkYzV4X8ZOhNUdVuJ7hXYZVLSLphHAfWhhVBMHirKlceS1K',NULL,NULL,NULL,'FPT_00016',NULL,'quynh',1,2,NULL),(59,NULL,NULL,NULL,NULL,NULL,NULL,'duy@gmail.com',NULL,'ta ngoc duy',NULL,'$2a$12$1IwF1HUkYzV4X8ZOhNUdVuJ7hXYZVLSLphHAfWhhVBMHirKlceS1K',NULL,NULL,NULL,'FPT_00017',NULL,'duy',1,2,NULL),(60,NULL,NULL,NULL,NULL,NULL,NULL,'donghphe150112@fpt.edu.vn',NULL,'Ho Phuong Dong',NULL,'$2a$12$eIIbe6MDqDzkYfk4U389ZeHShft56A1fBTwjiTjawvlG.BvtIKhuK',NULL,NULL,NULL,'FPT_00018',NULL,'dong',1,1,NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `working_time`
--

DROP TABLE IF EXISTS `working_time`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `working_time` (
                                `working_time_id` int NOT NULL AUTO_INCREMENT,
                                `end_time` time DEFAULT NULL,
                                `start_time` time DEFAULT NULL,
                                `working_time_name` varchar(100) NOT NULL,
    PRIMARY KEY (`working_time_id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `working_time`
--

LOCK TABLES `working_time` WRITE;
/*!40000 ALTER TABLE `working_time` DISABLE KEYS */;
INSERT INTO `working_time` VALUES (1,'17:00:00','07:00:00','FULLTIME'),(2,'11:00:00','07:00:00','MORNING_SHIFT'),(3,'17:00:00','13:00:00','AFTERNOON_SHIFT');
/*!40000 ALTER TABLE `working_time` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-11-06 17:26:24
