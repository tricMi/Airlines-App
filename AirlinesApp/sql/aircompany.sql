DROP SCHEMA IF EXISTS aircompany;
CREATE SCHEMA aircompany DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE aircompany;


CREATE TABLE users (
	id BIGINT auto_increment,
	username VARCHAR(30) NOT NULL,
    password VARCHAR(30) NOT NULL,
    registeredDate DATE NOT NULL,
    role ENUM('USER', 'ADMIN') NOT NULL DEFAULT 'USER',
    blockedUser BIT NOT NULL DEFAULT 0,
    active BIT NOT NULL DEFAULT 0,
    PRIMARY KEY (id, username)
);

INSERT INTO users (username, password, registeredDate, role, blockedUser, active) VALUES ('miki', 'miki123', '2019-01-12', 'ADMIN', 0, 0);
INSERT INTO users (username, password, registeredDate, role, blockedUser, active) VALUES ('aco', 'aco', '2019-01-29', 'USER', 0, 0);
INSERT INTO users (username, password, registeredDate, role, blockedUser, active) VALUES ('sanja', 's', '2019-02-01', 'USER', 1, 0);
INSERT INTO users (username, password, registeredDate, role, blockedUser, active) VALUES ('pera', '123', '2019-02-05', 'ADMIN', 1, 0);
INSERT INTO users (username, password, registeredDate, role, blockedUser, active) VALUES ('m', 'm', '2019-02-05', 'ADMIN', 0, 0);

CREATE TABLE airports (
	id BIGINT auto_increment,
    airportName VARCHAR(30) NOT NULL,
    PRIMARY KEY(id)
);

INSERT INTO airports(airportName) VALUES('LAX');
INSERT INTO airports(airportName) VALUES('Nikola Tesla');
INSERT INTO airports(airportName) VALUES('Charles de Gaulle');
INSERT INTO airports(airportName) VALUES('Heathrow');
INSERT INTO airports(airportName) VALUES('Malpensa Airport');

CREATE TABLE flights (
	id BIGINT auto_increment,
    flightNum VARCHAR(10) NOT NULL, 
    departureTime DATETIME NOT NULL,
    arrivalTime DATETIME NOT NULL,
    departures BIGINT NOT NULL,
    arrivals BIGINT NOT NULL,
    seatNum INT NOT NULL DEFAULT 50,
    ticketPrice DECIMAL(10, 2) NOT NULL DEFAULT 99999999.00,
    active BIT NOT NULL DEFAULT 0,
    PRIMARY KEY(id),
    FOREIGN KEY (departures) REFERENCES airports(id) ON DELETE RESTRICT,
    FOREIGN KEY (arrivals) REFERENCES airports(id) ON DELETE RESTRICT
);


INSERT INTO flights(flightNum, departureTime, arrivalTime, departures, arrivals, seatNum, ticketPrice, active) VALUES ('Air98', '2019-01-21 10:40:00', '2019-01-21 14:30:10', 1, 2, 50, 45000, 0); 
INSERT INTO flights(flightNum, departureTime, arrivalTime, departures, arrivals, seatNum, ticketPrice, active) VALUES ('Air70', '2019-01-29 17:22:42', '2019-01-29 23:30:15', 2, 5, 68, 80000, 0); 
INSERT INTO flights(flightNum, departureTime, arrivalTime, departures, arrivals, seatNum, ticketPrice, active) VALUES ('Air55', '2019-02-03 17:22:42', '2019-02-03 23:30:15', 5, 2, 68, 80000, 0); 
INSERT INTO flights(flightNum, departureTime, arrivalTime, departures, arrivals, seatNum, ticketPrice, active) VALUES ('Air36', '2019-02-05 14:22:42', '2019-02-05 22:30:15', 4, 5, 68, 80000, 0); 
INSERT INTO flights(flightNum, departureTime, arrivalTime, departures, arrivals, seatNum, ticketPrice, active) VALUES ('Air50', '2019-02-01 09:15:42', '2019-02-01 13:30:15', 4, 1, 100, 100000, 0);
INSERT INTO flights(flightNum, departureTime, arrivalTime, departures, arrivals, seatNum, ticketPrice, active) VALUES ('Air80', '2019-01-28 17:22:42', '2019-01-28 23:30:15', 2, 1, 70, 90000, 0); 
INSERT INTO flights(flightNum, departureTime, arrivalTime, departures, arrivals, seatNum, ticketPrice, active) VALUES ('Air78', '2019-02-07 07:22:42', '2019-02-07 13:30:15', 5, 4, 85, 80000, 0); 
INSERT INTO flights(flightNum, departureTime, arrivalTime, departures, arrivals, seatNum, ticketPrice, active) VALUES ('Air10', '2019-02-04 18:22:00', '2019-02-04 23:30:00', 1, 4, 78, 93000, 0);   
INSERT INTO flights(flightNum, departureTime, arrivalTime, departures, arrivals, seatNum, ticketPrice, active) VALUES ('Air66', '2019-02-16 10:15:42', '2019-02-16 18:30:15', 2, 1, 150, 48000, 0);
INSERT INTO flights(flightNum, departureTime, arrivalTime, departures, arrivals, seatNum, ticketPrice, active) VALUES ('Air13', '2019-02-14 12:35:22', '2019-02-15 01:30:25', 3, 4, 150, 70000, 0);
INSERT INTO flights(flightNum, departureTime, arrivalTime, departures, arrivals, seatNum, ticketPrice, active) VALUES ('Air00', '2019-02-20 12:35:22', '2019-02-20 01:30:25', 4, 3, 150, 88000, 0);
INSERT INTO flights(flightNum, departureTime, arrivalTime, departures, arrivals, seatNum, ticketPrice, active) VALUES ('Air02', '2019-02-28 08:28:23', '2019-02-28 11:45:27', 3, 4, 99, 90000, 0);
INSERT INTO flights(flightNum, departureTime, arrivalTime, departures, arrivals, seatNum, ticketPrice, active) VALUES ('Air03', '2019-02-25 08:28:23', '2019-02-25 11:45:27', 3, 4, 93, 87000, 0);
INSERT INTO flights(flightNum, departureTime, arrivalTime, departures, arrivals, seatNum, ticketPrice, active) VALUES ('Air51', '2019-02-14 12:35:22', '2019-02-15 01:30:25', 2, 1, 100, 70000, 0);
INSERT INTO flights(flightNum, departureTime, arrivalTime, departures, arrivals, seatNum, ticketPrice, active) VALUES ('Air21', '2019-02-18 13:10:32', '2019-02-18 20:30:49', 1, 2, 110, 93000, 0);
INSERT INTO flights(flightNum, departureTime, arrivalTime, departures, arrivals, seatNum, ticketPrice, active) VALUES ('Air48', '2019-02-15 18:15:22', '2019-02-15 22:30:25', 1, 4, 86, 76000, 0);
INSERT INTO flights(flightNum, departureTime, arrivalTime, departures, arrivals, seatNum, ticketPrice, active) VALUES ('Air85', '2019-02-13 18:15:22', '2019-02-13 22:30:25', 5, 2, 86, 76000, 0);
INSERT INTO flights(flightNum, departureTime, arrivalTime, departures, arrivals, seatNum, ticketPrice, active) VALUES ('Air77', '2019-02-19 14:15:42', '2019-02-19 18:30:15', 5, 1, 100, 74000, 0);
INSERT INTO flights(flightNum, departureTime, arrivalTime, departures, arrivals, seatNum, ticketPrice, active) VALUES ('Air34', '2019-02-27 08:15:42', '2019-02-27 13:30:15', 1, 5, 90, 78000, 0);


CREATE TABLE tickets (
	id BIGINT auto_increment,
    departureFlight BIGINT NOT NULL,
    roundtripFlight BIGINT NULL DEFAULT 0,
    departureSeat INT NOT NULL,
    roundtripSeat INT NULL,
    reservationDateTime DATETIME NULL,
    purchaseDateTime DATETIME NULL,
    username BIGINT NOT NULL,
    firstName VARCHAR(30) NOT NULL,
    surname VARCHAR(30) NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY (departureFlight) REFERENCES flights(id) ON DELETE CASCADE,
    FOREIGN KEY (roundtripFlight) REFERENCES flights(id) ON DELETE CASCADE,
    FOREIGN KEY (username) REFERENCES users(id) ON DELETE CASCADE
);

INSERT INTO tickets(departureFlight, roundtripFlight, departureSeat, roundtripSeat, reservationDateTime, purchaseDateTime, username, firstName, surname) VALUES (3, null, 17, null,null, '2019-01-29 10:05:55', 3, 'Sanja', 'Savic'); -- sold
INSERT INTO tickets(departureFlight, roundtripFlight, departureSeat, roundtripSeat, reservationDateTime, purchaseDateTime, username, firstName, surname) VALUES (2, 3, 13, 66,null, '2019-01-27 15:55:55', 3, 'Sanja', 'Savic'); -- sold
INSERT INTO tickets(departureFlight, roundtripFlight, departureSeat, roundtripSeat, reservationDateTime, purchaseDateTime, username, firstName, surname) VALUES (1, 6, 21, 54, '2019-01-20 12:35:22', null, 2, 'Aleksandar', 'Aleksic');
INSERT INTO tickets(departureFlight, roundtripFlight, departureSeat, roundtripSeat, reservationDateTime, purchaseDateTime, username, firstName, surname) VALUES (18, 19 , 23, 11,null, '2019-02-05 09:05:55', 2, 'Aleksandar', 'Aleksic'); -- sold
INSERT INTO tickets(departureFlight, roundtripFlight, departureSeat, roundtripSeat, reservationDateTime, purchaseDateTime, username, firstName, surname) VALUES (4, null, 9, null,null, '2019-02-01 15:05:55', 3, 'Sanja', 'Savic'); -- sold
INSERT INTO tickets(departureFlight, roundtripFlight, departureSeat, roundtripSeat, reservationDateTime, purchaseDateTime, username, firstName, surname) VALUES (7, null, 1, null,'2019-02-06 15:05:55', null, 3, 'Sanja', 'Savic');
INSERT INTO tickets(departureFlight, roundtripFlight, departureSeat, roundtripSeat, reservationDateTime, purchaseDateTime, username, firstName, surname) VALUES (5, null, 4, null,'2019-01-31 15:05:55', null, 2, 'Aleksandar', 'Savic');
INSERT INTO tickets(departureFlight, roundtripFlight, departureSeat, roundtripSeat, reservationDateTime, purchaseDateTime, username, firstName, surname) VALUES (10, null, 33, null, null, '2019-02-10 15:05:55', 4, 'Pera', 'Peric'); -- sold
INSERT INTO tickets(departureFlight, roundtripFlight, departureSeat, roundtripSeat, reservationDateTime, purchaseDateTime, username, firstName, surname) VALUES (16, null, 34, null, '2019-02-10 15:05:55', null, 5, 'm', 'm'); 
INSERT INTO tickets(departureFlight, roundtripFlight, departureSeat, roundtripSeat, reservationDateTime, purchaseDateTime, username, firstName, surname) VALUES (17, null, 65, null, '2019-02-10 15:05:55', null, 4, 'm', 'm'); 