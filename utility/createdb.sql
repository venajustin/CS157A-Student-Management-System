
-- Encription extension, used for passwords
CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- Setting up Project Tables

-- Clear all Procedures, views, ect
DROP FUNCTION grade_letter ( grade REAL );
--DROP FUNCTION new_account;
--DROP TRIGGER t_acc_insert ON Accounts;
--DROP VIEW Accounts;

-- Clear all tables
DROP TABLE Enrollments ;
DROP TABLE Prerequisites ;
DROP TABLE Sections ;
DROP TABLE Courses ;
DROP TABLE Sessions ;
DROP TABLE Professors ;
DROP TABLE Students ;
DROP TABLE Departments ;
DROP TABLE ACCOUNTS ;


-- Departments
CREATE TABLE Departments (
    abbr CHAR(5) PRIMARY KEY,
    name TEXT NOT NULL
);

-- Classes
CREATE TABLE Sessions (
    sessionId SERIAL PRIMARY KEY,
    firstDay DATE,
    lastDay DATE,
    season char(6),
    CONSTRAINT valid_days
                      CHECK (firstDay < lastDay)
);

-- Courses
CREATE TABLE Courses (
    number INT,
    dept CHAR(5),
    name TEXT NOT NULL,
    description TEXT,
    units INTEGER,
    CONSTRAINT id_abbrev_number
                     PRIMARY KEY (dept, number),
    CONSTRAINT fk_department
                     FOREIGN KEY (dept)
                     REFERENCES Departments(abbr)
);

-- Prerequisites
CREATE TABLE Prerequisites (
    id SERIAL PRIMARY KEY,
    courseDept CHAR(5),
    courseNum INT,
    requiresDept CHAR(5),
    requiresNum INT,
    CONSTRAINT fk_course
                           FOREIGN KEY (courseDept, courseNum)
                           REFERENCES Courses(dept, number),
    CONSTRAINT fk_require
                           FOREIGN KEY (requiresDept, requiresNum)
                           REFERENCES Courses(dept, number)
);

-- Index on prerequisites because there will be a lot of recursive prerequisite checks that will look up by course dept + number often
CREATE INDEX IF NOT EXISTS ind_course ON
    Prerequisites (courseDept, courseNum);

CREATE TABLE Accounts (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    email TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL,
    account_type TEXT NOT NULL DEFAULT 'student'
);

-- Professors - Will be able to modify classes they are assigned too and drop students / assign grades
CREATE TABLE Professors (
    employeeId INT UNIQUE,
    dept CHAR(5),
    CONSTRAINT fk_department
                        FOREIGN KEY (dept)
                        REFERENCES Departments(abbr),
    CONSTRAINT fk_id
        FOREIGN KEY (employeeId)
        REFERENCES Accounts(id)
);

-- Students
CREATE TABLE Students (
    studentId INT UNIQUE,
    major TEXT,
    CONSTRAINT fk_id
        FOREIGN KEY (studentId)
            REFERENCES Accounts(id)
);



CREATE OR REPLACE FUNCTION current_session ()
    RETURNS INTEGER AS
    'SELECT sessionId FROM Sessions
    WHERE Sessions.firstDay <= current_date
    AND Sessions.lastDay >= current_date'
    LANGUAGE SQL
    RETURNS NULL ON NULL INPUT;


-- Sections
CREATE TABLE Sections (
                          sectionCode SERIAL PRIMARY KEY,
                          dept CHAR(5),
                          course INT,
                          session INT NOT NULL,
    -- Monday/Thursday = 'MR', Sunday/Monday/Tuesday/Wednesday/Thursday/Friday/Saturday = 'UMTWRFS'
                          days CHAR(7),
                          startTime TIME,
                          endTime TIME,
                          teacher INT,
                          CONSTRAINT fk_pk_course
                              FOREIGN KEY (dept, course)
                                  REFERENCES Courses(dept, number),
                          CONSTRAINT fk_session
                              FOREIGN KEY (session)
                                  REFERENCES Sessions(sessionId),
                          CONSTRAINT valid_time
                              CHECK (startTime < endTime),
                          CONSTRAINT fk_teacher
                              FOREIGN KEY (teacher)
                                  REFERENCES Professors(employeeId)
);


-- Enrollments
CREATE TABLE Enrollments (
     sectionid INT,
     student INT,
     grade REAL,
     CONSTRAINT p_key
         PRIMARY KEY (sectionid, student),
     CONSTRAINT fk_course
         FOREIGN KEY (sectionid)
             REFERENCES Sections(sectionCode),
     CONSTRAINT fk_student
         FOREIGN KEY (student)
             REFERENCES Students(studentId),
     CONSTRAINT grade_range
        CHECK (grade >= 0 AND grade <= 4.0)
);


-- DEPRECIATED for accounts table
-----------------------------------------------------------------
-- -- Accounts view is used for all signup/login purposes
-- CREATE VIEW Accounts AS
--     SELECT
--         studentid AS id, name AS name, email, password, 'student' AS account_type
--         FROM students
--     UNION
--     SELECT
--         employeeId AS id, name AS name, email, password, 'professor' AS account_type
--         FROM professors;
--
--  CREATE OR REPLACE FUNCTION new_account () RETURNS TRIGGER
--      AS $$
--      BEGIN
--          IF NEW.account_type = 'student' THEN
--             INSERT INTO Students (name, email, password) VALUES
--                                  (NEW.name, NEW.email, crypt(NEW.password, gen_salt('bf')));
--         END IF;
--         IF NEW.account_type = 'professor' THEN
--             INSERT INTO Professors (name, email, password) VALUES
--                 (NEW.name, NEW.email, crypt(NEW.password, gen_salt('bf')));
--         END IF;
--         RETURN NEW;
--     END;
--     $$ LANGUAGE plpgsql;
--
-- CREATE TRIGGER t_acc_insert
--     INSTEAD OF INSERT ON Accounts
--     FOR EACH ROW
--     EXECUTE FUNCTION new_account();


CREATE FUNCTION grade_letter ( grade REAL )
    RETURNS CHAR(2)
    AS $$
    BEGIN
        IF grade > 4 OR grade < 0 THEN
            return ' ';
        end if;
        IF grade = 4 THEN
            return 'A';
        end if;
        IF grade >= 3 THEN
            return 'B';
        end if;
        IF grade >= 2 THEN
            return 'C';
        end if;
        IF grade >= 1 THEN
            return 'D';
        end if;
        IF grade >= 0 THEN
            return 'F';
        end if;
        return '-';
    END;
    $$ LANGUAGE plpgsql;
