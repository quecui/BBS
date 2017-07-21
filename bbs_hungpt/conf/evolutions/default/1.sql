
# --- !Ups

CREATE TABLE `posts` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(200) NOT NULL,
  `content` VARCHAR(1000) NOT NULL,
  `created_at` DATETIME NOT NULL,
  `author_id` INT,
  PRIMARY KEY (`id`)
)ENGINE=INNODB DEFAULT CHARSET=utf8;

# --- !Downs
DROP TABLE posts;
