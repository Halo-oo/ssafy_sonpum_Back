-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema sonpum
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema sonpum
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `sonpum` DEFAULT CHARACTER SET utf8 ;
USE `sonpum` ;

-- -----------------------------------------------------
-- Table `sonpum`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sonpum`.`user` (
  `userId` VARCHAR(45) NOT NULL,
  `userName` VARCHAR(45) NOT NULL,
  `userPwd` VARCHAR(5000) NOT NULL,
  `email` VARCHAR(100) NOT NULL,
  `joindate` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `role` VARCHAR(45) NOT NULL DEFAULT 'USER',
  `delFlag` INT NOT NULL DEFAULT 0,
  `phoneNumber` VARCHAR(45) NULL,
  `reportCount` INT NULL DEFAULT 0,
  `profileImage` VARCHAR(100) NULL,
  `token` VARCHAR(5000) NULL,
  PRIMARY KEY (`userId`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sonpum`.`dongcode`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sonpum`.`dongcode` (
  `dongCode` VARCHAR(45) NOT NULL,
  `sidoName` VARCHAR(45) NULL,
  `gugunName` VARCHAR(45) NULL,
  `dongName` VARCHAR(45) NULL,
  PRIMARY KEY (`dongCode`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sonpum`.`address`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sonpum`.`address` (
  `addressId` BIGINT NOT NULL AUTO_INCREMENT,
  `dongCode` VARCHAR(45) NOT NULL,
  `roadName` VARCHAR(100) NULL,
  `roadNameBonbun` VARCHAR(45) NULL,
  `roadNameBubun` VARCHAR(45) NULL,
  `apartName` VARCHAR(50) NULL DEFAULT NULL,
  `buildYear` INT NULL,
  PRIMARY KEY (`addressId`),
  INDEX `fk_address_dongcode1_idx` (`dongCode` ASC) VISIBLE,
  CONSTRAINT `fk_address_dongcode1`
    FOREIGN KEY (`dongCode`)
    REFERENCES `sonpum`.`dongcode` (`dongCode`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sonpum`.`house_product`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sonpum`.`house_product` (
  `houseProductId` INT NOT NULL AUTO_INCREMENT,
  `userId` VARCHAR(45) NOT NULL,
  `addressId` BIGINT NOT NULL,
  `floor` VARCHAR(45) NULL,
  `buildYear` VARCHAR(45) NULL,
  `dealAmount` VARCHAR(45) NULL,
  `area` VARCHAR(45) NULL,
  `dealType` VARCHAR(45) NULL,
  `stateFlag` INT NOT NULL DEFAULT 0,
  `content` TEXT NULL,
  PRIMARY KEY (`houseProductId`),
  INDEX `fk_HouseAsset_Address1_idx` (`addressId` ASC) VISIBLE,
  INDEX `fk_house_product_user1_idx` (`userId` ASC) VISIBLE,
  CONSTRAINT `fk_HouseAsset_Address1`
    FOREIGN KEY (`addressId`)
    REFERENCES `sonpum`.`address` (`addressId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_house_product_user1`
    FOREIGN KEY (`userId`)
    REFERENCES `sonpum`.`user` (`userId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sonpum`.`house_image`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sonpum`.`house_image` (
  `houseImageId` INT NOT NULL AUTO_INCREMENT,
  `houseProductId` INT NOT NULL,
  `saveFolder` VARCHAR(1000) NULL,
  `originalFileName` VARCHAR(1000) NULL,
  `saveFileName` VARCHAR(1000) NULL,
  PRIMARY KEY (`houseImageId`),
  INDEX `fk_HouseImage_HouseAsset_idx` (`houseProductId` ASC) VISIBLE,
  CONSTRAINT `fk_HouseImage_HouseAsset`
    FOREIGN KEY (`houseProductId`)
    REFERENCES `sonpum`.`house_product` (`houseProductId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sonpum`.`house_deal`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sonpum`.`house_deal` (
  `houseDealId` INT NOT NULL AUTO_INCREMENT,
  `addressId` BIGINT NOT NULL,
  `dealAmount` VARCHAR(45) NULL,
  `dealYear` INT NULL,
  `dealMonth` INT NULL,
  `dealDay` INT NULL,
  `area` VARCHAR(45) NULL,
  `floor` VARCHAR(45) NULL,
  `cancelDealType` VARCHAR(1) NULL,
  PRIMARY KEY (`houseDealId`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sonpum`.`board_notice`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sonpum`.`board_notice` (
  `articleNo` INT NOT NULL AUTO_INCREMENT,
  `userId` VARCHAR(45) NOT NULL,
  `subject` VARCHAR(100) NULL,
  `content` TEXT NULL,
  `hit` INT NOT NULL DEFAULT 0,
  `regitime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`articleNo`),
  INDEX `fk_board_notice_user1_idx` (`userId` ASC) VISIBLE,
  CONSTRAINT `fk_board_notice_user1`
    FOREIGN KEY (`userId`)
    REFERENCES `sonpum`.`user` (`userId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sonpum`.`board_report`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sonpum`.`board_report` (
  `articleNo` INT NOT NULL AUTO_INCREMENT,
  `userId` VARCHAR(45) NOT NULL,
  `subject` VARCHAR(100) NULL,
  `content` TEXT NULL,
  `hit` VARCHAR(45) NOT NULL DEFAULT 0,
  `regitime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`articleNo`),
  INDEX `fk_board_report_user1_idx` (`userId` ASC) VISIBLE,
  CONSTRAINT `fk_board_report_user1`
    FOREIGN KEY (`userId`)
    REFERENCES `sonpum`.`user` (`userId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sonpum`.`board_report_image`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sonpum`.`board_report_image` (
  `boardReportImageId` INT NOT NULL AUTO_INCREMENT,
  `articleNo` INT NOT NULL,
  `saveFolder` VARCHAR(1000) NULL,
  `originalFileName` VARCHAR(1000) NULL,
  `saveFileName` VARCHAR(1000) NULL,
  PRIMARY KEY (`boardReportImageId`),
  INDEX `fk_board_report_image_board_report1_idx` (`articleNo` ASC) VISIBLE,
  CONSTRAINT `fk_board_report_image_board_report1`
    FOREIGN KEY (`articleNo`)
    REFERENCES `sonpum`.`board_report` (`articleNo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sonpum`.`house_product_review`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sonpum`.`house_product_review` (
  `houseProductReviewId` INT NOT NULL AUTO_INCREMENT,
  `houseProductId` INT NOT NULL,
  `userId` VARCHAR(45) NOT NULL,
  `writerUserId` VARCHAR(45) NOT NULL,
  `rating` INT NOT NULL DEFAULT 0,
  `content` TEXT NULL,
  `image` VARCHAR(1000) NULL DEFAULT NULL,
  `regtime` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`houseProductReviewId`),
  INDEX `fk_house_product_review_house_product1_idx` (`houseProductId` ASC) VISIBLE,
  INDEX `fk_house_product_review_user1_idx` (`userId` ASC) VISIBLE,
  INDEX `fk_house_product_review_writer1_idx` (`writerUserId` ASC) VISIBLE,
  CONSTRAINT `fk_house_product_review_house_product1`
    FOREIGN KEY (`houseProductId`)
    REFERENCES `sonpum`.`house_product` (`houseProductId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_house_product_review_user1`
    FOREIGN KEY (`userId`)
    REFERENCES `sonpum`.`user` (`userId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_house_product_review_writer1`
    FOREIGN KEY (`writerUserId`)
    REFERENCES `sonpum`.`user` (`userId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sonpum`.`house_product_bookmark`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sonpum`.`house_product_bookmark` (
  `houseProductBookmarkId` INT NOT NULL AUTO_INCREMENT,
  `userId` VARCHAR(45) NOT NULL,
  `houseProductId` INT NOT NULL,
  PRIMARY KEY (`houseProductBookmarkId`),
  INDEX `fk_house_product_bookmark_user1_idx` (`userId` ASC) VISIBLE,
  INDEX `fk_house_product_bookmark_house_product1_idx` (`houseProductId` ASC) VISIBLE,
  CONSTRAINT `fk_house_product_bookmark_user1`
    FOREIGN KEY (`userId`)
    REFERENCES `sonpum`.`user` (`userId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_house_product_bookmark_house_product1`
    FOREIGN KEY (`houseProductId`)
    REFERENCES `sonpum`.`house_product` (`houseProductId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sonpum`.`sidocode`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sonpum`.`sidocode` (
  `sidoCode` VARCHAR(10) NOT NULL,
  `sidoName` VARCHAR(30) NULL DEFAULT NULL,
  PRIMARY KEY (`sidoCode`),
  UNIQUE INDEX `sidoName_UNIQUE` (`sidoName` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sonpum`.`guguncode`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sonpum`.`guguncode` (
  `gugunCode` VARCHAR(10) NOT NULL,
  `gugunName` VARCHAR(30) NULL DEFAULT NULL,
  PRIMARY KEY (`gugunCode`))
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
