-- Indexes for primary keys have been explicitly created.

DROP TABLE Purchases;
DROP TABLE SessionMovies;
DROP TABLE Movies;
DROP TABLE Rooms;
DROP TABLE Cinemas;
DROP TABLE Provinces;
DROP TABLE UserProfile;

-- ------------------------------ UserProfile ----------------------------------

CREATE TABLE UserProfile (
    usrId BIGINT NOT NULL AUTO_INCREMENT,
    loginName VARCHAR(30) COLLATE latin1_bin NOT NULL,
    enPassword VARCHAR(13) NOT NULL, 
    firstName VARCHAR(30) NOT NULL,
    role VARCHAR (20) NOT NULL,
    lastName VARCHAR(40) NOT NULL, 
	email VARCHAR(60) NOT NULL,
    CONSTRAINT UserProfile_PK PRIMARY KEY (usrId),
    CONSTRAINT LoginNameUniqueKey UNIQUE (loginName)) 
    ENGINE = InnoDB;

CREATE INDEX UserProfileIndexByLoginName ON UserProfile (loginName);

-- ------------------------------ Provinces ----------------------------------

CREATE TABLE Provinces (
    provinceId	BIGINT NOT NULL AUTO_INCREMENT,
    name		VARCHAR(20) NOT NULL,
	CONSTRAINT  Provinces_PK PRIMARY KEY (provinceId))    
	ENGINE = InnoDB;

-- ------------------------------ Cinemas ----------------------------------
	
CREATE TABLE Cinemas
       (cinemaId	BIGINT NOT NULL AUTO_INCREMENT,
		provinceId	BIGINT NOT NULL,
        name		VARCHAR(20) NOT NULL,
		CONSTRAINT CinemasPK PRIMARY KEY(cinemaId),
		CONSTRAINT CinemasProvinceIdFK FOREIGN KEY(provinceId)
			REFERENCES Provinces(provinceId))
			ENGINE = InnoDB;

-- ------------------------------ Rooms ----------------------------------
			
CREATE TABLE Rooms	
       (roomId		BIGINT NOT NULL AUTO_INCREMENT,
		cinemaId	BIGINT NOT NULL,
        name		VARCHAR(20) NOT NULL,
        capacity	SMALLINT NOT NULL,
		CONSTRAINT RoomsPK PRIMARY KEY(roomId),
		CONSTRAINT RoomsCinemaIdFK FOREIGN KEY(cinemaId)
			REFERENCES Cinemas(cinemaId))
		ENGINE = InnoDB;

-- ------------------------------ Movies ----------------------------------

CREATE TABLE Movies	
       (movieId		BIGINT NOT NULL AUTO_INCREMENT,
        title		VARCHAR(50) NOT NULL ,
        review		VARCHAR(200) NOT NULL,
		lenght	 	SMALLINT  NOT NULL,
		initDate 	DATE  NOT NULL,
		finalDate	DATE  NOT NULL,
		CONSTRAINT MoviesPK PRIMARY KEY(movieId),
	    CONSTRAINT ValidDate CHECK ( finalDate > initDate))
		ENGINE = InnoDB;

-- ------------------------------ SessionMovies ----------------------------------

CREATE TABLE SessionMovies	
       (sessionId	BIGINT NOT NULL AUTO_INCREMENT,
		roomId		BIGINT NOT NULL,
		movieId		BIGINT NOT NULL,
        date		DATETIME NOT NULL,
        price		FLOAT NOT NULL,
		freeLocationsCount SMALLINT NOT NULL,
		version INTEGER NOT NULL DEFAULT 0, 
		CONSTRAINT	SessionMoviesPK PRIMARY KEY(sessionId),
		CONSTRAINT	SessionRoomsIdFK FOREIGN KEY(roomId)
			REFERENCES Rooms(roomId),
		CONSTRAINT	SessionMoviesIdFK FOREIGN KEY(movieId)
			REFERENCES Movies(movieId),
		CONSTRAINT	ValidHour CHECK ( hour >= 0 AND hour <= 23))
		ENGINE = InnoDB;

-- ------------------------------ Purchases ----------------------------------

CREATE TABLE Purchases	
       (purchaseId				BIGINT NOT NULL AUTO_INCREMENT,
		usrId					BIGINT NOT NULL,
		sessionId				BIGINT NOT NULL,
		creditCardNumber 		VARCHAR(20) NOT NULL,
		creditCardExpiration 	DATE NOT NULL,
		locationCount			SMALLINT NOT NULL,
		purchaseState			VARCHAR(20) NOT NULL,
		date					DATETIME NOT NULL,
		CONSTRAINT PurchasesPK PRIMARY KEY(purchaseId),
		CONSTRAINT PurchasesLoginFK FOREIGN KEY(usrId)
			REFERENCES UserProfile(usrId),
		CONSTRAINT PurchasesSessionIdFK FOREIGN KEY(sessionId)
			REFERENCES SessionMovies(sessionId),
		CONSTRAINT ValidLocationCount CHECK (locationCount<=10))
		ENGINE = InnoDB;

