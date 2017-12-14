--
-- File generated with SQLiteStudio v3.1.1 on Wed Jul 26 15:17:52 2017
--
-- Text encoding used: System
--
PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

-- Table: Passwords
CREATE TABLE Passwords (Username VARCHAR (3, 20) PRIMARY KEY NOT NULL, Password VARCHAR (8, 20) NOT NULL, RegNo INT (10));

COMMIT TRANSACTION;
PRAGMA foreign_keys = on;
