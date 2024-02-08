CREATE TABLE Users (
	ID SERIAL PRIMARY KEY,
	Username varchar NOT NULL UNIQUE,
	Password varchar NOT NULL,
	salt varchar NOT NULL
);

CREATE TYPE Product_class as enum ('Fruit', 'Vegetable', 'Berry');

CREATE TABLE Product_sorts (
	ID SERIAL PRIMARY KEY,
	status Product_class,
	Product_type_id integer NOT NULL,
	Product_sort varchar NOT NULL,
	Num_of_comments integer NOT NULL,
	Num_of_grades integer NOT NULL,
	Mean_grade double precision NOT NULL
);

CREATE TABLE Product_types (
	ID SERIAL PRIMARY KEY,
	Product_type varchar NOT NULL
);

CREATE TABLE Product_grades (
	ID SERIAL PRIMARY KEY,
	User_id integer NOT NULL,
	Product_sort_id integer NOT NULL,
	Grade bytea NOT NULL
);

CREATE TABLE Product_comments (
	ID SERIAL PRIMARY KEY,
	User_id integer NOT NULL,
	Product_sort integer NOT NULL,
	Comment text NOT NULL,
	Date_time TIMESTAMP NOT NULL
);

DROP TABLE Product_comments

ALTER TABLE Product_sorts ADD CONSTRAINT Sorts_Types FOREIGN KEY (Product_type_id) REFERENCES Product_types (ID);

ALTER TABLE Product_grades ADD CONSTRAINT Grades_Users FOREIGN KEY (User_id) REFERENCES Users (ID);

ALTER TABLE Product_grades ADD CONSTRAINT Grades_Sorts FOREIGN KEY (Product_sort_id) REFERENCES Product_sorts (ID);

ALTER TABLE Product_comments ADD CONSTRAINT Comments_Users FOREIGN KEY (User_id) REFERENCES Users (ID);

ALTER TABLE Product_comments ADD CONSTRAINT Comments_Sorts FOREIGN KEY (Product_sort) REFERENCES Product_sorts (ID);





