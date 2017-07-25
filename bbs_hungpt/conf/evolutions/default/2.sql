# --- !Ups

CREATE TABLE `users` (
  `id` BIGINT UNSIGNED NULL AUTO_INCREMENT,
  `name` VARCHAR(200) NOT NULL,
  `email` VARCHAR(200) NOT NULL,
  `created_at` DATETIME NOT NULL,
  PRIMARY KEY (`id`)
);

# --- !Downs

DROP TABLE users;
