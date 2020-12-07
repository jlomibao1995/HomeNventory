DROP SCHEMA IF EXISTS `inventorydb`;
CREATE SCHEMA IF NOT EXISTS `inventorydb` DEFAULT CHARACTER SET latin1;
USE `inventorydb`;

-- -----------------------------------------------------
-- Table `inventorydb`.`category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `inventorydb`.`category` (
  `category_id` INT(11) NOT NULL AUTO_INCREMENT,
  `category_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`category_id`));

-- -----------------------------------------------------
-- Table `inventorydb`.`role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `inventorydb`.`role` (
  `role_id` INT(11) NOT NULL,
  `role_name` VARCHAR(25) NOT NULL,
  PRIMARY KEY (`role_id`));

-- -----------------------------------------------------
-- Table `inventorydb`.`company`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `inventorydb`.`company` (
  `company_id` INT(11) NOT NULL,
  `company_name` VARCHAR(40) NOT NULL,
   PRIMARY KEY (`company_id`));

-- -----------------------------------------------------
-- Table `inventorydb`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `inventorydb`.`user` (
  `email` VARCHAR(40) NOT NULL,
  `active` TINYINT(1) NOT NULL DEFAULT '1',
  `first_name` VARCHAR(20) NOT NULL,
  `last_name` VARCHAR(20) NOT NULL,
  `salt` CHAR(64) NOT NULL,
  `password` CHAR(64) NOT NULL,
  `role` INT(11) NOT NULL,
  `reset_password_uuid` VARCHAR(50),
  `activate_user_uuid` VARCHAR(50),
  `company` INT(11),
  PRIMARY KEY (`email`),
  CONSTRAINT `fk_user_role`
    FOREIGN KEY (`role`)
    REFERENCES `inventorydb`.`role` (`role_id`),
  CONSTRAINT `fk_user_company`
    FOREIGN KEY (`company`)
    REFERENCES `inventorydb`.`company` (`company_id`));


-- -----------------------------------------------------
-- Table `inventorydb`.`item`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `inventorydb`.`item` (
  `item_id` INT(11) NOT NULL AUTO_INCREMENT,
  `category` INT(11) NOT NULL,
  `item_name` VARCHAR(45) NOT NULL,
  `price` DOUBLE NOT NULL,
  `owner` VARCHAR(40) NOT NULL,
  PRIMARY KEY (`item_id`),
  CONSTRAINT `fk_items_categories`
    FOREIGN KEY (`category`)
    REFERENCES `inventorydb`.`category` (`category_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_items_owner`
    FOREIGN KEY (`owner`)
    REFERENCES `inventorydb`.`user` (`email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

INSERT INTO `role` VALUES (1, 'system admin');
INSERT INTO `role` VALUES (2, 'regular user');
INSERT INTO `role` VALUES (3, 'company admin');

INSERT INTO `category` (`category_name`) VALUES ('kitchen');
INSERT INTO `category` (`category_name`) VALUES ('bathroom');
INSERT INTO `category` (`category_name`) VALUES ('living room');
INSERT INTO `category` (`category_name`) VALUES ('basement');
INSERT INTO `category` (`category_name`) VALUES ('bedrooom');
INSERT INTO `category` (`category_name`) VALUES ('garage');
INSERT INTO `category` (`category_name`) VALUES ('office');
INSERT INTO `category` (`category_name`) VALUES ('utility room');
INSERT INTO `category` (`category_name`) VALUES ('storage');
INSERT INTO `category` (`category_name`) VALUES ('other');

INSERT INTO `company` VALUES (1, 'Google');
INSERT INTO `company` VALUES (2, 'Facebook');
INSERT INTO `company` VALUES (3, 'Instagram');
INSERT INTO `company` VALUES (4, 'HQ');

INSERT INTO `user` (`email`,`active`,`first_name`,`last_name`,`password`,`salt`,`role`, `company`)
	VALUES ('cprg352+admin@gmail.com', true, 'Admin','Admin', '9328cf33549e378c1982c6b61f7fcb431e9ac8c9afba8511c3ba0dbaf4193dc6', 'NeZRJMuFfiSQjZ+CpNH1Z23fI7X7EEUWTC/4x7Cv9P0=', 1, 4);
INSERT INTO `user` (`email`,`active`,`first_name`,`last_name`,`password`,`salt`,`role`, `company`)
	VALUES ('cprg352+admin2@gmail.com', true, 'Admin2','Admin2', 'b738b0b8418ff006832c260db40d535cc06d01533c42fe6dd3d6640b9ef4afbc', 'a9hEZ+l5tn4UjYU0l6eRICyAWODlvyBdSAVy4TX+rm0=', 3, 1);
INSERT INTO `user` (`email`,`active`,`first_name`,`last_name`,`password`,`salt`,`role`, `company`)
	VALUES ('cprg352+anne@gmail.com', true, 'Anne','Annerson', 'a4a6860684f8381fb90c7e5bc43a9e13964f37fd86bca6258e05c07df11845fe', 'UJ+g7vrgAMj4yZB6LKGxdJdi3RXELSu6a4ZvoJOd7rY=', 2, 1);
INSERT INTO `user` (`email`,`active`,`first_name`,`last_name`,`password`,`salt`,`role`, `company`)
	VALUES ('cprg352+barb@gmail.com', true, 'Barb','Barber', '2698a1b2e328e9d220d4326394f70198d70ea4f56b3c157ca1dbd794239591dd', 'L1VFEqhswZ36AQMUKJdFahAiCoN10k9LlrCxUKdM5Ks=', 2, 2);
INSERT INTO `user` (`email`,`active`,`first_name`,`last_name`,`password`,`salt`,`role`, `company`)
	VALUES ('cprg352sait+fbadmin@gmail.com', true, 'FB','Admin', 'b738b0b8418ff006832c260db40d535cc06d01533c42fe6dd3d6640b9ef4afbc', 'a9hEZ+l5tn4UjYU0l6eRICyAWODlvyBdSAVy4TX+rm0=', 3, 2);
INSERT INTO `user` (`email`,`active`,`first_name`,`last_name`,`password`,`salt`,`role`, `company`)
	VALUES ('cprg352sait+igadmin@gmail.com', true, 'IG','Admin', 'b738b0b8418ff006832c260db40d535cc06d01533c42fe6dd3d6640b9ef4afbc', 'a9hEZ+l5tn4UjYU0l6eRICyAWODlvyBdSAVy4TX+rm0=', 3, 3);
INSERT INTO `user` (`email`,`active`,`first_name`,`last_name`,`password`,`salt`,`role`, `company`)
       VALUES ('cprg352sait+ryan@gmail.com', true, 'Ryan','Underwood', '9328cf33549e378c1982c6b61f7fcb431e9ac8c9afba8511c3ba0dbaf4193dc6', 'NeZRJMuFfiSQjZ+CpNH1Z23fI7X7EEUWTC/4x7Cv9P0=', 2, 3);
INSERT INTO `user` (`email`,`active`,`first_name`,`last_name`,`password`,`salt`,`role`, `company`)
       VALUES ('cprg352sait+mary@gmail.com', true, 'Mary','Peters', '9328cf33549e378c1982c6b61f7fcb431e9ac8c9afba8511c3ba0dbaf4193dc6', 'NeZRJMuFfiSQjZ+CpNH1Z23fI7X7EEUWTC/4x7Cv9P0=', 2, 1);
INSERT INTO `user` (`email`,`active`,`first_name`,`last_name`,`password`,`salt`,`role`, `company`)
       VALUES ('cprg352sait+lisa@gmail.com', true, 'Lisa','Newman', '9328cf33549e378c1982c6b61f7fcb431e9ac8c9afba8511c3ba0dbaf4193dc6', 'NeZRJMuFfiSQjZ+CpNH1Z23fI7X7EEUWTC/4x7Cv9P0=', 2, 2);
INSERT INTO `user` (`email`,`active`,`first_name`,`last_name`,`password`,`salt`,`role`, `company`)
       VALUES ('cprg352sait+andrew@gmail.com', true, 'Andrew','Lee', '9328cf33549e378c1982c6b61f7fcb431e9ac8c9afba8511c3ba0dbaf4193dc6', 'NeZRJMuFfiSQjZ+CpNH1Z23fI7X7EEUWTC/4x7Cv9P0=', 2, 3);
INSERT INTO `user` (`email`,`active`,`first_name`,`last_name`,`password`,`salt`,`role`, `company`)
       VALUES ('cprg352sait+robert@gmail.com', true, 'Robert','Kerr', '9328cf33549e378c1982c6b61f7fcb431e9ac8c9afba8511c3ba0dbaf4193dc6', 'NeZRJMuFfiSQjZ+CpNH1Z23fI7X7EEUWTC/4x7Cv9P0=', 2, 1);
INSERT INTO `user` (`email`,`active`,`first_name`,`last_name`,`password`,`salt`,`role`, `company`)
       VALUES ('cprg352sait+dan@gmail.com', true, 'Dan','Scott', '9328cf33549e378c1982c6b61f7fcb431e9ac8c9afba8511c3ba0dbaf4193dc6', 'NeZRJMuFfiSQjZ+CpNH1Z23fI7X7EEUWTC/4x7Cv9P0=', 2, 2);
INSERT INTO `user` (`email`,`active`,`first_name`,`last_name`,`password`,`salt`,`role`, `company`)
       VALUES ('cprg352sait+kate@gmail.com', true, 'Kate','Walsh', '9328cf33549e378c1982c6b61f7fcb431e9ac8c9afba8511c3ba0dbaf4193dc6', 'NeZRJMuFfiSQjZ+CpNH1Z23fI7X7EEUWTC/4x7Cv9P0=', 2, 2);

INSERT INTO `item` (`category`,`item_name`,`price`,`owner`) VALUES (1, 'blender',29.99,'cprg352+anne@gmail.com');
INSERT INTO `item` (`category`,`item_name`,`price`,`owner`) VALUES (1, 'toaster',19.99,'cprg352+anne@gmail.com');
INSERT INTO `item` (`category`,`item_name`,`price`,`owner`) VALUES (3, 'lamp',5,'cprg352+anne@gmail.com');
INSERT INTO `item` (`category`,`item_name`,`price`,`owner`) VALUES (6, 'winter tires',200,'cprg352+anne@gmail.com');
INSERT INTO `item` (`category`,`item_name`,`price`,`owner`) VALUES (5, 'dresser',50,'cprg352+anne@gmail.com');
