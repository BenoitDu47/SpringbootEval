-- ------------------------------------------------------------------------------
-- - Gestion des droits d'accès                                     ---
-- ------------------------------------------------------------------------------
USE Stock;

-- -----------------------------------------------------------------------------
-- - Construction de la table des Users                               ---
-- -----------------------------------------------------------------------------
CREATE TABLE T_Users (
	username			varchar(25)		PRIMARY KEY,
	password			varchar(250),
	active				boolean
) ENGINE = InnoDB;

INSERT INTO T_Users (username, password, active) VALUES ( 'benoit',  '$2a$12$A.1omyeduJjn9BulU5TVxuLmvfC6FFiqUQieW2Y8Nc2xGwr44p5N2' , '1' );	-- pwd 12345
INSERT INTO T_Users (username, password, active) VALUES ( 'toto',  '$2a$12$/FxJ4RIYiIcO8eZp6wT.1e54T9q5uk4HVtHmUteGZqW2XGKs0RMRm' , '1' );

SELECT * FROM T_Users;

-- -----------------------------------------------------------------------------
-- - Construction de la table avec 2 Roles principaux                        ---
-- -----------------------------------------------------------------------------
CREATE TABLE T_Roles (
	role 		varchar(25)		PRIMARY KEY
) ENGINE = InnoDB;

INSERT INTO T_Roles (role) VALUES ('ADMIN');
INSERT INTO T_Roles (role) VALUES ('USER');

-- -----------------------------------------------------------------------------
-- - Construction de la table des rôles par utilisateur	                     ---
-- -----------------------------------------------------------------------------
CREATE TABLE T_Users_Roles (
	username 		varchar(25),
	role 			varchar(25),
	PRIMARY KEY(username,role)
) ENGINE = InnoDB;

INSERT INTO T_Users_Roles (username,role) VALUES ('benoit','ADMIN');
INSERT INTO T_Users_Roles (username,role) VALUES ('benoit','USER');
INSERT INTO T_Users_Roles (username,role) VALUES ('toto','USER');

