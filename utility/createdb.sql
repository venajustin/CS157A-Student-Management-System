CREATE DATABASE StudentManagementSystem
    WITH OWNER smsdeveloper;

CREATE SCHEMA testing;

DROP TABLE testing.test1;
CREATE TABLE testing.test1 (
    count INT,
    time TIME
);

INSERT INTO testing.test1
    VALUES (1, CURRENT_TIME);

SELECT * FROM testing.test1;