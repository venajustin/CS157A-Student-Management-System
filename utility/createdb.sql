
CREATE TABLE test1 (
    count INT,
    time TIME
);

INSERT INTO test1
    VALUES (1, CURRENT_TIME);

SELECT * FROM test1;


-- Setting up Project Tables

-- Quick Clear of all tables, ordered so each runs without conflict
DROP TABLE Enrollments;
DROP TABLE Teaches;
DROP TABLE Students;
DROP TABLE Professors;
DROP TABLE Faculty;
DROP TABLE Prerequisites;
DROP TABLE Sections;
DROP TABLE Courses;
DROP TABLE Sessions;
DROP TABLE Departments;



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
    name TEXT,
    description TEXT,
    units INTEGER,
    CONSTRAINT id_abbrev_number
                     PRIMARY KEY (dept, number),
    CONSTRAINT fk_department
                     FOREIGN KEY (dept)
                     REFERENCES Departments(abbr)
);

-- Sections
CREATE TABLE Sections (
    dept CHAR(5),
    course INT,
    session INT NOT NULL,
    -- Monday/Thursday = 'MR', Sunday/Monday/Tuesday/Wednesday/Thursday/Friday/Saturday = 'UMTWRFS'
    days CHAR(7),
    startTime TIME,
    endTime TIME,
    CONSTRAINT fk_pk_course
                      FOREIGN KEY (dept, course)
                      REFERENCES Courses(dept, number),
    CONSTRAINT fk_session
                      FOREIGN KEY (session)
                      REFERENCES Sessions(sessionId),
    CONSTRAINT valid_time
                      CHECK (startTime < endTime)
);

-- Prerequisites
CREATE TABLE Prerequisites (
    id SERIAL PRIMARY KEY,
    courseId INT,
    requiresId INT,
    CONSTRAINT 
);

--Faculty


-- Professors
CREATE TABLE Professors (
    employeeId SERIAL PRIMARY KEY,
    name TEXT,
    dept CHAR(5),
    email TEXT,
    CONSTRAINT fk_department
                        FOREIGN KEY (dept)
                        REFERENCES Departments(abbr)

);



-- Students
-- Teaches
-- Enrollments
