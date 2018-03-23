-- Indexes for primary keys have been explicitly created.

-- ------------------------------ UserProfile ----------------------------------

DROP TABLE UserProfile;
DROP TABLE Purchases;
DROP TABLE SessionMovies;
DROP TABLE Movies;
DROP TABLE Rooms;
DROP TABLE Cinemas;
DROP TABLE Provinces;

CREATE TABLE UserProfile (
    usrId BIGINT NOT NULL AUTO_INCREMENT,
    loginName VARCHAR(30) COLLATE latin1_bin NOT NULL,
    enPassword VARCHAR(13) NOT NULL,
    firstName VARCHAR(30) NOT NULL,
    role VARCHAR (20) NOT NULL
    lastName VARCHAR(40) NOT NULL, email VARCHAR(60) NOT NULL,
    CONSTRAINT UserProfile_PK PRIMARY KEY (usrId),
    CONSTRAINT LoginNameUniqueKey UNIQUE (loginName))
    ENGINE = InnoDB;

CREATE INDEX UserProfileIndexByLoginName ON UserProfile (loginName);

CREATE TABLE Provinces
       (provinceId	BIGINT NOT NULL AUTO_INCREMENT,
        name		VARCHAR2(20) NOT NULL,
		CONSTRAINT ProvincesPK PRIMARY KEY(provinceId));

CREATE TABLE Cinemas
       (cinemaId	BIGINT NOT NULL AUTO_INCREMENT,
		provinceId	BIGINT NOT NULL,
        name		VARCHAR(20) NOT NULL,
		CONSTRAINT CinemasPK PRIMARY KEY(cinemaId)
		CONSTRAINT CinemasProvinceIdFK FOREIGN KEY(provinceId)
			REFERENCES Provinces(provinceId));


CREATE TABLE Rooms
       (roomId		BIGINT NOT NULL AUTO_INCREMENT,
		cinemaId	BIGINT NOT NULL,
        name		VARCHAR(20) NOT NULL ,
        capacity	SMALLINT NOT NULL,
		CONSTRAINT RoomsPK PRIMARY KEY(roomId),
		CONSTRAINT RoomsCinemaIdFK FOREIGN KEY(cinemaId)
			REFERENCES Cinemas(cinemaId));


CREATE TABLE Movies
       (movieId		BIGINT NOT NULL AUTO_INCREMENT,
        title		VARCHAR(20) NOT NULL ,
        review		VARCHAR(100) NOT NULL,
		length 	    TIME  NOT NULL,
		initDate 	DATETIME  NOT NULL,
		finalDate	DATETIME  NOT NULL,
		CONSTRAINT MoviesPK PRIMARY KEY(movieId),
	    CONSTRAINT ValidDate CHECK ( finalDate > initDate));


CREATE TABLE SessionMovies
       (sessionId	BIGINT NOT NULL AUTO_INCREMENT,
		roomId		BIGINT NOT NULL,
		movieId		BIGINT NOT NULL,
        hour		TIME NOT NULL ,
        price		FLOAT NOT NULL,
		freeLocationsCount SMALLINT NOT NULL,
		CONSTRAINT	SessionMoviesPK PRIMARY KEY(sessionId),
		CONSTRAINT	SessionRoomsIdFK FOREIGN KEY(roomId)
			REFERENCES Rooms(roomId),
		CONSTRAINT	SessionMoviesIdFK FOREIGN KEY(movieId)
			REFERENCES Movies(movieId),
		CONSTRAINT	ValidHour CHECK ( hour >= 0 AND hour <= 23));

CREATE TABLE Purchases
       (purchaseId				BIGINT NOT NULL AUTO_INCREMENT,
		usrId					BIGINT NOT NULL,
		sessionId				BIGINT NOT NULL,
		creditCard 				BIGINT NOT NULL,
		creditCardExpiration 	DATETIME NOT NULL,
		locationCount			SMALLINT NOT NULL,
		purchaseState			VARCHAR(20) NOT NULL,
		date					DATETIME NOT NULL,
		CONSTRAINT PurchasesPK PRIMARY KEY(purchaseId),
		CONSTRAINT PurchasesLoginFK FOREIGN KEY(usrId)
			REFERENCES UserProfile(usrId),
		CONSTRAINT PurchasesSessionIdFK FOREIGN KEY(sessionId)
			REFERENCES SessionMovies(sessionId)),
		CONSTRAINT ValidLocationCount CHECK (locationCount<=10);
