-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema Quizmaster
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema Quizmaster
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `Quizmaster` DEFAULT CHARACTER SET utf8 ;
USE `Quizmaster` ;

-- -----------------------------------------------------
-- Table `Quizmaster`.`Gebruiker`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Quizmaster`.`Gebruiker` (
  `userId` INT NOT NULL AUTO_INCREMENT,
  `gebruikersnaam` VARCHAR(45) NOT NULL,
  `wachtwoord` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`userId`),
  UNIQUE INDEX `userId_UNIQUE` (`userId` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Quizmaster`.`Cursus`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Quizmaster`.`Cursus` (
  `cursusId` INT NOT NULL AUTO_INCREMENT,
  `cursusNaam` VARCHAR(45) NOT NULL,
  `userIdcoordinator` INT NOT NULL,
  PRIMARY KEY (`cursusId`),
  INDEX `verzinzelf5_idx` (`userIdcoordinator` ASC) VISIBLE,
  UNIQUE INDEX `cursusId_UNIQUE` (`cursusId` ASC) VISIBLE,
  CONSTRAINT `verzinzelf5`
    FOREIGN KEY (`userIdcoordinator`)
    REFERENCES `Quizmaster`.`Gebruiker` (`userId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Quizmaster`.`Groep`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Quizmaster`.`Groep` (
  `groepId` INT NOT NULL AUTO_INCREMENT,
  `groepNaam` VARCHAR(45) NOT NULL,
  `userdocentId` INT NOT NULL,
  `cursusId` INT NOT NULL,
  PRIMARY KEY (`groepId`),
  INDEX `verzinzelf4_idx` (`userdocentId` ASC) VISIBLE,
  INDEX `verzinzelf8_idx` (`cursusId` ASC) VISIBLE,
  UNIQUE INDEX `groepId_UNIQUE` (`groepId` ASC) VISIBLE,
  CONSTRAINT `verzinzelf4`
    FOREIGN KEY (`userdocentId`)
    REFERENCES `Quizmaster`.`Gebruiker` (`userId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `verzinzelf8`
    FOREIGN KEY (`cursusId`)
    REFERENCES `Quizmaster`.`Cursus` (`cursusId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Quizmaster`.`Inschrijving`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Quizmaster`.`Inschrijving` (
  `userstudentId` INT NOT NULL AUTO_INCREMENT,
  `groepId` INT NOT NULL,
  PRIMARY KEY (`userstudentId`, `groepId`),
  INDEX `verzinzelf3_idx` (`userstudentId` ASC) VISIBLE,
  INDEX `verzinzelf6_idx` (`groepId` ASC) VISIBLE,
  CONSTRAINT `verzinzelf3`
    FOREIGN KEY (`userstudentId`)
    REFERENCES `Quizmaster`.`Gebruiker` (`userId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `verzinzelf6`
    FOREIGN KEY (`groepId`)
    REFERENCES `Quizmaster`.`Groep` (`groepId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Quizmaster`.`Quiz`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Quizmaster`.`Quiz` (
  `quiznummer` INT NOT NULL AUTO_INCREMENT,
  `quizNaam` VARCHAR(45) NOT NULL,
  `cesuur` DECIMAL NOT NULL,
  `cursusId` INT NOT NULL,
  PRIMARY KEY (`quiznummer`),
  INDEX `verzinzelf10_idx` (`cursusId` ASC) VISIBLE,
  UNIQUE INDEX `quiznummer_UNIQUE` (`quiznummer` ASC) VISIBLE,
  CONSTRAINT `verzinzelf10`
    FOREIGN KEY (`cursusId`)
    REFERENCES `Quizmaster`.`Cursus` (`cursusId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Quizmaster`.`Quizvraag`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Quizmaster`.`Quizvraag` (
  `vraagId` INT NOT NULL AUTO_INCREMENT,
  `vraag` VARCHAR(45) NOT NULL,
  `antwoordJuist` VARCHAR(45) NULL,
  `quiznummer` INT NOT NULL,
  PRIMARY KEY (`vraagId`),
  INDEX `verzinzelf13_idx` (`quiznummer` ASC) VISIBLE,
  UNIQUE INDEX `vraagId_UNIQUE` (`vraagId` ASC) VISIBLE,
  CONSTRAINT `verzinzelf13`
    FOREIGN KEY (`quiznummer`)
    REFERENCES `Quizmaster`.`Quiz` (`quiznummer`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Quizmaster`.`Antwoord`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Quizmaster`.`Antwoord` (
  `aantwoordId` INT NOT NULL AUTO_INCREMENT,
  `antwoord` VARCHAR(45) NOT NULL,
  `vraagId` INT NOT NULL,
  PRIMARY KEY (`aantwoordId`, `vraagId`),
  INDEX `verzinzelf11_idx` (`vraagId` ASC) VISIBLE,
  UNIQUE INDEX `aantwoordId_UNIQUE` (`aantwoordId` ASC) VISIBLE,
  CONSTRAINT `verzinzelf11`
    FOREIGN KEY (`vraagId`)
    REFERENCES `Quizmaster`.`Quizvraag` (`vraagId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Quizmaster`.`Quizresult`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Quizmaster`.`Quizresult` (
  `quizresultId` INT NOT NULL AUTO_INCREMENT,
  `datum_invullen` DATETIME NOT NULL,
  `score` DECIMAL NOT NULL,
  `quiznummer` INT NOT NULL,
  `userstudentId` INT NOT NULL,
  PRIMARY KEY (`quizresultId`),
  INDEX `verzinzelf7_idx` (`quiznummer` ASC) VISIBLE,
  INDEX `verzinzelf9_idx` (`userstudentId` ASC) VISIBLE,
  UNIQUE INDEX `quizresultId_UNIQUE` (`quizresultId` ASC) VISIBLE,
  CONSTRAINT `verzinzelf7`
    FOREIGN KEY (`quiznummer`)
    REFERENCES `Quizmaster`.`Quiz` (`quiznummer`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `verzinzelf9`
    FOREIGN KEY (`userstudentId`)
    REFERENCES `Quizmaster`.`Gebruiker` (`userId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Quizmaster`.`Rol`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Quizmaster`.`Rol` (
  `rolId` INT NOT NULL AUTO_INCREMENT,
  `rol_beschrijving` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`rolId`),
  UNIQUE INDEX `rolId_UNIQUE` (`rolId` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Quizmaster`.`GebruikerRol`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Quizmaster`.`GebruikerRol` (
  `userId` INT NOT NULL AUTO_INCREMENT,
  `roleId` INT NOT NULL,
  PRIMARY KEY (`userId`, `roleId`),
  INDEX `verzinzelf2_idx` (`roleId` ASC) VISIBLE,
  INDEX `verzinzelf1_idx` (`userId` ASC) VISIBLE,
  UNIQUE INDEX `userId_UNIQUE` (`userId` ASC) VISIBLE,
  CONSTRAINT `verzinzelf1`
    FOREIGN KEY (`userId`)
    REFERENCES `Quizmaster`.`Gebruiker` (`userId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `verzinzelf2`
    FOREIGN KEY (`roleId`)
    REFERENCES `Quizmaster`.`Rol` (`rolId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
