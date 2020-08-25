CREATE DATABASE [PUJ1-Database]
GO

USE [PUJ1-Database]
GO

										/* CREATING TABLES */

CREATE TABLE ApplicationUserType
(
	IDApplicationUserType		int	CONSTRAINT PK_ApplicationUserType PRIMARY KEY IDENTITY(1,1),
	UserType					nvarchar(25)
)
GO

CREATE TABLE ApplicationUser
(
	IDAplicationUser		int				CONSTRAINT PK_ApplicationUSER PRIMARY KEY IDENTITY(1,1),
	Username				nvarchar(50)	CONSTRAINT UQ_Username UNIQUE NOT NULL,
	Password				nvarchar(20)	NOT NULL,
	ApplicationUserTypeID	int				CONSTRAINT FK_ApplicationUser_ApplicationUserType
		FOREIGN KEY REFERENCES ApplicationUserType(IDApplicationUserType) NOT NULL
)
GO

CREATE TABLE Actor
(
	IDActor		int				CONSTRAINT PK_Actor PRIMARY KEY IDENTITY(1,1),
	Fullname	nvarchar(100)	CONSTRAINT UQ_Actor_Fullname UNIQUE NOT NULL
)
GO

CREATE TABLE Director
(
	IDDirector	int				CONSTRAINT PK_Director PRIMARY KEY IDENTITY(1,1),
	Fullname	nvarchar(100)	CONSTRAINT UQ_Diretor_Fullname UNIQUE NOT NULL
)
GO

CREATE TABLE Genre
(
	IDGenre		int				CONSTRAINT PK_Genre	PRIMARY KEY IDENTITY(1,1),
	GenreName	nvarchar(50)	CONSTRAINT UQ_GenreName UNIQUE NOT NULL
)
GO

CREATE TABLE Movie
(
	IDMovie				int				CONSTRAINT PK_Movie	PRIMARY KEY IDENTITY(1,1),
	Title				nvarchar(75)	NOT NULL,
	PublishedDate		nvarchar(25)	NOT NULL,
	MovieDescription	nvarchar(max)	NULL,
	OriginalName		nvarchar(75)	NULL,
	MovieLength			nvarchar(5)		NULL,
	PicturePath			nvarchar(100)	NULL,
	Link				nvarchar(max)	NULL,
	StartDate			nvarchar(15)	NULL
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

CREATE PROCEDURE clearMovies
AS
BEGIN
	DELETE FROM MovieGenre
	DELETE FROM Genre

	DELETE FROM MovieDirector
	DELETE FROM Director

	DELETE FROM MovieActor
	DELETE FROM Actor
	
	DELETE FROM Movie
END
GO

CREATE PROCEDURE CreateMovies
	@Title nvarchar(75),
	@PublishedDate nvarchar(25),
	@Description nvarchar(max),
	@OriginalName nvarchar(75),
	@Directors nvarchar(max),
	@Actors nvarchar(max),
	@Length nvarchar(5),
	@Genre nvarchar(max),
	@PicturePath nvarchar(100),
	@Link nvarchar(max),
	@StartDate nvarchar(15)
AS
BEGIN
	-- in case some of the data is "bigger than" nvarchar(max) 
	SET ANSI_WARNINGS  OFF;
	
	--------------------------------------------------------------------------------------------------------------------------
	INSERT INTO Movie (Title, PublishedDate, MovieDescription, OriginalName, MovieLength, PicturePath, Link, StartDate) VALUES
	(
		TRIM(@Title), 
		TRIM(@PublishedDate),
		TRIM(@Description),
		TRIM(@OriginalName),
		TRIM(@Length), 
		TRIM(@PicturePath),
		TRIM(@Link), 
		TRIM(@StartDate)
	)
	DECLARE @IDMovie int = SCOPE_IDENTITY()

	--------------------------------------------------------------------------------------------------------------------------
	INSERT INTO Genre (GenreName) 
	SELECT TRIM(value) FROM string_split(@Genre, ',') WHERE RTRIM(value) <> ''

	INSERT INTO Director(Fullname) 
	SELECT TRIM(value) FROM string_split(@Directors, ',') WHERE RTRIM(value) <> ''

	INSERT INTO Actor(Fullname) 
	SELECT TRIM(value) FROM string_split(@Actors, ',') WHERE RTRIM(value) <> ''
	
	--------------------------------------------------------------------------------------------------------------------------
	INSERT INTO MovieGenre (MovieID, GenreID)
	SELECT @IDMovie, IDGenre from Genre where GenreName IN (select TRIM(value) from string_split(@Genre, ',') WHERE RTRIM(value) <> '')

	INSERT INTO MovieDirector (MovieID, DirectorID)
	SELECT @IDMovie, IDDirector from Director where Fullname IN (select TRIM(value) from string_split(@Directors, ',') WHERE RTRIM(value) <> '')

	INSERT INTO MovieActor (MovieID, ActorID)
	SELECT @IDMovie, IDActor from Actor where Fullname IN (select TRIM(value) from string_split(@Actors, ',') WHERE RTRIM(value) <> '')
	
	--------------------------------------------------------------------------------------------------------------------------
	SET ANSI_WARNINGS ON;
END
GO

CREATE PROCEDURE CreateMovie
	@Title nvarchar(75),
	@PublishedDate nvarchar(25),
	@Description nvarchar(max),
	@OriginalName nvarchar(75),
	@Length nvarchar(5),
	@PicturePath nvarchar(100),
	@Link nvarchar(max),
	@StartDate nvarchar(15)
AS
BEGIN
	INSERT INTO Movie (Title, PublishedDate, MovieDescription, OriginalName, MovieLength, PicturePath, Link, StartDate) VALUES
	(
		TRIM(@Title), 
		TRIM(@PublishedDate),
		TRIM(@Description),
		TRIM(@OriginalName),
		TRIM(@Length), 
		TRIM(@PicturePath),
		TRIM(@Link), 
		TRIM(@StartDate)
	)
END
GO

CREATE PROCEDURE selectMovies
AS
BEGIN
	SELECT
		IDMovie,
		Title,
		PublishedDate,
		MovieDescription,
		OriginalName,
		MovieLength,
		PicturePath,
		Link,
		StartDate
	FROM Movie
END
GO

CREATE PROCEDURE selectMovie
	@IDMovie int
AS
BEGIN
	SELECT
		IDMovie,
		Title,
		PublishedDate,
		MovieDescription,
		OriginalName,
		MovieLength,
		PicturePath,
		Link,
		StartDate
	FROM Movie
	WHERE
		IDMovie = @IDMovie
END
GO

CREATE PROCEDURE UpdateMovie
	@IDMovie int,
	@Title nvarchar(75),
	@PublishedDate nvarchar(25),
	@Description nvarchar(max),
	@OriginalName nvarchar(75),
	@Length nvarchar(5),
	@PicturePath nvarchar(100),
	@Link nvarchar(max),
	@StartDate nvarchar(15)
AS
BEGIN
	UPDATE
		Movie
	SET
		Title = @Title,
		PublishedDate = @PublishedDate,
		MovieDescription = @Description,
		OriginalName = @OriginalName,
		MovieLength = @Length,
		PicturePath = @PicturePath,
		Link = @Link,
		StartDate = @StartDate
	WHERE
		IDMovie = @IDMovie
END
GO

CREATE PROCEDURE DeleteMovie
	@IDMovie int
AS
BEGIN
	DELETE FROM
		MovieDirector
	WHERE
		MovieID = @IDMovie

	DELETE FROM
		MovieActor
	WHERE
		MovieID = @IDMovie

	DELETE FROM
		MovieGenre
	WHERE
		MovieID = @IDMovie
	
	DELETE FROM
		Movie
	WHERE
		IDMovie = @IDMovie
END
GO

CREATE PROCEDURE SelectActors
AS
BEGIN
	SELECT 
		a.IDActor,
		TRIM(left(a.Fullname, charindex(' ', a.Fullname))) as Firstname,
		TRIM(SUBSTRING(a.Fullname, LEN(left(a.Fullname, charindex(' ', a.Fullname))) + 1, 100)) as Lastname
	FROM Actor as a
END
GO

CREATE PROCEDURE SelectActorMovies
	@IDActor int
AS
BEGIN
	SELECT
		m.IDMovie,
		m.Title,
		m.PublishedDate,
		m.MovieDescription,
		m.OriginalName,
		m.MovieLength,
		m.PicturePath,
		m.Link,
		m.StartDate
	FROM Movie as m
	inner join MovieActor as ma
		on m.IDMovie = ma.MovieID
	inner join Actor as a
		on a.IDActor = ma.ActorID
	WHERE a.IDActor = @IDActor
END
GO

CREATE PROCEDURE selectActor
	@IDActor int
AS
BEGIN
	SELECT
		a.IDActor,
		TRIM(left(a.Fullname, charindex(' ', a.Fullname))) as Firstname,
		TRIM(SUBSTRING(a.Fullname, LEN(left(a.Fullname, charindex(' ', a.Fullname))) + 1, 100)) as Lastname
	FROM Actor as a
	WHERE
		a.IDActor = @IDActor
END
GO

CREATE PROCEDURE CreateActor
	@Firstname nvarchar(50),
	@Lastname nvarchar(50)
AS
BEGIN
	INSERT INTO Actor (Fullname) VALUES
		(@Firstname + ' ' + @Lastname)
END
GO

CREATE PROCEDURE UpdateActor
	@IDActor int,
	@Firstname nvarchar(50),
	@Lastname nvarchar(50)
AS
BEGIN
	UPDATE 
		Actor
	SET
		Fullname = @Firstname + ' ' + @Lastname
	WHERE
		IDActor = @IDActor
END
GO

CREATE PROCEDURE DeleteActor
	@IDActor int
AS
BEGIN
	DELETE FROM MovieActor
		WHERE ActorID = @IDActor

	DELETE FROM Actor
		WHERE IDActor = @IDActor
END
GO

CREATE PROCEDURE CreateMovieActor
	@IDMovie int,
	@IDActor int
AS
BEGIN
	INSERT INTO MovieActor (MovieID, ActorID) VALUES
		(@IDMovie, @IDActor)
END
GO

CREATE PROCEDURE DeleteMovieActor
	@IDMovie int,
	@IDActor int
AS
BEGIN
	DELETE FROM MovieActor
		WHERE ActorID = @IDActor AND
			  MovieID = @IDMovie
END
GO

CREATE PROCEDURE SelectDirectors
AS
BEGIN
	SELECT 
		d.IDDirector,
		TRIM(left(d.Fullname, charindex(' ', d.Fullname))) as Firstname,
		TRIM(SUBSTRING(d.Fullname, LEN(left(d.Fullname, charindex(' ', d.Fullname))) + 1, 100)) as Lastname
	FROM Director as d
END
GO

CREATE PROCEDURE SelectDirectorMovies
	@IDDirector int
AS
BEGIN
	SELECT
		m.IDMovie,
		m.Title,
		m.PublishedDate,
		m.MovieDescription,
		m.OriginalName,
		m.MovieLength,
		m.PicturePath,
		m.Link,
		m.StartDate
	FROM Movie as m
	inner join MovieDirector as md
		on m.IDMovie = md.MovieID
	inner join Director as d
		on d.IDDirector = md.DirectorID
	WHERE d.IDDirector = @IDDirector
END
GO

CREATE PROCEDURE selectDirector
	@IDDirector int
AS
BEGIN
	SELECT
		d.IDDirector,
		TRIM(left(d.Fullname, charindex(' ', d.Fullname))) as Firstname,
		TRIM(SUBSTRING(d.Fullname, LEN(left(d.Fullname, charindex(' ', d.Fullname))) + 1, 100)) as Lastname
	FROM Director as d
	WHERE
		d.IDDirector = @IDDirector
END
GO

CREATE PROCEDURE CreateDirector
	@Firstname nvarchar(50),
	@Lastname nvarchar(50)
AS
BEGIN
	INSERT INTO Director(Fullname) VALUES
		(@Firstname + ' ' + @Lastname)
END
GO

CREATE PROCEDURE UpdateDirector
	@IDDirector int,
	@Firstname nvarchar(50),
	@Lastname nvarchar(50)
AS
BEGIN
	UPDATE 
		Director
	SET
		Fullname = @Firstname + ' ' + @Lastname
	WHERE
		IDDirector = @IDDirector
END
GO

CREATE PROCEDURE DeleteDirector
	@IDDirector int
AS
BEGIN
	DELETE FROM MovieDirector
		WHERE DirectorID = @IDDirector

	DELETE FROM Director
		WHERE IDDirector = @IDDirector
END
GO

CREATE PROCEDURE CreateMovieDirector
	@IDMovie int,
	@IDDirector int
AS
BEGIN
	INSERT INTO MovieDirector(MovieID, DirectorID) VALUES
		(@IDMovie, @IDDirector)
END
GO

CREATE PROCEDURE DeleteMovieDirector
	@IDMovie int,
	@IDDirector int
AS
BEGIN
	DELETE FROM MovieDirector
		WHERE DirectorID = @IDDirector AND
			  MovieID = @IDMovie
END
GO