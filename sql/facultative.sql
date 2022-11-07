-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema facultative
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema facultative
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS facultative DEFAULT CHARACTER SET utf8 ;
USE facultative ;

-- -----------------------------------------------------
-- Table category
-- -----------------------------------------------------
DROP TABLE IF EXISTS category ;

CREATE TABLE IF NOT EXISTS category (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  description VARCHAR(255) NULL,
  PRIMARY KEY (id),
  UNIQUE INDEX name_UNIQUE (name ASC) VISIBLE);


-- -----------------------------------------------------
-- Table course_status
-- -----------------------------------------------------
DROP TABLE IF EXISTS course_status ;

CREATE TABLE IF NOT EXISTS course_status (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(45) NOT NULL,
  PRIMARY KEY (id))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table course
-- -----------------------------------------------------
DROP TABLE IF EXISTS course ;

CREATE TABLE IF NOT EXISTS course (
  id INT NOT NULL AUTO_INCREMENT,
  title VARCHAR(255) NOT NULL,
  duration INT UNSIGNED NOT NULL,
  course_start_date DATETIME NOT NULL DEFAULT current_timestamp, 
  category_id INT NOT NULL,
  course_status_id INT NOT NULL,
  description VARCHAR(45) NULL,
  PRIMARY KEY (id),
  INDEX fk_course_category_idx (category_id ASC) VISIBLE,
  INDEX fk_course_course_status1_idx (course_status_id ASC) VISIBLE,
  UNIQUE INDEX title_UNIQUE (title ASC) VISIBLE,
  CONSTRAINT fk_course_category
    FOREIGN KEY (category_id)
    REFERENCES category (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_course_course_status1
    FOREIGN KEY (course_status_id)
    REFERENCES course_status (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table role
-- -----------------------------------------------------
DROP TABLE IF EXISTS role ;

CREATE TABLE IF NOT EXISTS role (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE INDEX name_UNIQUE (name ASC) VISIBLE);


-- -----------------------------------------------------
-- Table user
-- -----------------------------------------------------
DROP TABLE IF EXISTS user ;

CREATE TABLE IF NOT EXISTS user (
  id INT NOT NULL AUTO_INCREMENT,
  login VARCHAR(16) NOT NULL,
  password VARCHAR(255) NOT NULL,
  first_name VARCHAR(32) NOT NULL,
  last_name VARCHAR(45) NOT NULL,
  email VARCHAR(45) NOT NULL,
  role_id INT NOT NULL,
  PRIMARY KEY (id),
  INDEX fk_user_role1_idx (role_id ASC) VISIBLE,
  UNIQUE INDEX login_UNIQUE (login ASC) VISIBLE,
  CONSTRAINT fk_user_role1
    FOREIGN KEY (role_id)
    REFERENCES role (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table users_course
-- -----------------------------------------------------
DROP TABLE IF EXISTS users_course ;

CREATE TABLE IF NOT EXISTS users_course (
  course_id INT NOT NULL,
  user_id INT NOT NULL,
  PRIMARY KEY (course_id, user_id),
  INDEX fk_course_has_user_user1_idx (user_id ASC) VISIBLE,
  INDEX fk_course_has_user_course1_idx (course_id ASC) VISIBLE,
  CONSTRAINT fk_course_has_user_course1
    FOREIGN KEY (course_id)
    REFERENCES course (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT fk_course_has_user_user1
    FOREIGN KEY (user_id)
    REFERENCES user (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table grade_book
-- -----------------------------------------------------
DROP TABLE IF EXISTS grade_book ;

CREATE TABLE IF NOT EXISTS grade_book (
  course_id INT NOT NULL,
  student_id INT NOT NULL,
  grade INT UNSIGNED NOT NULL,
  PRIMARY KEY (course_id, student_id),
  INDEX fk_course_has_user1_user1_idx (student_id ASC) VISIBLE,
  INDEX fk_course_has_user1_course1_idx (course_id ASC) VISIBLE,
  CONSTRAINT fk_course_has_user1_course1
    FOREIGN KEY (course_id)
    REFERENCES course (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_course_has_user1_user1
    FOREIGN KEY (student_id)
    REFERENCES user (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

-- -----------------------------------------------------
-- Standard INSERTS
-- -----------------------------------------------------

-- role
INSERT INTO role (id,name)
	VALUES 
		(DEFAULT, "admin"),
		(DEFAULT, "teacher"),
		(DEFAULT, "student");    
       
-- course_status
INSERT INTO course_status (id, name)
	VALUES
		(DEFAULT, "coming_soon"),
        (DEFAULT, "in_process"),
        (DEFAULT, "completed");
      
SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
