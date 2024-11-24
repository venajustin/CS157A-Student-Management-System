
CREATE TABLE test1 (
    count INT,
    time TIME
);

INSERT INTO test1
    VALUES (1, CURRENT_TIME);

SELECT * FROM test1;


SELECT value FROM test1 ORDER BY test1.time DESC LIMIT 1;
