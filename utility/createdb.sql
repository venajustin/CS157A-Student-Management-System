
CREATE TABLE test1 (
    count INT,
    time TIME
);

INSERT INTO test1
    VALUES (1, CURRENT_TIME);

SELECT * FROM test1;

SELECT count FROM test1 ORDER BY test1.time DESC LIMIT 1;


-- DROP TABLE test1;

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
    name TEXT NOT NULL,
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

--Faculty - Will be able to modify all classes and assign professors
CREATE TABLE Faculty (
    employeeId SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    email TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL
);

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

-- Teaches - Maps teachers to the classes they teach
CREATE TABLE Teaches (
   id SERIAL PRIMARY KEY,
   courseDept CHAR(5),
   courseNum INT,
   teacher INT,
   CONSTRAINT fk_course
       FOREIGN KEY (courseDept, courseNum)
           REFERENCES Courses(dept, number),
   CONSTRAINT fk_teacher
        FOREIGN KEY (teacher)
        REFERENCES Professors(employeeId),
    CONSTRAINT one_instructor -- Remove this to allow for multiple teachers to teach one class
        UNIQUE (courseDept, courseNum)
);

-- Enrollments
CREATE TABLE Enrollments (
    id SERIAL PRIMARY KEY,
    courseDept CHAR(5),
    courseNum INT,
    student INT,
    CONSTRAINT fk_course
         FOREIGN KEY (courseDept, courseNum)
         REFERENCES Courses(dept, number),
    CONSTRAINT fk_student
        FOREIGN KEY (student)
        REFERENCES Students(studentId)
);
