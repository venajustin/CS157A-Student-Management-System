
-- Populate Departments
INSERT INTO departments VALUES ('MATH', 'Mathmatics');
INSERT INTO departments VALUES ('CS', 'Computer Science');
INSERT INTO departments VALUES ('CMPE', 'Computer Engineering');
INSERT INTO departments VALUES ('SE', 'Software Engineering');
INSERT INTO departments VALUES ('EE', 'Electrical Engineering');
INSERT INTO departments VALUES ('ME', 'Mechanical Engineering');
INSERT INTO departments VALUES ('COMM', 'Communications');
INSERT INTO departments VALUES ('PHYS', 'Physics and Astronomy');
INSERT INTO departments VALUES ('ENGR', 'General Engineering');
INSERT INTO departments VALUES ('AFAM', 'African American Studies');
INSERT INTO departments VALUES ('ISE', 'Industrial Engineering');


-- Populate Courses
INSERT INTO courses VALUES ('130', 'ISE', 'Engineering Statistics', 'Introduction to statistical analysys and probability distributions', 3);
INSERT INTO courses VALUES ('120', 'CMPE', 'Computer Organization and Architecture', 'CPU Architecture and design. Cache hierarchy, Multithreading and RISC-V assembly', 3);
INSERT INTO courses VALUES ('102', 'CMPE', 'Assebly Language Programming', 'Assembly language. Fundamentals of static compilation of C source code.', 4);

-- Add an ongoing session ( this current season )
INSERT INTO sessions VALUES (default, to_date('7/15/2024', 'MM/DD/YYYY'), to_date('12/30/2024', 'MM/DD/YYYY'), 'FALL');

-- Add a default teacher
INSERT INTO professors VALUES (default, 'Smith', 'ISE', 'smith@school.edu', crypt('12345qwert', gen_salt('bf')));

-- Populate Sections
INSERT INTO sections VALUES (default, 'ISE', 130, current_session(), 'MW', '10:30:00', '13:00:00', (SELECT employeeid from professors where name LIKE 'Smith'));
INSERT INTO sections VALUES (default, 'ISE', 130, current_session(), 'MW', '12:15:00', '14:30:00', (SELECT employeeid from professors where name LIKE 'Smith'));

