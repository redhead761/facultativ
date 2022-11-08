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
CREATE SCHEMA IF NOT EXISTS `facultative` DEFAULT CHARACTER SET utf8 ;
USE `facultative` ;

-- -----------------------------------------------------
-- Table `categories`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `categories` ;

CREATE TABLE IF NOT EXISTS `categories` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(255) NOT NULL,
  `description` VARCHAR(255) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`title` ASC) VISIBLE);


-- -----------------------------------------------------
-- Table `course_statuses`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `course_statuses` ;

CREATE TABLE IF NOT EXISTS `course_statuses` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `title_UNIQUE` (`title` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `courses`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `courses` ;

CREATE TABLE IF NOT EXISTS `courses` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(255) NOT NULL,
  `duration` INT UNSIGNED NOT NULL,
  `course_start_date` DATETIME NOT NULL,
  `description` VARCHAR(45) NULL,
  `category_id` INT NOT NULL,
  `course_status_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_course_category_idx` (`category_id` ASC) VISIBLE,
  INDEX `fk_course_course_status1_idx` (`course_status_id` ASC) VISIBLE,
  UNIQUE INDEX `title_UNIQUE` (`title` ASC) VISIBLE,
  CONSTRAINT `fk_course_category`
    FOREIGN KEY (`category_id`)
    REFERENCES `categories` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_course_course_status1`
    FOREIGN KEY (`course_status_id`)
    REFERENCES `course_statuses` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table `roles`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `roles` ;

CREATE TABLE IF NOT EXISTS `roles` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE);


-- -----------------------------------------------------
-- Table `users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `users` ;

CREATE TABLE IF NOT EXISTS `users` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `login` VARCHAR(16) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `first_name` VARCHAR(32) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `role_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_user_role1_idx` (`role_id` ASC) VISIBLE,
  UNIQUE INDEX `login_UNIQUE` (`login` ASC) VISIBLE,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
  CONSTRAINT `fk_user_role1`
    FOREIGN KEY (`role_id`)
    REFERENCES `roles` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table `users_course`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `users_course` ;

CREATE TABLE IF NOT EXISTS `users_course` (
  `course_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`course_id`, `user_id`),
  INDEX `fk_course_has_user_user1_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_course_has_user_course1_idx` (`course_id` ASC) VISIBLE,
  CONSTRAINT `fk_course_has_user_course1`
    FOREIGN KEY (`course_id`)
    REFERENCES `courses` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_course_has_user_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

-- -----------------------------------------------------
-- Table `grade_book`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `grade_book` ;

CREATE TABLE IF NOT EXISTS `grade_book` (
  `course_id` INT NOT NULL,
  `student_id` INT NOT NULL,
  `grade` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`course_id`, `student_id`),
  INDEX `fk_course_has_user1_user1_idx` (`student_id` ASC) VISIBLE,
  INDEX `fk_course_has_user1_course1_idx` (`course_id` ASC) VISIBLE,
  CONSTRAINT `fk_course_has_user1_course1`
    FOREIGN KEY (`course_id`)
    REFERENCES `courses` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_course_has_user1_user1`
    FOREIGN KEY (`student_id`)
    REFERENCES `users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

    -- -----------------------------------------------------
    -- Inserts
    -- -----------------------------------------------------

    -- roles
    INSERT INTO roles VALUES(1, 'ADMIN');
    INSERT INTO roles VALUES(2, 'TEACHER');
    INSERT INTO roles VALUES(3, 'STUDENT');

    -- course statuses
    INSERT INTO course_statuses VALUES(DEFAULT, "COMING_SOON");
    INSERT INTO course_statuses VALUES(DEFAULT, "IN_PROCESS");
    INSERT INTO course_statuses VALUES(DEFAULT, "COMPLETED");

    -- categories
    INSERT INTO categories VALUES(DEFAULT, 'PROGRAMMING',null);
    INSERT INTO categories VALUES(DEFAULT, 'FOREIGN_LANGUAGE',null);
    INSERT INTO categories VALUES(DEFAULT, 'MATH',null);

    -- users
    -- --admin
    INSERT INTO users VALUES(DEFAULT, 'admin', 'admin', 'admin', 'admin,', 'admin@gmail.com', (SELECT id FROM roles WHERE name='ADMIN'));

    -- --teachers
    INSERT INTO users VALUES(DEFAULT, 'Eddard', '111111', 'Eddard', 'Stark,', 'Stark@game.com', (SELECT id FROM roles WHERE name='TEACHER'));
    INSERT INTO users VALUES(DEFAULT, 'Tywin', '222222', 'Tywin', 'Lannister,', 'Lannister@game.com', (SELECT id FROM roles WHERE name='TEACHER'));
    INSERT INTO users VALUES(DEFAULT, 'Rhaegar', '333333', 'Rhaegar', 'Targaryen,', 'Targaryen@game.com', (SELECT id FROM roles WHERE name='TEACHER'));
    INSERT INTO users VALUES(DEFAULT, 'Baratheon ', '444444', 'Robert', 'Baratheon ,', 'Baratheon@game.com', (SELECT id FROM roles WHERE name='TEACHER'));

    -- --students
    INSERT INTO users VALUES(DEFAULT, 'Sansa ', '555555', 'Sansa', 'Stark', 'Sansa@game.com', (SELECT id FROM roles WHERE name='STUDENT'));
    INSERT INTO users VALUES(DEFAULT, 'Arya ', '666666', 'Arya', 'Stark', 'Arya@game.com', (SELECT id FROM roles WHERE name='STUDENT'));
    INSERT INTO users VALUES(DEFAULT, 'Brandon ', '777777', 'Brandon', 'Stark', 'Brandon@game.com', (SELECT id FROM roles WHERE name='STUDENT'));
    INSERT INTO users VALUES(DEFAULT, 'Tyrion ', '888888', 'Tyrion', 'Lannister', 'Tyrion@game.com', (SELECT id FROM roles WHERE name='STUDENT'));
    INSERT INTO users VALUES(DEFAULT, 'Bronn ', '999999', 'Bronn', 'Lannister', 'Bronn@game.com', (SELECT id FROM roles WHERE name='STUDENT'));
    INSERT INTO users VALUES(DEFAULT, 'Joffrie ', '101010', 'Joffrie', 'Lannister', 'Joffrie@game.com', (SELECT id FROM roles WHERE name='STUDENT'));
    INSERT INTO users VALUES(DEFAULT, 'Viserys ', '121212', 'Viserys', 'Targaryen', 'Viserys@game.com', (SELECT id FROM roles WHERE name='STUDENT'));
    INSERT INTO users VALUES(DEFAULT, 'Daenerys ', '131313', 'Daenerys', 'Targaryen', 'Daenerys@game.com', (SELECT id FROM roles WHERE name='STUDENT'));
    INSERT INTO users VALUES(DEFAULT, 'Missandei ', '141414', 'Missandei', 'Targaryen', 'Missandei@game.com', (SELECT id FROM roles WHERE name='STUDENT'));
    INSERT INTO users VALUES(DEFAULT, 'Renly ', '171717', 'Renly', 'Baratheon', 'Renly@game.com', (SELECT id FROM roles WHERE name='STUDENT'));
    INSERT INTO users VALUES(DEFAULT, 'Stannis ', '181818', 'Stannis', 'Baratheon', 'Stannis@game.com', (SELECT id FROM roles WHERE name='STUDENT'));
    INSERT INTO users VALUES(DEFAULT, 'Selyse ', '191919', 'Selyse', 'Baratheon', 'Selyse@game.com', (SELECT id FROM roles WHERE name='STUDENT'));

    -- courses
    -- --coming soon
    INSERT INTO courses VALUES(DEFAULT, 'Java', 100, '2020-11-22', null, (SELECT id FROM categories WHERE title="PROGRAMMING"), (SELECT id FROM course_statuses WHERE title="COMING_SOON"));
    INSERT INTO courses VALUES(DEFAULT, 'English', 100, '2020-11-22', null, (SELECT id FROM categories WHERE title="FOREIGN_LANGUAGE"), (SELECT id FROM course_statuses WHERE title="COMING_SOON"));
    INSERT INTO courses VALUES(DEFAULT, 'Higher mathematics', 100, '2020-11-22', null, (SELECT id FROM categories WHERE title="MATH"), (SELECT id FROM course_statuses WHERE title="COMING_SOON"));

    -- --in progress
     INSERT INTO courses VALUES(DEFAULT, 'Python', 100, '2020-11-08', null, (SELECT id FROM categories WHERE title="PROGRAMMING"), (SELECT id FROM course_statuses WHERE title="IN_PROCESS"));
     INSERT INTO courses VALUES(DEFAULT, 'Polish', 100, '2020-11-08', null, (SELECT id FROM categories WHERE title="FOREIGN_LANGUAGE"), (SELECT id FROM course_statuses WHERE title="IN_PROCESS"));
     INSERT INTO courses VALUES(DEFAULT, 'Basics of geometry', 100, '2020-11-08', null, (SELECT id FROM categories WHERE title="MATH"), (SELECT id FROM course_statuses WHERE title="IN_PROCESS"));

     -- --completed
     INSERT INTO courses VALUES(DEFAULT, 'C++', 100, '2020-05-08', null, (SELECT id FROM categories WHERE title="PROGRAMMING"), (SELECT id FROM course_statuses WHERE title="COMPLETED"));
     INSERT INTO courses VALUES(DEFAULT, 'Italian ', 100, '2020-05-08', null, (SELECT id FROM categories WHERE title="FOREIGN_LANGUAGE"), (SELECT id FROM course_statuses WHERE title="COMPLETED"));
     INSERT INTO courses VALUES(DEFAULT, 'Mathematical analysis', 100, '2020-05-08', null, (SELECT id FROM categories WHERE title="MATH"), (SELECT id FROM course_statuses WHERE title="COMPLETED"));


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
