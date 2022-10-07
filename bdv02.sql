-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema db_gestionacademica
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema db_gestionacademica
-- -----------------------------------------------------
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema db_gestionacademica
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `db_gestionacademica` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `db_gestionacademica` ;

-- -----------------------------------------------------
-- Table `db_gestionacademica`.`fechas_inscripciones`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_gestionacademica`.`fechas_inscripciones` (
  `idfechas_inscripciones` INT NOT NULL AUTO_INCREMENT,
  `inscripcion_cuatrimestres_desde` DATE NULL DEFAULT NULL,
  `inscripcion_cuatrimestres_hasta` DATE NULL DEFAULT NULL,
  `inscripcion_finales_desde` DATE NULL DEFAULT NULL,
  `inscripciones_finales_hasta` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`idfechas_inscripciones`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `db_gestionacademica`.`usuarios`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_gestionacademica`.`usuarios` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `apellido` VARCHAR(255) NULL DEFAULT NULL,
  `dni` INT NOT NULL,
  `nombre` VARCHAR(255) NULL DEFAULT NULL,
  `password` VARCHAR(255) NULL DEFAULT NULL,
  `tipo_usuario` INT NULL DEFAULT NULL,
  `usuario` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `db_gestionacademica`.`turno`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_gestionacademica`.`turno` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `horario` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `db_gestionacademica`.`cuatrimestre`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_gestionacademica`.`cuatrimestre` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `anio` INT NOT NULL,
  `periodo` INT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `db_gestionacademica`.`carrera`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_gestionacademica`.`carrera` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `db_gestionacademica`.`materia`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_gestionacademica`.`materia` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `anio` INT NOT NULL,
  `nombre` VARCHAR(255) NULL DEFAULT NULL,
  `id_carrera` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FKpw211cqmud0bc08lpqu90acw8` (`id_carrera` ASC),
  CONSTRAINT `FKpw211cqmud0bc08lpqu90acw8`
    FOREIGN KEY (`id_carrera`)
    REFERENCES `db_gestionacademica`.`carrera` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `db_gestionacademica`.`catedra`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_gestionacademica`.`catedra` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `es_final` BIT(1) DEFAULT 0,
  `turno_id` INT NOT NULL,
  `usuarios_id` INT NOT NULL,
  `cuatrimestre_id` INT NOT NULL,
  `materia_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_catedra_turno1_idx` (`turno_id` ASC),
  INDEX `fk_catedra_usuarios1_idx` (`usuarios_id` ASC),
  INDEX `fk_catedra_cuatrimestre1_idx` (`cuatrimestre_id` ASC),
  INDEX `fk_catedra_materia1_idx` (`materia_id` ASC),
  CONSTRAINT `fk_catedra_turno1`
    FOREIGN KEY (`turno_id`)
    REFERENCES `db_gestionacademica`.`turno` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_catedra_usuarios1`
    FOREIGN KEY (`usuarios_id`)
    REFERENCES `db_gestionacademica`.`usuarios` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_catedra_cuatrimestre1`
    FOREIGN KEY (`cuatrimestre_id`)
    REFERENCES `db_gestionacademica`.`cuatrimestre` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_catedra_materia1`
    FOREIGN KEY (`materia_id`)
    REFERENCES `db_gestionacademica`.`materia` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `db_gestionacademica`.`usuario_materia_cuatrimestre`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_gestionacademica`.`usuario_materia_cuatrimestre` (
  `idusuario_materia_cuatrimestre` INT NOT NULL AUTO_INCREMENT,
  `nota_final` FLOAT NULL DEFAULT NULL,
  `nota_definitiva` FLOAT NULL DEFAULT NULL,
  `fecha_limite_carga` DATETIME NULL DEFAULT NULL,
  `idusuario` INT NOT NULL,
  `id_materia_cuatrimestre` INT NOT NULL,
  PRIMARY KEY (`idusuario_materia_cuatrimestre`),
  INDEX `fk_usuario_materia_cuatrimestre_usuarios1_idx` (`idusuario` ASC),
  INDEX `fk_usuario_materia_cuatrimestre_catedra1_idx` (`id_materia_cuatrimestre` ASC),
  CONSTRAINT `fk_usuario_materia_cuatrimestre_usuarios1`
    FOREIGN KEY (`idusuario`)
    REFERENCES `db_gestionacademica`.`usuarios` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_usuario_materia_cuatrimestre_catedra1`
    FOREIGN KEY (`id_materia_cuatrimestre`)
    REFERENCES `db_gestionacademica`.`catedra` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `db_gestionacademica`.`nota_parciales`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_gestionacademica`.`nota_parciales` (
  `idnota_parciales` INT NOT NULL AUTO_INCREMENT,
  `nota_parcial` FLOAT NULL DEFAULT NULL,
  `idusuario_materia_cuatrimestre` INT NOT NULL,
  PRIMARY KEY (`idnota_parciales`),
  INDEX `fk_nota_parciales_usuario_materia_cuatrimestre1_idx` (`idusuario_materia_cuatrimestre` ASC),
  CONSTRAINT `fk_nota_parciales_usuario_materia_cuatrimestre1`
    FOREIGN KEY (`idusuario_materia_cuatrimestre`)
    REFERENCES `db_gestionacademica`.`usuario_materia_cuatrimestre` (`idusuario_materia_cuatrimestre`))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb3;

USE `db_gestionacademica` ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
