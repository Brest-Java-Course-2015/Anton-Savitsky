SET DATABASE SQL SYNTAX ORA TRUE;
DROP TABLE CAR IF EXISTS;
CREATE TABLE CAR (
    carId INT IDENTITY primary key NOT NULL,
    carName VARCHAR(30) NOT NULL,
    producer_producer_id INT NOT NULL,
    dateOfCreation date NOT NULL
);