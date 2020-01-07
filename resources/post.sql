DROP TABLE IF EXISTS `post`;

CREATE TABLE post (
  id int(11) NOT NULL AUTO_INCREMENT,
  title varchar(200) NOT NULL,
  description longtext NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY id_UNIQUE (id)
);
