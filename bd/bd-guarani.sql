CREATE DATABASE  IF NOT EXISTS `siu-guarani` /*!40100 DEFAULT CHARACTER SET utf8mb3 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `siu-guarani`;
-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: localhost    Database: siu-guarani
-- ------------------------------------------------------
-- Server version	8.0.30

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
-- Table structure for table `carrera`
--

DROP TABLE IF EXISTS `carrera`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `carrera` (
  `idcarrera` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idcarrera`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `carrera`
--

LOCK TABLES `carrera` WRITE;
/*!40000 ALTER TABLE `carrera` DISABLE KEYS */;
INSERT INTO `carrera` VALUES (1,'Licenciatura en Sistemas');
/*!40000 ALTER TABLE `carrera` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cuatrimestre`
--

DROP TABLE IF EXISTS `cuatrimestre`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cuatrimestre` (
  `idcuatrimestre` int NOT NULL AUTO_INCREMENT,
  `nro_cuatrimestre` int DEFAULT NULL,
  `cohorte` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idcuatrimestre`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cuatrimestre`
--

LOCK TABLES `cuatrimestre` WRITE;
/*!40000 ALTER TABLE `cuatrimestre` DISABLE KEYS */;
INSERT INTO `cuatrimestre` VALUES (1,1,'2022'),(2,2,'2022');
/*!40000 ALTER TABLE `cuatrimestre` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fechas_inscripciones`
--

DROP TABLE IF EXISTS `fechas_inscripciones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fechas_inscripciones` (
  `idfechas_inscripciones` int NOT NULL AUTO_INCREMENT,
  `inscripcion_cuatrimestres_desde` date DEFAULT NULL,
  `inscripcion_cuatrimestres_hasta` date DEFAULT NULL,
  `inscripcion_finales_desde` date DEFAULT NULL,
  `inscripciones_finales_hasta` date DEFAULT NULL,
  PRIMARY KEY (`idfechas_inscripciones`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fechas_inscripciones`
--

LOCK TABLES `fechas_inscripciones` WRITE;
/*!40000 ALTER TABLE `fechas_inscripciones` DISABLE KEYS */;
/*!40000 ALTER TABLE `fechas_inscripciones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `materia`
--

DROP TABLE IF EXISTS `materia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `materia` (
  `idmateria` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) DEFAULT NULL,
  `año` int DEFAULT NULL,
  `idcarrera` int NOT NULL,
  PRIMARY KEY (`idmateria`),
  KEY `fk_materia_carrera_idx` (`idcarrera`),
  CONSTRAINT `fk_materia_carrera` FOREIGN KEY (`idcarrera`) REFERENCES `carrera` (`idcarrera`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `materia`
--

LOCK TABLES `materia` WRITE;
/*!40000 ALTER TABLE `materia` DISABLE KEYS */;
INSERT INTO `materia` VALUES (1,'Matematica I',1,1),(2,'Programacion de Computadoras',1,1),(3,'Arquitectura de Computadoras',1,1),(4,'Matematica II',1,1),(5,'Matematica III',2,1),(6,'Ingenieria de Software I',2,1),(7,'Orientacion a Objetos I',2,1),(8,'Probabilidad y Estadistica',2,1);
/*!40000 ALTER TABLE `materia` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `materia_cuatrimestre`
--

DROP TABLE IF EXISTS `materia_cuatrimestre`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `materia_cuatrimestre` (
  `idmateria_cuatrimestre` int NOT NULL AUTO_INCREMENT,
  `idmateria` int NOT NULL,
  `idcuatrimestre` int NOT NULL,
  `horario_cursada` varchar(45) DEFAULT NULL,
  `turno` varchar(45) DEFAULT NULL,
  `idusuario_docente` int NOT NULL,
  PRIMARY KEY (`idmateria_cuatrimestre`),
  KEY `fk_materia_cuatrimestre_materia1_idx` (`idmateria`),
  KEY `fk_materia_cuatrimestre_cuatrimestre1_idx` (`idcuatrimestre`),
  KEY `fk_materia_cuatrimestre_usuario1_idx` (`idusuario_docente`),
  CONSTRAINT `fk_materia_cuatrimestre_cuatrimestre1` FOREIGN KEY (`idcuatrimestre`) REFERENCES `cuatrimestre` (`idcuatrimestre`),
  CONSTRAINT `fk_materia_cuatrimestre_materia1` FOREIGN KEY (`idmateria`) REFERENCES `materia` (`idmateria`),
  CONSTRAINT `fk_materia_cuatrimestre_usuario1` FOREIGN KEY (`idusuario_docente`) REFERENCES `usuario` (`idusuario`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `materia_cuatrimestre`
--

LOCK TABLES `materia_cuatrimestre` WRITE;
/*!40000 ALTER TABLE `materia_cuatrimestre` DISABLE KEYS */;
INSERT INTO `materia_cuatrimestre` VALUES (1,1,1,'Martes de 8 a 14hs','Mañana',2),(2,2,1,'Lunes de 8 a 14hs','Mañana',3),(3,3,2,'Miercoles de 8 a 14hs','Mañana',4),(4,4,1,'Miercoles de 8 a 14hs','Mañana',2),(5,5,1,'Jueves de 8 a 13hs','Mañana',5),(6,6,1,'Viernes de 8 a 14hs','Mañana',6),(7,7,1,'Martes de 8 a 13hs','Mañana',7),(8,8,2,'Miercoles de 9 a 12hs','Mañana',8);
/*!40000 ALTER TABLE `materia_cuatrimestre` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mesas_examen`
--

DROP TABLE IF EXISTS `mesas_examen`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mesas_examen` (
  `idmesas_examen` int NOT NULL AUTO_INCREMENT,
  `nro_acta` varchar(45) DEFAULT NULL,
  `idmateria` int NOT NULL,
  `fecha_examen` datetime DEFAULT NULL,
  `idusuario_docente` int NOT NULL,
  PRIMARY KEY (`idmesas_examen`),
  KEY `fk_mesas_examen_materia1_idx` (`idmateria`),
  KEY `fk_mesas_examen_usuario1_idx` (`idusuario_docente`),
  CONSTRAINT `fk_mesas_examen_materia1` FOREIGN KEY (`idmateria`) REFERENCES `materia` (`idmateria`),
  CONSTRAINT `fk_mesas_examen_usuario1` FOREIGN KEY (`idusuario_docente`) REFERENCES `usuario` (`idusuario`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mesas_examen`
--

LOCK TABLES `mesas_examen` WRITE;
/*!40000 ALTER TABLE `mesas_examen` DISABLE KEYS */;
INSERT INTO `mesas_examen` VALUES (1,'356',1,'2022-09-19 00:00:00',2),(2,'875',4,'2022-09-19 00:00:00',2);
/*!40000 ALTER TABLE `mesas_examen` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nota_parciales`
--

DROP TABLE IF EXISTS `nota_parciales`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nota_parciales` (
  `idnota_parciales` int NOT NULL AUTO_INCREMENT,
  `nota_parcial` float DEFAULT NULL,
  `idusuario_materia_cuatrimestre` int NOT NULL,
  PRIMARY KEY (`idnota_parciales`),
  KEY `fk_nota_parciales_usuario_materia_cuatrimestre1_idx` (`idusuario_materia_cuatrimestre`),
  CONSTRAINT `fk_nota_parciales_usuario_materia_cuatrimestre1` FOREIGN KEY (`idusuario_materia_cuatrimestre`) REFERENCES `usuario_materia_cuatrimestre` (`idusuario_materia_cuatrimestre`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nota_parciales`
--

LOCK TABLES `nota_parciales` WRITE;
/*!40000 ALTER TABLE `nota_parciales` DISABLE KEYS */;
INSERT INTO `nota_parciales` VALUES (1,4,1),(2,6,1);
/*!40000 ALTER TABLE `nota_parciales` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipo_usuario`
--

DROP TABLE IF EXISTS `tipo_usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tipo_usuario` (
  `idtipo_usuario` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idtipo_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipo_usuario`
--

LOCK TABLES `tipo_usuario` WRITE;
/*!40000 ALTER TABLE `tipo_usuario` DISABLE KEYS */;
INSERT INTO `tipo_usuario` VALUES (1,'Estudiante'),(2,'Docente'),(3,'Administrador');
/*!40000 ALTER TABLE `tipo_usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `idusuario` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) DEFAULT NULL,
  `apellido` varchar(45) DEFAULT NULL,
  `dni` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `direccion` varchar(45) DEFAULT NULL,
  `codigo_postal` int DEFAULT NULL,
  `username` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `idtipo_usuario` int NOT NULL,
  `promedio` float DEFAULT NULL,
  PRIMARY KEY (`idusuario`),
  KEY `fk_usuario_tipo_usuario1_idx` (`idtipo_usuario`),
  CONSTRAINT `fk_usuario_tipo_usuario1` FOREIGN KEY (`idtipo_usuario`) REFERENCES `tipo_usuario` (`idtipo_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'Martin','Agostino','45662345','magostino@hotmail.com','Av. Frias 456',1838,'magostino','dni45662345',1,NULL),(2,'Laura','Loidi','39765432','lloidi@gmail.com','Av 27 de Septiembre 321',1842,'lloidi','dni39765432',2,NULL),(3,'Hector','Carballo','31477352','hcarballo@hotmail.com','Burzaco 653',1765,'hcarballo','dni31477352',2,NULL),(4,'Roberto','Garcia','34772114','licgarcia@yahoo.com','Chacabuco 234',1832,'rgarcia','dni34772114',2,NULL),(5,'Jose','Vazquez','32455213','jvazquez@gmail.com','A. Argentina 314',1834,'jvazquez','dni32455213',2,NULL),(6,'Hernan','Amatrain','35433221','hamatrain@hotmail.com','Santa Fe 345',1844,'hamatrain','dni35433221',2,NULL),(7,'Alejandra','Vranik','36783267','avranik@yahoo.com','Belgrano 567',1832,'avranik','dni36783267',2,NULL),(8,'Edgardo','Di Dio','32456741','edidio@live.com','Granaderos 56',1830,'edidio','dni32456741',2,NULL);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario_materia_cuatrimestre`
--

DROP TABLE IF EXISTS `usuario_materia_cuatrimestre`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario_materia_cuatrimestre` (
  `idusuario_materia_cuatrimestre` int NOT NULL AUTO_INCREMENT,
  `idusuario` int NOT NULL,
  `idmateria_cuatrimestre` int NOT NULL,
  `nota_final` float DEFAULT NULL,
  `nota_definitiva` float DEFAULT NULL,
  `fecha_limite_carga` datetime DEFAULT NULL,
  PRIMARY KEY (`idusuario_materia_cuatrimestre`),
  KEY `fk_usuario_materia_usuario1_idx` (`idusuario`),
  KEY `fk_usuario_materia_materia_cuatrimestre1_idx` (`idmateria_cuatrimestre`),
  CONSTRAINT `fk_usuario_materia_materia_cuatrimestre1` FOREIGN KEY (`idmateria_cuatrimestre`) REFERENCES `materia_cuatrimestre` (`idmateria_cuatrimestre`),
  CONSTRAINT `fk_usuario_materia_usuario1` FOREIGN KEY (`idusuario`) REFERENCES `usuario` (`idusuario`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario_materia_cuatrimestre`
--

LOCK TABLES `usuario_materia_cuatrimestre` WRITE;
/*!40000 ALTER TABLE `usuario_materia_cuatrimestre` DISABLE KEYS */;
INSERT INTO `usuario_materia_cuatrimestre` VALUES (1,1,1,NULL,NULL,'2022-06-25 00:00:00'),(2,1,2,NULL,NULL,'2022-06-25 00:00:00');
/*!40000 ALTER TABLE `usuario_materia_cuatrimestre` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario_mesa`
--

DROP TABLE IF EXISTS `usuario_mesa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario_mesa` (
  `idusuario_mesa` int NOT NULL AUTO_INCREMENT,
  `usuario_idusuario` int NOT NULL,
  `mesas_examen_idmesas_examen` int NOT NULL,
  PRIMARY KEY (`idusuario_mesa`),
  KEY `fk_usuario_mesa_usuario1_idx` (`usuario_idusuario`),
  KEY `fk_usuario_mesa_mesas_examen1_idx` (`mesas_examen_idmesas_examen`),
  CONSTRAINT `fk_usuario_mesa_mesas_examen1` FOREIGN KEY (`mesas_examen_idmesas_examen`) REFERENCES `mesas_examen` (`idmesas_examen`),
  CONSTRAINT `fk_usuario_mesa_usuario1` FOREIGN KEY (`usuario_idusuario`) REFERENCES `usuario` (`idusuario`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario_mesa`
--

LOCK TABLES `usuario_mesa` WRITE;
/*!40000 ALTER TABLE `usuario_mesa` DISABLE KEYS */;
INSERT INTO `usuario_mesa` VALUES (1,1,1),(2,1,2);
/*!40000 ALTER TABLE `usuario_mesa` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-10-06 14:11:28
