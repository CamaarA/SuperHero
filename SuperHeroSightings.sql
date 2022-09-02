DROP DATABASE IF EXISTS HeroSightingsDBTest;

CREATE DATABASE HeroSightingsDBTest;

USE HeroSightingsDBTest;

CREATE TABLE Hero(
id int PRIMARY KEY auto_increment,
`Name` varchar(45) not null,
`Description` varchar(100),
Superpower varchar(30) not null
);

CREATE TABLE `Organization`(
id int PRIMARY KEY auto_increment,
`Name` varchar(45) not null,
`Description` varchar(100),
Address varchar(100),
Contact varchar(100)
);

CREATE TABLE Hero_Organization(
Heroid int,
Organizationid int,
PRIMARY KEY (Heroid, Organizationid),
constraint fk_heroid FOREIGN KEY (Heroid) references Hero(id),
constraint fk_organiztionid FOREIGN KEY (organizationid) references `organization`(id)
);

CREATE TABLE Location(
id int PRIMARY KEY auto_increment,
`Name` varchar(45) not null,
`Description` varchar(100),
Address varchar(100) not null,
Longitude Decimal(9,6) not null,
Latitude Decimal(9,6) not null
);

CREATE TABLE Sighting(
id int PRIMARY KEY auto_increment,
`Date` DATE not null,
Locationid int,
constraint fk_locationid FOREIGN KEY (Locationid) references Location(id)
);

CREATE TABLE Hero_Sighting(
Heroid int,
Sightingid int,
PRIMARY KEY (Heroid, Sightingid),
constraint fk_hero FOREIGN KEY (Heroid) references Hero(id),
constraint fk_Sightingid FOREIGN KEY (Sightingid) references Sighting(id)
);



