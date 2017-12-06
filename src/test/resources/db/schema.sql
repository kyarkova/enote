
DROP SCHEMA enote;
CREATE SCHEMA enote;
use  enote;
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `login` varchar(45) NOT NULL unique,
  `password` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
);
DROP TABLE IF EXISTS `notebook`;
CREATE TABLE `notebook` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE(`name`,`user_id`),
  FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
);
DROP TABLE  IF EXISTS `note`;
CREATE TABLE `note` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(45) NOT NULL,
  `notebook_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`notebook_id`) REFERENCES `notebook` (`id`) ON DELETE CASCADE
);
DROP TABLE IF EXISTS `tag`;
CREATE TABLE `tag` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE(`name`,`user_id`),
  FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
);
DROP TABLE IF EXISTS `note_tag`;
CREATE TABLE `note_tag` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `note_id` int(11) NOT NULL,
  `tag_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`note_id`) REFERENCES `note` (`id`) ON DELETE CASCADE,
  FOREIGN KEY (`tag_id`) REFERENCES `tag` (`id`) ON DELETE CASCADE,
  UNIQUE(`note_id`,`tag_id`)
);




