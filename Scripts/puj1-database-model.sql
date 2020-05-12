CREATE DATABASE [PUJ1-Database]
USE [PUJ1-Database]

CREATE TABLE Actor
(
	IDActor		int				CONSTRAINT PK_Actor PRIMARY KEY IDENTITY(1,1),
	Firstname	nvarchar(50)	NOT NULL,
	Lastname	nvarchar(50)	NOT NULL
)

CREATE TABLE Director
(
	IDDirector	int				CONSTRAINT PK_Director PRIMARY KEY IDENTITY(1,1),
	Firstname	nvarchar(50)	NOT NULL,
	Lastname	nvarchar(50)	NOT NULL
)

CREATE TABLE ApplicationUserType
(
	IDApplicationUserType		int		CONSTRAINT PK_ApplicationUserType PRIMARY KEY IDENTITY(1,1),
	UserType					nvarchar(25)
)

INSERT INTO ApplicationUserType (UserType) VALUES
	('Administrator'),
	('User')
	
CREATE TABLE ApplicationUser
(
	IDAplicationUser		int				CONSTRAINT PK_ApplicationUSER PRIMARY KEY IDENTITY(1,1),
	Username				nvarchar(50)	CONSTRAINT UQ_Username NOT NULL UNIQUE,
	UserPassword			nvarchar(20)	NOT NULL,
	ApplicationUserTypeID	int				CONSTRAINT FK_ApplicationUser_ApplicationUserType
		FOREIGN KEY REFERENCES ApplicationUserType(IDApplicationUserType) NOT NULL,
)

INSERT INTO ApplicationUser (Username, UserPassword, ApplicationUserTypeID) VALUES
	('Admin', 'Admin', 1)

CREATE TABLE Genre
(
	IDGenre				int				CONSTRAINT PK_Genre		PRIMARY KEY IDENTITY(1,1),
	GenreName			nvarchar(50)
)

CREATE TABLE Movie
(
	IDMovie				int				CONSTRAINT PK_Movie		PRIMARY KEY IDENTITY(1,1),
	Title				nvarchar(50)	NOT NULL,
	PublishedDate		datetime		NOT NULL,
	MovieDescription	nvarchar(100)	NULL,
	OriginalName		nvarchar(50)	NULL,
	MovieLength			int				NULL,
	Poster				nvarchar(90)	NULL,
	Link				nvarchar(90)	NULL,
	MovieGuid			nvarchar(50)	NULL,
	Picture				nvarchar(90)	NULL,
	Trailer				nvarchar(50)	NULL,
	StartDate			datetime		NULL
)

CREATE TABLE MovieDirector
(
	IDMovieDirector		int				CONSTRAINT PK_MovieDirector
				PRIMARY KEY IDENTITY(1,1),
	MovieID				int				CONSTRAINT FK_Movie_Director
		FOREIGN KEY REFERENCES Movie(IDMovie) NOT NULL,
	DirectorID			int				CONSTRAINT FK_Director_Movie
		FOREIGN KEY REFERENCES Director(IDDirector) NOT NULL,
)

CREATE TABLE MovieActor
(
	IDMovieActor		int				CONSTRAINT PK_MovieActor
				PRIMARY KEY IDENTITY(1,1),
	MovieID				int				CONSTRAINT FK_Movie_Actor
		FOREIGN KEY REFERENCES Movie(IDMovie) NOT NULL,
	ActorID			int				CONSTRAINT FK_Actor_Movie
		FOREIGN KEY REFERENCES Actor(IDActor) NOT NULL,
)

CREATE TABLE MovieGenre
(
	IDMovieGenre		int				CONSTRAINT PK_MovieGenre
				PRIMARY KEY IDENTITY(1,1),
	MovieID				int				CONSTRAINT FK_Movie_Genre
		FOREIGN KEY REFERENCES Movie(IDMovie) NOT NULL,
	GenreID 			int				CONSTRAINT FK_Genre_Movie
		FOREIGN KEY REFERENCES Genre(IDGenre) NOT NULL,
)