DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id`       INT(11)     NOT NULL AUTO_INCREMENT,
  `login`    VARCHAR(45) NOT NULL UNIQUE,
  `password` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `notebook`;

CREATE TABLE `notebook` (
  `id`      INT(11)     NOT NULL AUTO_INCREMENT,
  `name`    VARCHAR(45) NOT NULL,
  `user_id` INT(11)     NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE (`name`, `user_id`),
  FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
    ON DELETE CASCADE
);

DROP TABLE IF EXISTS `note`;

CREATE TABLE `note` (
  `id`          INT(11)     NOT NULL AUTO_INCREMENT,
  `title`       VARCHAR(45) NOT NULL,
  `notebook_id` INT(11)     NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`notebook_id`) REFERENCES `notebook` (`id`)
    ON DELETE CASCADE
);

DROP TABLE IF EXISTS `tag`;

CREATE TABLE `tag` (
  `id`   INT(11)     NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL UNIQUE,
  PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `note_tag`;

CREATE TABLE `note_tag` (
  `id`      INT(11) NOT NULL AUTO_INCREMENT,
  `note_id` INT(11) NOT NULL,
  `tag_id`  INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`note_id`) REFERENCES `note` (`id`)
    ON DELETE CASCADE,
  FOREIGN KEY (`tag_id`) REFERENCES `tag` (`id`)
    ON DELETE CASCADE,
  UNIQUE (`note_id`, `tag_id`)
);




