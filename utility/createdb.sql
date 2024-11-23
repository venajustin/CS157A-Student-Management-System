
CREATE TABLE test1 (
    count INT,
    time TIME
);

INSERT INTO test1
    VALUES (1, CURRENT_TIME);

SELECT * FROM test1;
