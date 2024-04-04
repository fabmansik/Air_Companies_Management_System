INSERT INTO air_company (id,name, company_type, founded_at) VALUES
(1,'Ukraine Airlines','NATIONAL','1999-10-10'),
(2,'Alibaba Air','PRIVATE','1981-11-05'),
(3,'British Airlines','MILITARY','1986-01-24'),
(4,'Turkish Airlines','NATIONAL','1975-10-23'),
(5,'Prima Air','PRIVATE','2020-11-03');

INSERT INTO airplane (id, created_at, factory_serial_number, flight_distance, fuel_capacity, name, number_of_flights, type,
        air_company_id) VALUES
(1,'2000-01-03','100B',1000,10000,'Boeing 747',1,'PASSENGER',1),
(2,'2001-10-23','101B',1876,10000,'Boeing 747',2,'PASSENGER',2),
(3,'2002-11-23','102B',0,10000,'Boeing 747',0,'PASSENGER',null),
(4,'2005-10-13','203B',0,15000,'Boeing 777',0,'PASSENGER',null),
(5,'2008-12-10','204B',872,15000,'Boeing 777',1,'PASSENGER',5),
(6,'2005-09-11','205B',6723,15000,'Boeing 777',7,'PASSENGER',1),
(7,'2010-10-01','100D',9872,13000,'Douglas DC-8',10,'PASSENGER',2),
(8,'2008-09-21','101D',673,13000,'Douglas DC-8',1,'PASSENGER',1),
(9,'2013-11-02','102D',15093,13000,'Douglas DC-8',18,'PASSENGER',4),
(10,'2010-01-03','103D',7798,13000,'Douglas DC-8',5,'PASSENGER',3),
(11,'2001-04-06','104D',0,13000,'Douglas DC-8',0,'PASSENGER',null),
(12,'2001-12-07','105D',6523,13000,'Douglas DC-8',7,'PASSENGER',2),
(13,'1999-10-18','100A',8734,25000,'Antonov An-225',10,'CARGO',4),
(14,'1998-05-28','101A',0,25000,'Antonov An-225',0,'CARGO',null),
(15,'1988-07-10','100M',0,17000,'Atlas A400M',0,'MILITARY',null),
(16,'1987-09-23','101M',2304,17000,'Atlas A400M',2,'MILITARY',3),
(17,'2001-05-12','100T',8912,12000,'Tupalev Tu-144',8,'PASSENGER',5),
(18,'2011-08-11','101T',0,12000,'Tupalev Tu-144',0,'PASSENGER',null),
(19,'2007-07-23','102T',7620,12000,'Tupalev Tu-144',6,'PASSENGER',5),
(20,'2015-12-20','103T',562,12000,'Tupalev Tu-144',1,'PASSENGER',4);

INSERT INTO flight (id,created_at, delay_started_at, departure_country, destination_country, distance, started_at, estimated_flight_time, flight_status, ended_at, air_company_id, airplane_id) VALUES
(1,'2024-01-03 18:10:55', null,'Istanbul','Brazil', 2500,'2024-02-03 10:00:00', 8, 'COMPLETED','2024-01-03 18:30:00', 1,1),
(2,'2024-02-10 13:12:00', null,'Iraq','Canada', 1873,'2024-02-12 12:00:00', 10, 'COMPLETED','2024-01-03 18:30:00', 2,2),
(3,'2024-03-04 13:12:00', null,'Israel','Ukraine', 2799,null, 5, 'PENDING',null, 5,5),
(4,'2024-03-02 15:25:04', null,'USA','Britain', 2000,'2024-04-04 01:00', 5, 'ACTIVE',null, 4,20),
(5,'2024-02-08 17:34:00', null,'Australia','Japan', 1534,null, 3, 'PENDING',null, 3,10);