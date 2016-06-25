CREATE DATABASE IF NOT EXISTS `carproducer` DEFAULT CHARACTER SET utf8;

USE `carproducer`;

DROP TABLE IF EXISTS `car`;

CREATE TABLE `car` (
  `carId` int(11) NOT NULL AUTO_INCREMENT,
  `carName` varchar(30) NOT NULL,
  `producerId` int(11) NOT NULL,
  `dateOfCreation` date NOT NULL,
  `picture` blob,
  PRIMARY KEY (`carId`),
  KEY `producerId` (`producerId`),
  CONSTRAINT `car_ibfk_1` FOREIGN KEY (`producerId`) REFERENCES `producer` (`producerId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `producer`;

CREATE TABLE `producer` (
  `producerId` int(11) NOT NULL AUTO_INCREMENT,
  `producerName` varchar(30) NOT NULL,
  `country` varchar(30) NOT NULL,
  PRIMARY KEY (`producerId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

USE `carproducer`;

INSERT INTO `car` VALUES (1,'Cruiser',1,'2015-10-14',NULL),(2,'Adventure',1,'2012-11-01',NULL),(3,'SLR',2,'2013-09-01',NULL);

INSERT INTO `producer` VALUES (1,'toyota','Korea'),(2,'mercedes','Germany');
