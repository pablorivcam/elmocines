-- ----------------------------------------------------------------------------
-- Put here INSERT statements for inserting data required by the application
-- in the "pojo" database.
-------------------------------------------------------------------------------
INSERT INTO Provinces VALUES (1, 'A Coruña');
INSERT INTO Provinces VALUES (2, 'Lugo');
INSERT INTO Provinces VALUES (3, 'Pontevedra');
INSERT INTO Provinces VALUES (4, 'Ourense');

-- cinemaId,provinceId,name
INSERT INTO Cinemas VALUES (1, 1,'Los Rosales');
INSERT INTO Cinemas VALUES (2, 1,'Espacio Coruña');
INSERT INTO Cinemas VALUES (3, 2,'Melchor Roel');
INSERT INTO Cinemas VALUES (4, 2,'A Leira');
INSERT INTO Cinemas VALUES (5, 3,'Afonía');
INSERT INTO Cinemas VALUES (6, 4,'As Termas');

-- RoomId,cinemaId,name,capacity
INSERT INTO Rooms VALUES (1,3,'Sala Apátrida',100);
INSERT INTO Rooms VALUES (2,3,'Sala Imana',100);
INSERT INTO Rooms VALUES (3,3,'Sala Maya',100);
INSERT INTO Rooms VALUES (4,3,'Sala Crisis',100);
INSERT INTO Rooms VALUES (5,3,'Sala Mutex TWD',50);

INSERT INTO Rooms VALUES (6,2,'Sala Computación',100);
INSERT INTO Rooms VALUES (7,2,'Sala Cafeta',80);

INSERT INTO Rooms VALUES (8,1,'Sala Cúbica',10);
INSERT INTO Rooms VALUES (9,1,'Sala Elmo',100);
INSERT INTO Rooms VALUES (10,1,'Sala 4ªconvocatoria',100);

INSERT INTO Rooms VALUES (11,4,'Sala Pemento',25);
INSERT INTO Rooms VALUES (12,4,'Sala Cogomelo',25);

INSERT INTO Rooms VALUES (13,5,'Sala Dinoseto',25);

INSERT INTO Rooms VALUES (14,6,'Sala Magosto',25);
INSERT INTO Rooms VALUES (15,6,'Sala Gonzalo Jácome',25);

-- MovieId,name,review,lenght,startDate,finalDate
INSERT INTO Movies VALUES (1,'Lo que la concurrencia se llevó','Un alumno despistado, pone un mutex en donde no debe y la lía parda. Lo que pasó a continuación, solo el paralelismo lo sabe.','90','2018-06-07','2018-4-07');
INSERT INTO Movies VALUES (2,'Segmentation Fault Core Dumped','El compilador decide enviar un error inesperado, que ni un ingeniero puede encontrar. Podrá ser desenmascarado antes de que la práctica se entregue a tiempo?','121','2018-06-07','2018-4-07');
INSERT INTO Movies VALUES (3,'Tecnoloxía Electrónica, El Regreso','Tras no haberse estudiado la materia con su propia sangre, nuestro protagonista ha de matricularse por tercera vez en la asignatura. Conseguirá evadir la temida cuarta convocatoria?','141','2018-06-07','2018-4-07');
INSERT INTO Movies VALUES (4,'Instanciar a un Ruiseñor',' Empleado es una clase de seis años, sin herencia, agrega a la clase Department en el imaginario programa en java.','95','2018-06-07','2018-4-07');
INSERT INTO Movies VALUES (5,'El club de los objetos muertos','-','136','2018-06-07','2018-4-07');
INSERT INTO Movies VALUES (6,'El Recolector de Basura','-','150','2018-06-07','2018-4-07');
INSERT INTO Movies VALUES (7,'La luz es mi fuerza','Un plan sólido','95','2018-06-07','2018-4-07');


-- SessionId,roomId,movieId,hour,price,freeLocationsCount

-- Sesiones Espacio Coruña
INSERT INTO SessionMovies (sessionId,roomId,movieId,date,price,freeLocationsCount) 
	VALUES (1,8,1,'2018-07-20 10:00:00',7.5,10);
INSERT INTO SessionMovies (sessionId,roomId,movieId,date,price,freeLocationsCount) 
	VALUES (2,9,2,'2018-07-20 10:00:00',7.0,100);
INSERT INTO SessionMovies (sessionId,roomId,movieId,date,price,freeLocationsCount) 
	VALUES (3,10,3,'2018-07-20 10:00:00',7.0,100);
INSERT INTO SessionMovies (sessionId,roomId,movieId,date,price,freeLocationsCount) 
	VALUES (4,8,4,'2018-07-20 13:00:00',6.0,10);
INSERT INTO SessionMovies (sessionId,roomId,movieId,date,price,freeLocationsCount) 
	VALUES (5,9,5,'2018-07-20 13:00:00',7.3,100);
INSERT INTO SessionMovies (sessionId,roomId,movieId,date,price,freeLocationsCount) 
	VALUES (6,10,6,'2018-07-20 13:00:00',8.5,100);	
INSERT INTO SessionMovies (sessionId,roomId,movieId,date,price,freeLocationsCount) 
	VALUES (7,8,7,'2018-07-20 17:30:00',7.5,10);
INSERT INTO SessionMovies (sessionId,roomId,movieId,date,price,freeLocationsCount) 
	VALUES (8,9,6,'2018-07-20 20:00:00',7.0,100);
INSERT INTO SessionMovies (sessionId,roomId,movieId,date,price,freeLocationsCount) 
	VALUES (9,10,3,'2018-07-20 19:00:00',7.0,100);

INSERT INTO SessionMovies (sessionId,roomId,movieId,date,price,freeLocationsCount) 
	VALUES (10,8,1,'2018-07-21 10:00:00',7.5,10);
INSERT INTO SessionMovies (sessionId,roomId,movieId,date,price,freeLocationsCount) 
	VALUES (11,9,2,'2018-07-21 10:00:00',7.0,100);
INSERT INTO SessionMovies (sessionId,roomId,movieId,date,price,freeLocationsCount) 
	VALUES (12,10,3,'2018-07-21 10:00:00',7.0,100);
INSERT INTO SessionMovies (sessionId,roomId,movieId,date,price,freeLocationsCount) 
	VALUES (13,8,4,'2018-07-21 13:00:00',6.0,10);
INSERT INTO SessionMovies (sessionId,roomId,movieId,date,price,freeLocationsCount) 
	VALUES (14,9,5,'2018-07-21 13:00:00',7.3,100);
INSERT INTO SessionMovies (sessionId,roomId,movieId,date,price,freeLocationsCount) 
	VALUES (15,10,6,'2018-07-21 13:00:00',8.5,100);	
INSERT INTO SessionMovies (sessionId,roomId,movieId,date,price,freeLocationsCount) 
	VALUES (16,8,7,'2018-07-21 17:30:00',7.5,10);
INSERT INTO SessionMovies (sessionId,roomId,movieId,date,price,freeLocationsCount) 
	VALUES (17,9,6,'2018-07-21 20:00:00',7.0,100);
INSERT INTO SessionMovies (sessionId,roomId,movieId,date,price,freeLocationsCount) 
	VALUES (18,10,3,'2018-07-21 19:00:00',7.0,100);
	
INSERT INTO SessionMovies (sessionId,roomId,movieId,date,price,freeLocationsCount) 
	VALUES (19,8,1,'2018-07-22 10:00:00',7.5,10);
INSERT INTO SessionMovies (sessionId,roomId,movieId,date,price,freeLocationsCount) 
	VALUES (20,9,2,'2018-07-22 10:00:00',7.0,100);
INSERT INTO SessionMovies (sessionId,roomId,movieId,date,price,freeLocationsCount) 
	VALUES (21,10,3,'2018-07-22 10:00:00',7.0,100);
INSERT INTO SessionMovies (sessionId,roomId,movieId,date,price,freeLocationsCount) 
	VALUES (22,8,4,'2018-07-22 13:00:00',6.0,10);
INSERT INTO SessionMovies (sessionId,roomId,movieId,date,price,freeLocationsCount) 
	VALUES (23,9,5,'2018-07-22 13:00:00',7.3,100);
INSERT INTO SessionMovies (sessionId,roomId,movieId,date,price,freeLocationsCount) 
	VALUES (24,10,6,'2018-07-22 13:00:00',8.5,100);	
INSERT INTO SessionMovies (sessionId,roomId,movieId,date,price,freeLocationsCount) 
	VALUES (25,8,7,'2018-07-22 17:30:00',7.5,10);
INSERT INTO SessionMovies (sessionId,roomId,movieId,date,price,freeLocationsCount) 
	VALUES (26,9,6,'2018-07-22 20:00:00',7.0,100);
INSERT INTO SessionMovies (sessionId,roomId,movieId,date,price,freeLocationsCount) 
	VALUES (27,10,3,'2018-07-22 22:00:00',7.0,100);
	
-- Sesiones afonía
INSERT INTO SessionMovies (sessionId,roomId,movieId,date,price,freeLocationsCount) 
	VALUES (28,13,3,'2018-07-22 19:00:00',7.0,25);
INSERT INTO SessionMovies (sessionId,roomId,movieId,date,price,freeLocationsCount) 
	VALUES (29,13,5,'2018-07-22 22:30:00',7.0,25);	
INSERT INTO SessionMovies (sessionId,roomId,movieId,date,price,freeLocationsCount) 
	VALUES (30,13,7,'2018-07-22 23:50:00',7.0,25);	
