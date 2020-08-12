CREATE DATABASE [PUJ1-Database]
GO

USE [PUJ1-Database]
GO

										/* CREATING TABLES */

CREATE TABLE Actor
(
	IDActor		int				CONSTRAINT PK_Actor PRIMARY KEY IDENTITY(1,1),
	Fullname	nvarchar(100)	NOT NULL
)
GO

CREATE TABLE Director
(
	IDDirector	int				CONSTRAINT PK_Director PRIMARY KEY IDENTITY(1,1),
	Fullname	nvarchar(100)	NOT NULL
)
GO

CREATE TABLE ApplicationUserType
(
	IDApplicationUserType		int		CONSTRAINT PK_ApplicationUserType PRIMARY KEY IDENTITY(1,1),
	UserType					nvarchar(25)
)
GO

CREATE TABLE ApplicationUser
(
	IDAplicationUser		int				CONSTRAINT PK_ApplicationUSER PRIMARY KEY IDENTITY(1,1),
	Username				nvarchar(50)	CONSTRAINT UQ_Username NOT NULL UNIQUE,
	Password				nvarchar(20)	NOT NULL,
	ApplicationUserTypeID	int				CONSTRAINT FK_ApplicationUser_ApplicationUserType
		FOREIGN KEY REFERENCES ApplicationUserType(IDApplicationUserType) NOT NULL
)
GO

CREATE TABLE Genre
(
	IDGenre		int	CONSTRAINT PK_Genre	PRIMARY KEY IDENTITY(1,1),
	GenreName	nvarchar(50)
)
GO

CREATE TABLE Movie
(
	IDMovie				int				CONSTRAINT PK_Movie		PRIMARY KEY IDENTITY(1,1),
	Title				nvarchar(50)	NOT NULL,
	PublishedDate		datetime		NOT NULL,
	MovieDescription	nvarchar(100)	NULL,
	OriginalName		nvarchar(50)	NULL,
	MovieLength			nvarchar(5)		NULL,
	PicturePath			nvarchar(90)	NULL,
	Link				nvarchar(90)	NULL,
	StartDate			nvarchar(50)	NULL
)
GO

CREATE TABLE MovieDirector
(
	IDMovieDirector	int	CONSTRAINT PK_MovieDirector	 PRIMARY KEY IDENTITY(1,1),
	MovieID			int	CONSTRAINT FK_Movie_Director FOREIGN KEY REFERENCES Movie(IDMovie) NOT NULL,
	DirectorID		int	CONSTRAINT FK_Director_Movie FOREIGN KEY REFERENCES Director(IDDirector) NOT NULL
)
GO

CREATE TABLE MovieActor
(
	IDMovieActor	int	CONSTRAINT PK_MovieActor  PRIMARY KEY IDENTITY(1,1),
	MovieID			int	CONSTRAINT FK_Movie_Actor FOREIGN KEY REFERENCES Movie(IDMovie) NOT NULL,
	ActorID			int	CONSTRAINT FK_Actor_Movie FOREIGN KEY REFERENCES Actor(IDActor) NOT NULL
)
GO

CREATE TABLE MovieGenre
(
	IDMovieGenre	int	CONSTRAINT PK_MovieGenre  PRIMARY KEY IDENTITY(1,1),
	MovieID			int	CONSTRAINT FK_Movie_Genre FOREIGN KEY REFERENCES Movie(IDMovie) NOT NULL,
	GenreID 		int	CONSTRAINT FK_Genre_Movie FOREIGN KEY REFERENCES Genre(IDGenre) NOT NULL
)
GO

									/* INSERT INITIAL VALUES */

INSERT INTO ApplicationUserType (UserType) VALUES
	('Administrator'),
	('User')
GO

INSERT INTO ApplicationUser (Username, Password, ApplicationUserTypeID) VALUES
	('Admin', 'admin', 1),
	('User', 'user', 2)
GO

										/* PROCEDURES */

CREATE PROCEDURE GetApplicationUser
	@Username nvarchar(50),
	@Password nvarchar(50)
AS
BEGIN
	SELECT 
		Username,
		Password,
		ApplicationUserTypeID
	FROM 
		ApplicationUser
	WHERE 
		Username = TRIM(@Username) AND
		Password = TRIM(@Password)
END
GO

CREATE PROCEDURE CreateNewUser
	@Username nvarchar(50),
	@Password nvarchar(50)
AS
BEGIN
	INSERT INTO ApplicationUser (Username, Password, ApplicationUserTypeID) VALUES
		(TRIM(@Username), TRIM(@Password), 2)
END
GO