
CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- Setting up Project Tables

-- Quick Clear of all tables, ordered so each runs without conflict
DROP TABLE Enrollments CASCADE;
DROP TABLE Students CASCADE;
DROP TABLE Prerequisites CASCADE;
DROP TABLE Sections CASCADE;
DROP TABLE Courses CASCADE;
DROP TABLE Sessions CASCADE;
DROP TABLE Departments CASCADE;
DROP TABLE Professors CASCADE;

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


-- Professors - Will be able to modify classes they are assigned too and drop students / assign grades
CREATE TABLE Professors (
    employeeId SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    dept CHAR(5),
    email TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL,
    CONSTRAINT fk_department
                        FOREIGN KEY (dept)
                        REFERENCES Departments(abbr)

);

-- Students
CREATE TABLE Students (
    studentId SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    email TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL,
    birthdate DATE,
    major TEXT,
    minor TEXT,
    gpa REAL,
    unitsCompleted INT
);



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

CREATE OR REPLACE FUNCTION current_session ()
    RETURNS INTEGER AS
    'SELECT sessionId FROM Sessions
    WHERE Sessions.firstDay <= current_date
    AND Sessions.lastDay >= current_date'
    LANGUAGE SQL
    RETURNS NULL ON NULL INPUT;

-- Enrollments
CREATE TABLE Enrollments (
                             sectionid INT,
                             student INT,
                             CONSTRAINT p_key
                                 PRIMARY KEY (sectionid, student),
                             CONSTRAINT fk_course
                                 FOREIGN KEY (sectionid)
                                     REFERENCES Sections(sectionCode),
                             CONSTRAINT fk_student
                                 FOREIGN KEY (student)
                                     REFERENCES Students(studentId)
);