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
INSERT INTO Rooms VALUES (8,2,'Sala Cúbica',10);

INSERT INTO Rooms VALUES (9,1,'Sala Elmo',100);
INSERT INTO Rooms VALUES (10,1,'Sala 4ªconvocatoria.',100);

-- MovieId,name,review,lenght,startDate,finalDate
INSERT INTO Movies VALUES (1,'Lo que la concurrencia se llevó','Un alumno despistado, pone un mutex en donde no debe y la lía parda. Lo que pasó a continuación, solo el paralelismo lo sabe.','90','2018-06-07','2018-4-07');
INSERT INTO Movies VALUES (2,'Segmentation Fault Core Dumped','El compilador decide enviar un error inesperado, que ni un ingeniero puede encontrar. Podrá ser desenmascarado antes de que la práctica se entregue a tiempo?','121','2018-06-07','2018-4-07');
INSERT INTO Movies VALUES (3,'Tecnoloxía Electrónica, El Regreso','Tras no haberse estudiado la materia con su propia sangre, nuestro protagonista ha de matricularse por tercera vez en la asignatura. Conseguirá evadir la temida cuarta convocatoria?','141','2018-06-07','2018-4-07');
INSERT INTO Movies VALUES (4,'Instanciar a un Ruiseñor',' Empleado es una clase de seis años, sin herencia, agrega a la clase Department en el imaginario programa en java.','95','2018-06-07','2018-4-07');
INSERT INTO Movies VALUES (5,'El club de los objetos muertos','-','136','2018-06-07','2018-4-07');
INSERT INTO Movies VALUES (6,'El Recolector de Basura','-','150','2018-06-07','2018-4-07');


-- SessionId,roomId,movieId,hour,price,freeLocationsCount
INSERT INTO SessionMovies VALUES (1,3,1,'2018-03-20 10:00:00',7.5,100);
INSERT INTO SessionMovies VALUES (2,2,1,'2018-07-19 10:00:00',7.5,100);
INSERT INTO SessionMovies VALUES (3,1,2,'2018-07-21 16:00:00',7.0,50);
INSERT INTO SessionMovies VALUES (4,2,3,'2018-07-22 17:30:00',8.0,100);
