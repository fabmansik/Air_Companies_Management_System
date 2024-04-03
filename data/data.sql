INSERT INTO air_company values (id,company_type,founded_at,name) VALUES (1,'Ukraine Airlines','civil','1999-10-10');
INSERT INTO air_company values (id,company_type,founded_at,name) VALUES (2,'Alibaba Air','private','1981-11-05');
INSERT INTO air_company values (id,company_type,founded_at,name) VALUES (3,'British Airlines','military','1986-01-24');
INSERT INTO air_company values (id,company_type,founded_at,name) VALUES (4,'Turkish Airlines','civil','1975-10-23');
INSERT INTO air_company values (id,company_type,founded_at,name) VALUES (5,'Prima Air','private','2020-11-03');

INSERT INTO air_plane values (id,created_at, factory_serial_number, flight_distance, fuel_capacity ,name ,number_of_flights ,type ,air_company_id ) VALUES
(1,'2000-01-03','100B,0,10000,'Boeing 747',0,passenger,1),
(2,'2001-10-23','102B',0,10000,'Boeing 747',0,passenger,2),
(3,'2002-11-23','103B',0,10000,'Boeing 747',0,passenger,null),
(4,'2005-10-13','203B',0,15000,'Boeing 777',0,passenger,null),
(5,'2008-12-10','204B',0,15000,'Boeing 777',0,passenger,null),
(6,'2005-09-11','205B',0,15000,'Boeing 777',0,passenger,1),
(7,'2010-10-01','100D',0,13000,'Douglas DC-8',0,passenger,2),
(8,'2008-09-21','101D',0,13000,'Douglas DC-8',0,passenger,1),
(9,'2013-11-02','102D',0,13000,'Douglas DC-8',0,passenger,3),
(10,'2010-01-03','103D',0,13000,'Douglas DC-8',0,passenger,3),
(11,'2001-04-06','104D',0,13000,'Douglas DC-8',0,passenger,null),
(12,'2001-12-07','105D',0,13000,'Douglas DC-8',0,passenger,2),
(13,'1999-10-18','100A',0,25000,'Antonov An-225',0,cargo,1),
(14,'1998-05-28','101A',0,25000,'Antonov An-225',0,cargo,null),
(15,'1988-07-10','100M',0,17000,'Atlas A400M',0,military,null),
(16,'1987-09-23','101M',0,17000,'Atlas A400M',0,military,3),
(17,'2001-05-12','100T',0,12000,'Tupalev Tu-144',0,passenger,1),
(18,'2011-08-11','100T',0,12000,'Tupalev Tu-144',0,passenger,1),