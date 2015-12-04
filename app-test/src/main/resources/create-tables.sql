SET DATABASE SQL SYNTAX ORA TRUE;
DROP TABLE CAR IF EXISTS;
DROP TABLE PRODUCER IF EXISTS;

CREATE TABLE PRODUCER (
    producerId INT IDENTITY primary key NOT NULL,
    producerName VARCHAR(30) NOT NULL,
    country VARCHAR(30) NOT NULL
);

CREATE TABLE CAR (
    carId INT IDENTITY primary key NOT NULL,
    carName VARCHAR(30) NOT NULL,
    producer_producer_id INT NOT NULL,
    dateOfCreation date NOT NULL,
    FOREIGN KEY (producer_producer_id) REFERENCES PRODUCER(producerId)
);


